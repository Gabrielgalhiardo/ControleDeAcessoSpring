package com.senai.controle_de_acesso_spring.interface_ui.controller.curso;

import com.senai.controle_de_acesso_spring.application.dto.curso.AmbienteDto;
import com.senai.controle_de_acesso_spring.application.service.curso.AmbienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ambientes")
public class AmbienteController {

    @Autowired
    AmbienteService ambienteService;

    @PostMapping
    public ResponseEntity<Void> cadastrarAmbiente(@RequestBody AmbienteDto ambienteDto) {
        ambienteService.cadastrarAmbiente(ambienteDto);
        return ResponseEntity.ok().build();
    }



    @GetMapping("/{id}")
    public ResponseEntity<AmbienteDto> buscarAmbientePorId(@PathVariable Long id) {
        return ambienteService.buscarPorId(id).
                map(ResponseEntity::ok).
                orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<AmbienteDto>> listarAmbientes() {
        return ResponseEntity.ok(ambienteService.listarAmbientesAtivos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarAmbiente(@PathVariable Long id, @RequestBody AmbienteDto ambienteDto) {
        if (ambienteService.atualizarAmbientes(id, ambienteDto)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativarAmbiente(@PathVariable Long id) {
        if (ambienteService.inativarAmbiente(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
