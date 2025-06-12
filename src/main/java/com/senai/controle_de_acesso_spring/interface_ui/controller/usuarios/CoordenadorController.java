package com.senai.controle_de_acesso_spring.interface_ui.controller.usuarios;

import com.senai.controle_de_acesso_spring.application.dto.usuarios.CoordenadorDto;
import com.senai.controle_de_acesso_spring.application.service.usuarios.CoordenadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coordenadores")
public class CoordenadorController {

    @Autowired
    CoordenadorService coordenadorService;

    @PostMapping
    public ResponseEntity<Void> cadastrarCoordenador(@RequestBody CoordenadorDto coordenadorDto) {
        coordenadorService.cadastrarCoordenador(coordenadorDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoordenadorDto> buscarCoordenadorPorId(@PathVariable Long id) {
        return coordenadorService.buscarPorId(id).
                map(ResponseEntity::ok).
                orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<CoordenadorDto>> listarCoordenadoresAtivos() {
        return ResponseEntity.ok(coordenadorService.listarCoordenadoresAtivos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarCoordenador(@PathVariable Long id, @RequestBody CoordenadorDto coordenadorDto) {
        if (coordenadorService.atualizarCoordenador(id, coordenadorDto)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativarCoordenador(@PathVariable Long id) {
        if (coordenadorService.inativarCoordenador(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
