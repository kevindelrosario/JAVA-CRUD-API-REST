package com.kevin.horasDeTrabajo.Controller;

import com.kevin.horasDeTrabajo.Entities.Empleado;
import com.kevin.horasDeTrabajo.Entities.HoraEmpleado;
import com.kevin.horasDeTrabajo.Repositories.EmpleadoRepository;
import com.kevin.horasDeTrabajo.Repositories.HoraEmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empleado")
public class EmpleadoController {

    private final EmpleadoRepository empleadoRepository;
    private final HoraEmpleadoRepository horaEmpleadoRepository;

    @Autowired
    public EmpleadoController(EmpleadoRepository empleadoRepository, HoraEmpleadoRepository horaEmpleadoRepository) {
        this.empleadoRepository = empleadoRepository;
        this.horaEmpleadoRepository = horaEmpleadoRepository;
    }
    //Obtener todos
    @GetMapping
    public List<Empleado> obtenerEmpleados() {
        return empleadoRepository.findAll();
    }

    //crear
    @PostMapping
    public Empleado crearEmpleado(@RequestBody Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @GetMapping("/{dni}")
    public Empleado obtenerEmpleadoPorDni(@PathVariable String dni) {
        return empleadoRepository.findById(dni)
                .orElseThrow(() -> new RuntimeException("No se encontro el empleado con el dni: "+dni));
    }


    //actualizar
   @PutMapping("/{dni}")
    public Empleado actualizarEmpleado(@PathVariable String dni, @RequestBody Empleado detallesEmpleado) { //entran los datos nuevos para el empleado
        //busca el empleado y verifica que existe
      Empleado empleado = empleadoRepository.findById(dni)
               .orElseThrow(() -> new RuntimeException("No se encontro el empleado con el dni: "+dni));

      //Ingresan los nuevos valores
       empleado.setNombre(detallesEmpleado.getNombre());
       empleado.setPuesto(detallesEmpleado.getPuesto());
      // empleado.setFechaContratacion(detallesEmpleado.getFechaContratacion());

       return  empleadoRepository.save(empleado); //retorna el empleado si se encontro como señal de que se ha actulizado
    }

    @DeleteMapping("/{dni}")
    public String borrarEmpleado(@PathVariable String dni) {
        //busca el empleado y verifica que existe
        Empleado empleado = empleadoRepository.findById(dni)
                .orElseThrow(() -> new RuntimeException("No se encontro el empleado con el dni: "+dni));
        //Si lo encuentra lo eliminara
        empleadoRepository.delete(empleado);

        return "El empleado con dni: " + dni+ " fue eliminado.";
    }


    /************************AGREGADO DE HORAS***************************************/
   // @Autowired

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

}
