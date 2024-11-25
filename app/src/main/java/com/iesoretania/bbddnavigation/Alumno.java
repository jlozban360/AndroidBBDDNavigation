package com.iesoretania.bbddnavigation;

public class Alumno {
    private String dni;
    private String nombre;
    private String apellidos;
    private String sexo;

    // Constructor
    public Alumno( String dni, String nombre, String apellidos, String sexo ) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.sexo = sexo;
    }

    // Getters
    public String getDni( ) {
        return dni;
    }

    public String getNombre( ) {
        return nombre;
    }

    public String getApellidos( ) {
        return apellidos;
    }

    public String getSexo( ) {
        return sexo;
    }
}
