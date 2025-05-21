package com.senai.controle_de_acesso_spring.interface_ui.controller;

import com.senai.controle_de_acesso_spring.application.service.OcorrenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OcorrenciaController {
    @Autowired
    OcorrenciaService ocorrenciaService;

    public void criarOcorrenciaDeAtraso(String idAcesso) {
       ocorrenciaService.criarOcorrenciaAtraso(idAcesso);

    }
}
