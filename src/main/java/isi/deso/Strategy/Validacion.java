/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.Strategy;

import isi.deso.Modelo.Huesped;

public interface Validacion {
    boolean validar(Huesped h);
    String getMensajeError();
}
