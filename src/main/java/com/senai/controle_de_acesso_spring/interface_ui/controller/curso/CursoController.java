package com.senai.controle_de_acesso_spring.interface_ui.controller.curso;

import com.senai.controle_de_acesso_spring.application.dto.curso.CursoDto;
import com.senai.controle_de_acesso_spring.application.service.curso.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/curso")
public class CursoController {
    @Autowired
    private CursoService cursoService;

    @PostMapping
    public ResponseEntity<Void> cadastrarCurso(@RequestBody CursoDto cursoDto){
        cursoService.cadastrarCurso(cursoDto);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoDto> buscarCursoPorId(@PathVariable Long id){
        return cursoService.buscarPorId(id)
                .map(cursoDto -> ResponseEntity.ok().body(cursoDto))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<CursoDto>> listarCursos(){
        return ResponseEntity.ok().body(cursoService.listarCursos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarCurso(@PathVariable Long id, @RequestBody CursoDto cursoDto){
        if (cursoService.atualizarCurso(id, cursoDto)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativarCurso(@PathVariable Long id){
        if (cursoService.inativarCurso(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
