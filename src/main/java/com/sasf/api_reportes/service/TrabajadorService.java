package com.sasf.api_reportes.service;

import com.sasf.api_reportes.dto.TrabajadorDto;
import com.sasf.api_reportes.dto.TrabajadorRequest;
import com.sasf.api_reportes.entity.Trabajador;
import com.sasf.api_reportes.mapper.TrabajadorMapper;
import com.sasf.api_reportes.repository.TrabajadorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TrabajadorService {

    private final TrabajadorRepository repository;
    private final TrabajadorMapper mapper;

    public Page<TrabajadorDto> getAll(Pageable pageable){
        return repository.findAll(pageable).map(mapper::toTrabajadorDto);
    }

    public Optional<TrabajadorDto> getById(int id){
        return Optional.ofNullable(mapper.toTrabajadorDto(repository.getOne(id)));
    }

    @Transactional
    public TrabajadorDto save(TrabajadorRequest trabajadorDto){
        //return mapper.toTrabajadorDto(repository.save(mapper.toTrabajador(request)));
        Trabajador trabajador = mapper.toTrabajador(trabajadorDto);
        return mapper.toTrabajadorDto(repository.save(trabajador));
    }

    @Transactional
    public boolean deleteById(int id){
        return repository.findById(id).map(
                trabajador -> {
                    repository.delete(trabajador);
                    return true;
                }).orElse(false);
    }

    /*public TrabajadorDto update(TrabajadorDto trabajadorDto){
        return repository.save(mapper.toTrabajador(trabajadorDto));
    }*/


}
