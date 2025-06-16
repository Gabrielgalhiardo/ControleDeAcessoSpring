package com.senai.controle_de_acesso_spring.application.dto.curso.unidade_curricular;

import com.senai.controle_de_acesso_spring.domain.model.entity.curso.UnidadeCurricular;

public record UnidadeCurricularProfessorDTO(
    Long id,
    String nomeUnidadeCurricular,
    String nomeProfessor,
    Long professorId,
    Long unidadeCurricularId){

}

