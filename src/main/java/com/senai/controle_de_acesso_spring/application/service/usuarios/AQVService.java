package com.senai.controle_de_acesso_spring.application.service.usuarios;

import com.senai.controle_de_acesso_spring.application.dto.usuarios.AQVDto;
import com.senai.controle_de_acesso_spring.domain.model.entity.usuarios.AQV;
import com.senai.controle_de_acesso_spring.domain.model.enums.StatusDoUsuario;
import com.senai.controle_de_acesso_spring.domain.repository.usuarios.AQVRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AQVService{

    @Autowired
    private AQVRepository aqvRepository;

    public void cadastrarAQV(AQVDto aqvDto) {
        aqvRepository.save(aqvDto.fromDTO());
    }

    public List<AQVDto>listarAqvsAtivos(){
        return aqvRepository.findByStatusDoUsuario(StatusDoUsuario.ATIVO)
                .stream().map(AQVDto::toDTO).collect(Collectors.toList());
    }

    public Optional<AQVDto>buscarPorId(Long id){
        return aqvRepository.findById(id).filter(a -> a.getStatusDoUsuario()
                .equals(StatusDoUsuario.ATIVO)).map(AQVDto::toDTO);
    }

    public boolean atualizarAQV(Long id, AQVDto aqvDto){
        return aqvRepository.findById(id).map(aqv -> {
            AQV aqvAtualizado = aqvDto.fromDTO();
            aqv.setNome(aqvAtualizado.getNome());
            aqv.setCpf(aqvAtualizado.getCpf());
            aqv.setEmail(aqvAtualizado.getEmail());
            aqv.setDataNascimento(aqvAtualizado.getDataNascimento());
            aqvRepository.save(aqv);
            return true;
        }).orElse(false);
    }

    public boolean inativarAQV(Long id){
        return aqvRepository.findById(id).map(aqv ->{
            aqv.setStatusDoUsuario(StatusDoUsuario.INATIVO);
            aqvRepository.save(aqv);
            return true;
        }).orElse(false);

    }



}
