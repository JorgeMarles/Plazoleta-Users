package com.jamarlesf.plazoletausers.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    public static final String ADMINISTRADOR = "ADMINISTRADOR";
    public static final String PROPIETARIO = "PROPIETARIO";
    public static final String EMPLEADO = "EMPLEADO";
    public static final String CLIENTE = "CLIENTE";

    private Long id;
    private String name;
    private String description;
}
