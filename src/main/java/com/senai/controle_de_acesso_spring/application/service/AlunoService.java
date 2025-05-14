package com.senai.controle_de_acesso_spring.application.service;

import com.senai.controle_de_acesso_spring.application.dto.users.AlunoDto;
import com.senai.controle_de_acesso_spring.application.dto.users.UsuarioDto;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Usuario;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Aluno;
import com.senai.controle_de_acesso_spring.domain.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    public void cadastrarAluno(AlunoDto alunoDto){
        alunoRepository.save(alunoDto.fromDTO());
    }

    public List<AlunoDto> listarAlunosAtivos(){
       return alunoRepository.findByAtivoTrue().stream().map(AlunoDto::toDTO).collect(Collectors.toList());
    }

    public Optional<AlunoDto> buscarPorId(Long id, AlunoDto alunoDto){
        return alunoRepository.findById(id).map(alunoAntigo ->{
            Aluno alunoAtualizado = alunoDto.fromDTO();
            alunoAntigo.setNome(alunoAtualizado.getNome());
        })
    }



}
