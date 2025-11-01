/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.Strategy;

import isi.deso.Modelo.Huesped;

/**
 * Interfaz del patron Strategy para validaciones.
 * <p>
 * Define la base de todas las estrategias de validacion.
 * Cada implementacion representa una estrategia de validacion distinta.
 * </p>
 */
public interface Validacion {
    /**
     * Ejecuta la validacion correspondiente sobre el huesped.
     *
     * @param h {@link isi.deso.Modelo.Huesped} a validar
     * @return {@code true} si la validación fue exitosa, {@code false} en caso contrario
     */
    boolean validar(Huesped h);

    /**
     * Devuelve el mensaje de error asociado a la validacion fallida.
     *
     * @return una descripcion del error.
     */
    String getMensajeError();
}
