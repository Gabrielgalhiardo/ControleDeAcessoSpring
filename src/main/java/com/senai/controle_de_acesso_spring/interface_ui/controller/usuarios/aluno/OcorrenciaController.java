package com.senai.controle_de_acesso_spring.interface_ui.controller.usuarios.aluno;

import com.senai.controle_de_acesso_spring.application.dto.usuarios.aluno.OcorrenciaDto;
import com.senai.controle_de_acesso_spring.application.service.usuarios.aluno.OcorrenciaService;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDaOcorrencia;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ocorrencias")
@AllArgsConstructor
public class OcorrenciaController {

    @Autowired
    private OcorrenciaService ocorrenciaService;

    @PostMapping
    public ResponseEntity<Void> cadastrarOcorrencia(@RequestBody OcorrenciaDto ocorrenciaDto) {
        ocorrenciaService.cadastrarOcorrencia(ocorrenciaDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<OcorrenciaDto>> listarOcorrencias() {
        return ResponseEntity.ok(ocorrenciaService.listarOcorrencias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OcorrenciaDto> buscarOcorrenciaPorId(@PathVariable Long id) {
        return ocorrenciaService.buscarOcorrenciaPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<OcorrenciaDto> atualizarOcorrencia(@PathVariable Long id, @RequestBody OcorrenciaDto ocorrenciaDto) {
        if (ocorrenciaService.atualizarOcorrencia(id, ocorrenciaDto)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OcorrenciaDto> atualizarStatusDaOcorrencia(@PathVariable Long id, @RequestBody StatusDaOcorrencia statusDaOcorrencia) {
        if (ocorrenciaService.mudarStatusDaOcorrencia(id, statusDaOcorrencia)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }

}
