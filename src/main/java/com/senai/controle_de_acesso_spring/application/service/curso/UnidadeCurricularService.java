package com.senai.controle_de_acesso_spring.application.service.curso;

import com.senai.controle_de_acesso_spring.application.dto.curso.UnidadeCurricularDto;
import com.senai.controle_de_acesso_spring.domain.repository.curso.UnidadeCurricularRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnidadeCurricularService {
    @Autowired
    private UnidadeCurricularRepository unidadeCurricularRepository;

//    public void cadastrarUnidadeCurricular(UnidadeCurricularDto unidadeCurricularDto) {
//        unidadeCurricularRepository.save(unidadeCurricularDto.fromDTO(unidade));
//    }
}
