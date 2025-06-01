package com.senai.controle_de_acesso_spring.application.service.usuarios.aluno;

import com.senai.controle_de_acesso_spring.application.dto.usuarios.aluno.AlunoDto;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.SubTurma;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Aluno;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDoUsuario;
import com.senai.controle_de_acesso_spring.domain.repository.usuarios.aluno.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    public void cadastrarAluno(AlunoDto alunoDto) {
        alunoRepository.save(alunoDto.fromDTO());
    }

    public List<AlunoDto> listarAlunosAtivos() {
        return alunoRepository.findByStatusDoUsuario(StatusDoUsuario.ATIVO).stream().map(AlunoDto::toDTO).collect(Collectors.toList());
    }

    public void associarAluno(SubTurma subTurma, List<Long> alunosIds) {
        List<Aluno> alunos = alunosIds.stream()
                .map(alunoId -> alunoRepository.findById(alunoId)
                        .orElseThrow(() -> new RuntimeException("Aluno não encontrado com ID: " + alunoId)))
                .collect(Collectors.toList());
        subTurma.setAlunos(alunos);
    }


    public Optional<AlunoDto> buscarAlunoPorId(Long id) {
        return alunoRepository.findById(id).filter(a -> a.getStatusDoUsuario().equals(StatusDoUsuario.ATIVO)).map(AlunoDto::toDTO);
    }

    public boolean atualizarAluno(Long id, AlunoDto alunoDto) {
        return alunoRepository.findById(id).map(alunoAntigo -> {
            Aluno alunoAtualizado = alunoDto.fromDTO();
            alunoAntigo.setNome(alunoAtualizado.getNome());
            alunoAntigo.setEmail(alunoAtualizado.getEmail());
            alunoAntigo.setDataNascimento(alunoAtualizado.getDataNascimento());
            alunoAntigo.setCpf(alunoAtualizado.getCpf());
            alunoRepository.save(alunoAntigo);
            return true;
        }).orElse(false);
    }

    public boolean inativarAluno(Long id) {
        return alunoRepository.findById(id).map(aluno -> {
            aluno.setStatusDoUsuario(StatusDoUsuario.INATIVO);
            alunoRepository.save(aluno);
            return true;
        }).orElse(false);
    }

    public boolean validarIdadeAluno(Long id, AlunoDto alunoDto) {
        return alunoRepository.findById(id).map(idadeAluno -> {
            int anoNascimentoAluno = alunoDto.dataNascimento().getYear();
            int mesNascimentoAluno = alunoDto.dataNascimento().getMonthValue();
            int diaNascimentoAluno = alunoDto.dataNascimento().getDayOfMonth();

            LocalDate dataAtual = LocalDate.now();
            int anoAtual = dataAtual.getYear();
            int mesAtual = dataAtual.getMonthValue();
            int diaAtual = dataAtual.getDayOfMonth();

            int idade = anoAtual - anoNascimentoAluno;

            if (mesNascimentoAluno > mesAtual || (mesNascimentoAluno == mesAtual && diaNascimentoAluno > diaAtual)){
                idade--;
            }
            return idade >= 18;
        }).orElse(false);
    }
}
