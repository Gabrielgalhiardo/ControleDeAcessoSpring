package com.senai.controle_de_acesso_spring.application.service.turma;

import com.senai.controle_de_acesso_spring.application.dto.turma.SubTurmaDTO;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.Semestre;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.SubTurma;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.Turma;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.HorarioPadrao;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Aluno;
import com.senai.controle_de_acesso_spring.domain.repository.turma.SubTurmaRepository;
import com.senai.controle_de_acesso_spring.domain.repository.turma.TurmaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubTurmaService {

    @Autowired
    private SubTurmaRepository subTurmaRepository;

    @Autowired
    private TurmaRepository turmaRepository;


    @Transactional
    public void criarSubTurma(Long turmaId) {
        Turma turma = turmaRepository.findById(turmaId)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        SubTurma subTurma = new SubTurma();
        subTurma.setNome("Turma "+subTurma.getTurma().getSubTurmas().size());
        subTurma.setTurma(turma);

        turma.getSubTurmas().add(subTurma);

        Semestre semestre = new Semestre();
        subTurma.getSemestres().add(semestre);

        semestre.setNumero(subTurma.getSemestres().size());
        semestre.setNomeDaTurma(
                subTurma.getSemestres().size() +
                        subTurma.getTurma().getSiglaDaTurma() +
                        subTurma.getTurma().getPeriodo().getSigla()
        );
        semestre.setSubTurma(subTurma);

        // Criar HorarioPadrao vazio
//        HorarioPadrao horarioPadrao = horarioService.criarHorarioPadraoVazio(semestre);
//        semestre.setHorarioPadrao(horarioPadrao);

        semestre.setHorariosSemanais(new ArrayList<>());
        subTurma.setAlunos(new ArrayList<>());

        subTurmaRepository.save(subTurma);
    }

    public List<SubTurmaDTO> listar() {
        return subTurmaRepository.findAll().stream()
                .map(SubTurmaDTO::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<SubTurmaDTO> buscarPorId(Long id) {
        return subTurmaRepository.findById(id).map(SubTurmaDTO::toDTO);
    }

    @Transactional
    public boolean atualizar(Long id, SubTurmaDTO dto) {
        Optional<SubTurma> optional = subTurmaRepository.findById(id);
        if (optional.isEmpty()) return false;

        SubTurma subTurma = optional.get();
        subTurma.setNome(dto.nome());

        subTurmaRepository.save(subTurma);
        return true;
    }

    @Transactional
    public boolean deletar(Long id) {
        if (!subTurmaRepository.existsById(id)) return false;
        subTurmaRepository.deleteById(id);
        return true;
    }

    public static SubTurma pegarSubTurmaAtual(Aluno aluno){
        LocalTime horarioAtual = LocalTime.now();
        for (SubTurma subTurma : aluno.getSubTurmas()){
            LocalTime horarioEntrada = subTurma.getTurma().getHorarioEntrada();
            int minutosPorAula = subTurma.getTurma().getCurso().getTipoDeCurso().getMinutosPorAula();
            int minutosPorIntervalo = subTurma.getTurma().getCurso().getTipoDeCurso().getIntevarloMinutos();
            int quantidadeDeAulasPorDia = subTurma.getTurma().getQtdAulasPorDia();
            LocalTime horarioDeSaida = horarioEntrada.plusMinutes((minutosPorAula*quantidadeDeAulasPorDia)+minutosPorIntervalo);
            if (horarioAtual.isAfter(horarioEntrada) && horarioAtual.isBefore(horarioDeSaida)) {
                return subTurma;
            }
        }
        throw new RuntimeException("O aluno não têm nehuma turma nesse horario");
    }
}
