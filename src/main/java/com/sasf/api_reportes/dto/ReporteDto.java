package com.sasf.api_reportes.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class ReporteDto {

    private Integer idReporte;

    private String descripcion;

    private LocalDate fechaReporte;

    private String ubicacion;

}
