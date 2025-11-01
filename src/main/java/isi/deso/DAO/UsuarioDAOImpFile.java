package isi.deso.DAO;

import isi.deso.Modelo.Usuario;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementacion de UsuarioDAO que utiliza archivos de texto como
 * medio de persistencia.
 * <p>
 * Se encarga de guardar y recuperar usuarios 
 * desde el archivo usuariosCargados.txt
 * </p>
 * 
 * @see isi.deso.DAO.UsuarioDAO
 * @see isi.deso.Modelo.Usuario
 */
public class UsuarioDAOImpFile implements UsuarioDAO {
    private static final String ARCHIVO = "usuariosCargados.txt";

    /**
     * Devuelve una lista con los usuarios leidos en el archivo.
     *
     * @return coleccion con los usuarios leidos
     * @throws RuntimeException si hubo un error al leer el archivo
     */
    @Override
    public List<Usuario> listaCompUser() {
        List<Usuario> out = new ArrayList<>();
        File f = new File(ARCHIVO);
        if (!f.exists()) return out; // sin usuarios aún

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {
            String l;
            while ((l = br.readLine()) != null) {
                l = l.trim();
                if (l.isEmpty() || l.startsWith("#")) continue;

                // Formato: Nombre Apellido Usuario Contraseña (separados por espacios)
                String[] p = l.split("\\s+");
                if (p.length >= 4) {
                    String nombre      = p[0].trim();
                    String apellido    = p[1].trim();
                    String usuario     = p[2].trim();
                    String contrasenia = p[3].trim();  // asumimos sin espacios internos
                    out.add(new Usuario(nombre, apellido, usuario, contrasenia));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo " + ARCHIVO, e);
        }
        return out;
    }

    /**
     * Guarda un nuevo usuario en el archivo.
     *
     * @param u usuario a registrar
     * @throws RuntimeException si hubo un error al escribir en el archivo
     */
    @Override
    public void guardarUsuario(Usuario u) {
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(ARCHIVO, true), StandardCharsets.UTF_8))) {
            // Escribir con espacios como separador
            bw.write(u.getNombre() + " " + u.getApellido() + " " + u.getNUsuario() + " " + u.getContrasenia());
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error escribiendo " + ARCHIVO, e);
        }
    }
}
