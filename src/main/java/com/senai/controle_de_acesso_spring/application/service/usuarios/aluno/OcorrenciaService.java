package com.senai.controle_de_acesso_spring.application.service.usuarios.aluno;

import com.senai.controle_de_acesso_spring.application.dto.usuarios.aluno.OcorrenciaDto;
import com.senai.controle_de_acesso_spring.application.service.turma.SubTurmaService;
import com.senai.controle_de_acesso_spring.application.service.turma.horarios.AulaService;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.SubTurma;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.Aula;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Usuario;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Aluno;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Ocorrencia;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDaOcorrencia;
import com.senai.controle_de_acesso_spring.domain.model.enums.TipoDeOcorrencia;
import com.senai.controle_de_acesso_spring.domain.repository.turma.SubTurmaRepository;
import com.senai.controle_de_acesso_spring.domain.repository.usuarios.aluno.OcorrenciaRepository;
import com.senai.controle_de_acesso_spring.domain.repository.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public String criarOcorrenciaDeAtraso(String idAcesso) {
        Optional<Usuario> usuario = usuarioRepository.findByIdAcesso(idAcesso);
        if (usuario.isEmpty() || !(usuario.get() instanceof Aluno aluno)) {
            throw new RuntimeException("Usuário não encontrado ou não é um aluno.");
        }

        SubTurma subTurma = SubTurmaService.pegarSubTurmaAtual(aluno);
        LocalTime horarioDeEntrada = subTurma.getTurma().getHorarioEntrada();
        int tolerancia = subTurma.getTurma().getCurso().getToleranciaMinutos();

        if (LocalTime.now().isBefore(horarioDeEntrada.plusMinutes(tolerancia))) {
            throw new RuntimeException("Ainda está dentro do horário permitido, não há atraso.");
        }

        Aula aulaAtual = AulaService.pegarAulaAtualPelaSubTurma(subTurma);

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

    public String criarOcorrenciaDeSaida(String idAcesso) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByIdAcesso(idAcesso);
        if (usuarioOptional.isPresent()) {
            if (usuarioOptional.get() instanceof Aluno aluno){

                SubTurma subTurma = SubTurmaService.pegarSubTurmaAtual(aluno);
//                LocalTime horarioDeEntrada = subTurma.getTurma().getHorarioEntrada();
//                int tolerancia = subTurma.getTurma().getCurso().getToleranciaMinutos();
                Aula aulaAtual = AulaService.pegarAulaAtualPelaSubTurma(subTurma);

                Ocorrencia ocorrencia = new Ocorrencia();
                ocorrencia.setTipo(TipoDeOcorrencia.SAIDA_ANTECIPADA);
                ocorrencia.setDescricao("Saída Antecipada");
                ocorrencia.setStatusDaOcorrencia(StatusDaOcorrencia.AGUARDANDO_AUTORIZACAO);
                ocorrencia.setDataHoraCriacao(LocalDateTime.now());
                ocorrencia.setAluno(aluno);
                ocorrencia.setProfessorResponsavel(aulaAtual.getProfessor());
                ocorrencia.setUnidadeCurricular(aulaAtual.getUnidadeCurricular());
                ocorrenciaRepository.save(ocorrencia);
                System.out.println("Ocorrência de saída antecipada criada com sucesso!");
                return "Ocorrência de saída antecipada criada com sucesso!";
            } else {
                System.out.println("Usuário não é aluno");
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
