/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.DAO;

import isi.deso.Modelo.Usuario;
import java.util.List;
import java.util.function.Predicate;
/**
 *
 * @author USUARIO
 */
public interface UsuarioDAO {
    public List<Usuario> listaCompUser();
    public void guardarUsuario(Usuario usuario);
}
