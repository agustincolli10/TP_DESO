package isi.deso.Servicio;

import isi.deso.Excepcion.AutenticacionException;
import isi.deso.DAO.UsuarioDAO;
import isi.deso.Modelo.Usuario;

import java.util.Objects;

public class AuthService {
  private final UsuarioDAO usuarioDAO;

  public AuthService(UsuarioDAO usuarioDAO) {
    this.usuarioDAO = Objects.requireNonNull(usuarioDAO, "usuarioDAO no puede ser null");
  }

  public void autenticar(String username, String password) {
    if (username == null || password == null || username.isBlank() || password.isBlank()) {
      throw new AutenticacionException("Credenciales incompletas");
    }

    boolean ok = usuarioDAO.listaCompUser().stream()
        .anyMatch(u -> equalsUser(u, username) && equalsPass(u, password));

    if (!ok) throw new AutenticacionException("Usuario o contraseña inválidos");
  }

  private static boolean equalsUser(Usuario u, String username) {
    return u.getNUsuario() != null && u.getNUsuario().equals(username);
  }
  private static boolean equalsPass(Usuario u, String pass) {
    return u.getContrasenia() != null && u.getContrasenia().equals(pass);
  }
}
