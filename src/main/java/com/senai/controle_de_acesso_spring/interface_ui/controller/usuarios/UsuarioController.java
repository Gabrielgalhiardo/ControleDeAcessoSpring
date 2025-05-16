package com.senai.controle_de_acesso_spring.interface_ui.controller.usuarios;

import com.senai.controle_de_acesso_spring.application.dto.usuarios.UsuarioDto;
import com.senai.controle_de_acesso_spring.application.service.usuarios.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Void> cadastrarUsuario(@RequestBody UsuarioDto dto) {
        usuarioService.cadastrarUsuario(dto);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarUsuario(@RequestBody UsuarioDto usuarioNovo, @PathVariable long id){
        usuarioService.atualizar(id, usuarioNovo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDto>> listarUsuarios(){
      return ResponseEntity.ok().body(usuarioService.listarAtivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> listarUsuarioPeloId(@PathVariable long id){
        return ResponseEntity.ok().body(usuarioService.buscarPorId(id).orElseThrow(() -> new RuntimeException("Usuario não encontrado")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuarioPeloId(@PathVariable long id){
        usuarioService.inativar(id);
        return ResponseEntity.noContent().build();
    }
}
