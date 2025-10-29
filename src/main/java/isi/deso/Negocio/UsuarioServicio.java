package isi.deso.Negocio;

import isi.deso.DAO.UsuarioDAO;
import isi.deso.DAO.UsuarioDAOImpFile; //  usar archivo
import isi.deso.Modelo.Usuario;
import java.util.List;

public class UsuarioServicio {
    private final UsuarioDAO usuarioDao;

    public UsuarioServicio() {
        this.usuarioDao = new UsuarioDAOImpFile();
    }
  
    public UsuarioServicio(UsuarioDAO dao) {
        this.usuarioDao = dao;
    }

    public void registrarUsuario(Usuario nuevoUsuario) {
        usuarioDao.guardarUsuario(nuevoUsuario);
    }

    public List<Usuario> listarTodos() {
        return usuarioDao.listaCompUser();
    }

    public Boolean validarUser (String nombreUs, String contra){
      return usuarioDao.listaCompUser().stream()
              .anyMatch(u -> u.getNUsuario().equals(nombreUs) && u.getContrasenia().equals(contra));
    }
}
