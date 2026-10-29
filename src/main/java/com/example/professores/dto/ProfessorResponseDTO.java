
package com.example.professores.dto;

public class ProfessorResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String area;
    private String telefone;
    private Long departamentoId;
    private String departamentoNome;

    public ProfessorResponseDTO(
            Long id,
            String nome,
            String email,
            String area,
            String telefone,
            Long departamentoId,
            String departamentoNome) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.area = area;
        this.telefone = telefone;
        this.departamentoId = departamentoId;
        this.departamentoNome = departamentoNome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getArea() {
        return area;
    }

    public String getTelefone() {
        return telefone;
    }

    public Long getDepartamentoId() {
        return departamentoId;
    }

    public String getDepartamentoNome() {
        return departamentoNome;
    }
}

