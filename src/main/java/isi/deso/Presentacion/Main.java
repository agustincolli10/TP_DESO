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
import isi.deso.Strategy.ValidacionCampos;
import isi.deso.Strategy.ValidacionDocumentoUnico;

private static final HuespedDAO huespedDAO = new HuespedDAOImp();

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

           
            // Menú principal (CU02)
           
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

static void cu09() {
    System.out.println("\nCU09 - Alta de huésped");

    // Datos personales
    System.out.print("Nombres: "); String nombres = scanner.nextLine().trim();
    System.out.print("Apellido: "); String apellido = scanner.nextLine().trim();

    System.out.print("Tipo doc (DNI/LE/LC/PASAPORTE/OTRO): ");
    TipoDocumento tipo = parseTipo(scanner.nextLine());

    System.out.print("Número doc: "); String nroDoc = scanner.nextLine().trim();

    System.out.print("CUIT (ENTER si no tiene): "); String cuit = scanner.nextLine().trim();
    if (cuit.isEmpty()) cuit = null;

    System.out.print("Posición IVA (ResponsableInscripto/Monotributista/Exento/ConsumidorFinal): ");
    PosicionIVA pos = parsePos(scanner.nextLine());

    System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
    java.time.LocalDate fNac = parseFecha(scanner.nextLine());

    // Dirección
    System.out.println("\n--- Dirección ---");
    System.out.print("Calle: "); String calle = scanner.nextLine().trim();
    System.out.print("Número: "); String numero = scanner.nextLine().trim();
    System.out.print("Departamento: "); String depto = scanner.nextLine().trim();
    System.out.print("Piso: "); String piso = scanner.nextLine().trim();
    System.out.print("Código Postal: "); String cp = scanner.nextLine().trim();
    System.out.print("Localidad: "); String loc = scanner.nextLine().trim();
    System.out.print("Provincia: "); String prov = scanner.nextLine().trim();
    System.out.print("País: "); String pais = scanner.nextLine().trim();
    DireccionDTO dir = new DireccionDTO(calle, numero, depto, piso, cp, loc, prov, pais);

    // Contacto y otros
    System.out.println("\n--- Contacto y otros ---");
    System.out.print("Teléfono: "); String tel = scanner.nextLine().trim();
    System.out.print("Email (ENTER si no tiene): "); String email = scanner.nextLine().trim();
    if (email.isEmpty()) email = null;
    System.out.print("Ocupación: "); String ocu = scanner.nextLine().trim();
    System.out.print("Nacionalidad: "); String nac = scanner.nextLine().trim();

    // Construir entidad
    Huesped h = new Huesped(
        nombres, apellido, tipo, nroDoc, cuit,
        pos, fNac, dir, tel, email, ocu, nac
    );

    // Validaciones CU09
    Validacion v1 = new ValidacionCampos();
    Validacion v2 = new ValidacionDocumentoUnico(huespedDAO);

    boolean ok = v1.validar(h);
    if (!ok) { System.out.println(v1.getMensajeError()); return; }

    ok = v2.validar(h);
    if (!ok) { System.out.println(v2.getMensajeError()); return; }

    // Persistir
    try {
        huespedDAO.crearHuesped(h);
        System.out.println("✅ Huésped dado de alta correctamente.");
    } catch (Exception e) {
        System.out.println("❌ Error al guardar el huésped: " + e.getMessage());
    }
}
d) Helpers de parse (agregalos al final del Main)
java
Copiar código
private static PosicionIVA parsePos(String s) {
    if (s == null || s.isBlank()) return PosicionIVA.ConsumidorFinal;
    try { return PosicionIVA.valueOf(s.trim().replace(" ", "")); }
    catch (Exception e) { return PosicionIVA.ConsumidorFinal; }
}

private static java.time.LocalDate parseFecha(String s) {
    if (s == null || s.isBlank()) return null;
    try { return java.time.LocalDate.parse(s.trim()); } // YYYY-MM-DD
    catch (Exception e) { 
        // intento dd/MM/yyyy
        try {
            java.time.format.DateTimeFormatter f = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return java.time.LocalDate.parse(s.trim(), f);
        } catch (Exception ex) { return null; }
    }
}
    
}
