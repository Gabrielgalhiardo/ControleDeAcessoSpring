package com.senai.controle_de_acesso_spring.interface_ui.controller;

import com.senai.controle_de_acesso_spring.application.dto.users.AlunoDto;
import com.senai.controle_de_acesso_spring.application.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aluno")
public class AlunoController {
    @Autowired
    private AlunoService alunoService;

    @PostMapping
    public ResponseEntity<AlunoDto> salvarAluno(@RequestBody AlunoDto alunoDto){
        alunoService.salvarAluno(alunoDto);
        return ResponseEntity.status(201).body(alunoDto);
    }

}
