/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.Presentacion;

import java.util.List;
import java.util.Scanner;

import isi.deso.Modelo.TipoDocumento;
import isi.deso.Servicio.HuespedService;
import isi.deso.Servicio.AuthService;
import isi.deso.Excepcion.AutenticacionException;
import isi.deso.DAO.HuespedDAO;
import isi.deso.DAO.HuespedDAOImp;
import isi.deso.Modelo.Huesped;
import isi.deso.Modelo.DireccionDTO;
import isi.deso.Modelo.PosicionIVA;
import isi.deso.Strategy.Validacion;
import isi.deso.DAO.UsuarioDAO;
import isi.deso.DAO.UsuarioDAOImpFile;
import isi.deso.DAO.HuespedDAO;
import isi.deso.DAO.HuespedDAOImp;

import isi.deso.Strategy.ValidacionCampos;
import isi.deso.Strategy.ValidacionDocumentoUnico;

private static final HuespedDAO huespedDAO = new HuespedDAOImp();
private static final HuespedService huespedService = new HuespedService(new HuespedDAOImp());

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final HuespedService huespedService = new HuespedService();

    public static void main(String[] args) {
        try {
            
            // CU01 - Autenticar usuario
         
            System.out.println("BIENVENIDO");
            System.out.println("Ingrese sus datos para continuar\n");

            AuthService auth = new AuthService();

            while (true) {
                System.out.print("Ingresar nombre de usuario: ");
                String nombreUs = scanner.nextLine();

                System.out.print("Ingresar contraseña: ");
                String contrasenia = scanner.nextLine();

                try {
                    // Lanza AutenticacionException si falla 
                    auth.autenticar(nombreUs, contrasenia);
                    System.out.println("Usuario validado\n");
                    break;
                } catch (AutenticacionException e) {
                    System.out.println("ERROR: " + e.getMessage());
                    // vuelve a pedir credenciales
                }
            }

           
            // Menú principal 
           
            while (true) {
                System.out.println();
                System.out.println("Menú:");
                System.out.println("1) CU02 - Buscar huésped");
                System.out.println("0) Salir");
                System.out.print("> ");
                String op = scanner.nextLine();
                switch (op) {
                    case "1" -> cu02(); // <<-- activar CU02
                    case "0" -> { System.out.println("Fin."); return; }
                    default -> System.out.println("Opción inválida");
                }
            }

        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            System.out.println("Fin.");
        }
    }

    
    // CU02 - Buscar huésped

    static void cu02() {
        System.out.println("CU02 - Buscar huésped");

        System.out.print("Apellido empieza con (ENTER salta): ");
        String ap = scanner.nextLine();

        System.out.print("Nombres empieza con (ENTER salta): ");
        String no = scanner.nextLine();

        System.out.print("Tipo doc (DNI/LE/LC/PASAPORTE/OTRO) o ENTER: ");
        String t = scanner.nextLine();
        TipoDocumento tipo = parseTipo(t);

        System.out.print("Número doc (ENTER salta): ");
        String nro = scanner.nextLine();

        List<isi.deso.Modelo.Huesped> lista = huespedService.buscar(ap, no, tipo, nro);

if (lista.isEmpty()) {
    System.out.println("No existen concordancias para los criterios de búsqueda.");
    cu09();     
    return;
}

        for (int i = 0; i < lista.size(); i++) {
            System.out.printf("[%d] %s%n", i + 1, lista.get(i));
        }

        System.out.print("Seleccione Nº (Enter vacío = alta): ");
        String sel = scanner.nextLine();
if (sel.isBlank()) {
    cu09();       
    return;
}

    private static TipoDocumento parseTipo(String s) {
        if (s == null || s.isBlank()) return null;
        try { return TipoDocumento.valueOf(s.trim().toUpperCase()); }
        catch (Exception e) { return null; }
    }


    
}
