package com.senai.controle_de_acesso_spring.application.service;

import com.senai.controle_de_acesso_spring.application.dto.users.UsuarioDto;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.*;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Aluno;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDoUsuario;
import com.senai.controle_de_acesso_spring.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.senai.controle_de_acesso_spring.domain.model.enums.TipoDeUsuario.*;

@Service
public class UsuarioService {
    @Autowired private UsuarioRepository usuarioRepository;

    public void cadastrarUsuario(UsuarioDto dto) {
        usuarioRepository.save(dto.fromDTO());
    }

    public List<UsuarioDto> listarAtivos() {
        return usuarioRepository.findByStatusDoUsuarioATIVO()
                .stream().map(UsuarioDto::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<UsuarioDto> buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .filter(u -> u.getStatusDoUsuario().equals(StatusDoUsuario.ATIVO))
                .map(UsuarioDto::toDTO);
    }

    public boolean atualizar(Long id, UsuarioDto dto) {
        return usuarioRepository.findById(id).map(usuario -> {
            Usuario usuarioAtualizado = dto.fromDTO();
            usuario.setNome(usuarioAtualizado.getNome());
            usuario.setEmail(usuarioAtualizado.getEmail());
            usuario.setDataNascimento(usuarioAtualizado.getDataNascimento());
            usuario.setCpf(usuarioAtualizado.getCpf());
            usuarioRepository.save(usuario);
            return true;
        }).orElse(false);
    }

    public boolean inativar(Long id) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setStatusDoUsuario(StatusDoUsuario.INATIVO);
            usuarioRepository.save(usuario);
            return true;
        }).orElse(false);
    }
}