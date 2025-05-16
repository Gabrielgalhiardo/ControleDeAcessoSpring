package com.senai.controle_de_acesso_spring.application.service.usuarios;

import com.senai.controle_de_acesso_spring.application.dto.usuarios.AdminDto;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Admin;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDoUsuario;
import com.senai.controle_de_acesso_spring.domain.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public void cadastrarAdmin(AdminDto adminDto) {
        adminRepository.save(adminDto.fromDTO());
    }

    public List<AdminDto> listarAdminsAtivos() {
                 return adminRepository.findByStatusDoUsuario(StatusDoUsuario.ATIVO)
                         .stream().map(AdminDto::toDTO)
                         .collect(Collectors.toList());
    }

    public Optional<AdminDto> buscarPorId(Long id) {
        return adminRepository.findById(id)
                .filter(a -> a.getStatusDoUsuario()
                .equals(StatusDoUsuario.ATIVO))
                .map(AdminDto::toDTO);
    }

    public boolean atualizarAdmin(Long id, AdminDto adminDto) {
        return adminRepository.findById(id).map(admin -> {
            Admin adminAtualizado = adminDto.fromDTO();
            admin.setNome(adminAtualizado.getNome());
            admin.setCpf(adminAtualizado.getCpf());
            admin.setEmail(adminAtualizado.getEmail());
            admin.setDataNascimento(adminAtualizado.getDataNascimento());
            adminRepository.save(admin);
            return true;
        }).orElse(false);
    }

    public boolean inativarAdmin(Long id) {
        return  adminRepository.findById(id).map(admin -> {
            admin.setStatusDoUsuario(StatusDoUsuario.ATIVO);
            adminRepository.save(admin);
            return true;
        }).orElse(false);
    }
}
