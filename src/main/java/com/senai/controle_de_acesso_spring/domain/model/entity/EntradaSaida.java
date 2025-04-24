package com.senai.controle_de_acesso_spring.domain.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class EntradaSaida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Justificativas justificativas;//falta isso

    private LocalDateTime dataHora;

    private boolean status;


}
