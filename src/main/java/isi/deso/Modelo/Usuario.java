
package isi.deso.Modelo;

/**
 * Representa a un usuario del sistema.
 * <p>
 * Contiene la informacion basica del usuario utilizada para la autenticacion,
 * como el nombre de usuario y la contraseña.
 * </p>
 */
public class Usuario {
    //Atributos
    private int id;
    private String nombre;
    private String apellido;
    private String nombreUsuario;
    private String contrasenia;
    
    //Constructor
    /**
     * Crea una instancia de Usuario.
     *
     * @param nombre {@code String} nombre del usuario
     * @param apellido {@code String} apellido del usuario
     * @param nombreUsuario {@code String} nombre de usuario para autenticacion.
     * @param contrasenia {@code String} contraseña del usuario
     */
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
    
    /** Devuelve el id del usuario. */
    public int getId() { return id; }
    /** Establece el id del usuario. */
    public void setId(int id) { this.id = id; }
    
    /**
     * Devuelve un texto que describe al usuario.
     *
     * @return texto del usuario
     */
    @Override
    public String toString(){
        return "Conserje: " + '\n' + "ID: " + nombreUsuario + '\n' + "Nombre: " + nombre + " " + apellido + '\n';
    }
    
}
