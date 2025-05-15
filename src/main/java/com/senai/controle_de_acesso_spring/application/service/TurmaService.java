package com.senai.controle_de_acesso_spring.application.service;

import com.senai.controle_de_acesso_spring.domain.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TurmaService {
    @Autowired
    private TurmaRepository turmaRepo;


}
