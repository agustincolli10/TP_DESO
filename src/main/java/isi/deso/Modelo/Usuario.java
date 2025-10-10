/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.Modelo;

/**
 *
 * @author USUARIO
 */
public class Usuario {
    private String nombre;
    private String apellido;
    private String nombreUsuario;
    private String contrasenia;
    
    public Usuario(String nombre, String apellido, String nombreUsuario, String contrasenia){
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
    }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getNUsuario() { return nombreUsuario; }
    public void setNUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getContrasenia() { return contrasenia; }
    public void setContrasenia(String contrasenia) { this.contrasenia = contrasenia; }
    
    @Override
    public String toString(){
        return "Conserje: " + '\n' + "ID: " + nombreUsuario + '\n' + "Nombre: " + nombre + " " + apellido + '\n';
    }
    
}
