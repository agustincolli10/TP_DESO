package isi.deso.Presentacion;

import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import isi.deso.DAO.HuespedDAO;
import isi.deso.DAO.HuespedDAOImp;
import isi.deso.DAO.UsuarioDAO;
import isi.deso.DAO.UsuarioDAOImpFile;

import isi.deso.Modelo.Huesped;
import isi.deso.Modelo.TipoDocumento;
import isi.deso.Modelo.PosicionIVA;
import isi.deso.Modelo.DireccionDTO;

import isi.deso.Servicio.HuespedService;
import isi.deso.Servicio.AuthService;
import isi.deso.Excepcion.AutenticacionException;

import isi.deso.Strategy.Validacion;
import isi.deso.Strategy.ValidacionCampos;
import isi.deso.Strategy.ValidacionDocumentoUnico;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

   
    private static final HuespedDAO huespedDAO = new HuespedDAOImp();               // huespedesCargados.txt
    private static final UsuarioDAO usuarioDAO = new UsuarioDAOImpFile();           

    
    private static final HuespedService huespedService = new HuespedService(huespedDAO);

    public static void main(String[] args) {
        try {
            
            // CU01 - Autenticar usuario
     
            System.out.println("BIENVENIDO");
            System.out.println("Ingrese sus datos para continuar\n");

            AuthService auth = new AuthService(usuarioDAO);

            while (true) {
                System.out.print("Ingresar nombre de usuario: ");
                String nombreUs = scanner.nextLine();

                System.out.print("Ingresar contraseña: ");
                String contrasenia = scanner.nextLine();

                try {
                    auth.autenticar(nombreUs, contrasenia); // valida contra usuariosCargados.txt
                    System.out.println("Usuario validado\n");
                    break;
                } catch (AutenticacionException e) {
                    System.out.println("ERROR: " + e.getMessage());
                }
            }
            

            // Menú principal (CU02)
          
            while (true) {
                System.out.println();
                System.out.println("Menú:");
                System.out.println("1) CU02 - Buscar huésped");
                System.out.println("2) CU09 - Dar de alta huésped");
                System.out.println("3) CU10 - Dar de baja huésped");
                System.out.println("0) Salir");
                System.out.print("> ");
                String op = scanner.nextLine();
                switch (op) {
                    case "1" -> cu02();
                    case "2" -> cu09();
                    case "3" -> cu10();
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

        List<Huesped> lista = huespedService.buscar(ap, no, tipo, nro);

        if (lista.isEmpty()) {
            System.out.println("No existen concordancias para los criterios de búsqueda.");
            cu09(); // alta directa si no hay resultados
            return;
        }

        for (int i = 0; i < lista.size(); i++) {
            System.out.printf("[%d] %s%n", i + 1, lista.get(i));
        }

        System.out.print("Seleccione Nº (Enter vacío = alta): ");
        String sel = scanner.nextLine();
        if (sel.isBlank()) {
            cu09(); // alta si el usuario no selecciona ninguno
            return;
        }

        try {
            int idx = Integer.parseInt(sel) - 1;
            System.out.println("Seleccionado: " + lista.get(idx));
            System.out.println("Simular CU10: Modificar huésped.");
        } catch (Exception e) {
            System.out.println("Selección inválida → alta.");
            cu09();
        }
    }

    
    // CU09 - Alta de huésped
  
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

        System.out.print("Fecha de nacimiento (YYYY-MM-DD o dd/MM/yyyy): ");
        LocalDate fNac = parseFecha(scanner.nextLine());

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

       
        try {
            huespedDAO.crearHuesped(h);
            System.out.println("✅ Huésped dado de alta correctamente.");
        } catch (Exception e) {
            System.out.println("❌ Error al guardar el huésped: " + e.getMessage());
        }
    }
    
    //CU10 - Modificar Huesped
    
    static void cu10(){
        System.out.println("\nCU10 - Modificar huésped");
        
        
                       
    }

    //CU11 - Dar baja de Huesped
    
    static void cu11(){
        System.out.println("\nCU11 - Baja de huésped");
                       
    }

    private static TipoDocumento parseTipo(String s) {
        if (s == null || s.isBlank()) return null;
        try { return TipoDocumento.valueOf(s.trim().toUpperCase()); }
        catch (Exception e) { return null; }
    }

    private static PosicionIVA parsePos(String s) {
        if (s == null || s.isBlank()) return PosicionIVA.ConsumidorFinal;
        try { return PosicionIVA.valueOf(s.trim().replace(" ", "")); }
        catch (Exception e) { return PosicionIVA.ConsumidorFinal; }
    }

    private static LocalDate parseFecha(String s) {
        if (s == null || s.isBlank()) return null;
        String v = s.trim();
        try { return LocalDate.parse(v); } // YYYY-MM-DD
        catch (Exception e) {
            try {
                DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                return LocalDate.parse(v, f);
            } catch (Exception ex) { return null; }
        }
    }
}
