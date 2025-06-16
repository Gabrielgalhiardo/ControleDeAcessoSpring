package com.senai.controle_de_acesso_spring.interface_ui.controller.curso;

import com.senai.controle_de_acesso_spring.application.dto.curso.UnidadeCurricularDto;
import com.senai.controle_de_acesso_spring.application.dto.curso.unidade_curricular.UnidadeCurricularProfessorDTO;
import com.senai.controle_de_acesso_spring.application.service.curso.UnidadeCurricularService;
import com.senai.controle_de_acesso_spring.domain.model.entity.curso.UnidadeCurricular;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/unidades-curriculares")
public class UnidadeCurricularController {
    @Autowired
    private UnidadeCurricularService unidadeCurricularService;

    public ResponseEntity<Void> cadastrarUnidadeCurricular(UnidadeCurricularDto unidadeCurricularDto) {
//        unidadeCurricularService.cadastrarUnidadeCurricular(unidadeCurricularDto);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/{idAluno}/aluno")
    public ResponseEntity<List<UnidadeCurricularProfessorDTO>> listarUnidadesCurricularesDoAluno(@PathVariable Long idAluno) {
        System.out.println("Listando unidades curriculares do aluno com ID: " + idAluno);
        return ResponseEntity.ok(unidadeCurricularService.listarUnidadesCurricularesDoAluno(idAluno));
    }
}
