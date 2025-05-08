package com.senai.controle_de_acesso_spring.interface_ui.controller;

import com.senai.controle_de_acesso_spring.application.service.AlunoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UsuarioController {

    private final AlunoService alunoService;

//    @PostMapping
//    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario){
//        alunoService.criarUsuario(usuario);
//        return ResponseEntity.status(201).body(usuario);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Void> atualizarUsuario(@RequestBody Usuario usuarioNovo, @PathVariable long id){
//        alunoService.atualizarUsuario(usuarioNovo, id);
//        return ResponseEntity.noContent().build();
//    }
//
//
//    @GetMapping
//    public ResponseEntity<List<Usuario>> listarUsuarios(){
//      return ResponseEntity.ok().body(alunoService.listarUsuarios());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Usuario> listarUsuarioPeloId(@PathVariable long id){
//        return ResponseEntity.ok().body(alunoService.listarUsuarioPeloId(id));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deletarUsuarioPeloId(@PathVariable long id){
//        alunoService.deletarUsuario(id);
//        return ResponseEntity.noContent().build();
//    }




}
