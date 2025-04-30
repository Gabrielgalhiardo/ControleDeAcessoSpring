package com.senai.controle_de_acesso_spring.interface_ui.controller;

import com.senai.controle_de_acesso_spring.application.dto.users.AlunoDto;
import com.senai.controle_de_acesso_spring.application.service.AlunoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aluno")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService alunoService;

    @PostMapping
    public ResponseEntity<AlunoDto> salvarAluno(@RequestBody AlunoDto alunoDto){
        alunoService.salvarAluno(alunoDto);
        return ResponseEntity.status(201).body(alunoDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoDto> buscarAlunoPorId(@PathVariable Long id){
        AlunoDto alunoDto = alunoService.buscarAlunoPorId(id);
        return ResponseEntity.ok(alunoDto);
    }

    @GetMapping
    public ResponseEntity<List<AlunoDto>> pegarTodosAlunos(){
        List<AlunoDto> alunos = alunoService.pegarTodosAlunos();
        return ResponseEntity.ok(alunos);
    }

    @PutMapping
    public ResponseEntity<Void> atualizarAluno(@PathVariable Long id, AlunoDto alunoDto) {
        alunoService.atualizarAluno(id, alunoDto);
        return ResponseEntity.status(204).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAluno(@PathVariable Long id) {
        alunoService.deletarAluno(id);
        return ResponseEntity.status(204).build();
    }



}
