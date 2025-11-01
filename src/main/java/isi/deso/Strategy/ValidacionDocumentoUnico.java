/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.Strategy;

import isi.deso.DAO.HuespedDAO;
import isi.deso.Modelo.Huesped;

/**
 * Estrategia de validacion que verifica que el documento
 * del huesped sea unico.
 * <p>
 * Comprueba que no exista otro huesped con el mismo tipo y numero
 * de documento en la lista de huespedes ya registrados.
 * </p>
 *
 * @see Validacion
 */
public class ValidacionDocumentoUnico implements Validacion{
    private HuespedDAO dao;
    
    /**
     * Crea una validacion de documento y recibe el DAO.
     *
     * @param dao instancia de {@link isi.deso.DAO.HuespedDAO} utilizado para consultar huespedes registrados
     */
    public ValidacionDocumentoUnico(HuespedDAO dao){
        this.dao=dao;
    }
    
    /**
     * Verifica que el huesped no tenga un documento repetido.
     *
     * @param h {@link isi.deso.Modelo.Huesped} a validar
     * @return {@code true} si el tipo y numero de documento son unicos, {@code false} si ya existe un huesped con el mismo documento
     */
    @Override
    public boolean validar(Huesped h){
        return dao.obtenerHuesped(h.getTipoDocumento(),h.getNumeroDocumento())==null;
    }
    @Override
    public String getMensajeError(){
        return "¡CUIDADO! El tipo y número de documento ya existen en el sistema.";
    }
}
