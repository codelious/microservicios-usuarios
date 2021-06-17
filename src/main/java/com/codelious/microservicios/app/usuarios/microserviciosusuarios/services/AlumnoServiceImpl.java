package com.codelious.microservicios.app.usuarios.microserviciosusuarios.services;

import java.util.List;

import com.codelious.microservicios.app.usuarios.microserviciosusuarios.model.repository.AlumnoRepository;
import com.codelious.microservicios.commons.alumnos.commonsalumnos.models.entity.Alumno;
import com.codelious.microservicios.commons.commonsmicroservicios.services.CommonServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlumnoServiceImpl extends CommonServiceImpl<Alumno, AlumnoRepository> implements AlumnoService {

    @Override
    @Transactional(readOnly = true)
    public List<Alumno> findByNombreOrApellido(String text) {
        return repository.findByNombreOrApellido(text);
    }

}
