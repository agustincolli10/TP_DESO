package isi.deso.Servicio;

import isi.deso.Excepcion.AutenticacionException;
import isi.deso.DAO.UsuarioDAO;
import isi.deso.Modelo.Usuario;

import java.util.Objects;

/**
 * Servicio encargado de la autenticacion de usuarios.
 * <p>
 * Esta clase implementa la logica para validar el inicio de sesion,
 * utiliza una instancia de {@link isi.deso.DAO.UsuarioDAO} para verificar su validez.
 * </p>
 * 
 * @see isi.deso.DAO.UsuarioDAO
 */
public class AuthService {
  private final UsuarioDAO usuarioDAO;

  /**
   * Crea el Servicio de Autenticacion con un DAO de usuario.
   *
   * @param usuarioDAO instancia de {@link isi.deso.DAO.UsuarioDAO} utilizado para consultar el registro de usuarios
   */
  public AuthService(UsuarioDAO usuarioDAO) {
    this.usuarioDAO = Objects.requireNonNull(usuarioDAO, "usuarioDAO no puede ser null");
  }

  /**
   * Verifica que los campos no esten incompletos y luego busca un match en el registro de usuarios.
   *
   * @param username {@code String} que representa el nombre del usuario
   * @param password {@code String} que representa la contraseña del usuario
   * 
   * @throws AutenticacionException si una de las validaciones falla,
   * contiene un mensaje con el motivo del error
   */
  public void autenticar(String username, String password) {
    if (username == null || password == null || username.isBlank() || password.isBlank()) {
      throw new AutenticacionException("Credenciales incompletas");
    }

    boolean ok = usuarioDAO.listaCompUser().stream()
        .anyMatch(u -> equalsUser(u, username) && equalsPass(u, password));

    if (!ok) throw new AutenticacionException("Usuario o contraseña inválidos");
  }

  /**
   * Implementa la comparacion entre el nombre de usuario recibido y el registrado.
   * 
   * @param u {@link isi.deso.Modelo.Usuario} instancia de usuario registrado en el sistema
   * @param username {@code String} que representa el nombre recibido
   * @return {@code true} si la comparacion fue exitosa, {@code false} en caso contrario
   */
  private static boolean equalsUser(Usuario u, String username) {
    return u.getNUsuario() != null && u.getNUsuario().equals(username);
  }

  /**
   * Implementa la comparacion entre la contraseña recibida y la del usuario registrado.
   * 
   * @param u {@link isi.deso.Modelo.Usuario} instancia de usuario registrado en el sistema
   * @param pass {@code String} que representa la contraseña recibida
   * @return {@code true} si la comparacion fue exitosa, {@code false} en caso contrario
   */
  private static boolean equalsPass(Usuario u, String pass) {
    return u.getContrasenia() != null && u.getContrasenia().equals(pass);
  }
}
