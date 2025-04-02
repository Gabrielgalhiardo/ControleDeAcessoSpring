package com.senai.controle_de_acesso_spring.controller;

import com.senai.controle_de_acesso_spring.model.Usuario;
import com.senai.controle_de_acesso_spring.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public Usuario criarUsuario(@RequestBody Usuario usuario){
        usuarioService.criarUsuario(usuario);
        return usuario;
    }

    @PutMapping("/{id}")
    public void atualizarUsuario(@RequestBody Usuario usuarioNovo, @PathVariable long id){
        usuarioService.atualizarUsuario(usuarioNovo, id);
    }


    @GetMapping
    public List<Usuario> listarUsuarios(){
      return usuarioService.listarUsuarios();
    }

    @GetMapping("/{id}")
    public Usuario listarUsuarioPeloId(@PathVariable long id){
        return usuarioService.listarUsuarioPeloId(id);
    }

    @DeleteMapping("/{id}")
    public void deletarUsuarioPeloId(@PathVariable long id){
        System.out.println(id);
        usuarioService.deletarUsuario(id);
    }




}
