package com.sasf.api_reportes.controller;

import com.sasf.api_reportes.dto.TrabajadorDto;
import com.sasf.api_reportes.dto.TrabajadorRequest;
import com.sasf.api_reportes.service.TrabajadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/trabajadores")
public class TrabajadorController {

    @Autowired
    private TrabajadorService service;

    @GetMapping
    public ResponseEntity<Page<TrabajadorDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.getAll(pageable));
    }

    @GetMapping("/{id}")
    public Optional<TrabajadorDto> getById(@PathVariable int id){
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<TrabajadorDto> save(@RequestBody TrabajadorRequest request){
        TrabajadorDto trabajador = service.save(request);
        return ResponseEntity.ok(trabajador);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable int id){
        return ResponseEntity.ok(service.deleteById(id));
    }

}
