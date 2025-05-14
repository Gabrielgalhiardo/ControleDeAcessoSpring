package com.senai.controle_de_acesso_spring.application.service;

import com.senai.controle_de_acesso_spring.application.dto.users.CoordenadorDto;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Coordenador;
import com.senai.controle_de_acesso_spring.domain.repository.CoordenadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CoordenadorService {
    @Autowired
    private CoordenadorRepository coordenadorRepository;

    public void  cadastrarCoordenador(CoordenadorDto coordenadorDto) {
        coordenadorRepository.save(coordenadorDto.fromDTO());
    }

    public List<CoordenadorDto> listarCoordenadoresAtivos(){
        return coordenadorRepository.findByAtivoTrue()
                .stream().map(CoordenadorDto::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<CoordenadorDto> buscarPorId(Long id) {
        return coordenadorRepository.findById(id).filter(Coordenador::isAtivo).map(CoordenadorDto::toDTO);
    }

}
