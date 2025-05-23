package com.senai.controle_de_acesso_spring.application.service.curso;

import com.senai.controle_de_acesso_spring.application.dto.curso.CursoDto;
import com.senai.controle_de_acesso_spring.domain.repository.curso.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {
    @Autowired
    private CursoRepository cursoRepository;

    public void cadastrarCurso(CursoDto cursoDTO) {
        cursoRepository.save(cursoDTO.fromDTO());
    }

    public List<CursoDto> listarCursos() {
        return cursoRepository.findAll()
                .stream()
                .map(CursoDto::toDTO)
                .toList();
    }

    public Optional<CursoDto> buscarPorId(Long id) {
        return cursoRepository.findById(id)
                .map(CursoDto::toDTO);
    }

    public boolean atualizarCurso(Long id, CursoDto cursoDTO) {
        return cursoRepository.findById(id).map(curso -> {
            curso.setTitulo(cursoDTO.titulo());
            curso.setTipoDeCurso(cursoDTO.tipoDeCurso());
            curso.setCargaHoraria(cursoDTO.cargaHoraria());
            curso.setToleranciaMinutos(cursoDTO.toleranciaMinutos());
            curso.setQuantidadeDeSemestres(cursoDTO.quantidadeDeSemestres());
            curso.setUnidadesCurriculares(cursoDTO.unidadesCurriculares());
            cursoRepository.save(curso);
            return true;
        }).orElse(false);
    }

    public boolean inativarCurso(Long id) {
        return cursoRepository.findById(id).map(curso -> {
            cursoRepository.delete(curso);
            return true;
        }).orElse(false);
    }
}
