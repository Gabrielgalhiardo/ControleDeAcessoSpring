package com.senai.controle_de_acesso_spring.application.service;

import com.senai.controle_de_acesso_spring.application.dto.users.ProfessorDto;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Professor;
import com.senai.controle_de_acesso_spring.domain.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {
    @Autowired
    private ProfessorRepository professorRepo;

    public void cadastrarProfessor(ProfessorDto professorDto){
        Professor professor = new Professor();
        professor.setNome(professorDto.nome());
        professor.setCpf(professorDto.cpf());
        professor.setEmail(professorDto.email());
        //Adicionar a UnidadeCurricular depois

        professorRepo.save(professor);
    }

    public List<ProfessorDto> listarProfessor(){
        return professorRepo.findAll().stream().map(professor -> new ProfessorDto(
                professor.getId(),
                professor.getNome(),
                professor.getCpf(),
                professor.getEmail()
                //Adicionar a UnidadeCurricular depois
            )
        ).toList();
    }

    public Optional<ProfessorDto> buscarPorId(Long id){
        return professorRepo.findById(id).map(professor -> new ProfessorDto(
                professor.getId(),
                professor.getNome(),
                professor.getCpf(),
                professor.getEmail()
                //Adicionar a UnidadeCurricular depois
        ));
    }

//   public boolean atualizarProfessor(Long id, ProfessorDto professorDto){
//       return professorRepo.findById(id).map(professor -> {
//                    professor.setNome(professorDto.nome());
//                    professor.setCpf(professorDto.cpf());
//                    professor.setEmail(professorDto.email());
//                    professorRepo.save(professor);
//                    //Adicionar a UnidadeCurricular depois
//        }).orElse(false);
//   }

    public boolean deletarProfessor(Long id){
        if (professorRepo.existsById(id)){
            professorRepo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }


}
