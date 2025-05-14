package com.senai.controle_de_acesso_spring.interface_ui.controller;

import com.senai.controle_de_acesso_spring.application.dto.users.AdminDto;
import com.senai.controle_de_acesso_spring.application.service.AdminService;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Admin;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping
    public ResponseEntity<Admin> criarAdmin(@RequestBody Admin admin){
    }
}
