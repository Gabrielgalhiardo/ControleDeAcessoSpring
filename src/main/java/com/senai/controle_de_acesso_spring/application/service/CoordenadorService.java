package com.senai.controle_de_acesso_spring.application.service;

import com.senai.controle_de_acesso_spring.application.dto.users.CoordenadorDto;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.Coordenador;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDoUsuario;
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
        return coordenadorRepository.findByStatusDoUsuario(StatusDoUsuario.ATIVO)
                .stream().map(CoordenadorDto::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<CoordenadorDto> buscarPorId(Long id) {
        return coordenadorRepository.findById(id)
                .filter(c -> c.getStatusDoUsuario()
                .equals(StatusDoUsuario.ATIVO))
                .map(CoordenadorDto::toDTO);
    }

    public boolean atualizarCoordenador(Long id, CoordenadorDto coordenadorDto) {
        return coordenadorRepository.findById(id).map(coordenador -> {
            Coordenador coordenadorAtualizado = coordenadorDto.fromDTO();
            coordenador.setNome(coordenadorAtualizado.getNome());
            coordenador.setCpf(coordenadorAtualizado.getCpf());
            coordenador.setEmail(coordenadorAtualizado.getEmail());
            coordenador.setDataNascimento(coordenadorAtualizado.getDataNascimento());
            coordenador.setEquipeProfessores(coordenadorAtualizado.getEquipeProfessores());
            coordenadorRepository.save(coordenador);
            return true;
        }).orElse(false);
    }

    public boolean inativarCoordenador(Long id) {
        return coordenadorRepository.findById(id).map(coordenador -> {
            coordenador.setStatusDoUsuario(StatusDoUsuario.INATIVO);
            coordenadorRepository.save(coordenador);
            return true;
        }).orElse(false);
    }
}
