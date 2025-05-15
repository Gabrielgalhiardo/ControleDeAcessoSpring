package com.senai.controle_de_acesso_spring.application.dto.users;


import java.time.LocalDate;

public record AdminDto(Long id, String nome, String cpf, String email, LocalDate dataNascimento) {
}
