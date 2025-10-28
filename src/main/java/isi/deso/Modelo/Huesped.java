package isi.deso.Modelo;

public class Huesped extends Persona {
  public Huesped(){}
  public Huesped(String apellido, String nombres, TipoDocumento tipo, String numero){
    this.apellido=apellido; this.nombres=nombres; this.tipoDocumento=tipo; this.numeroDocumento=numero;
  }
    public String getApellido() { return apellido; }
  public String getNombres()  { return nombres; }
  public TipoDocumento getTipoDocumento() { return tipoDocumento; }
  public String getNumeroDocumento() { return numeroDocumento; }
  @Override public String toString(){ return apellido + ", " + nombres + " ("+idNatural()+")"; }
}

