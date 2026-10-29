
package com.example.professores.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.professores.dto.ProfessorRequestDTO;
import com.example.professores.dto.ProfessorResponseDTO;
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
    @Operation(summary = "Listar professores")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping("/professores")
    public ResponseEntity<List<ProfessorResponseDTO>> listarProfessores() {
        return ResponseEntity.ok(professorService.listarTodosDTO());
    }

    // Buscar professor por nome
    @Operation(summary = "Pesquisar professores por nome")
    @ApiResponse(responseCode = "200", description = "Pesquisa realizada com sucesso")
    @GetMapping("/professores/nome/{nome}")
    public ResponseEntity<List<ProfessorResponseDTO>> buscarProfessorNome(
            @PathVariable String nome) {
        return ResponseEntity.ok(professorService.buscarProfessorPorNomeDTO(nome));
    }

    // Buscar professor por área
    @Operation(summary = "Pesquisar professores por área")
    @ApiResponse(responseCode = "200", description = "Pesquisa realizada com sucesso")
    @GetMapping("/professores/area/{area}")
    public ResponseEntity<List<ProfessorResponseDTO>> buscarProfessorArea(
            @PathVariable String area) {
        return ResponseEntity.ok(professorService.buscarProfessorPorAreaDTO(area));
    }

    // Buscar professor por ID
    @Operation(summary = "Buscar professor por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Professor encontrado"),
        @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    })
    @GetMapping("/professor/{id}")
    public ResponseEntity<ProfessorResponseDTO> buscarProfessorId(
            @PathVariable Long id) {
        return ResponseEntity.ok(professorService.buscarProfessorIdDTO(id));
    }

    // Cadastrar professor
    @Operation(
        summary = "Cadastrar professor",
        description = "Cadastra um professor em um departamento existente. O e-mail deve ser exclusivo."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Professor cadastrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Departamento não encontrado"),
        @ApiResponse(responseCode = "409", description = "E-mail já cadastrado")
    })
    @PostMapping("/professores")
    public ResponseEntity<ProfessorResponseDTO> cadastrar(
            @Valid @RequestBody ProfessorRequestDTO professor) {

        ProfessorResponseDTO professorSalvo =
                professorService.cadastrarProfessorDTO(professor);

        return ResponseEntity.status(HttpStatus.CREATED).body(professorSalvo);
    }

    // Atualizar professor
    @Operation(summary = "Atualizar professor")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Professor atualizado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Professor ou departamento não encontrado"),
        @ApiResponse(responseCode = "409", description = "E-mail já utilizado por outro professor")
    })
    @PutMapping("/professor/{id}")
    public ResponseEntity<ProfessorResponseDTO> atualizarProfessor(
            @PathVariable Long id,
            @Valid @RequestBody ProfessorRequestDTO professorAtualizado) {

        ProfessorResponseDTO atualizado =
                professorService.atualizarProfessorDTO(id, professorAtualizado);

        return ResponseEntity.ok(atualizado);
    }

    // Excluir professor
    @Operation(summary = "Excluir professor")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Professor excluído"),
        @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    })
    @DeleteMapping("/professor/{id}")
    public ResponseEntity<Void> deletarProfessor(@PathVariable Long id) {
        professorService.deletarProfessor(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar professores por departamento
    @Operation(summary = "Listar professores por departamento")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Consulta realizada"),
        @ApiResponse(responseCode = "404", description = "Departamento não encontrado")
    })
    @GetMapping("/professores/departamento/{departamentoId}")
    public ResponseEntity<List<ProfessorResponseDTO>> buscarPorDepartamento(
            @Parameter(description = "ID do departamento")
            @PathVariable Long departamentoId) {

        return ResponseEntity.ok(
                professorService.buscarPorDepartamentoDTO(departamentoId));
    }
}
