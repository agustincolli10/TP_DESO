
package isi.deso.Modelo;

public abstract class Persona {
  protected String apellido, nombres, numeroDocumento;
  protected TipoDocumento tipoDocumento;
  public String getApellido(){return apellido;} public void setApellido(String v){apellido=v;}
  public String getNombres(){return nombres;} public void setNombres(String v){nombres=v;}
  public TipoDocumento getTipoDocumento(){return tipoDocumento;} public void setTipoDocumento(TipoDocumento v){tipoDocumento=v;}
  public String getNumeroDocumento(){return numeroDocumento;} public void setNumeroDocumento(String v){numeroDocumento=v;}
  public String idNatural(){ return (tipoDocumento==null?"":tipoDocumento.name())+"|"+(numeroDocumento==null?"":numeroDocumento); }
}
