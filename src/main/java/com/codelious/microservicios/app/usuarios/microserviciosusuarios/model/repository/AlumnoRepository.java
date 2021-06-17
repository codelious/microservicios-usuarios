package com.codelious.microservicios.app.usuarios.microserviciosusuarios.model.repository;

import java.util.List;

import com.codelious.microservicios.commons.alumnos.commonsalumnos.models.entity.Alumno;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AlumnoRepository extends PagingAndSortingRepository<Alumno, Long> {
    
    @Query("select a from Alumno a where a.nombre like %?1% or a.apellido like %?1%")
    public List<Alumno> findByNombreOrApellido(String text);
    
}
