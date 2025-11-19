package com.sasf.api_reportes.controller;

import com.sasf.api_reportes.dto.ReporteDto;
import com.sasf.api_reportes.service.ReporteService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/reportes")
@AllArgsConstructor
public class ReporteController {

    private final ReporteService service;

    @GetMapping
    public ResponseEntity<Page<ReporteDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.getAll(pageable));
    }

    @GetMapping("/{id}")
    public Optional<ReporteDto> getById(@PathVariable int id){
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable int id){
        return ResponseEntity.ok(service.deleteById(id));
    }

    @PostMapping
    public ResponseEntity<ReporteDto> save(@RequestBody ReporteDto reporteDto){
        ReporteDto reporte = service.save(reporteDto);
        return ResponseEntity.ok(reporte);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReporteDto> update(
            @PathVariable int id,
            @RequestBody ReporteDto reporte
    ){
        return ResponseEntity.ok(service.update(id, reporte));
    }

}
