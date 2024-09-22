package com.kevin.horasDeTrabajo.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hora_empleados")
public class HoraEmpleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //con estos dos indicamos la relacion N a 1 (Muchos a 1), es decir, muchos registros de hora pertencen a un empleado.
    @ManyToOne
    @JoinColumn(name = "empleado_dni", referencedColumnName = "dni")
    private Empleado empleado;


    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    @Column(name = "horas_trabajadas")
    private double horasTrabajadas;

}
