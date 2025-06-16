package com.senai.controle_de_acesso_spring.application.service.usuarios.aluno;

import com.senai.controle_de_acesso_spring.application.dto.auth.OcorrenciaDTO;
import com.senai.controle_de_acesso_spring.application.dto.usuarios.ProfessorDto;
//import com.senai.controle_de_acesso_spring.application.dto.usuarios.aluno.OcorrenciaDTO;
import com.senai.controle_de_acesso_spring.application.service.curso.UnidadeCurricularService;
import com.senai.controle_de_acesso_spring.application.service.turma.SubTurmaService;
import com.senai.controle_de_acesso_spring.application.service.turma.horarios.AulaService;
import com.senai.controle_de_acesso_spring.application.service.usuarios.ProfessorService;
import com.senai.controle_de_acesso_spring.domain.model.entity.curso.UnidadeCurricular;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.SubTurma;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.Aula;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.AQV;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Professor;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Usuario;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Aluno;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Ocorrencia;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDaOcorrencia;
import com.senai.controle_de_acesso_spring.domain.model.enums.TipoDeOcorrencia;
import com.senai.controle_de_acesso_spring.domain.repository.usuarios.AQVRepository;
import com.senai.controle_de_acesso_spring.domain.repository.usuarios.aluno.AlunoRepository;
import com.senai.controle_de_acesso_spring.domain.repository.usuarios.aluno.OcorrenciaRepository;
import com.senai.controle_de_acesso_spring.domain.repository.usuarios.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OcorrenciaService {

    //Nosso jeito
    @Autowired
    private OcorrenciaRepository ocorrenciaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AulaService aulaService;

    @Autowired
    private SubTurmaService subTurmaService;

    //Exemplo do professor
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private ProfessorService professorService;
    @Autowired
    private AQVRepository aqvRepository;

    @Autowired
    private UnidadeCurricularService unidadeCurricularService;

    @Transactional
    public void solicitarSaidaAntecipada(OcorrenciaDTO dto) {
        Aluno aluno = alunoRepository.findById(dto.alunoId())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        AQV aqv = aqvRepository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new RuntimeException("AQV não encontrada"));

        Professor professor = professorService.buscarPorId(dto.professorResponsavelId()).map(ProfessorDto::fromDTO)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        UnidadeCurricular unidadeCurricular = unidadeCurricularService.(professor.getId())
                .orElseThrow(() -> new RuntimeException("Unidade Curricular não encontrada"));

        Ocorrencia ocorrencia = dto.fromDTO();
        ocorrencia.setProfessorResponsavel(professor);
        ocorrencia.setDataHoraCriacao(LocalDateTime.now());
        ocorrencia.setAluno(aluno);
        ocorrencia.setUnidadeCurricular(unidadeCurricular);
        ocorrencia.setDataHoraCriacao(LocalDateTime.now());
        ocorrencia.setTipo(TipoDeOcorrencia.SAIDA_ANTECIPADA);
        this.mudarStatusEEnviaOcorrencia(
                StatusDaOcorrencia.AGUARDANDO_AUTORIZACAO,
                aqv,
                ocorrencia
        );
    }

    public void decidir(OcorrenciaDTO dto) {

        Ocorrencia ocorrencia = ocorrenciaRepository.findById(dto.id())
                .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada"));

        if (dto.status() == StatusDaOcorrencia.REPROVADO) {
            mudarStatusEEnviaOcorrencia(
                    StatusDaOcorrencia.REPROVADO,
                    ocorrencia.getAluno(),
                    ocorrencia
            );
        }else {
            mudarStatusEEnviaOcorrencia(
                    StatusDaOcorrencia.AGUARDANDO_CIENCIA_PROFESSOR,
                    ocorrencia.getProfessorResponsavel(),
                    ocorrencia
            );
        }
    }

    public void confirmarCiencia(OcorrenciaDTO dto) {

        Ocorrencia ocorrencia = ocorrenciaRepository.findById(dto.id())
                .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada"));

        mudarStatusEEnviaOcorrencia(
                StatusDaOcorrencia.APROVADO,
                ocorrencia.getAluno(),
                ocorrencia
        );
    }

    public void mudarStatusEEnviaOcorrencia(StatusDaOcorrencia status, Usuario usuarioDestino, Ocorrencia ocorrencia){
        ocorrencia.setStatusDaOcorrencia(status);
        ocorrenciaRepository.save(ocorrencia);
        System.out.println("Vai tentar pegar usuário");
        System.out.println("Enviado para "+ usuarioDestino.getEmail() + " a ocorrencia de id: " + ocorrencia.getId());
        messagingTemplate.convertAndSendToUser(
                usuarioDestino.getEmail(),
                "/queue/ocorrencia",
                com.senai.controle_de_acesso_spring.application.dto.auth.OcorrenciaDTO.toDTO(ocorrencia)
        );
    }

    public List<OcorrenciaDTO> listarOcorrencias(){
        return ocorrenciaRepository.findAll().stream().map(OcorrenciaDTO::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public String criarOcorrenciaDeAtraso(String idAcesso) {
        System.out.println("Iniciando a criação da ocorrência de atraso para o id de acesso: " + idAcesso);
        Optional<Usuario> usuario = usuarioRepository.findByIdAcesso(idAcesso);
        Optional<AQV> aqvOptional = aqvRepository.findFirstByOrderByIdAsc();
        System.out.println("Encontrou o usuário");
        if (usuario.isEmpty() || !(usuario.get() instanceof Aluno aluno)) {
            throw new RuntimeException("Usuário não encontrado ou não é um aluno.");
        }

        System.out.println("Vai buscar Subturma");
        SubTurma subTurma = subTurmaService.pegarSubTurmaAtual(aluno);
        System.out.println("Encontrou subturma: " + subTurma.getNome());
        LocalTime horarioDeEntrada = subTurma.getTurma().getHorarioEntrada();
        System.out.println("Pegou o horario de entrada: " + horarioDeEntrada);
        int tolerancia = subTurma.getTurma().getCurso().getToleranciaMinutos();
        System.out.println("pegou a tolerÂncia: " + tolerancia);

        LocalDateTime agora = LocalDateTime.now();

        if (LocalTime.now().isBefore(horarioDeEntrada.plusMinutes(tolerancia))) {
            throw new RuntimeException("Ainda está dentro do horário permitido, não há atraso.");
        }
        System.out.println("Criando ocorrência de atraso para o aluno: " + aluno.getNome());

        System.out.println("VAi buscar a aula da aluna(o) "+aluno.getNome());
        Aula aulaAtual = aulaService.pegarAulaAtualPelaSubTurma(subTurma);
        System.out.println("Encontrou a aula atual: " + aulaAtual.getUnidadeCurricular().getNome());

        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setTipo(TipoDeOcorrencia.ATRASO);
        ocorrencia.setDescricao("Atraso na entrada");
        ocorrencia.setStatusDaOcorrencia(StatusDaOcorrencia.AGUARDANDO_AUTORIZACAO);
        ocorrencia.setDataHoraCriacao(agora);
        ocorrencia.setAluno(aluno);
        System.out.println("Professor que recebeu a ocorrência "+aulaAtual.getProfessor().getIdAcesso());
        System.out.println("Professor que recebeu a ocorrência "+aulaAtual.getProfessor().getEmail());
        ocorrencia.setProfessorResponsavel(aulaAtual.getProfessor());
        ocorrencia.setUnidadeCurricular(aulaAtual.getUnidadeCurricular());

        ocorrenciaRepository.save(ocorrencia);
        mudarStatusEEnviaOcorrencia(
                StatusDaOcorrencia.AGUARDANDO_AUTORIZACAO,
                aqvOptional.get(),
                ocorrencia
        );


        return "Ocorrência de atraso criada com sucesso!";
    }

    public Optional<OcorrenciaDTO> buscarOcorrenciaPorId(Long id){
        return ocorrenciaRepository.findById(id).map(OcorrenciaDTO::toDTO);
    }

    public boolean atualizarOcorrencia (Long id, OcorrenciaDTO ocorrenciaDto){
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

//    public boolean mudarStatusDaOcorrencia (Long id, StatusDaOcorrencia statusDaOcorrencia){
//        return ocorrenciaRepository.findById(id).map( ocorrencia ->{
//            ocorrencia.setStatusDaOcorrencia(statusDaOcorrencia);
//            ocorrenciaRepository.save(ocorrencia);
//            return true;
//        }).orElse(false);
//    }

}
