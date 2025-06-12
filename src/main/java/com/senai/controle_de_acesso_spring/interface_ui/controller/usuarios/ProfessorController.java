package com.senai.controle_de_acesso_spring.interface_ui.controller.usuarios;

import com.senai.controle_de_acesso_spring.application.dto.usuarios.ProfessorDto;
import com.senai.controle_de_acesso_spring.application.service.usuarios.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professores")
public class ProfessorController {
    @Autowired
    ProfessorService professorService;

    @PostMapping
    public ResponseEntity<Void> cadastrarProfessor(@RequestBody ProfessorDto professorDto){
        professorService.cadastrarProfessor(professorDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorDto> buscarPorId(@PathVariable Long id){
        return professorService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ProfessorDto>> listarAtivos() {
        return ResponseEntity.ok(professorService.listarProfessoresAtivos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarProfessor(@PathVariable Long id, @RequestBody ProfessorDto professorDto){
        if (professorService.atualizarProfessor(id, professorDto)) {
            return  ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        if (professorService.inativar(id)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


}
