/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.Strategy;

import isi.deso.DAO.HuespedDAO;
import isi.deso.Modelo.Huesped;


public class ValidacionDocumentoUnico implements Validacion{
    private HuespedDAO dao;
    
    public ValidacionDocumentoUnico(HuespedDAO dao){
        this.dao=dao;
    }
    
    @Override
    public boolean validar(Huesped h){
        return dao.obtenerHuesped(h.getTipoDocumento(),h.getNumeroDocumento())==null;
    }
    @Override
    public String getMensajeError(){
        return "¡CUIDADO! El tipo y número de documento ya existen en el sistema.";
    }
}
