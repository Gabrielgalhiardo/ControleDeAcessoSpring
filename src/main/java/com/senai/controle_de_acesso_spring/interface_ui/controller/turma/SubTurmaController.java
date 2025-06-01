package com.senai.controle_de_acesso_spring.interface_ui.controller.turma;

import com.senai.controle_de_acesso_spring.application.dto.turma.SubTurmaDTO;
import com.senai.controle_de_acesso_spring.application.service.turma.SubTurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subturma")
public class SubTurmaController {

    @Autowired
    private SubTurmaService subTurmaService;

    @PostMapping
    public ResponseEntity<Void> cadastrarSubTurma(@RequestBody SubTurmaDTO subTurmaDTO) {
        subTurmaService.criarSubTurma(subTurmaDTO);
        return ResponseEntity.ok().build();
    }
}
