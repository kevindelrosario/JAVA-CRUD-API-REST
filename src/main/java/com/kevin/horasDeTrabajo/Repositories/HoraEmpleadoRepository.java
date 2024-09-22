package com.kevin.horasDeTrabajo.Repositories;

import com.kevin.horasDeTrabajo.Entities.HoraEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HoraEmpleadoRepository extends JpaRepository<HoraEmpleado,Long> {
List<HoraEmpleado> findByEmpleadoDni(String dni);

}
