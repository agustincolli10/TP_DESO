package isi.deso.DAO;

import isi.deso.Modelo.Usuario;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpFile implements UsuarioDAO {
    private static final String ARCHIVO = "usuariosCargados.txt";
    private static final String SEP = ";";

    @Override
    public List<Usuario> listaCompUser() {
        List<Usuario> out = new ArrayList<>();
        File f = new File(ARCHIVO);
        if (!f.exists()) return out; // sin usuarios aún

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {
            String l;
            while ((l = br.readLine()) != null) {
                if (l.isBlank() || l.startsWith("#")) continue;
                String[] p = l.split(SEP, -1);
                if (p.length >= 4) {
                    // formato: nombre;apellido;usuario;contrasenia
                    out.add(new Usuario(p[0].trim(), p[1].trim(), p[2].trim(), p[3].trim()));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo " + ARCHIVO, e);
        }
        return out;
    }

    @Override
    public void guardarUsuario(Usuario u) {
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(ARCHIVO, true), StandardCharsets.UTF_8))) {
            bw.write(u.getNombre() + SEP + u.getApellido() + SEP + u.getNUsuario() + SEP + u.getContrasenia());
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error escribiendo " + ARCHIVO, e);
        }
    }
}

