
package isi.deso.Servicio;
import ar.edu.utn.ds2025.hotel.exception.AutenticacionException;
import java.io.*; import java.nio.charset.StandardCharsets;
public class AuthService {
  public void autenticar(String username, String password) {
    if (username == null || password == null || username.isBlank() || password.isBlank()) {
      throw new AutenticacionException("Credenciales incompletas");
    }
    boolean ok = false;
    try (InputStream is = getClass().getClassLoader().getResourceAsStream("data/usuarios.csv")) {
      if (is == null) throw new AutenticacionException("No se encontró usuarios.csv");
      try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
        String line;
        while ((line = br.readLine()) != null) {
          if (line.isBlank() || line.startsWith("#")) continue;
          String[] p = line.split(";", -1);
          if (p.length >= 2 && p[0].equals(username) && p[1].equals(password)) { ok = true; break; }
        }
      }
    } catch (IOException e) { throw new AutenticacionException("Error leyendo usuarios"); }
    if (!ok) throw new AutenticacionException("Usuario o contraseña inválidos");
  }
}
