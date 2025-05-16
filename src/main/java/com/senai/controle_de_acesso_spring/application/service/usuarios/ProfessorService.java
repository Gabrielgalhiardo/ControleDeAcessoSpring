package com.senai.controle_de_acesso_spring.application.service.usuarios;

import com.senai.controle_de_acesso_spring.application.dto.usuarios.ProfessorDto;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Professor;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDoUsuario;
import com.senai.controle_de_acesso_spring.domain.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfessorService {
    @Autowired
    private ProfessorRepository professorRepo;

    public void cadastrarProfessor(ProfessorDto professorDto){
        professorRepo.save(professorDto.fromDTO());
    }

    public List<ProfessorDto> listarProfessoresAtivos(){
        return professorRepo.findByStatusDoUsuario(StatusDoUsuario.ATIVO)
                .stream().map(ProfessorDto::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProfessorDto> buscarPorId(Long id){
       return professorRepo.findById(id)
                .filter( p -> p.getStatusDoUsuario().equals(StatusDoUsuario.ATIVO))
                .map(ProfessorDto::toDTO);
    }

  public boolean atualizarProfessor(Long id, ProfessorDto professorDto){
        return professorRepo.findById(id).map(professor -> {
            Professor professorAtualizado = professorDto.fromDTO();
            professor.setNome(professorAtualizado.getNome());
            professor.setEmail(professorAtualizado.getEmail());
            professor.setDataNascimento(professorAtualizado.getDataNascimento());
            professor.setCpf(professorAtualizado.getCpf());
            professorRepo.save(professor);
            return true;
        }).orElse(false);
  }

    public boolean inativar(Long id){
        return professorRepo.findById(id).map(professor -> {
            professor.setStatusDoUsuario(StatusDoUsuario.INATIVO);
            professorRepo.save(professor);
            return true;
        }).orElse(false);
    }


}
