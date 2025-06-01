package com.senai.controle_de_acesso_spring.application.service.turma;

import com.senai.controle_de_acesso_spring.application.dto.turma.SemestreDTO;
import com.senai.controle_de_acesso_spring.application.dto.turma.SubTurmaDTO;
import com.senai.controle_de_acesso_spring.application.dto.turma.TurmaDto;
import com.senai.controle_de_acesso_spring.application.dto.turma.horario.AulasDoDiaDTO;
import com.senai.controle_de_acesso_spring.application.dto.turma.horario.HorarioBaseDTO;
import com.senai.controle_de_acesso_spring.application.dto.turma.horario.HorarioPadraoDTO;
import com.senai.controle_de_acesso_spring.application.dto.turma.horario.HorarioSemanalDTO;
import com.senai.controle_de_acesso_spring.application.service.turma.horarios.HorarioBaseService;
import com.senai.controle_de_acesso_spring.application.service.turma.horarios.HorarioPadraoService;
import com.senai.controle_de_acesso_spring.application.service.turma.horarios.HorarioSemanalService;
import com.senai.controle_de_acesso_spring.application.service.usuarios.aluno.AlunoService;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.Semestre;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.SubTurma;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.Turma;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.HorarioBase;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.HorarioPadrao;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.HorarioSemanal;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Aluno;
import com.senai.controle_de_acesso_spring.domain.repository.turma.SubTurmaRepository;
import com.senai.controle_de_acesso_spring.domain.repository.turma.TurmaRepository;
import com.senai.controle_de_acesso_spring.domain.repository.turma.horarios.HorarioBaseRepository;
import com.senai.controle_de_acesso_spring.domain.repository.usuarios.aluno.AlunoRepository;
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

    @Autowired
    private HorarioSemanalService horarioSemanlService;

    @Autowired
    private HorarioPadraoService horarioPadraoService;

    @Autowired
    private SemestreService semestreService;

    @Autowired
    private AlunoRepository alunoRepository;

//    @Transactional
//    public void criarSubTurma(SubTurmaDTO subTurmaDTO) {
////        Turma turma = turmaRepository.findById(turmaId)
////                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
//
//        Optional<Turma> turmaOptional = turmaRepository.findById(subTurmaDTO.turma().getId());
//        Turma turma = turmaOptional
//                .orElseThrow(() -> new RuntimeException("Turma não encontrada: "));
//
//        SubTurma subTurma = subTurmaDTO.fromDTO();
//        subTurma.setTurma(subTurmaDTO.turma());
//        subTurma.setNome("Turma "+(turma.getSubTurmas().isEmpty() ? 1 : turma.getSubTurmas().size() + 1));
//        subTurma.setTurma(turma);
//        turma.getSubTurmas().add(subTurma);
//
//        List<Aluno> alunos = subTurmaDTO.fromDTO().getAlunos().stream()
//                .map(Aluno::getId)
//                .map(alunoId -> alunoRepository.findById(alunoId)
//                        .orElseThrow(() -> new RuntimeException("Aluno não encontrado com ID: " + alunoId)))
//                .collect(Collectors.toList());
//        subTurma.setAlunos(alunos);
//
//        Semestre semestre = subTurmaDTO.semestreDTOS().stream().map(SemestreDTO::fromDTO)
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("Nenhum semestre encontrado para a sub-turma"));
//        subTurma.getSemestres().add(semestre);
//
//        semestre.setNumero(subTurma.getSemestres().size());
//        semestre.setNomeDaTurma(
//                subTurma.getSemestres().size() +
//                        subTurma.getTurma().getSiglaDaTurma() +
//                        subTurma.getTurma().getPeriodo().getSigla()
//        );
//        semestre.setSubTurma(subTurma);
//
//
//
////        System.out.println("id de Semestre: " + semestre.getId());
////        System.out.println("id de subTurma: " + subTurma.getId());
////        SemestreDTO semestreDTO = semestreService.criarSemestre(subTurma.getId());
//
//
////        SemestreDTO semestreDTO = subTurmaDTO.semestreDTOS().stream()
////                .filter(s -> s.numero() == semestre.getNumero())
////                .findFirst()
////                .orElseThrow(() -> new RuntimeException("SemestreDTO não encontrado para o semestre " + semestre.getNumero()));
//
//        if (semestre.getHorarioPadrao() == null) {
//            throw new RuntimeException("HorarioPadrao não informado para o semestre " );
//        }
//
////        System.out.println("id de semestreDTO: " + semestreDTO.id());
//
////        HorarioPadrao horarioPadrao = horarioPadraoService.salvarHorarioPadrao(
////                semestre.getId(),
////                new HorarioPadraoDTO(
////                        semestre.getHorarioPadrao().getId(),
////                        semestre.getHorarioPadrao().getListaDeAulasDoDia().stream()
////                                .map(AulasDoDiaDTO::toDTO)
////                                .collect(Collectors.toList()))
////        );
//
//        HorarioPadrao horarioPadrao = subTurmaDTO.semestreDTOS().stream()
//                .filter(s -> s.numero() == semestre.getNumero())
//                .findFirst()
//                .map(SemestreDTO::horarioPadrao)
////                .map(HorarioPadraoDTO::fromDTO)
////                .map(horarioPadraoService::salvarHorarioPadrao)
//                .orElseThrow(() -> new RuntimeException("HorarioPadrao não encontrado para o semestre " + semestre.getNumero()));
//
//        semestre.setHorarioPadrao(horarioPadrao);
//
//
////        List<HorarioSemanalDTO> horariosSemanaisDTOs = horarioSemanlService.cadastrarHorariosSemanais(subTurmaDTO.semestreDTOS().stream()
////                .filter(s -> s.numero() == semestre.getNumero())
////                .findFirst()
////                .map(SemestreDTO::horarioSemanalDTOS)
////                .orElse(new ArrayList<>()));
////        semestre.setHorariosSemanais(horariosSemanaisDTOs.stream()
////                .map(HorarioSemanalDTO::fromDTO).collect(Collectors.toList()));
//
//        List<HorarioSemanal> horarioSemanals = subTurmaDTO.semestreDTOS().stream()
//                .filter(s -> s.numero() == semestre.getNumero())
//                .findFirst()
//                .map(SemestreDTO::horarioSemanalDTOS)
//                .orElse(new ArrayList<>())
//                .stream()
//                .map(HorarioSemanalDTO::fromDTO)
//                .collect(Collectors.toList());
//        semestre.setHorariosSemanais(horarioSemanals);
//
////        List<Aluno> alunos = subTurmaDTO.alunoDtos().stream()
////                .map(aDTO -> alunoRepository.findById(aDTO.id())
////                        .orElseThrow(() -> new RuntimeException("Aluno não encontrado: " + aDTO.id())))
////                .collect(Collectors.toList());
////        subTurma.setAlunos(alunos);
//
//        subTurma.setSemestres(subTurmaDTO.semestreDTOS().stream()
//                .map(SemestreDTO::fromDTO)
//                .collect(Collectors.toList()));
//
//
//        subTurmaRepository.save(subTurma);
//    }

@Transactional
public void criarSubTurma(SubTurmaDTO subTurmaDTO) {
    // Buscar Turma
    Turma turma = turmaRepository.findById(subTurmaDTO.turma().getId())
            .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

    // Criar SubTurma
    SubTurma subTurma = subTurmaDTO.fromDTO();
    subTurma.setTurma(turma);
    subTurma.setNome("Turma " + (turma.getSubTurmas().isEmpty() ? 1 : turma.getSubTurmas().size() + 1));
    turma.getSubTurmas().add(subTurma);

    // Associar Alunos
    List<Aluno> alunos = subTurmaDTO.fromDTO().getAlunos().stream()
            .map(Aluno::getId)
            .map(alunoId -> alunoRepository.findById(alunoId)
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado com ID: " + alunoId)))
            .collect(Collectors.toList());
    subTurma.setAlunos(alunos);

    // ⚠️ Primeiro salva a SubTurma para gerar o ID
    subTurma = subTurmaRepository.save(subTurma);

    // Agora cria e associa os Semestres
    SubTurma finalSubTurma = subTurma;
    List<Semestre> semestres = subTurmaDTO.semestreDTOS().stream()
            .map(SemestreDTO::fromDTO)
            .peek(semestre -> {
                semestre.setSubTurma(finalSubTurma);

                semestre.setNumero(finalSubTurma.getSemestres().size() + 1);
                semestre.setNomeDaTurma(
                        semestre.getNumero() +
                                finalSubTurma.getTurma().getSiglaDaTurma() +
                                finalSubTurma.getTurma().getPeriodo().getSigla()
                );

                if (semestre.getHorarioPadrao() == null) {
                    throw new RuntimeException("HorarioPadrao não informado para o semestre");
                }

                // Associar Horario Semanal
                List<HorarioSemanal> horarioSemanals = subTurmaDTO.semestreDTOS().stream()
                        .filter(s -> s.numero() == semestre.getNumero())
                        .findFirst()
                        .map(SemestreDTO::horarioSemanalDTOS)
                        .orElse(new ArrayList<>())
                        .stream()
                        .map(HorarioSemanalDTO::fromDTO)
                        .collect(Collectors.toList());
                semestre.setHorariosSemanais(horarioSemanals);
            })
            .collect(Collectors.toList());

    subTurma.setSemestres(semestres);

    // ⚠️ Atualiza a SubTurma com os Semestres associados
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
//        for (SubTurma subTurma : aluno.getSubTurmas()){
//            LocalTime horarioEntrada = subTurma.getTurma().getHorarioEntrada();
//            int minutosPorAula = subTurma.getTurma().getCurso().getTipoDeCurso().getMinutosPorAula();
//            int minutosPorIntervalo = subTurma.getTurma().getCurso().getTipoDeCurso().getIntevarloMinutos();
//            int quantidadeDeAulasPorDia = subTurma.getTurma().getQtdAulasPorDia();
//            LocalTime horarioDeSaida = horarioEntrada.plusMinutes((minutosPorAula*quantidadeDeAulasPorDia)+minutosPorIntervalo);
//            if (horarioAtual.isAfter(horarioEntrada) && horarioAtual.isBefore(horarioDeSaida)) {
//                return subTurma;
//            }
//        }
        return aluno.getSubTurma();

//        throw new RuntimeException("O aluno não têm nehuma turma nesse horario");
    }
}
