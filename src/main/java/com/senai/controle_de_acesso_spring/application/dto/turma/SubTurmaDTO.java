package com.senai.controle_de_acesso_spring.application.dto.turma;

import com.senai.controle_de_acesso_spring.application.dto.usuarios.aluno.AlunoDto;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.Semestre;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.SubTurma;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.Turma;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Aluno;

import java.util.List;
import java.util.stream.Collectors;

public record SubTurmaDTO(
        Long id,
        String nome,
        TurmaDto turmaDto,
        List<AlunoDto> alunoDtos,
        List<SemestreDTO> semestreDTOS
) {
    public static SubTurmaDTO toDTO(SubTurma s) {
        return new SubTurmaDTO(
                s.getId(),
                s.getNome(),
                TurmaDto.toDTO(s.getTurma()),
                s.getAlunos().stream()
                        .map(AlunoDto::toDTO)
                        .collect(Collectors.toList()),
                s.getSemestres().stream()
                        .map(SemestreDTO::toDTO)
                        .collect(Collectors.toList())
        );
    }

    public SubTurma fromDTO() {
        SubTurma subTurma = new SubTurma();
        subTurma.setNome(nome);
        subTurma.setTurma(turmaDto.fromDTO());
        List<Aluno> alunosConvertidos = alunoDtos.stream()
                .map(AlunoDto::fromDTO)
                .collect(Collectors.toList());
        alunosConvertidos.forEach(a -> a.setSubTurma(subTurma));
        subTurma.setAlunos(alunosConvertidos);

        List<Semestre> semestresConvertidos = semestreDTOS.stream()
                .map(SemestreDTO::fromDTO)
                .collect(Collectors.toList());
        semestresConvertidos.forEach(s -> s.setSubTurma(subTurma));
        subTurma.setSemestres(semestresConvertidos);
        return subTurma;
    }
}
