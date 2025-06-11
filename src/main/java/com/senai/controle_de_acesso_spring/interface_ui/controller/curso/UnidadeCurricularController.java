package com.senai.controle_de_acesso_spring.interface_ui.controller.curso;

import com.senai.controle_de_acesso_spring.application.dto.curso.AmbienteDto;
import com.senai.controle_de_acesso_spring.application.dto.curso.UnidadeCurricularDto;
import com.senai.controle_de_acesso_spring.application.service.curso.UnidadeCurricularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unidade-curricular")
public class UnidadeCurricularController {
    @Autowired
    private UnidadeCurricularService unidadeCurricularService;

//    public ResponseEntity<Void> cadastrarUnidadeCurricular(@RequestBody UnidadeCurricularDto unidadeCurricularDto) {
//        unidadeCurricularService.cadastrarUnidadeCurricular(unidadeCurricularDto);
//        return ResponseEntity.ok().build();
//    }

    @GetMapping("/{id}")
    public ResponseEntity<UnidadeCurricularDto> buscarUnidadeCurricularPorId(@PathVariable Long id) {
        return unidadeCurricularService.buscarPorId(id).
                map(ResponseEntity::ok).
                orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UnidadeCurricularDto>> listarUnidadeCurricular() {
        return ResponseEntity.ok(unidadeCurricularService.listarUnidadeCurricular());
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Void> atualizarUnidadeCurricular(@PathVariable Long id, RequestBody UnidadeCurricularDto unidadeCurricularDto) {
//        if (unidadeCurricularService.atualizarUnidadeCurricular(id, unidadeCurricularDto)) {
//            return ResponseEntity.ok().build();
//        }
//        return ResponseEntity.notFound().build();
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativarUnidadeCurricular(@PathVariable Long id) {
        if (unidadeCurricularService.inativarUnidadeCurricular(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
