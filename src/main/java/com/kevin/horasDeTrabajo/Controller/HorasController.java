package com.kevin.horasDeTrabajo.Controller;

import com.kevin.horasDeTrabajo.Entities.HoraEmpleado;
import com.kevin.horasDeTrabajo.Repositories.EmpleadoRepository;
import com.kevin.horasDeTrabajo.Repositories.HoraEmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empleado")
public class HorasController {

    @Autowired
    private HoraEmpleadoRepository  horaEmpleadoRepository;
    @Autowired
    private EmpleadoRepository empleadoRepository;

    /************************AGREGADO DE HORAS***************************************/


    // Registrar horas de trabajo para un empleado
    @PostMapping("/{dni}/horas")
    public ResponseEntity<HoraEmpleado> registrarHoras(
            @PathVariable String dni,
            @RequestBody HoraEmpleado horaEmpleado) {

        return empleadoRepository.findById(dni).map(empleado -> {
            horaEmpleado.setEmpleado(empleado);
            HoraEmpleado nuevaHora = horaEmpleadoRepository.save(horaEmpleado);
            return ResponseEntity.ok(nuevaHora);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Obtener las horas trabajadas por un empleado
    @GetMapping("/{dni}/horas")
    public ResponseEntity<List<HoraEmpleado>> obtenerHorasPorEmpleado(@PathVariable String dni) {
        if (!empleadoRepository.existsById(dni)) {
            return ResponseEntity.notFound().build();
        }
        List<HoraEmpleado> horas = horaEmpleadoRepository.findByEmpleadoDni(dni);
        return ResponseEntity.ok(horas);
    }
    //Las horas se eliminan por su id
    // Método para eliminar una hora de trabajo por su ID
    @DeleteMapping("/horas/{id}")
    public ResponseEntity<Object> eliminarHoraEmpleado(@PathVariable Long id) {
        return horaEmpleadoRepository.findById(id).map(horaEmpleado -> {
            horaEmpleadoRepository.delete(horaEmpleado);
            return ResponseEntity.noContent().build();  // 204 No Content
        }).orElse(ResponseEntity.notFound().build());  // 404 Not Found si no se encuentra
    }

    // Método para actualizar las horas de trabajo de un empleado
    @PutMapping("/horas/{id}")
    public ResponseEntity<HoraEmpleado> actualizarHorasEmpleado(
            @PathVariable Long id,
            @RequestBody HoraEmpleado horasActualizadas) {

        return horaEmpleadoRepository.findById(id).map(horaEmpleado -> {
            // Actualiza los campos que quieras
            horaEmpleado.setHorasTrabajadas(horasActualizadas.getHorasTrabajadas());
            horaEmpleado.setFecha(horasActualizadas.getFecha());

            // Guarda los cambios
            HoraEmpleado horaActualizada = horaEmpleadoRepository.save(horaEmpleado);
            return ResponseEntity.ok(horaActualizada); // Respuesta 200 OK
        }).orElse(ResponseEntity.notFound().build()); // Respuesta 404 Not Found si no se encuentra
    }

}
