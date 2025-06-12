package com.senai.controle_de_acesso_spring.application.service.turma;

import com.senai.controle_de_acesso_spring.application.dto.turma.SemestreDTO;
import com.senai.controle_de_acesso_spring.application.service.turma.horarios.HorarioPadraoService;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.Semestre;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.SubTurma;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.Turma;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.HorarioPadrao;
import com.senai.controle_de_acesso_spring.domain.repository.turma.SemestreRepository;
import com.senai.controle_de_acesso_spring.domain.repository.turma.SubTurmaRepository;
import com.senai.controle_de_acesso_spring.domain.service.HorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SemestreService {

    @Autowired
    private SemestreRepository semestreRepository;

    @Autowired
    private SubTurmaRepository subTurmaRepository;

    @Autowired
    private HorarioService horarioService;

    @Transactional
    public void criarSemestre(Long subTurmaId) {
        SubTurma subTurma = subTurmaRepository.findById(subTurmaId)
                .orElseThrow(() -> new RuntimeException("SubTurma não encontrada"));

        Semestre semestre = new Semestre();
        Optional<List<Semestre>> semestreOptional = Optional.ofNullable(subTurma.getSemestres());
        if (semestreOptional.isPresent()) {
            subTurma.getSemestres().add(semestre);
        }else {
            subTurma.setSemestres(new ArrayList<>());
            subTurma.getSemestres().add(semestre);
        }
        semestre.setNumero(subTurma.getSemestres().size());
        semestre.setSubTurma(subTurma);
        semestre.setNomeDaTurma(
                subTurma.getSemestres().size() +
                        subTurma.getTurma().getSiglaDaTurma() +
                        subTurma.getTurma().getPeriodo().getSigla()
        );
        semestre.setHorariosSemanais(new ArrayList<>());
        semestre.setHorarioPadrao(horarioService.criarHorarioPadraoVazio(semestre));
        semestreRepository.save(semestre);
    }

    public Optional<SemestreDTO> buscarPorId(Long id) {
        return semestreRepository.findById(id).map(SemestreDTO::toDTO);
    }

    public List<SemestreDTO> listarTodos() {
        return semestreRepository.findAll().stream()
                .map(SemestreDTO::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean atualizar(Long id, SemestreDTO dto) {
        Optional<Semestre> optional = semestreRepository.findById(id);
        if (optional.isEmpty()) return false;

        Semestre semestre = optional.get();
        semestre.setNumero(dto.numero());

        semestreRepository.save(semestre);
        return true;
    }

    public boolean deletar(Long id) {
        Optional<Semestre> optional = semestreRepository.findById(id);
        if (optional.isEmpty()) return false;

        semestreRepository.deleteById(id);
        return true;
    }

    public static int pegarSemestreAtual(Turma turma) {
        LocalDate dataInicio = turma.getDataInicial();
        LocalDate dataAtual = LocalDate.now();

        System.out.println("Pegando data inicial: " + dataInicio);
        if (dataAtual.isBefore(dataInicio)) {
            System.out.println("entrou IF e retornou 1");
            return 1;
        }


        long meses = ChronoUnit.MONTHS.between(dataInicio, dataAtual);
        System.out.println("Quantidade de meses: "+meses );
        int semestreAtual = (int) (meses / 6) + 1;

        System.out.println("Semestre atual: " + semestreAtual);
        if (turma.getQtdSemestres() != null && semestreAtual > turma.getQtdSemestres()) {
            System.out.println("Entrou no if: " + turma.getQtdSemestres());
            return turma.getQtdSemestres();


        }
        System.out.println("Semestre atual final: " + semestreAtual) ;
        return semestreAtual;
    }
}
