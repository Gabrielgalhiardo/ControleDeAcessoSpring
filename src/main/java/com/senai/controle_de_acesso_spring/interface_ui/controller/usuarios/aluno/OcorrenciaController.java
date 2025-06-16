package com.senai.controle_de_acesso_spring.interface_ui.controller.usuarios.aluno;

import com.senai.controle_de_acesso_spring.application.dto.auth.OcorrenciaDTO;
import com.senai.controle_de_acesso_spring.application.service.usuarios.aluno.OcorrenciaService;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDaOcorrencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OcorrenciaController {

    @Autowired
    private OcorrenciaService ocorrenciaService;

    @MessageMapping("/ocorrencia/saida")
    public void solicitarSaida(@Payload OcorrenciaDTO dto) {
        ocorrenciaService.solicitarSaidaAntecipada(dto);
    }

    @MessageMapping("/ocorrencia/decisao")
    public void decidirSaida(@Payload OcorrenciaDTO dto) {
        ocorrenciaService.decidir(dto);
    }

    @MessageMapping("/ocorrencia/ciencia")
    public void darCiencia(@Payload OcorrenciaDTO dto) {
        ocorrenciaService.confirmarCiencia(dto);
    }

    @GetMapping("/ola")
    public ResponseEntity<?> ola() {
        return ResponseEntity.ok("ok");
    }


    //    @PostMapping
//    public ResponseEntity<Void> cadastrarOcorrencia(@RequestBody OcorrenciaDto ocorrenciaDto) {
//        ocorrenciaService.cadastrarOcorrencia(ocorrenciaDto);
//        return ResponseEntity.ok().build();
//    }
//
    public void criarOcorrenciaDeAtraso(String idAcesso) {
        ocorrenciaService.criarOcorrenciaDeAtraso(idAcesso);
    }


    @GetMapping
    public ResponseEntity<List<OcorrenciaDTO>> listarOcorrencias() {
        return ResponseEntity.ok(ocorrenciaService.listarOcorrencias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OcorrenciaDTO> buscarOcorrenciaPorId(@PathVariable Long id) {
        return ocorrenciaService.buscarOcorrenciaPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<OcorrenciaDTO> atualizarOcorrencia(@PathVariable Long id, @RequestBody OcorrenciaDTO ocorrenciaDto) {
        if (ocorrenciaService.atualizarOcorrencia(id, ocorrenciaDto)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/decidir")
    public ResponseEntity<OcorrenciaDTO> atualizarStatusDaOcorrencia(@RequestBody OcorrenciaDTO ocorrenciaDTO) {

        ocorrenciaService.decidir(ocorrenciaDTO);

        return ResponseEntity.ok().build();

    }

}
