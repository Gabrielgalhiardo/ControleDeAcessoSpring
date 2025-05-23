package com.senai.controle_de_acesso_spring.application.service.usuarios.aluno;

import com.senai.controle_de_acesso_spring.application.dto.usuarios.aluno.AlunoDto;
import com.senai.controle_de_acesso_spring.application.dto.usuarios.aluno.OcorrenciaDto;
import com.senai.controle_de_acesso_spring.domain.model.entity.curso.UnidadeCurricular;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.Semestre;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.SubTurma;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.Aula;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.AulasDoDia;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.HorarioPadrao;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Professor;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Usuario;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Aluno;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Ocorrencia;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDaOcorrencia;
import com.senai.controle_de_acesso_spring.domain.model.enums.TipoDeOcorrencia;
import com.senai.controle_de_acesso_spring.domain.repository.turma.SubTurmaRepository;
import com.senai.controle_de_acesso_spring.domain.repository.usuarios.aluno.OcorrenciaRepository;
import com.senai.controle_de_acesso_spring.domain.repository.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OcorrenciaService {

    @Autowired
    private OcorrenciaRepository ocorrenciaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SubTurmaRepository subTurmaRepository;

    public void cadastrarOcorrencia(OcorrenciaDto ocorrenciaDto){
        ocorrenciaRepository.save(ocorrenciaDto.fromDTO());
    }

    public List<OcorrenciaDto> listarOcorrencias(){
        return ocorrenciaRepository.findAll().stream().map(OcorrenciaDto::toDTO).collect(Collectors.toList());
    }

    public Aula pegarAulaAtual(SubTurma subTurma) {
        LocalTime horarioEntrada = subTurma.getTurma().getHorarioEntrada();
        int minutosPorAula = subTurma.getTurma().getCurso().getTipoDeCurso().getMinutosPorAula();
        int minutosDeIntervalo = subTurma.getTurma().getCurso().getTipoDeCurso().getIntevarloMinutos();

        DayOfWeek hoje = DayOfWeek.from(LocalDate.now());

        Semestre semestre = subTurma.getSemestres().stream()
                .max(Comparator.comparing(Semestre::getNumero))
                .orElseThrow(() -> new RuntimeException("Semestre não encontrado."));

        HorarioPadrao horario = semestre.getHorarioPadrao();
        AulasDoDia aulasDoDia = horario.getDiasDaSemana().stream()
                .filter(dia -> dia.getDiaDaSemana().equals(hoje))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Sem aulas hoje."));

        LocalTime agora = LocalTime.now();

        for (Aula aula : aulasDoDia.getAulas()) {
            int ordem = aula.getOrdem();
            LocalTime inicioAula = horarioEntrada.plusMinutes((ordem - 1) * minutosPorAula + Math.max(0, ordem - 1) * minutosDeIntervalo);
            LocalTime fimAula = inicioAula.plusMinutes(minutosPorAula);

            if (agora.isAfter(inicioAula) && agora.isBefore(fimAula)) {
                return aula;
            }
        }

        throw new RuntimeException("Nenhuma aula acontecendo neste momento.");
    }


    public SubTurma pegarSubTurmaAtual(Aluno aluno){
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

    public String criarOcorrenciaDeAtraso(String idAcesso) {
        Optional<Usuario> usuario = usuarioRepository.findByIdAcesso(idAcesso);
        if (usuario.isEmpty() || !(usuario.get() instanceof Aluno aluno)) {
            throw new RuntimeException("Usuário não encontrado ou não é um aluno.");
        }

        SubTurma subTurma = pegarSubTurmaAtual(aluno);
        LocalTime horarioDeEntrada = subTurma.getTurma().getHorarioEntrada();
        int tolerancia = subTurma.getTurma().getCurso().getToleranciaMinutos();

        if (LocalTime.now().isBefore(horarioDeEntrada.plusMinutes(tolerancia))) {
            throw new RuntimeException("Ainda está dentro do horário permitido, não há atraso.");
        }

        Aula aulaAtual = pegarAulaAtual(subTurma);

        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setTipo(TipoDeOcorrencia.ATRASO);
        ocorrencia.setDescricao("Atraso na entrada");
        ocorrencia.setStatusDaOcorrencia(StatusDaOcorrencia.AGUARDANDO_AUTORIZACAO);
        ocorrencia.setDataHoraCriacao(LocalDateTime.now());
        ocorrencia.setAluno(aluno);
        ocorrencia.setProfessorResponsavel(aulaAtual.getProfessor());
        ocorrencia.setUnidadeCurricular(aulaAtual.getUnidadeCurricular());

        ocorrenciaRepository.save(ocorrencia);

        return "Ocorrência de atraso criada com sucesso!";
    }


    public Optional<OcorrenciaDto> buscarOcorrenciaPorId(Long id){
        return ocorrenciaRepository.findById(id).map(OcorrenciaDto::toDTO);
    }

    public boolean atualizarOcorrencia (Long id, OcorrenciaDto ocorrenciaDto){
        return ocorrenciaRepository.findById(id).map(ocorrenciaAntiga ->{
            Ocorrencia ocorrenciaAtualizada = ocorrenciaDto.fromDTO();
            ocorrenciaAntiga.setTipo(ocorrenciaAtualizada.getTipo());
            ocorrenciaAntiga.setDescricao(ocorrenciaAtualizada.getDescricao());
            ocorrenciaAntiga.setStatusDaOcorrencia(ocorrenciaAtualizada.getStatusDaOcorrencia());
            ocorrenciaAntiga.setDataHoraCriacao(ocorrenciaAtualizada.getDataHoraCriacao());
            ocorrenciaAntiga.setDataHoraConclusao(ocorrenciaAtualizada.getDataHoraConclusao());
            ocorrenciaAntiga.setAluno(ocorrenciaAtualizada.getAluno());
            ocorrenciaAntiga.setProfessorResponsavel(ocorrenciaAtualizada.getProfessorResponsavel());
            ocorrenciaAntiga.setUnidadeCurricular(ocorrenciaAtualizada.getUnidadeCurricular());
            ocorrenciaRepository.save(ocorrenciaAntiga);
            return true;
        }).orElse(false);
    }

    public boolean mudarStatusDaOcorrencia (Long id, StatusDaOcorrencia statusDaOcorrencia){
        return ocorrenciaRepository.findById(id).map( ocorrencia ->{
            ocorrencia.setStatusDaOcorrencia(statusDaOcorrencia);
            ocorrenciaRepository.save(ocorrencia);
            return true;
        }).orElse(false);
    }

}
