
package isi.deso.Modelo;

import java.time.LocalDate;
import java.util.List;

/**
 * Clase que representa una estadia registrada.
 * <p>
 * Contiene todos los atributos necesarios para
 * almacenar una reserva en el sistema.
 * </p>
 *
 * @see isi.deso.Modelo.Huesped
 */
public class Estadia {
   public String idEstadia, codReserva, codFactura;
   public String costo;
   public LocalDate fechaIn, fechaOut;
   public List<Huesped> huespedes;
   
    public Estadia() {
    }
   
   /**
     * Crea una instancia de Estadia con todos sus atributos.
     * 
     * @param id id de la estadia
     * @param codR codigo de la reserva
     * @param codF codigo de la factura
     * @param costo precio final de la estadia
     * @param fIn {@code java.time.LocalDate} fecha de ingreso
     * @param fOut {@code java.time.LocalDate} fecha de salida
     * @param h lista de huespedes
     */
   public Estadia(String id, String codR, String codF, String costo, LocalDate fIn, LocalDate fOut, List<Huesped> h){
       this.idEstadia = id;
       this.codReserva = codR;
       this.codFactura = codF;
       this.costo = costo;
       this.fechaIn = fIn;
       this.fechaOut = fOut;
       this.huespedes = h;
   }
   
   public String getIdEstadia(){
       return idEstadia;
   }
   public void setIdEstadia(String id){
       idEstadia = id;
   }
   public String getCodReserva(){
       return codReserva;
   }
   public void setCodReserva(String codR){
       codReserva = codR;
   }
   public String getCodFactura(){
       return codFactura;
   }
   public void setCodFactura(String codF){
       codFactura = codF;
   }
   public String getCosto(){
       return costo;
   }
   public void setCosto(String c){
       costo = c;
   }
   public LocalDate getFechaIngreso(){
       return fechaIn;
   }
   public void setFechaIngreso(LocalDate fI){
       fechaIn = fI;
   }
   public LocalDate getFechaSalida(){
       return fechaOut;
   }
   public void setFechaSalida(LocalDate fS){
       fechaOut = fS;
   }
   public List<Huesped> getHuespedes() {
        return this.huespedes;
    }
    public void setHuespedes(List<Huesped> huespedes) {
        this.huespedes = huespedes;
    }
}

