package com.example.professores.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.professores.model.ProfessorModel;

public interface ProfessorRepository extends JpaRepository<ProfessorModel, Long>{

    List<ProfessorModel> findByNomeContaining(String nome);

    List<ProfessorModel> findByNomeContainingIgnoreCase(String nome);

    List<ProfessorModel> findByAreaContainingIgnoreCase(String area);

    List<ProfessorModel> findByDepartamento_Id(Long departamentoId);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByDepartamento_Id(Long departamentoId);
}
