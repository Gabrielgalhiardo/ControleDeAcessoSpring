package com.senai.controle_de_acesso_spring.interface_ui.controller.curso;

import com.senai.controle_de_acesso_spring.application.dto.curso.UnidadeCurricularDto;
import com.senai.controle_de_acesso_spring.application.service.curso.UnidadeCurricularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/unidade-curricular")
public class UnidadeCurricularController {
    @Autowired
    private UnidadeCurricularService unidadeCurricularService;

    public ResponseEntity<Void> cadastrarUnidadeCurricular(UnidadeCurricularDto unidadeCurricularDto) {
//        unidadeCurricularService.cadastrarUnidadeCurricular(unidadeCurricularDto);
        return ResponseEntity.status(201).build();
    }
}
