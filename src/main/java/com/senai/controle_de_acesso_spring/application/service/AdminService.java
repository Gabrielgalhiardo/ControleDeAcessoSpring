package com.senai.controle_de_acesso_spring.application.service;

import com.senai.controle_de_acesso_spring.application.dto.users.AdminDto;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Admin;
import com.senai.controle_de_acesso_spring.domain.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public void cadastrarAdmin(AdminDto dto) {
        Admin admin = new Admin();

        admin.setNome(dto.nome());
        admin.setCpf(dto.cpf());
        admin.setEmail(dto.email());
        admin.setDataNascimento(dto.dataNascimento());
        admin.setIdAcesso("");
        admin.setSenha("");

        adminRepository.save(admin);
    }

    public void atualizarAdminPeloId(Long id, AdminDto adminDto) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Admin não encontrado"));

        admin.setNome(adminDto.nome());
        admin.setCpf(adminDto.cpf());
        admin.setEmail(adminDto.email());
        admin.setDataNascimento(adminDto.dataNascimento());
        admin.setIdAcesso("");
        admin.setSenha("");

        adminRepository.save(admin);
    }

    public List<AdminDto> pegarTodosOsAdmin() {
        return adminRepository.findAll().stream().map(admin -> new AdminDto(admin.getId(),
                admin.getNome(),
                admin.getCpf(),
                admin.getEmail(),
                admin.getDataNascimento())).toList();
    }

    public AdminDto pegarUmAdminPeloId(Long id) {
        Admin admin = adminRepository.findById(id).orElseThrow(() -> new RuntimeException("Admin não encontrado"));
        AdminDto adminDto = new AdminDto(admin.getId(),
                admin.getNome(),
                admin.getCpf(),
                admin.getEmail(),
                admin.getDataNascimento());
        return adminDto;
    }

    public void deletarAdminPeloId(Long id) {
        adminRepository.deleteById(id);
    }
}
