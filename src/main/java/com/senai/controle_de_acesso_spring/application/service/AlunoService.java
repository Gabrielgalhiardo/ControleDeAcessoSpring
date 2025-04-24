package com.senai.controle_de_acesso_spring.application.service;

import com.senai.controle_de_acesso_spring.application.dto.ResponsavelDoAlunoDto;
import com.senai.controle_de_acesso_spring.application.dto.users.AlunoDto;
import com.senai.controle_de_acesso_spring.domain.model.entity.ResponsavelDoAluno;
import com.senai.controle_de_acesso_spring.domain.model.entity.users.Aluno;
import com.senai.controle_de_acesso_spring.domain.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoDto salvarAluno(AlunoDto alunoDto){
        Aluno aluno = new Aluno();
        aluno.setNome(alunoDto.nome());
        aluno.setEmail(alunoDto.email());
        aluno.setTelefoneCelular(alunoDto.telefoneCelular());
        aluno.setTelefoneFixo(alunoDto.telefoneFixo());
        aluno.setCpf(alunoDto.cpf());
        aluno.setSenha(alunoDto.senha());
        aluno.setStatusDoUsuario(alunoDto.statusDoUsuario());
        aluno.setDataDeNascimento(alunoDto.dataDeNascimento());
        aluno.setIdade(alunoDto.idade());
        aluno.setResponsaveisDoAluno(mapResponsaveis(alunoDto.responsaveisDoAluno()));
        aluno.setTurma(alunoDto.turmas());
        return alunoDto;
    }

    public List<ResponsavelDoAluno> mapResponsaveis(List<ResponsavelDoAlunoDto> responsavelDoAlunoDtos){
        return responsavelDoAlunoDtos.stream().map(responsavelDoAlunoDto -> {
                    ResponsavelDoAluno responsavelDoAluno = new ResponsavelDoAluno();
                    responsavelDoAluno.setNome(responsavelDoAlunoDto.nome());
                    responsavelDoAluno.setEmail(responsavelDoAlunoDto.email());
                    responsavelDoAluno.setCpf(responsavelDoAlunoDto.cpf());
                    responsavelDoAluno.setTelefoneFixo(responsavelDoAlunoDto.telefoneFixo());
                    responsavelDoAluno.setTelefoneCelular(responsavelDoAlunoDto.telefoneCelular());
            return responsavelDoAluno;
        }).collect(Collectors.toList());
    }


}
