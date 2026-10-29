package com.example.professores.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.professores.model.DepartamentoModel;
import com.example.professores.repository.DepartamentoRepository;
import com.example.professores.repository.ProfessorRepository;

@Service
public class DepartamentoService {

    private final DepartamentoRepository departamentoRepository;
    private final ProfessorRepository professorRepository;

    public DepartamentoService(
            DepartamentoRepository departamentoRepository,
            ProfessorRepository professorRepository) {
        this.departamentoRepository = departamentoRepository;
        this.professorRepository = professorRepository;
    }

    public List<DepartamentoModel> listarTodos() {
        return departamentoRepository.findAll();
    }

    public DepartamentoModel buscarDepartamentoId(Long id) {
        return departamentoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Departamento não encontrado"));
    }

    public DepartamentoModel cadastrarDepartamento(DepartamentoModel departamento) {
        return departamentoRepository.save(departamento);
    }

    public DepartamentoModel atualizar(
            Long id, DepartamentoModel dadosAtualizados) {

        DepartamentoModel departamento = buscarDepartamentoId(id);

        departamento.setNome(dadosAtualizados.getNome());
        departamento.setDescricao(dadosAtualizados.getDescricao());

        return departamentoRepository.save(departamento);
    }

    public void excluir(Long id) {
    DepartamentoModel departamento = buscarDepartamentoId(id);

    if (professorRepository.existsByDepartamento_Id(id)) {
        throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Não é possível excluir um departamento que possui professores vinculados."
        );
    }

    departamentoRepository.delete(departamento);
}

}
