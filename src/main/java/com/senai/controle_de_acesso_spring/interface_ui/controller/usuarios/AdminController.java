package com.senai.controle_de_acesso_spring.interface_ui.controller.usuarios;

import com.senai.controle_de_acesso_spring.application.dto.usuarios.AdminDto;
import com.senai.controle_de_acesso_spring.application.service.usuarios.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
@AllArgsConstructor
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping
    public ResponseEntity<Void>cadastrarAdmin(@RequestBody AdminDto adminDto) {
        adminService.cadastrarAdmin(adminDto);
        return  ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarAdmin(@RequestBody AdminDto adminDto, @PathVariable Long id){
        adminService.atualizarAdmin(id, adminDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AdminDto>> listarAdmins(){
        return ResponseEntity.ok().body(adminService.listarAdminsAtivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminDto> buscarAdminPorId(@PathVariable Long id){
        return adminService.buscarPorId(id).
                map(ResponseEntity::ok).
                orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativarAdmin(@PathVariable Long id){
        adminService.inativarAdmin(id);
        return ResponseEntity.noContent().build();
    }
}
