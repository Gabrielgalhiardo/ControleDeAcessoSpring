package com.senai.controle_de_acesso_spring.interface_ui.controller;

import com.senai.controle_de_acesso_spring.application.dto.users.AlunoDto;
import com.senai.controle_de_acesso_spring.application.dto.users.OcorrenciaDto;
import com.senai.controle_de_acesso_spring.application.service.OcorrenciaService;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Ocorrencia;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Ocorrencias")
@AllArgsConstructor
public class OcorrenciaController {

    @Autowired
    private OcorrenciaService ocorrenciaService;

    @PostMapping
    public ResponseEntity<Void> cadastrarOcorrencia(@RequestBody OcorrenciaDto ocorrenciaDto){
        ocorrenciaService.cadastrarOcorrencia(ocorrenciaDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<OcorrenciaDto>> ListarOcorrencias(){
        return ResponseEntity.ok(ocorrenciaService.listarOcorrencias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoDto> atualizarOcorrencia(@PathVariable Long id, @RequestBody OcorrenciaDto ocorrenciaDto) {
        if (ocorrenciaService.atualizarOcorrencia(id, ocorrenciaDto)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
