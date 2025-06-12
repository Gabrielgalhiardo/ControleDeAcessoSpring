package com.senai.controle_de_acesso_spring.interface_ui.controller.turma.horarios;

import com.senai.controle_de_acesso_spring.application.dto.turma.horario.AulasDoDiaDTO;
import com.senai.controle_de_acesso_spring.application.service.turma.horarios.AulasDoDiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aulasDoDia")
public class AulaDoDiaController {
    @Autowired
    AulasDoDiaService aulasDoDiaService;

    @PostMapping
    public ResponseEntity<Void> adicionarAulaDoDia(@RequestBody AulasDoDiaDTO aulasDoDiaDTO){
        aulasDoDiaService.adicionarAulaDoDia(aulasDoDiaDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AulasDoDiaDTO> buscarPorId(@PathVariable Long id){
        return  aulasDoDiaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<AulasDoDiaDTO>> listarAulasDoDia(){
        return ResponseEntity.ok(aulasDoDiaService.listarAulasDoDia());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarAulasDoDia(@PathVariable Long id, @RequestBody AulasDoDiaDTO aulasDoDiaDTO){
        if (aulasDoDiaService.atualizarAulasDoDia(id, aulasDoDiaDTO)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        if (aulasDoDiaService.deletar(id)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
