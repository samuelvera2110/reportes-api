package com.sasf.api_reportes.service;

import com.sasf.api_reportes.dto.ReporteDto;
import com.sasf.api_reportes.dto.TrabajadorDetallesDto;
import com.sasf.api_reportes.dto.TrabajadorDto;
import com.sasf.api_reportes.entity.Reporte;
import com.sasf.api_reportes.mapper.ReporteMapper;
import com.sasf.api_reportes.repository.ReporteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReporteService {

    private final ReporteMapper mapper;

    private final ReporteRepository repository;

    public Page<ReporteDto> getAll(Pageable pageable){
        return repository.findAll(pageable).map(mapper::toReporteDto);
    }

    public Optional<ReporteDto> getById(int id){
        return Optional.ofNullable(mapper.toReporteDto(repository.getById(id)));
    }

    @Transactional
    public ReporteDto save(ReporteDto reporteDto){
        return mapper.toReporteDto(
                repository.save(
                        mapper.toReporte(reporteDto)
                )
        );
    }

    @Transactional
    public Boolean deleteById(int id){
        return repository.findById(id).map(
                reporte -> {
                    repository.delete(reporte);
                    return true;
                }
        ).orElse(false);
    }

    @Transactional
    public ReporteDto update(int id, ReporteDto reporteDto){
        Reporte reporte = repository.getReferenceById(id);
        mapper.update(reporteDto, reporte);
        return mapper.toReporteDto(repository.save(reporte));
    }

}
