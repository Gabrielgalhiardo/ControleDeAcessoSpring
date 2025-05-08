package com.senai.controle_de_acesso_spring.application.service;

import com.senai.controle_de_acesso_spring.application.dto.users.CoordenadorDto;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Coordenador;
import com.senai.controle_de_acesso_spring.domain.repository.CoordenadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoordenadorService {
    @Autowired
    private CoordenadorRepository coordenadorRepository;

    public CoordenadorDto salvarCoordenador(CoordenadorDto coordenadorDto) {
        Coordenador coordenador = new Coordenador();

        coordenador.setNome(coordenadorDto.nome());
        coordenador.setCpf(coordenadorDto.cpf());
        coordenador.setEmail(coordenadorDto.email());
        coordenador.setDataNascimento(coordenadorDto.dataNascimento());
        coordenador.setEquipeProfessores(coordenadorDto.equipeProfessores());

        coordenadorRepository.save(coordenador);
        return coordenadorDto;
    }

//    public List<CoordenadorDto> listarCoordenadores(){
//        return coordenadorRepository.findAll().stream().map(coordenador -> new CoordenadorDto(
//                coordenador.getNome(),
//                coordenador.getEmail(),
//                coordenador.getEmail(),
//                coordenador.getDataNascimento(),
//                coordenador.getEquipeProfessores()
//        )).collect(Collectors.toList());
//    }


}
