/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.DAO;

import isi.deso.Modelo.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author USUARIO
 */
public class UsuarioDaoImp implements UsuarioDAO{
    private Map<String, Usuario> usuarios; //key: ID
    private static UsuarioDaoImp instancia;
    
    private UsuarioDaoImp (){
        this.usuarios = new HashMap<String, Usuario>();
    }
    
    public static UsuarioDaoImp getInstance() {
	if (instancia == null) {
            instancia = new UsuarioDaoImp();
	}
        return instancia;
    }
    
    @Override
    public List<Usuario> listaCompUser() {
	return new ArrayList<>(this.usuarios.values());
    }

    @Override
    public void guardarUsuario(Usuario usuario) {
	this.usuarios.put(usuario.getNUsuario(), usuario);
    }

}
