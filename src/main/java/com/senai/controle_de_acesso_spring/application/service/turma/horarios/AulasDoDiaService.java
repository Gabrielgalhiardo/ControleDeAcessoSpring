package com.senai.controle_de_acesso_spring.application.service.turma.horarios;

import com.senai.controle_de_acesso_spring.application.dto.turma.horario.AulasDoDiaDTO;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.Aula;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.AulasDoDia;
import com.senai.controle_de_acesso_spring.domain.model.enums.DiaDaSemana;
import com.senai.controle_de_acesso_spring.domain.repository.curso.AmbienteRepository;
import com.senai.controle_de_acesso_spring.domain.repository.curso.UnidadeCurricularRepository;
import com.senai.controle_de_acesso_spring.domain.repository.turma.horarios.AulasDoDiaRepository;
import com.senai.controle_de_acesso_spring.domain.repository.usuarios.ProfessorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AulasDoDiaService {

    @Autowired
    private AulasDoDiaRepository aulasDoDiaRepo;

    @Autowired
    private AulaService aulaService;

    @Transactional
    public AulasDoDia adicionarAulaDoDia(AulasDoDiaDTO aulasDoDiaDTO){
            return aulasDoDiaRepo.save(toEntity(aulasDoDiaDTO));
    }
    @Transactional
    public List<AulasDoDia> adicionarAulaDoDia(List<AulasDoDiaDTO> aulasDoDia){
            return aulasDoDiaRepo.saveAll(aulasDoDia.stream().map(this::toEntity).collect(Collectors.toList()));
    }
    public AulasDoDia toEntity(AulasDoDiaDTO aulasDoDiaDTO) {
        AulasDoDia aulasDoDia = new AulasDoDia();
        aulasDoDia.setDiaDaSemana(aulasDoDiaDTO.fromDTO().getDiaDaSemana());
        aulasDoDia.setHorario(aulasDoDiaDTO.fromDTO().getHorario());
        List<Aula> aula = aulaService.salvarAulas(aulasDoDiaDTO.aulas());
        aulasDoDia.setAulas(aula);
        return aulasDoDia;
    }

        public List<AulasDoDiaDTO> listarAulasDoDia(){
            return aulasDoDiaRepo.findAll()
                    .stream().map(AulasDoDiaDTO::toDTO)
                    .collect(Collectors.toList());
        }

        public Optional<AulasDoDiaDTO> buscarPorId(Long id){
            return aulasDoDiaRepo.findById(id)
                    .map(AulasDoDiaDTO::toDTO);
        }

        public boolean atualizarAulasDoDia(Long id, AulasDoDiaDTO aulasDoDiaDTO){
            return aulasDoDiaRepo.findById(id).map(aulasDoDia -> {
                AulasDoDia aulasDoDiaAtualizado = aulasDoDiaDTO.fromDTO();
                aulasDoDia.setAulas(aulasDoDiaAtualizado.getAulas());
                aulasDoDia.setDiaDaSemana(aulasDoDiaAtualizado.getDiaDaSemana());
                aulasDoDia.setHorario(aulasDoDiaAtualizado.getHorario());
                aulasDoDiaRepo.save(aulasDoDia);
                return true;
            }).orElse(false);
        }

        public boolean deletar(Long id){
            return aulasDoDiaRepo.findById(id).map(aulasDoDia -> {
                aulasDoDiaRepo.delete(aulasDoDia);
                return true;
            }).orElse(false);
        }
}
