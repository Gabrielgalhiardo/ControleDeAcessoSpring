package com.senai.controle_de_acesso_spring.interface_ui.controller.turma.horarios;

import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.Aula;
import com.senai.controle_de_acesso_spring.domain.repository.turma.horarios.AulaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("aula")
public class AulaController {

    @Autowired
    private AulaRepository aulaRepository;

    @PostMapping
    public ResponseEntity<Aula> criarAula(@RequestBody Aula aula){
        aulaRepository.save(aula);
        return ResponseEntity.status(201).body(aula);
    }
}
