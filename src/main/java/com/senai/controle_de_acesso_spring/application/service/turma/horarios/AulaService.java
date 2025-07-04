package com.senai.controle_de_acesso_spring.application.service.turma.horarios;

import com.senai.controle_de_acesso_spring.application.dto.turma.horario.AulaDTO;
import com.senai.controle_de_acesso_spring.application.service.turma.SemestreService;
import com.senai.controle_de_acesso_spring.domain.model.entity.curso.Ambiente;
import com.senai.controle_de_acesso_spring.domain.model.entity.curso.UnidadeCurricular;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.Semestre;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.SubTurma;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.Aula;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.AulasDoDia;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.HorarioPadrao;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.HorarioSemanal;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Professor;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Aluno;
import com.senai.controle_de_acesso_spring.domain.model.enums.DiaDaSemana;
import com.senai.controle_de_acesso_spring.domain.model.enums.TipoDeCurso;
import com.senai.controle_de_acesso_spring.domain.repository.curso.AmbienteRepository;
import com.senai.controle_de_acesso_spring.domain.repository.curso.UnidadeCurricularRepository;
import com.senai.controle_de_acesso_spring.domain.repository.turma.horarios.AulaRepository;
import com.senai.controle_de_acesso_spring.domain.repository.usuarios.ProfessorRepository;
import com.senai.controle_de_acesso_spring.domain.service.turma.horarios.AulasHelperService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AulaService {

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private UnidadeCurricularRepository unidadeCurricularRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private AmbienteRepository ambienteRepository;

    @Autowired
    private SemestreService semestreService;

    @Autowired
    private AulasHelperService aulasHelperService;

    public Aula salvarAula(AulaDTO aulaDTO){
        Ambiente ambiente = ambienteRepository.findById(aulaDTO.ambienteId())
                .orElseThrow(() -> new RuntimeException("Ambiente não encontrado."));
        Professor professor = professorRepository.findById(aulaDTO.professorId())
                .orElseThrow(() -> new RuntimeException("Professor não encontrado."));
        UnidadeCurricular unidadeCurricular = unidadeCurricularRepository.findById(aulaDTO.unidadeCurricularId())
                .orElseThrow(() -> new RuntimeException("Unidade Curricular não encontrada."));
        Aula aula = new Aula();
        aula.setOrdem(aulaDTO.ordem());
        aula.setProfessor(professor);
        aula.setUnidadeCurricular(unidadeCurricular);
        aula.setAmbiente(ambiente);
        aula.setAulasDia(new AulasDoDia());
        return aulaRepository.save(aula);
    }

    public List<Aula> salvarAulas(List<AulaDTO> aulasDTO) {
        List<Aula> aulas = aulasDTO.stream().map(this::salvarAula).toList();
        return aulaRepository.saveAll(aulas);
    }

    public List<AulaDTO> listarAulas(){
        return aulaRepository.findAll()
                .stream().map(AulaDTO::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<AulaDTO> buscarId(Long id){
        return aulaRepository.findById(id)
                .map(AulaDTO::toDTO);
    }

    public boolean atualizarAulas(Long id, AulaDTO novaAulaDTO) {
        return aulaRepository.findById(id).map(aula -> {
            aula.setOrdem(novaAulaDTO.ordem());
            UnidadeCurricular uc = unidadeCurricularRepository.findById(novaAulaDTO.unidadeCurricularId())
                    .orElseThrow(() -> new RuntimeException("Unidade Curricular não encontrada."));
            Professor professor = professorRepository.findById(novaAulaDTO.professorId())
                    .orElseThrow(() -> new RuntimeException("Professor não encontrado."));
            Ambiente ambiente = ambienteRepository.findById(novaAulaDTO.ambienteId())
                    .orElseThrow(() -> new RuntimeException("Ambiente não encontrado."));
            aula.setUnidadeCurricular(uc);
            aula.setProfessor(professor);
            aula.setAmbiente(ambiente);

            aulaRepository.save(aula);
            return true;
        }).orElse(false);
    }

    public boolean deletar(Long id){
        return aulaRepository.findById(id).map(aula -> {
            aulaRepository.delete(aula);
            return true;
        }).orElse(false);
    }

    @Transactional
    public Aula pegarAulaAtualPelaSubTurma(SubTurma subTurma) {
        LocalTime horarioEntrada = subTurma.getTurma().getHorarioEntrada();
        TipoDeCurso tipoDeCurso = subTurma.getTurma().getCurso().getTipoDeCurso();
        int minutosPorAula = tipoDeCurso.getMinutosPorAula();
        int minutosDeIntervalo = tipoDeCurso.getIntevarloMinutos();
        DiaDaSemana diaDaSemana = DiaDaSemana.obterDiaDaSemanaAtual();
        Semestre semestre = semestreService.encontrarSemestreAtual(subTurma);
        LocalDate semanaAjustada = ajustarSemana(LocalDate.now());
        LocalTime agora = LocalTime.now();

        AulasDoDia aulasDoDia = aulasHelperService.buscarAulasDoDia(semestre, semanaAjustada, diaDaSemana);

        return encontrarAulaAtual(aulasDoDia, horarioEntrada, minutosPorAula, minutosDeIntervalo, tipoDeCurso, agora)
                .orElseThrow(() -> new RuntimeException("Nenhuma aula acontecendo neste momento."));
    }

    private LocalDate ajustarSemana(LocalDate dataAtual) {
        return switch (dataAtual.getDayOfWeek()) {
            case TUESDAY -> dataAtual.minusDays(1);
            case WEDNESDAY -> dataAtual.minusDays(2);
            case THURSDAY -> dataAtual.minusDays(3);
            case FRIDAY -> dataAtual.minusDays(4);
            default -> dataAtual;
        };
    }

    private Optional<Aula> encontrarAulaAtual(
            AulasDoDia aulasDoDia,
            LocalTime horarioEntrada,
            int minutosPorAula,
            int minutosDeIntervalo,
            TipoDeCurso tipoDeCurso,
            LocalTime agora
    ) {
        int aulasAntesDoIntervalo = tipoDeCurso.equals(TipoDeCurso.CAI) ? 2 : 3;

        return aulasDoDia.getAulas().stream()
                .filter(aula -> {
                    int ordem = aula.getOrdem();
                    LocalTime inicio = horarioEntrada.plusMinutes(minutosPorAula * ordem);
                    LocalTime fim = inicio.plusMinutes(minutosPorAula);

                    if (ordem >= aulasAntesDoIntervalo) {
                        inicio = inicio.minusMinutes(minutosDeIntervalo);
                        fim = fim.plusMinutes(minutosDeIntervalo);
                    }

                    return agora.isAfter(inicio) && agora.isBefore(fim);
                })
                .findFirst();
    }

//    @Transactional
//    public Aula pegarAulaAtualPelaSubTurma(SubTurma subTurma) {
//        System.out.println("Pegando aula atual pela SubTurma: " + subTurma.getNome());
//        LocalTime horarioEntrada = subTurma.getTurma().getHorarioEntrada();
//        System.out.println("Horário de entrada: " + horarioEntrada);
//        int minutosPorAula = subTurma.getTurma().getCurso().getTipoDeCurso().getMinutosPorAula();
//        int minutosDeIntervalo = subTurma.getTurma().getCurso().getTipoDeCurso().getIntevarloMinutos();
//
//        System.out.println("Minutos por aula: " + minutosPorAula+ ", Minutos de intervalo: " + minutosDeIntervalo);
////        DayOfWeek hoje = DayOfWeek.from(LocalDate.now());
//
//        System.out.println("Quantidade de semestres: " + subTurma.getSemestres().size());
//
//        System.out.println("vai pegar semestre atual da SubTurma: ");
//        Optional<Semestre> semestreOpt = Optional.of(subTurma.getSemestres().stream()
//                .filter(s -> s.getNumero() == SemestreService.pegarSemestreAtual(subTurma.getTurma()))
//                .findFirst()).get();
//
//        System.out.println("Semestre encontrado: " + semestreOpt.isPresent());
//        Semestre semestre = semestreOpt.orElseThrow(() -> new RuntimeException("Nenhum semestre encontrado."));
//
//        System.out.println("Semestre atual: " + (semestre != null ? semestre.getNumero() : "Nenhum semestre encontrado."));
//
//
//
////        HorarioSemanal horarioSemana = (HorarioSemanal) semestre.getHorariosSemanais().stream().filter(horarioSemanal -> horarioSemanal.getSemanaReferencia().equals(semanaAtual));
////        HorarioPadrao horarioPadrao = semestre.getHorarioPadrao();
////        Optional<AulasDoDia> aulasDoDiaSemana = Optional.of(horarioSemana.getListaDeAulasDoDia().stream()
////                .filter(dia -> dia.getDiaDaSemana().equals(hoje))
////                .findFirst()
////                .orElse(aulasoDia -> {
////                             aulasDoDia = horarioPadrao.getListaDeAulasDoDia().stream()
////                                    .filter(dia -> dia.getDiaDaSemana().equals(hoje))
////                                    .findFirst()
////                                    .orElseThrow(() -> new RuntimeException("Sem aulas hoje."));
////                        }
////                );
//
//        LocalDate semanaAtual = LocalDate.now();
//        System.out.println("Semana atual: " + semanaAtual);
//        switch (semanaAtual.getDayOfWeek()){
//            case DayOfWeek.TUESDAY -> semanaAtual.plusDays(-1);
//
//            case DayOfWeek.WEDNESDAY -> semanaAtual.plusDays(-2);
//
//            case DayOfWeek.THURSDAY -> semanaAtual.plusDays(-3);
//
//            case DayOfWeek.FRIDAY -> semanaAtual.plusDays(-4);
//
//        }
//        System.out.println("Semana atual ajustada: " + semanaAtual);
//        LocalTime agora = LocalTime.now();
//
//        System.out.println("Horário atual: " + agora);
//        Optional<AulasDoDia> aulasDoDiaOpt = Optional.empty();
//
//        System.out.println("Procurando aulas do dia para a semana atual: " + semanaAtual);
//
//        Optional<HorarioSemanal> horarioSemanaOpt = semestre.getHorariosSemanais().stream()
//                .filter(horarioSemanal -> horarioSemanal.getSemanaReferencia().equals(semanaAtual))
//                .findFirst();
//
//        System.out.println("Horário semanal encontrado: " + horarioSemanaOpt.isPresent());
//
//        Optional<HorarioPadrao> horarioSemanaPadrao = Optional.ofNullable(semestre.getHorarioPadrao());
//
//        if (horarioSemanaOpt.isPresent()) {
//            aulasDoDiaOpt = Optional.ofNullable(horarioSemanaOpt.get().getListaDeAulasDoDia().stream()
//                    .filter(dia -> dia.getDiaDaSemana().equals(DiaDaSemana.obterDiaDaSemanaAtual()))
//                    .findFirst()
//                    .orElseThrow(() -> new RuntimeException("Sem aulas hoje.")));
//
//        } else if (horarioSemanaPadrao.isPresent()) {
//            aulasDoDiaOpt = Optional.ofNullable(horarioSemanaPadrao.get().getListaDeAulasDoDia().stream()
//                    .filter(dia -> dia.getDiaDaSemana().equals(DiaDaSemana.obterDiaDaSemanaAtual()))
//                    .findFirst()
//                    .orElseThrow(() -> new RuntimeException("Sem aulas hoje.")));
//        }
//
//        System.out.println("Aulas do dia encontradas: " + aulasDoDiaOpt.isPresent());
//
////        AulasDoDia aulasDoDia = aulasDoDiaOpt.orElseGet(() -> horarioSemana.getListaDeAulasDoDia().stream()
////                .filter(dia -> dia.getDiaDaSemana().equals(hoje))
////                .findFirst()
////                .orElseThrow(() -> new RuntimeException("Sem aulas hoje.")));
//
//            TipoDeCurso tipoDeCurso = subTurma.getTurma().getCurso().getTipoDeCurso();
//        System.out.println("PEgou tipo de curso "+tipoDeCurso);
//        for (Aula aula : aulasDoDiaOpt.get().getAulas()) {
//            System.out.println("Entrou no for each de aula");
//            int ordem = aula.getOrdem();
//            int aulasAteIntervalo;
//
//            if (tipoDeCurso.equals(TipoDeCurso.CAI)){
//                aulasAteIntervalo = 2;
//            }else{
//                aulasAteIntervalo = 3;
//            }
//            LocalTime inicioAula = horarioEntrada.plusMinutes(minutosPorAula * ordem);
//            LocalTime fimAula = inicioAula.plusMinutes(minutosPorAula);
//
//            if (ordem>=aulasAteIntervalo){
//                System.out.println("Ordem da aula: " + ordem + ", Ajustando início da aula para intervalo.");
//                System.out.println("Minutos adicionados pro intervalo "+tipoDeCurso.getIntevarloMinutos());
//                inicioAula = inicioAula.plusMinutes(-tipoDeCurso.getIntevarloMinutos());
//                fimAula = fimAula.plusMinutes(tipoDeCurso.getIntevarloMinutos());
//            }
//
//            System.out.println("inicioAula: " + inicioAula+" de"+aula.getUnidadeCurricular().getNome());
////            if (horarioEntrada.plusMinutes(minutosPorAula*aulasAteIntervalo).isAfter(inicioAula)){
////                inicioAula.plusMinutes(minutosDeIntervalo);
////            }
//            System.out.println("inicioAula ajustado: " + inicioAula);
//            System.out.println("Ordem da aula: " + ordem +
//                    ", Início: " + inicioAula +
//                    ", Horário de entrada: " + horarioEntrada);
//            System.out.println("Fim da aula: " + fimAula);
//            if (agora.isAfter(inicioAula) && agora.isBefore(fimAula)) {
//                System.out.println("retornando aula atual: " + aula.getUnidadeCurricular().getNome());
//                return aula;
//            }
//            System.out.println("Aula: " + aula.getUnidadeCurricular().getNome() +
//                    ", Início: " + inicioAula +
//                    ", Fim: " + fimAula +
//                    ", Ordem: " + ordem);
//        }
//
//        throw new RuntimeException("Nenhuma aula acontecendo neste momento.");
//    }

}
