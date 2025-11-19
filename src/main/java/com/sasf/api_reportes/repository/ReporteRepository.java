package com.sasf.api_reportes.repository;

import com.sasf.api_reportes.entity.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReporteRepository extends JpaRepository<Reporte, Integer> {

    @Query(
            
    )
    List<Reporte> getNumeroReportesZona(String ubicacion);

}
