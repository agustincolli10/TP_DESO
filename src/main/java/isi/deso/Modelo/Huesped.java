package isi.deso.Modelo;

import java.time.LocalDate;

public class Huesped extends Persona {
  public Huesped(){}
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
  @Override
  public String toString(){ return apellido + ", " + nombres + " ("+idNatural()+")"; }
}

