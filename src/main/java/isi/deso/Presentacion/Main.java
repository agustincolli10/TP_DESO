package isi.deso.Presentacion;

import isi.deso.DAO.DireccionDAO;
import isi.deso.DAO.DireccionDAOImp;
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
import isi.deso.Gestor.GestorHuesped;

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

                System.out.print("Ingresar contrasenia: ");
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
                System.out.println("Menu:");
                System.out.println("1) Buscar huesped");
                System.out.println("2) Dar de alta huesped");
                System.out.println("3) Dar de baja huesped");
                System.out.println("0) Salir");
                System.out.print("> ");
                String op = scanner.nextLine();
                switch (op) {
                    case "1" -> cu02();
                    case "2" -> cu09();
                    case "3" -> cu10();
                    case "0" -> { System.out.println("Fin."); return; }
                    default -> System.out.println("Opcion invalida");
                }
            }

        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            System.out.println("Fin.");
        }
    }

   
    // CU02 - Buscar huésped
  
    static void cu02() {
        System.out.println("CU02 - Buscar huesped");

        System.out.print("Apellido empieza con (ENTER salta): ");
        String ap = scanner.nextLine();

        System.out.print("Nombres empieza con (ENTER salta): ");
        String no = scanner.nextLine();

        System.out.print("Tipo doc (DNI/LE/LC/PASAPORTE/OTRO) o ENTER: ");
        String t = scanner.nextLine();
        TipoDocumento tipo = parseTipo(t);

        System.out.print("Numero doc (ENTER salta): ");
        String nro = scanner.nextLine();

        List<Huesped> lista = huespedService.buscar(ap, no, tipo, nro);

        if (lista.isEmpty()) {
            System.out.println("No existen concordancias para los criterios de busqueda.");
            cu09(); // alta directa si no hay resultados
            return;
        }

        for (int i = 0; i < lista.size(); i++) {
            System.out.printf("[%d] %s%n", i + 1, lista.get(i));
        }

        System.out.print("Seleccione Numero (Enter vacio = alta): ");
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
            System.out.println("Selección invalida: alta.");
            cu09();
        }
    }

    
    // CU09 - Alta de huésped
  
    static void cu09() {
        boolean flag=true;
        String nombres,apellido,nroDoc,cuit,posicion,calle,numero,depto,piso,cp,loc,prov,pais,tel,email,ocu,nac;
        TipoDocumento tipo; PosicionIVA pos; LocalDate fNac;
        while(flag){
            boolean camposIncompletos=true;
            DireccionDAO ddao = new DireccionDAOImp();
            HuespedDAO dao = new HuespedDAOImp();
            Validacion v1 = new ValidacionCampos();
            Validacion v2 = new ValidacionDocumentoUnico(dao);
            Huesped h=null; DireccionDTO dir=null;
            GestorHuesped g = new GestorHuesped(ddao,dao,v1,v2);
            while(camposIncompletos){
                System.out.println("\nCU09 - Alta de huesped");

                // Datos personales
                System.out.print("Nombres: "); nombres = scanner.nextLine().trim();
                System.out.print("Apellido: "); apellido = scanner.nextLine().trim();

                System.out.print("Tipo doc (DNI/LE/LC/PASAPORTE/OTRO): ");
                tipo = parseTipo(scanner.nextLine());

                System.out.print("Numero doc: "); nroDoc = scanner.nextLine().trim();
                System.out.print("CUIT (ENTER si no tiene): "); cuit = scanner.nextLine().trim();
                if (cuit.isEmpty()) cuit = null;

                System.out.print("Posicion IVA (ResponsableInscripto/Monotributista/Exento/ConsumidorFinal): "); posicion = scanner.nextLine().trim();
                if (posicion.isEmpty()) posicion = "ConsumidorFinal";
                pos = parsePos(posicion);

                System.out.print("Fecha de nacimiento (YYYY-MM-DD o dd/MM/yyyy): ");
                fNac = parseFecha(scanner.nextLine());

                // Dirección
                System.out.println("\n--- Direccion ---");
                System.out.print("Calle: "); calle = scanner.nextLine().trim();
                System.out.print("Numero: "); numero = scanner.nextLine().trim();
                System.out.print("Departamento: "); depto = scanner.nextLine().trim();
                System.out.print("Piso: "); piso = scanner.nextLine().trim();
                System.out.print("Codigo Postal: "); cp = scanner.nextLine().trim();
                System.out.print("Localidad: "); loc = scanner.nextLine().trim();
                System.out.print("Provincia: "); prov = scanner.nextLine().trim();
                System.out.print("Pais: "); pais = scanner.nextLine().trim();
                DireccionDTO diraux = new DireccionDTO(calle, numero, depto, piso, cp, loc, prov, pais);
                
                // Contacto y otros
                System.out.println("\n--- Contacto y otros ---");
                System.out.print("Telefono: "); tel = scanner.nextLine().trim();
                System.out.print("Email (ENTER si no tiene): "); email = scanner.nextLine().trim();
                if (email.isEmpty()) email = null;
                System.out.print("Ocupacion: "); ocu = scanner.nextLine().trim();
                System.out.print("Nacionalidad: "); nac = scanner.nextLine().trim();
                
                System.out.print("Seleccione 1 para continuar o 2 para cancelar: "); String decision = scanner.nextLine().trim();
                while (!decision.equals("1") && !decision.equals("2")){
                    decision = scanner.nextLine().trim();
                }
                if (decision.equals("1")){
                    // Construir entidad 
                    Huesped haux = new Huesped(
                    nombres, apellido, tipo, nroDoc, cuit,
                    pos, fNac, diraux, tel, email, ocu, nac
                    );
                    
                    if(!v1.validar(haux)){
                        v1.getMensajeError();
                    } else {
                        if(!g.ValidacionDocumentoUnico(haux)){
                            v2.getMensajeError();
                            System.out.print("Seleccione 1 para aceptar igualmente o 2 para corregir: "); decision = scanner.nextLine().trim();
                            while (!decision.equals("1") && !decision.equals("2")){
                                decision = scanner.nextLine().trim();
                            }
                            if(decision.equals("1")){
                                camposIncompletos=false; h = haux; dir = diraux;
                            } else {
                                // Hacer Foco en TipoDocumento //
                            }
                        }else { 
                            System.out.println("--Ambas validaciones fueron exitosas. Preparando para guardar.--");
                            camposIncompletos = false; 
                            h = haux;
                            dir = diraux;
                        }
                    }
                } else {
                    flag = false; camposIncompletos=false;
                }
            }
            try {
                g.crearDireccion(dir);
                g.crearHuesped(h);
                System.out.println("---Huesped dado de alta correctamente.---");
                System.out.print("¿Desea cargar otro? Responda con SI o NO: "); String decision = scanner.nextLine().trim();
                while(!decision.equals("SI") && !decision.equals("NO")){
                    decision = scanner.nextLine().trim();
                }
                if(decision.equals("NO")){
                    flag = false;
                }
            } catch (Exception e) {
                System.out.println("---Error al guardar el huésped: " + e.getMessage());
            }
        }
        
    }
    
    //CU10 - Modificar Huesped
    
    static void cu10(){
        System.out.println("\nCU10 - Modificar huesped");
        
        
                       
    }

    //CU11 - Dar baja de Huesped
    
    static void cu11(){
        System.out.println("\nCU11 - Baja de huesped");
                       
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
