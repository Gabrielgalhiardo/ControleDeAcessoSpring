package com.senai.controle_de_acesso_spring.application.service.curso;

import com.senai.controle_de_acesso_spring.application.dto.curso.UnidadeCurricularDto;
import com.senai.controle_de_acesso_spring.domain.model.entity.curso.UnidadeCurricular;
import com.senai.controle_de_acesso_spring.domain.repository.curso.UnidadeCurricularRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UnidadeCurricularService {
    @Autowired
    private UnidadeCurricularRepository unidadeCurricularRepository;

//    public void cadastrarUnidadeCurricular(UnidadeCurricularDto unidadeCurricularDto) {
//        unidadeCurricularRepository.save(unidadeCurricularDto.);
//    }

    public List<UnidadeCurricularDto> listarUnidadeCurricular() {
        return unidadeCurricularRepository.findAll()
                .stream().map(UnidadeCurricularDto::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<UnidadeCurricularDto> buscarPorId(Long id) {
        return unidadeCurricularRepository.findById(id).map(uc -> new UnidadeCurricularDto(
                uc.getId(),
                uc.getNome(),
                uc.getCargaHorariaTotal(),
                uc.getProfessores()
        ));
    }

    public boolean atualizarUnidadeCurricular(Long id, UnidadeCurricularDto unidadeCurricularDto) {
        return unidadeCurricularRepository.findById(id).map(unidadeCurricular -> {
            UnidadeCurricular unidadeCurricularAtualizado = unidadeCurricularDto.fromDTO();
            unidadeCurricular.setId(unididadeCurricularAtualizado.getId());
            unidadeCurricular.setNome(unidadeCurricular.getNome());
            unidadeCurricularRepository.save(unidadeCurricular);
            return true;
        }).orElse(false);
    }

    public boolean inativarUnidadeCurricular(Long id) {
        if (unidadeCurricularRepository.existsById(id)){
            unidadeCurricularRepository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }
}