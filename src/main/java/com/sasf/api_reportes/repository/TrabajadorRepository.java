package com.sasf.api_reportes.repository;

import com.sasf.api_reportes.entity.Trabajador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrabajadorRepository extends JpaRepository<Trabajador, Integer> {
}
