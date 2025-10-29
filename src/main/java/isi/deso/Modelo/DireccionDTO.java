/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.Modelo;

public class DireccionDTO {
    private String calle,numero,departamento,piso,codigoPostal,localidad,provincia,pais;
    public DireccionDTO(String calle, String numero, String departamento, String piso, String codigoPostal, String localidad, String provincia, String pais) {
        this.calle = calle;
        this.numero = numero;
        this.departamento = departamento;
        this.piso = piso;
        this.codigoPostal = codigoPostal;
        this.localidad = localidad;
        this.provincia = provincia;
        this.pais = pais;
    }
    public String getCalle() { return calle; }
    public String getNumero() { return numero; }
    public String getDepartamento() { return departamento; }
    public String getPiso() { return piso; }
    public String getCodigoPostal() { return codigoPostal; }
    public String getLocalidad() { return localidad; }
    public String getProvincia() { return provincia; }
    public String getPais() { return pais; }
    @Override
    public String toString() {
        return calle + " " + numero + ", " + departamento + " "+ piso + ", " + codigoPostal + ", " + localidad + ", " + provincia + " (" + pais + ")";
    }
}
