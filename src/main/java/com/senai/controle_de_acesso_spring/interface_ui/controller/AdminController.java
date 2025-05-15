package com.senai.controle_de_acesso_spring.interface_ui.controller;

import com.senai.controle_de_acesso_spring.application.dto.users.AdminDto;
import com.senai.controle_de_acesso_spring.application.service.AdminService;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Admin;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping
    public ResponseEntity<Void>cadastrarAdmin(@RequestBody AdminDto dto) {
        adminService.cadastrarAdmin(dto);
        return  ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarAdmin(@RequestBody AdminDto adminDto, @PathVariable Long id){
        adminService.atualizarAdminPeloId(id, adminDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AdminDto>> listarAdmins(){
        return ResponseEntity.ok().body(adminService.pegarTodosOsAdmin());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminDto> listarAdminPeloId(@PathVariable Long id){
        return ResponseEntity.ok().body(adminService.pegarUmAdminPeloId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAdminPeloId(@PathVariable Long id){
        adminService.deletarAdminPeloId(id);
        return ResponseEntity.noContent().build();
    }
}
