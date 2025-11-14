package com.sasf.api_reportes.mapper;
import com.sasf.api_reportes.dto.TrabajadorDto;
import com.sasf.api_reportes.dto.TrabajadorRequest;
import com.sasf.api_reportes.entity.Trabajador;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TrabajadorMapper {

    @Mappings({
            @Mapping(source = "idTrabajador", target = "idTrabajador"),
            @Mapping(source = "nombre", target = "nombre"),
            @Mapping(source = "correo", target = "correo"),
            @Mapping(source = "fechaCreacion", target = "fechaCreacion")
    })
    TrabajadorDto toTrabajadorDto(Trabajador trabajador);

    @InheritInverseConfiguration
    @Mapping(target = "contrasena", ignore = true)
    Trabajador toTrabajador(TrabajadorDto trabajadorDto);

    @Mappings({
            @Mapping(target = "idTrabajador", ignore = true),
            @Mapping(source = "nombre", target = "nombre"),
            @Mapping(source = "correo", target = "correo"),
            @Mapping(source = "contrasena", target = "contrasena"),
    })
    Trabajador toTrabajador(TrabajadorRequest trabajadorRequest);


}
