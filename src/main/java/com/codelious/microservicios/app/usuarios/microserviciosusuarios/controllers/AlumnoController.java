package com.codelious.microservicios.app.usuarios.microserviciosusuarios.controllers;

import java.io.IOException;
import java.util.Optional;

import javax.validation.Valid;

import com.codelious.microservicios.app.usuarios.microserviciosusuarios.services.AlumnoService;
import com.codelious.microservicios.commons.alumnos.commonsalumnos.models.entity.Alumno;
import com.codelious.microservicios.commons.commonsmicroservicios.controllers.CommonController;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AlumnoController extends CommonController<Alumno, AlumnoService> {
    
    @GetMapping("/uploads/img/{id}")
    public ResponseEntity<?> showPhoto(@PathVariable Long id) {

        Optional<Alumno> o = service.findById(id);
        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ByteArrayResource imagen = new ByteArrayResource(o.get().getFoto());

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imagen);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Alumno alumno, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors()) {
            return this.validar(result);
        }

        Optional<Alumno> o = service.findById(id);
        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Alumno alumnoToUpdate = o.get();
        alumnoToUpdate.setNombre(alumno.getNombre());
        alumnoToUpdate.setApellido(alumno.getApellido());
        alumnoToUpdate.setEmail(alumno.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnoToUpdate));
    }

    @GetMapping("/filtrar/{text}")
    public ResponseEntity<?> filtrar(@PathVariable String text) {
        return ResponseEntity.ok(service.findByNombreOrApellido(text));
    }

    @PostMapping("/save-with-photo")
    public ResponseEntity<?> saveWithPhoto(@Valid Alumno alumno, BindingResult result, 
        @RequestParam MultipartFile archivo) throws IOException {
        if (!archivo.isEmpty()) {
            alumno.setFoto(archivo.getBytes());
        }
        return super.save(alumno, result);
    }

    @PutMapping("/update-with-photo/{id}")
    public ResponseEntity<?> updateWithPhoto(@Valid Alumno alumno, BindingResult result, @PathVariable Long id, 
        @RequestParam MultipartFile archivo) throws IOException {

        if (result.hasErrors()) {
            return this.validar(result);
        }

        Optional<Alumno> o = service.findById(id);
        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Alumno alumnoDb = o.get();
        alumnoDb.setNombre(alumno.getNombre());
        alumnoDb.setApellido(alumno.getApellido());
        alumnoDb.setEmail(alumno.getEmail());
        if (!archivo.isEmpty()) {
            alumnoDb.setFoto(archivo.getBytes());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnoDb));
    }
}
