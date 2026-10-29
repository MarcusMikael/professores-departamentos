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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.professores.model.DepartamentoModel;
import com.example.professores.service.DepartamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Tag(name = "Departamentos", description = "Cadastro, atualização, exclusão e consulta de departamentos")

@RestController
@RequestMapping("/departamentos")
public class DepartamentoController {

    private final DepartamentoService departamentoService;

    public DepartamentoController(DepartamentoService departamentoService) {
        this.departamentoService = departamentoService;
    }

    // Listar todos os departamentos

    @Operation(summary = "Listar departamentos", description = "Retorna todos os departamentos cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de departamentos retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<DepartamentoModel>> ListarDepartamentos() {
        return ResponseEntity.ok(departamentoService.listarTodos());
    }

    // buscar departamento por ID

    @Operation(summary = "Buscar departamento por ID", description = "Recebe o ID pela URL e retorna o departamento correspondente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Departamento encontrado"),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DepartamentoModel> buscarDepartamentoId(@PathVariable Long id) {
        return ResponseEntity.ok(departamentoService.buscarDepartamentoId(id));

    }

    // Cadastrar departamento

    @Operation(summary = "Cadastrar departamento", description = "Recebe o nome e a descrição e cadastra um novo departamento.")
    @ApiResponse(responseCode = "201", description = "Departamento cadastrado com sucesso")
    @PostMapping
    public ResponseEntity<DepartamentoModel> cadastrarDepartamento(@RequestBody DepartamentoModel departamento) {
        return ResponseEntity.status(HttpStatus.CREATED).body(departamentoService.cadastrarDepartamento(departamento));
    }

    // Atualizar departamento

    @Operation(summary = "Atualizar departamento", description = "Atualiza o nome e a descrição do departamento identificado pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Departamento atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<DepartamentoModel> atualizar(@PathVariable Long id,
            @RequestBody DepartamentoModel departamentoAtualizado) {
        return ResponseEntity.ok(departamentoService.atualizar(id, departamentoAtualizado));
    }

    // Excluir departamento

    @Operation(summary = "Excluir departamento", description = "Remove o departamento identificado pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Departamento excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        departamentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
