package com.senai.controle_de_acesso_spring.application.service;

import com.senai.controle_de_acesso_spring.application.dto.users.JustificativaDto;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Justificativa;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDaJustificativa;
import com.senai.controle_de_acesso_spring.domain.repository.JustificativaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JustificativaService {
    @Autowired
    private JustificativaRepository justificativaRepository;

    public void cadastrarJustificativa(JustificativaDto justificativaDto) {
        justificativaRepository.save(justificativaDto.fromDTO());
    }

    public List<JustificativaDto> listarJustificativas(){
        return justificativaRepository.findAll()
                .stream().map(JustificativaDto::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<JustificativaDto> buscarJustificativaPorId(Long id) {
        return justificativaRepository.findById(id).map(JustificativaDto::toDTO);
    }

    public boolean atualizarJustificativa(Long id, JustificativaDto justificativaDto) {
        return justificativaRepository.findById(id).map(justificativa -> {
            Justificativa justificativaAtualizada = justificativaDto.fromDTO();
            justificativa.setTipoDeJustificativa(justificativaAtualizada.getTipoDeJustificativa());
            justificativa.setDescricao(justificativaAtualizada.getDescricao());
            justificativa.setAnexo(justificativaAtualizada.getAnexo());
            justificativa.setDataInicial(justificativaAtualizada.getDataInicial());
            justificativa.setQtdDias(justificativaAtualizada.getQtdDias());
            justificativa.setStatusDaJustificativa(justificativaAtualizada.getStatusDaJustificativa());
            justificativaRepository.save(justificativa);
            return true;
        }).orElse(false);
    }

    public boolean mudarStatusDaJustificativa(Long id, StatusDaJustificativa statusDaJustificativa) {
        return justificativaRepository.findById(id).map(justificativa -> {
            justificativa.setStatusDaJustificativa(statusDaJustificativa);
            justificativaRepository.save(justificativa);
            return true;
        }).orElse(false);
    }
}
