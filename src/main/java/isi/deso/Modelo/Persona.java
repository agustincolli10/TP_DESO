
package isi.deso.Modelo;
import java.time.LocalDate;

/**
 * Clase abstracta que representa a una persona.
 * <p>
 * Contiene los datos personales y de identificacion comunes,
 * como nombre, apellido, documento, dirección, contacto y posición frente al IVA.
 * </p>
 * 
 * @see isi.deso.Modelo.TipoDocumento
 * @see isi.deso.Modelo.DireccionDTO
 */
public abstract class Persona {
  protected String apellido, nombres, numeroDocumento, cuit, telefono, email, ocupacion, nacionalidad;
  protected TipoDocumento tipoDocumento;
  protected PosicionIVA posicion;
  protected DireccionDTO dir;
  protected LocalDate fNac;
  
  public String getApellido(){return apellido;} public void setApellido(String v){apellido=v;}
  public String getNombres(){return nombres;} public void setNombres(String v){nombres=v;}
  public TipoDocumento getTipoDocumento(){return tipoDocumento;} public void setTipoDocumento(TipoDocumento v){tipoDocumento=v;}
  public String getNumeroDocumento(){return numeroDocumento;} public void setNumeroDocumento(String v){numeroDocumento=v;}

  /** Devuelve una cadena con el tipo y numero de documento */
  public String idNatural(){ return (tipoDocumento==null?"":tipoDocumento.name())+"|"+(numeroDocumento==null?"":numeroDocumento); }
  
  public DireccionDTO getDireccion() { return dir; } public void setDireccion(DireccionDTO d){dir=d;}
  public LocalDate getFechaNacimiento() { return fNac; } public void setFechaNacimiento(LocalDate f) {fNac=f;}
  public String getCuit() {return cuit; } public void setCuit(String c) {cuit=c;}
  public PosicionIVA getPosicionIVA() { return posicion; } public void setPosicionIVA(PosicionIVA p) {posicion=p;}
  public String getTelefono() { return telefono; } public void setTelefono(String t) {telefono=t;}
  public String getEmail() { return email; } public void setEmail(String e) {email=e;}
  public String getOcupacion() { return ocupacion; } public void setOcupacion(String o) {ocupacion=o;}
  public String getNacionalidad() { return nacionalidad; } public void setNacionalidad(String n) {nacionalidad=n;}
}
