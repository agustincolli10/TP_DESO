
package isi.deso.Modelo;


public class Usuario {
    //Atributos
    private int id;
    private String nombre;
    private String apellido;
    private String nombreUsuario;
    private String contrasenia;
    
    //Constructor
    public Usuario(String nombre, String apellido, String nombreUsuario, String contrasenia){
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
    }
    
    //Metodos
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getNUsuario() { return nombreUsuario; }
    public void setNUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getContrasenia() { return contrasenia; }
    public void setContrasenia(String contrasenia) { this.contrasenia = contrasenia; }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    @Override
    public String toString(){
        return "Conserje: " + '\n' + "ID: " + nombreUsuario + '\n' + "Nombre: " + nombre + " " + apellido + '\n';
    }
    
}
