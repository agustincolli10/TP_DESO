/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.DAO;

import isi.deso.Modelo.DireccionDTO;
import java.util.List;

public interface DireccionDAO {
    void crearDireccion(DireccionDTO d);
    void modificarDireccion(String calle, String numero, String departamento, String piso, String codigoPostal);
    void eliminarDireccion(DireccionDTO d);
    DireccionDTO obtenerDireccion(String calle, String numero, String departamento, String piso, String codigoPostal);
    List<DireccionDTO> obtenerTodos();
}
