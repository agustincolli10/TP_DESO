
package isi.deso.Negocio;

import isi.deso.DAO.UsuarioDAO;
import isi.deso.DAO.UsuarioDaoImp;
import isi.deso.Modelo.Usuario;
import java.util.List;

public class UsuarioServicio {
    private UsuarioDAO usuarioDao = UsuarioDaoImp.getInstance();

    public void registrarUsuario(Usuario nuevoUsuario) {
        usuarioDao.guardarUsuario(nuevoUsuario);
    }
    
    // Método para verificar que se guardó
    public List<Usuario> listarTodos() {
        return usuarioDao.listaCompUser();
    }
    
    public Boolean validarUser (String nombreUs, String contra){
      Boolean valido = false;
        
      List<Usuario> listaUsuarios = usuarioDao.listaCompUser();
      
      int i=0;
      
      while (i < listaUsuarios.size() && valido == false){
          Usuario userTemp = listaUsuarios.get(i);
          
          if((userTemp.getNUsuario().equals(nombreUs)) && (userTemp.getContrasenia().equals(contra))){
              valido = true;
              System.out.println("Bienvenido " + userTemp.getNombre() + " " + userTemp.getApellido());
          }
          
          i++;
      }
      
      return valido;
    }
    
    /*public Usuario validarUsuario(String nombreUs, String contrasenia) {
        Usuario usuarioEncontrado = usuarioDao.buscarPor(nombreUs);

        if (usuarioEncontrado == null) {
            return null; // El usuario no existe, la validación falla.
        }

        if (usuarioEncontrado.getContrasenia().equals(contrasenia)) {
            return usuarioEncontrado; // ¡Éxito! Las credenciales son correctas.
        } else {
            return null; // La contraseña es incorrecta, la validación falla.
        }
    }*/
}
