package com.senai.controle_de_acesso_spring.interface_ui.controller.turma.horarios;

import com.senai.controle_de_acesso_spring.application.dto.turma.horario.AulaDTO;
import com.senai.controle_de_acesso_spring.application.service.turma.horarios.AulaService;
import com.senai.controle_de_acesso_spring.domain.model.entity.turma.horarios.Aula;
import com.senai.controle_de_acesso_spring.domain.repository.turma.horarios.AulaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aulas")
public class AulaController {
    @Autowired
    AulaService aulaService;

    @Autowired
    private AulaRepository aulaRepository;

    @PostMapping
    public ResponseEntity<AulaDTO> criarAula(@RequestBody AulaDTO aulaDTO){
        Aula aulaSalva = aulaService.salvarAula(aulaDTO);
        return ResponseEntity.status(201).body(AulaDTO.toDTO(aulaSalva));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AulaDTO> buscarPorId(@PathVariable Long id){
        return aulaService.buscarId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping
    public ResponseEntity<List<AulaDTO>> listarAulas(){
        return ResponseEntity.ok(aulaService.listarAulas());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarAula(@PathVariable Long id, @RequestBody AulaDTO aulaDTO){
        if (aulaService.atualizarAulas(id, aulaDTO)){
           return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        if (aulaService.deletar(id)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
