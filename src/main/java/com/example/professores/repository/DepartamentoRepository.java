package com.example.professores.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.professores.model.DepartamentoModel;

public interface DepartamentoRepository extends JpaRepository<DepartamentoModel, Long> {

}
