package com.senai.controle_de_acesso_spring.interface_ui.controller;

import com.senai.controle_de_acesso_spring.domain.model.Usuario;
import com.senai.controle_de_acesso_spring.domain.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario){
        usuarioService.criarUsuario(usuario);
        return ResponseEntity.status(201).body(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarUsuario(@RequestBody Usuario usuarioNovo, @PathVariable long id){
        usuarioService.atualizarUsuario(usuarioNovo, id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios(){
      return ResponseEntity.ok().body(usuarioService.listarUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> listarUsuarioPeloId(@PathVariable long id){
        return ResponseEntity.ok().body(usuarioService.listarUsuarioPeloId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuarioPeloId(@PathVariable long id){
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }




}
