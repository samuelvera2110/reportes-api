package com.sasf.api_reportes.service;

import com.sasf.api_reportes.dto.TrabajadorDetallesDto;
import com.sasf.api_reportes.dto.TrabajadorDto;
import com.sasf.api_reportes.entity.Trabajador;
import com.sasf.api_reportes.mapper.TrabajadorMapper;
import com.sasf.api_reportes.repository.TrabajadorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TrabajadorService {

    private final TrabajadorRepository repository;
    private final TrabajadorMapper mapper;

    public Page<TrabajadorDetallesDto> getAll(Pageable pageable){
        return repository.findAll(pageable).map(mapper::toTrabajadorDetallesDto);
    }

    public Optional<TrabajadorDetallesDto> getById(int id){
        return Optional.ofNullable(mapper.toTrabajadorDetallesDto(repository.getOne(id)));
    }

    @Transactional
    public TrabajadorDto save(TrabajadorDto trabajadorDto){
        return mapper.toTrabajadorDto(repository.save(
                mapper.toTrabajador(trabajadorDto)
        ));
    }

    @Transactional
    public Boolean deleteById(int id){
        return repository.findById(id).map(
                trabajador -> {
                    repository.delete(trabajador);
                    return true;
                }).orElse(false);
    }

    @Transactional
    public TrabajadorDto update(int id, TrabajadorDto request){
        Trabajador trabajador = repository.getOne(id);
        mapper.updateTrabajador(request, trabajador);
        return mapper.toTrabajadorDto(repository.save(trabajador));
    }


}
