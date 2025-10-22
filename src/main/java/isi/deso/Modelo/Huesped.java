package isi.deso.Modelo;

public class Huesped extends Persona {
  public Huesped(){}
  public Huesped(String apellido, String nombres, TipoDocumento tipo, String numero){
    this.apellido=apellido; this.nombres=nombres; this.tipoDocumento=tipo; this.numeroDocumento=numero;
  }
  @Override public String toString(){ return apellido + ", " + nombres + " ("+idNatural()+")"; }
}
