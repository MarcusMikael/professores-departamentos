
package com.example.professores.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.professores.model.DepartamentoModel;
import com.example.professores.model.ProfessorModel;
import com.example.professores.repository.DepartamentoRepository;
import com.example.professores.repository.ProfessorRepository;

@Service
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final DepartamentoRepository departamentoRepository;

    public ProfessorService(
            ProfessorRepository professorRepository,
            DepartamentoRepository departamentoRepository) {
        this.professorRepository = professorRepository;
        this.departamentoRepository = departamentoRepository;
    }

    public List<ProfessorModel> listarTodos() {
        return professorRepository.findAll();
    }

    public ProfessorModel buscarProfessorId(Long id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Professor não encontrado com o ID: " + id));
    }

    public List<ProfessorModel> buscarProfessorPorNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nome não encontrado.");
        }
        List<ProfessorModel> professores = professorRepository.findByNomeContainingIgnoreCase(nome);

        if (professores.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum professor encontrado com o nome: " + nome);
        }

        return professores;
    }

    public List<ProfessorModel> buscarProfessorPorArea(String area) {
        if (area == null || area.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A área é obrigatória para a pesquisa.");
        }
        List<ProfessorModel> professores = professorRepository.findByAreaContainingIgnoreCase(area);

        if (professores.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum professor encontrado na área: " + area);
        }

        return professores;
    }

    private DepartamentoModel buscarDepartamento(Long departamentoId) {
        if (departamentoId == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O departamento é obrigatório.");
        }

        return departamentoRepository.findById(departamentoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Departamento não encontrado com o ID: "
                                + departamentoId));
    }

    public ProfessorModel cadastrarProfessor(ProfessorModel professor) {
        professor.setId(null);

        DepartamentoModel departamento = buscarDepartamento(
                professor.getDepartamento() == null
                        ? null
                        : professor.getDepartamento().getId());

        professor.setDepartamento(departamento);
        validarEmail(professor.getEmail(), null);

        return professorRepository.save(professor);
    }

    public ProfessorModel atualizarProfessor(
            Long id, ProfessorModel professorAtualizado) {

        ProfessorModel professorExistente = buscarProfessorId(id);

        DepartamentoModel departamento = buscarDepartamento(
                professorAtualizado.getDepartamento() == null
                        ? null
                        : professorAtualizado.getDepartamento().getId());

        validarEmail(professorAtualizado.getEmail(), id);

        professorExistente.setNome(professorAtualizado.getNome());
        professorExistente.setEmail(professorAtualizado.getEmail());
        professorExistente.setArea(professorAtualizado.getArea());
        professorExistente.setTelefone(professorAtualizado.getTelefone());
        professorExistente.setDepartamento(departamento);

        return professorRepository.save(professorExistente);
    }

    public void deletarProfessor(Long id) {
        ProfessorModel professor = buscarProfessorId(id);
        professorRepository.delete(professor);
    }

    public List<ProfessorModel> buscarPorDepartamento(Long departamentoId) {
        buscarDepartamento(departamentoId);

        return professorRepository.findByDepartamento_Id(departamentoId);
    }

    private void validarEmail(String email, Long idAtual) {
        if (email == null || email.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O e-mail é obrigatório.");
        }

        boolean emailExiste = professorRepository.existsByEmailIgnoreCase(email);

        if (emailExiste) {
            boolean pertenceAoAtual = idAtual != null
                    && professorRepository.findById(idAtual)
                            .map(p -> p.getEmail().equalsIgnoreCase(email))
                            .orElse(false);

            if (!pertenceAoAtual) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Já existe um professor com esse e-mail.");
            }
        }
    }
}
