package com.senai.controle_de_acesso_spring.interface_ui.controller.turma;

import com.senai.controle_de_acesso_spring.application.dto.turma.TurmaDto;
import com.senai.controle_de_acesso_spring.application.service.turma.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turmas")
public class TurmaController {
    @Autowired
    TurmaService turmaService;

    @PostMapping
    public ResponseEntity<Void> cadastrarTurma(@RequestBody TurmaDto turmaDto){
        turmaService.salvarTurma(turmaDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurmaDto> buscarPorId(@PathVariable Long id){
        return turmaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<TurmaDto>> listarAtivos(){
        return ResponseEntity.ok(turmaService.listarTurmasAtivos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarTurma(@PathVariable Long id, @RequestBody TurmaDto turmaDto){
        if (turmaService.atualizarTurma(id, turmaDto)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativar(@PathVariable Long id){
        if (turmaService.inativar(id)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
