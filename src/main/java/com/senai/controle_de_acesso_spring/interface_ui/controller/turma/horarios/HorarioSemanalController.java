package com.senai.controle_de_acesso_spring.interface_ui.controller.turma.horarios;

import com.senai.controle_de_acesso_spring.application.dto.turma.horario.HorarioSemanalDTO;
import com.senai.controle_de_acesso_spring.application.service.turma.horarios.HorarioSemanalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/horarioSemanal")
public class HorarioSemanalController {

    @Autowired
    private HorarioSemanalService horarioSemanalService;

    @PostMapping
    public ResponseEntity<Void> cadastrarHorariosSemanais(HorarioSemanalDTO horarioSemanalDTO) {
        horarioSemanalService.cadastrarHorariosSemanais(horarioSemanalDTO);
        return ResponseEntity.status(201).build();
    }
}
