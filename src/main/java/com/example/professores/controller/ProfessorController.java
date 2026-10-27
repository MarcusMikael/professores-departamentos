package com.example.professores.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.professores.model.ProfessorModel;
import com.example.professores.service.ProfessorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Professores", description = "Gerenciamento e consulta de professores")

@RestController
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    // Listar todos os professores

    @Operation(summary = "Listar professores", description = "Retorna todos os professores cadastrados, incluindo os dados de seus departamentos.")
    @ApiResponse(responseCode = "200", description = "Lista de professores retornada com sucesso")
    @GetMapping("/professores")
    public ResponseEntity<List<ProfessorModel>> listarProfessores() {
        return ResponseEntity.ok(professorService.listarTodos());
    }

    // Buscar professor por nome

    @Operation(summary = "Pesquisar professores por nome", description = "Retorna os professores cujos nomes contêm o texto informado.")
    @ApiResponse(responseCode = "200", description = "Pesquisa realizada com sucesso")
    @GetMapping("/professores/nome/{nome}")
    public ResponseEntity<List<ProfessorModel>> buscarProfessorNome(@PathVariable String nome) {
        return ResponseEntity.ok(professorService.buscarProfessorPorNome(nome));
    }

    // Buscar professor por área

    @Operation(summary = "Pesquisar professores por área", description = "Retorna os professores cuja área de atuação contém o texto informado.")
    @ApiResponse(responseCode = "200", description = "Pesquisa realizada com sucesso")
    @GetMapping("/professores/area/{area}")
    public ResponseEntity<List<ProfessorModel>> buscarProfessorArea(@PathVariable String area) {
        return ResponseEntity.ok(professorService.buscarProfessorPorArea(area));
    }

    // Buscar professor por ID
    @Operation(summary = "Buscar professor por ID", description = "Recebe o ID pela URL e retorna os dados do professor correspondente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Professor encontrado"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    })
    @GetMapping("/professor/{id}")
    public ResponseEntity<ProfessorModel> buscarProfessorId(@PathVariable Long id) {
        ProfessorModel professor = professorService.buscarProfessorId(id);
        return ResponseEntity.ok(professor);
    }

    // Cadastrar
    @Operation(summary = "- Cadastrar professor", description = "Cadastra um novo professor vinculado a um departamento existente. O e-mail deve ser exclusivo.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Professor cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou departamento obrigatório não informado"),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado"),
            @ApiResponse(responseCode = "409", description = "Já existe um professor com esse e-mail")
    })
    @PostMapping("/professores")
    public ResponseEntity<ProfessorModel> cadastrar(@RequestBody ProfessorModel professor) {
        ProfessorModel professorSalvo = professorService.cadastrarProfessor(professor);
        return ResponseEntity.status(HttpStatus.CREATED).body(professorSalvo);
    }

    // Atualizar

    @Operation(summary = "Atualizar professor", description = "Atualiza nome, e-mail, área, telefone e departamento de um professor existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Professor atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Professor ou departamento não encontrado"),
            @ApiResponse(responseCode = "409", description = "E-mail já utilizado por outro professor")
    })
    @PutMapping("/professor/{id}")
    public ResponseEntity<ProfessorModel> atualizarProfessor(
            @PathVariable Long id,
            @RequestBody ProfessorModel professorAtualizado) {

        ProfessorModel atualizado = professorService.atualizarProfessor(id, professorAtualizado);

        return ResponseEntity.ok(atualizado);
    }

    // Deletar

    @Operation(summary = "Excluir professor", description = "Remove do banco de dados o professor correspondente ao ID informado.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Professor excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    })
    @DeleteMapping("/professor/{id}")
    public ResponseEntity<Void> deletarProfessor(@PathVariable Long id) {
        professorService.deletarProfessor(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar professores por departamento

    @Operation(summary = "Listar professores por departamento", description = "Recebe o ID do departamento pela URL e retorna os professores vinculados a ele.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado")
    })
    @GetMapping("/professores/departamento/{departamentoId}")
    public ResponseEntity<List<ProfessorModel>> buscarPorDepartamento(
            @PathVariable Long departamentoId) {

        return ResponseEntity.ok(
                professorService.buscarPorDepartamento(departamentoId));
    }

}
