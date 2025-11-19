package com.sasf.api_reportes.mapper;
import com.sasf.api_reportes.dto.TrabajadorDetallesDto;
import com.sasf.api_reportes.dto.TrabajadorDto;
import com.sasf.api_reportes.entity.Trabajador;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TrabajadorMapper {

    @Mappings({
            @Mapping(source = "nombre", target = "nombre"),
            @Mapping(source = "correo", target = "correo")
    })
    TrabajadorDto toTrabajadorDto(Trabajador trabajador);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "idTrabajador", ignore = true),
            @Mapping(target = "fechaCreacion", ignore = true),
            @Mapping(target = "fechaModificacion", ignore = true)
    })
    Trabajador toTrabajador(TrabajadorDto trabajadorDto);

    @Mappings({
            @Mapping(target = "idTrabajador", ignore = true),
            @Mapping(target = "fechaCreacion", ignore = true),
            @Mapping(target = "fechaModificacion", ignore = true)
    })
    void updateTrabajador(TrabajadorDto dto, @MappingTarget Trabajador entity);

    @Mappings({
            @Mapping(source = "idTrabajador", target = "idTrabajador"),
            @Mapping(source = "nombre", target = "nombre"),
            @Mapping(source = "correo", target = "correo"),
            @Mapping(source = "fechaCreacion", target = "fechaCreacion")
    })
    TrabajadorDetallesDto toTrabajadorDetallesDto(Trabajador trabajador);
}
