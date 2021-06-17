package com.codelious.microservicios.app.usuarios.microserviciosusuarios.services;

import java.util.List;

import com.codelious.microservicios.commons.alumnos.commonsalumnos.models.entity.Alumno;
import com.codelious.microservicios.commons.commonsmicroservicios.services.CommonService;


public interface AlumnoService extends CommonService<Alumno> {
    
    public List<Alumno> findByNombreOrApellido(String text);

}
