package com.senai.controle_de_acesso_spring.interface_ui.controller.usuarios.aluno;

import com.senai.controle_de_acesso_spring.application.dto.usuarios.aluno.JustificativaDto;
import com.senai.controle_de_acesso_spring.application.service.usuarios.aluno.JustificativaService;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDaJustificativa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/justificativas")
public class JustificativaController {

    @Autowired
    JustificativaService justificativaService;

    @PostMapping
    public ResponseEntity<Void> cadastrarJustificativa(@RequestBody JustificativaDto justificativaDto) {
        justificativaService.cadastrarJustificativa(justificativaDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JustificativaDto> buscarJustificativaPorId(@PathVariable Long id) {
        return justificativaService.buscarJustificativaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<JustificativaDto>> listarJustificativasAtivas() {
        return ResponseEntity.ok(justificativaService.listarJustificativas());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarJustificativa(@PathVariable Long id, @RequestBody JustificativaDto justificativaDto) {
        if (justificativaService.atualizarJustificativa(id, justificativaDto)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> mudarStatusDaJustificativa(@PathVariable Long id, @RequestBody StatusDaJustificativa statusDaJustificativa) {
        if (justificativaService.mudarStatusDaJustificativa(id, statusDaJustificativa)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
