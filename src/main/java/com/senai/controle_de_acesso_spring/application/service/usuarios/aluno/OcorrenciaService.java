package com.senai.controle_de_acesso_spring.application.service.usuarios.aluno;

import com.senai.controle_de_acesso_spring.application.dto.usuarios.aluno.OcorrenciaDto;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.aluno.Ocorrencia;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDaOcorrencia;
import com.senai.controle_de_acesso_spring.domain.repository.OcorrenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OcorrenciaService {

    @Autowired
    private OcorrenciaRepository ocorrenciaRepository;

    public void cadastrarOcorrencia(OcorrenciaDto ocorrenciaDto){
        ocorrenciaRepository.save(ocorrenciaDto.fromDTO());
    }

    public List<OcorrenciaDto> listarOcorrencias(){
        return ocorrenciaRepository.findByStatusDaOcorrencia(StatusDaOcorrencia.AGUARDANDO_AUTORIZACAO).stream().map(OcorrenciaDto::toDTO).collect(Collectors.toList());
    }

    public Optional<OcorrenciaDto> buscarOcorrenciaPorId(Long id){
        return ocorrenciaRepository.findById(id).map(OcorrenciaDto::toDTO);
    }

    public boolean atualizarOcorrencia (Long id, OcorrenciaDto ocorrenciaDto){
        return ocorrenciaRepository.findById(id).map(ocorrenciaAntiga ->{
            Ocorrencia ocorrenciaAtualizada = ocorrenciaDto.fromDTO();
            ocorrenciaAntiga.setTipo(ocorrenciaAtualizada.getTipo());
            ocorrenciaAntiga.setDescricao(ocorrenciaAtualizada.getDescricao());
            ocorrenciaAntiga.setStatusDaOcorrencia(ocorrenciaAtualizada.getStatusDaOcorrencia());
            ocorrenciaAntiga.setDataHoraCriacao(ocorrenciaAtualizada.getDataHoraCriacao());
            ocorrenciaAntiga.setDataHoraConclusao(ocorrenciaAtualizada.getDataHoraConclusao());
            ocorrenciaAntiga.setAluno(ocorrenciaAtualizada.getAluno());
            ocorrenciaAntiga.setProfessorResponsavel(ocorrenciaAtualizada.getProfessorResponsavel());
            ocorrenciaAntiga.setUnidadeCurricular(ocorrenciaAtualizada.getUnidadeCurricular());
            return true;
        }).orElse(false);
    }

    public boolean mudarStatusDaOcorrencia (Long id, StatusDaOcorrencia statusDaOcorrencia){
        ocorrenciaRepository.findById(id).map( ocorrencia ->{
            ocorrencia.setStatusDaOcorrencia(statusDaOcorrencia);
            ocorrenciaRepository.save(ocorrencia);
            return true;
        }).orElse(false);
        return false;
    }
}
