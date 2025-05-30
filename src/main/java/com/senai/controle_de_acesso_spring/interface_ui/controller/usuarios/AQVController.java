package com.senai.controle_de_acesso_spring.interface_ui.controller.usuarios;


import com.senai.controle_de_acesso_spring.application.service.usuarios.AQVService;
import com.senai.controle_de_acesso_spring.application.dto.usuarios.AQVDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aqv")
public class AQVController {

    @Autowired
    AQVService aqvService;

    @PostMapping
    public ResponseEntity<Void>cadastrarAQV(@RequestBody AQVDto aqvDto){
        aqvService.cadastrarAQV(aqvDto);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{id}")
    public  ResponseEntity<AQVDto>buscarAqvPorID(@PathVariable Long id){
        return aqvService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<AQVDto>>listarAqvsAtivos(){
        return ResponseEntity.ok(aqvService.listarAqvsAtivos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarAQV(@PathVariable Long id, @RequestBody AQVDto aqvDto) {
        if (aqvService.atualizarAQV(id, aqvDto)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>inativarAQV(@PathVariable Long id){
        if (aqvService.inativarAQV(id)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
