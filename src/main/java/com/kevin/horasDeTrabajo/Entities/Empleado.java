package com.kevin.horasDeTrabajo.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "empleados")
public class Empleado implements Serializable {

   // @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Id
    private String dni;
    private String nombre;
    private String puesto;
  //  @Column(name = "fecha_contratacion")
  //  private Date fechaContratacion;

}
