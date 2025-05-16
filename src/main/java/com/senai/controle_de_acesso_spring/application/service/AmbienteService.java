package com.senai.controle_de_acesso_spring.application.service;

import com.senai.controle_de_acesso_spring.application.dto.users.AmbienteDto;
import com.senai.controle_de_acesso_spring.domain.model.entity.curso.Ambiente;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDoUsuario;
import com.senai.controle_de_acesso_spring.domain.repository.AmbienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AmbienteService {
    @Autowired
    private AmbienteRepository ambienteRepository;

    public void cadastrarAmbiente(AmbienteDto ambienteDto) {
        ambienteRepository.save(ambienteDto.fromDTO());
    }

    public List<AmbienteDto> listarAmbientesAtivos() {
        return ambienteRepository.findAll()
                .stream().map(AmbienteDto::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<AmbienteDto> buscarPorId(Long id) {
        return ambienteRepository.findById(id).map(a -> new AmbienteDto(
                a.getId(),
                a.getNome()
        ));
    }

    public boolean atualizarAmbientes(Long id, AmbienteDto ambienteDto) {
        return ambienteRepository.findById(id).map(ambiente -> {
            Ambiente ambienteAtualizado = ambienteDto.fromDTO();
            ambiente.setId(ambienteAtualizado.getId());
            ambiente.setNome(ambienteAtualizado.getNome());
            ambienteRepository.save(ambiente);
            return true;
        }).orElse(false);
    }

    public boolean inativarAmbiente(Long id) {
        if (ambienteRepository.existsById(id)){
            ambienteRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}