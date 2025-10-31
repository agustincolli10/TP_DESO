
package isi.deso.Modelo;

import java.time.LocalDate;
import java.util.List;


public class Estadia {
   public String idEstadia, codReserva, codFactura;
   public String costo;
   public LocalDate fechaIn, fechaOut;
   public List<Huesped> huespedes;
   
    public Estadia() {
    }
   
   
   
   
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

