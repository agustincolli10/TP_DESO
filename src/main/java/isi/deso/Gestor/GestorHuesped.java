/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.Gestor;

import isi.deso.Modelo.Huesped;
import isi.deso.Strategy.*;
import isi.deso.DAO.HuespedDAO;
import isi.deso.DAO.DireccionDAO;
import isi.deso.Modelo.DireccionDTO;
import java.util.List;
public class GestorHuesped {
    private DireccionDAO ddao;
    private HuespedDAO dao;
    private Validacion validaciones;
    private Validacion valid;
    public GestorHuesped(DireccionDAO ddao,HuespedDAO dao,Validacion validaciones, Validacion valid) {
        this.ddao = ddao;
        this.dao = dao;
        this.validaciones = validaciones;
        this.valid = valid;
    }
    public boolean ValidarCampos(Huesped h){
        return validaciones.validar(h);
    }
    public boolean ValidacionDocumentoUnico(Huesped h){
        return valid.validar(h);
    }
    public void crearHuesped(Huesped h){
        dao.crearHuesped(h);
    }
    public void crearDireccion(DireccionDTO d){
        ddao.crearDireccion(d);
    }
}
