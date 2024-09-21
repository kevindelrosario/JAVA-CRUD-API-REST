package com.kevin.horasDeTrabajo.Repositories;

import com.kevin.horasDeTrabajo.Entities.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado,String> {
}
