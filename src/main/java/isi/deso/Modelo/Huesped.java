package isi.deso.Modelo;

import java.time.LocalDate;

/**
 * Clase que representa a un huesped registrado.
 * <p>
 * Hereda los atributos de datos personales y de identificacion de {@link isi.deso.Modelo.Persona},
 * tambien hereda sus metodos.
 * </p>
 *
 * @see isi.deso.Modelo.Persona
 */
public class Huesped extends Persona {
  public Huesped(){}

  /**
   * Crea una instancia de Huesped con todos sus atributos esenciales.
   * 
   * @param nombre nombre del huesped
   * @param apellido apellido del huesped
   * @param tipo {@link isi.deso.Modelo.TipoDocumento} del huesped
   * @param numero numero del documento
   * @param direccion {@link isi.deso.Modelo.DireccionDTO} del huesped
   * @param telefono numero de telefono del huesped
   * @param email correo electronico del huesped
   * @param posicionIVA {@link isi.deso.Modelo.PosicionIVA} del huesped
   */
  public Huesped(String nombres, String apellido, TipoDocumento t, String numDoc, String cuit, 
        PosicionIVA pos, LocalDate fecha, DireccionDTO d, String telefono, String email, String ocupacion, String nacionalidad) {
      this.nombres = nombres;
      this.apellido = apellido;
      this.tipoDocumento = t;
      this.numeroDocumento = numDoc;
      this.cuit = cuit;
      this.posicion = pos;
      this.fNac = fecha;
      this.dir = d;
      this.telefono = telefono;
      this.email = email;
      this.ocupacion = ocupacion;
      this.nacionalidad = nacionalidad;
  }

  /**
    * Devuelve un texto que describe al huesped.
    *
    * @return texto del huesped
    */
  @Override
  public String toString(){ return apellido + ", " + nombres + " ("+idNatural()+")"; }
}

