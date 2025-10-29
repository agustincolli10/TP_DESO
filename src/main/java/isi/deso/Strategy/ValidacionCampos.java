/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.Strategy;
import isi.deso.Modelo.Huesped;
import isi.deso.Modelo.DireccionDTO;
public class ValidacionCampos implements Validacion{
    @Override
    public boolean validar(Huesped h){
        if (h.getNombres() == null || h.getNombres().isEmpty()) { System.out.println("El/Los nombres no han sido completado/s.");return false; }
        if (h.getApellido() == null || h.getApellido().isEmpty()) { System.out.println("El apellido no ha sido completado");return false; }
        if (h.getTipoDocumento() == null) { System.out.println("El tipo de documento no ha sido completado.");return false; }
        if (h.getNumeroDocumento()== null || h.getNumeroDocumento().isEmpty()) { System.out.println("El documento no ha sido completado.");return false; }
        if (h.getFechaNacimiento()== null) { System.out.println("La fecha no ha sido especificada.");return false; }
        DireccionDTO aux = h.getDireccion();
        if (aux.getCalle()== null || aux.getCalle().isEmpty()) { System.out.println("La calle no ha sido completada.");return false; }
        if (aux.getNumero()== null || aux.getNumero().isEmpty()) { System.out.println("El número no ha sido completado.");return false; }
        if (aux.getDepartamento()== null || aux.getDepartamento().isEmpty()) { System.out.println("El departamento no ha sido completado.");return false; }
        if (aux.getPiso()== null || aux.getPiso().isEmpty()) { System.out.println("La calle no ha sido completada.");return false; }
        if (aux.getCodigoPostal()== null || aux.getCodigoPostal().isEmpty()) { System.out.println("El código postal no ha sido completado.");return false; }
        if (aux.getLocalidad()== null || aux.getLocalidad().isEmpty()) { System.out.println("La localidad no ha sido completada.");return false; }
        if (aux.getProvincia()== null || aux.getProvincia().isEmpty()) { System.out.println("La provincia no ha sido completada.");return false; }
        if (aux.getPais()== null || aux.getPais().isEmpty()) { System.out.println("El pais no ha sido completado.");return false; }
        if (h.getTelefono()== null || h.getTelefono().isEmpty()) { System.out.println("El teléfono no ha sido completado.");return false; }
        if (h.getOcupacion()== null || h.getOcupacion().isEmpty()) { System.out.println("La ocupación no ha sido completada.");return false; }
        if (h.getNacionalidad()== null || h.getNacionalidad().isEmpty()) { System.out.println("La nacionalidad no ha sido completada.");return false; }
        return true;
    }
    @Override
    public String getMensajeError() {
        return "Todos los campos deben ser completados obligatoriamente a excepción de aquellos los cuales son opcionales.";
    }
}
