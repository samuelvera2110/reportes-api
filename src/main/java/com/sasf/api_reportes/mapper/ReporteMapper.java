package com.sasf.api_reportes.mapper;

import com.sasf.api_reportes.dto.ReporteDto;
import com.sasf.api_reportes.entity.Reporte;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ReporteMapper {


    @Mappings({
            @Mapping(source = "idReporte", target = "idReporte"),
            @Mapping(source = "descripcion", target = "descripcion"),
            @Mapping(source = "fechaReporte", target = "fechaReporte"),
            @Mapping(source = "ubicacion", target = "ubicacion")
    })
    ReporteDto toReporteDto(Reporte reporte);

    @InheritInverseConfiguration
    Reporte toReporte(ReporteDto reporteDto);

    void update(ReporteDto reporteDto, @MappingTarget Reporte reporte);

}
