package com.senai.controle_de_acesso_spring.interface_ui.controller.turma.horarios;

import com.senai.controle_de_acesso_spring.application.dto.turma.horario.HorarioSemanalDTO;
import com.senai.controle_de_acesso_spring.application.service.turma.horarios.HorarioSemanalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/horario-semanal")
public class HorarioSemanalController {

    @Autowired
    private HorarioSemanalService horarioSemanalService;

    @PostMapping("/{semestreId}")
    public ResponseEntity<Void> cadastrarHorariosSemanais(@PathVariable Long semestreId, @RequestBody List<HorarioSemanalDTO> horarioSemanalDTOs) {
        horarioSemanalDTOs.forEach(dto -> dto = new HorarioSemanalDTO(
                dto.id(),
                semestreId, // força o uso do ID da URL
                dto.semanaReferencia(),
                dto.listaDeAulasDoDia()
        ));

        horarioSemanalService.cadastrarHorariosSemanais(horarioSemanalDTOs);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity<List<HorarioSemanalDTO>> listarTodos(){
        return ResponseEntity.ok(horarioSemanalService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HorarioSemanalDTO> buscarPorId(@PathVariable Long id){
        return horarioSemanalService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, HorarioSemanalDTO dto){
        if (horarioSemanalService.atualizar(id, dto)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (horarioSemanalService.deletar(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
