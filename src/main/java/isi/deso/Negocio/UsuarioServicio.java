package isi.deso.Negocio;

import isi.deso.DAO.UsuarioDAO;
import isi.deso.DAO.UsuarioDAOImpFile; //  usar archivo
import isi.deso.Modelo.Usuario;
import java.util.List;

/**
 * Servicio de negocio para la gestión de usuarios.
 * 
 * <p>
 * Se encarga de manejar las operaciones principales de usuarios,
 * usando un DAO para guardar y acceder a los datos.
 * </p>
 */
public class UsuarioServicio {
    private final UsuarioDAO usuarioDao;

    /**
     * Crea un servicio de usuarios utilizando la implementacion de archivo
     * ({@link isi.deso.DAO.UsuarioDAOImpFile}).
     */
    public UsuarioServicio() {
        this.usuarioDao = new UsuarioDAOImpFile();
    }
  
    /**
     * Crea un servicio de usuarios con un {@link UsuarioDAO} provisto externamente.
     *
     * @param dao implementación del DAO a utilizar
     */
    public UsuarioServicio(UsuarioDAO dao) {
        this.usuarioDao = dao;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param nuevoUsuario usuario a registrar
     */
    public void registrarUsuario(Usuario nuevoUsuario) {
        usuarioDao.guardarUsuario(nuevoUsuario);
    }

    /**
     * Obtiene la lista completa de usuarios registrados.
     *
     * @return lista con todos los usuarios guardados
     */
    public List<Usuario> listarTodos() {
        return usuarioDao.listaCompUser();
    }

    /**
     * Valida las credenciales del usuario buscando una coincidencia de
     * nombre de usuario y contraseña.
     *
     * @param nombreUs nombre de usuario a validar
     * @param contra contraseña del usuario
     * @return {@code true} si existe un usuario con el nombre y la contraseña,
     * {@code false} en caso contrario
     */
    public Boolean validarUser (String nombreUs, String contra){
      return usuarioDao.listaCompUser().stream()
              .anyMatch(u -> u.getNUsuario().equals(nombreUs) && u.getContrasenia().equals(contra));
    }
}
