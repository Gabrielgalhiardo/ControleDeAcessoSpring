package com.senai.controle_de_acesso_spring.application.service;

import com.senai.controle_de_acesso_spring.application.dto.users.AlunoDto;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Aluno;
import com.senai.controle_de_acesso_spring.domain.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    public void cadastrarAluno(AlunoDto dto){
        Aluno aluno = new Aluno();

        aluno.setNome(dto.nome());
        aluno.setCpf(dto.cpf());
        aluno.setDataNascimento(dto.dataNascimento());
        aluno.setIdAcesso(dto.idAcesso());
        aluno.setEmail(dto.email());
    }

    public List<AlunoDto> listarAlunos(){
        return alunoRepository.findAll().stream().map(aluno -> new AlunoDto(
                aluno.getNome(),
                aluno.getCpf(),
                aluno.getDataNascimento(),
                aluno.getIdAcesso(),
                aluno.getEmail()
        )).toList();
    }

}
