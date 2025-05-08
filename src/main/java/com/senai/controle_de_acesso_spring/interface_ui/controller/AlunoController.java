package com.senai.controle_de_acesso_spring.interface_ui.controller;

import com.senai.controle_de_acesso_spring.application.dto.users.AlunoDto;
import com.senai.controle_de_acesso_spring.application.service.AlunoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alunos")
@AllArgsConstructor
public class AlunoController {

    @Autowired
    AlunoService alunoService;

    @PostMapping
    public ResponseEntity<Void>cadastrarUsuario(@RequestBody AlunoDto dto){
        alunoService.cadastrarUsuario(dto);
        return ResponseEntity.ok().build();
    }

}
