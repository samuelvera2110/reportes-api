package com.sasf.api_reportes.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class TrabajadorRequest {

    private String nombre;
    private String correo;
    private String contrasena;

}
