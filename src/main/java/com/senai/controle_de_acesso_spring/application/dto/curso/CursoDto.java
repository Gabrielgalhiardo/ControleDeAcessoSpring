package com.senai.controle_de_acesso_spring.application.dto.curso;

import com.senai.controle_de_acesso_spring.domain.model.entity.curso.Curso;
import com.senai.controle_de_acesso_spring.domain.model.entity.curso.UnidadeCurricular;
import com.senai.controle_de_acesso_spring.domain.model.enums.TipoDeCurso;
import java.util.List;

public record CursoDto(
    Long id,
    String titulo,
    TipoDeCurso tipoDeCurso,
    Integer cargaHoraria,
    Integer toleranciaMinutos,
//    Integer quantidadeDeSemestres,
    List<UnidadeCurricular> unidadesCurriculares
) {
    public static CursoDto toDTO(Curso c){
        return new CursoDto(c.getId(), c.getTitulo(), c.getTipoDeCurso(), c.getCargaHoraria(), c.getToleranciaMinutos(), c.getUnidadesCurriculares());
    }

    public Curso fromDTO(){
        Curso curso = new Curso();
        curso.setId(id);
        curso.setTitulo(titulo);
        curso.setTipoDeCurso(tipoDeCurso);
        curso.setCargaHoraria(cargaHoraria);
        curso.setToleranciaMinutos(toleranciaMinutos);
//        curso.set(quantidadeDeSemestres);
        curso.setUnidadesCurriculares(unidadesCurriculares);
        return curso;
    }

}
