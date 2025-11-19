package com.sasf.api_reportes.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "reportes")
@Getter @Setter
public class Reporte extends AuditableEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reporte")
    private Integer idReporte;

    private String descripcion;

    @Column(name = "fecha")
    @CreationTimestamp
    private LocalDateTime fechaReporte;

    private String ubicacion;

}
