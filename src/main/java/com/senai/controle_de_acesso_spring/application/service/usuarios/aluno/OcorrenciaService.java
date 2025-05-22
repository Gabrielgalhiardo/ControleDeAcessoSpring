package com.senai.controle_de_acesso_spring.application.service.usuarios.aluno;

import com.senai.controle_de_acesso_spring.application.dto.usuarios.aluno.AlunoDto;
import com.senai.controle_de_acesso_spring.application.dto.usuarios.aluno.OcorrenciaDto;
import com.senai.controle_de_acesso_spring.domain.model.entity.curso.UnidadeCurricular;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.SubTurma;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.Aula;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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

    public Aula pegarAula(Aluno aluno){
        DayOfWeek dataAtual = LocalDateTime.now().getDayOfWeek();
        for ()
    }

    public String criarOcorrenciaDeAtraso(String idAcesso) {
        Optional<Usuario> usuario = usuarioRepository.findByIdAcesso(idAcesso);
        if (usuario.isPresent()) {
            if (usuario.get() instanceof Aluno aluno){
                LocalTime horarioDeEntrada = pegarSubTurmaAtual(aluno).getTurma().getHorarioEntrada();
                int toleranciaDeAtraso = pegarSubTurmaAtual(aluno).getTurma().getCurso().getToleranciaMinutos();
                if (horarioDeEntrada.plusMinutes(toleranciaDeAtraso).isAfter(LocalTime.now())){
                    Professor professor = pegarSubTurmaAtual(aluno).getSemestres();
                    Ocorrencia ocorrencia = new Ocorrencia();
                    ocorrencia.setTipo(TipoDeOcorrencia.ATRASO);
                    ocorrencia.setDescricao("Atraso na entrada");
                    ocorrencia.setStatusDaOcorrencia(StatusDaOcorrencia.AGUARDANDO_AUTORIZACAO);
                    ocorrencia.setDataHoraCriacao(LocalDateTime.now());
                    ocorrencia.setAluno(aluno);
                    ocorrencia.setProfessorResponsavel();
                    ocorrencia.setUnidadeCurricular();
                    ocorrenciaRepository.save(ocorrencia);
                    System.out.println("Ocorrência de atraso criada com sucesso!");
                    return "Ocorrência de atraso criada com sucesso!";
                }

            } else {
                System.out.println("Usuário não é um aluno");
                throw new RuntimeException("Usuário não é um aluno");
            }
        } else {
            System.out.println("Usuário não encontrado");
            throw new RuntimeException("Usuário não encontrado");
        }
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
