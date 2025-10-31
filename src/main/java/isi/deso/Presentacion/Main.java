package isi.deso.Presentacion;

import isi.deso.DAO.DireccionDAO;
import isi.deso.DAO.DireccionDAOImp;
import isi.deso.DAO.HuespedDAO;
import isi.deso.DAO.HuespedDAOImp;
import isi.deso.DAO.UsuarioDAO;
import isi.deso.DAO.UsuarioDAOImpFile;
import isi.deso.DAO.EstadiaDAO;
import isi.deso.DAO.EstadiaDAOImp;

import isi.deso.Modelo.Huesped;
import isi.deso.Modelo.TipoDocumento;
import isi.deso.Modelo.PosicionIVA;
import isi.deso.Modelo.DireccionDTO;
import isi.deso.Modelo.Estadia;

import isi.deso.Servicio.HuespedService;
import isi.deso.Servicio.AuthService;
import isi.deso.Excepcion.AutenticacionException;
import isi.deso.Gestor.GestorHuesped;

import isi.deso.Strategy.Validacion;
import isi.deso.Strategy.ValidacionCampos;
import isi.deso.Strategy.ValidacionDocumentoUnico;

import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Main {
    
    // lee por consola
    private static final Scanner scanner = new Scanner(System.in);

    // dao de huesped → txt
    private static final HuespedDAO huespedDAO = new HuespedDAOImp();     // huespedesCargados.txt
    // dao de usuario → txt
    private static final UsuarioDAO usuarioDAO = new UsuarioDAOImpFile();          
    // dao de estadia → txt (usa el dao de huesped para reconstruir)
    private static final EstadiaDAO estadiaDAO = new EstadiaDAOImp(huespedDAO);
    // servicio que busca con filtros
    private static final HuespedService huespedService = new HuespedService(huespedDAO);

    public static void main(String[] args) {
        try {
            // cu01 - login
            System.out.println("BIENVENIDO");
            System.out.println("ingrese sus datos para continuar\n");

            AuthService auth = new AuthService(usuarioDAO);

            // bucle hasta que loguea
            while (true) {
                System.out.print("ingresar nombre de usuario: ");
                String nombreUs = scanner.nextLine();

                System.out.print("ingresar contrasenia: ");
                String contrasenia = scanner.nextLine();

                try {
                    // valida contra usuariosCargados.txt
                    auth.autenticar(nombreUs, contrasenia);
                    System.out.println("usuario validado\n");
                    break;
                } catch (AutenticacionException e) {
                    System.out.println("error: " + e.getMessage());
                }
            }
            
            // menú principal
            while (true) {
                System.out.println();
                System.out.println("menu:");
                System.out.println("1) buscar huesped");
                System.out.println("2) dar de alta huesped");
                System.out.println("3) modificar huesped");
                System.out.println("0) salir");
                System.out.print("> ");
                String op = scanner.nextLine();
                switch (op) {
                    case "1" -> cu02();
                    case "2" -> cu09();
                    case "3" -> cu10();
                    case "0" -> { System.out.println("fin."); return; }
                    default -> System.out.println("opcion invalida");
                }
            }

        } catch (Exception e) {
            System.err.println("error: " + e.getMessage());
            System.out.println("fin.");
        }
    }

    // cu02 - buscar huesped
    static void cu02() {
        System.out.println("cu02 - buscar huesped");

        // pide filtros
        System.out.print("apellido empieza con (enter salta): ");
        String ap = scanner.nextLine();

        System.out.print("nombres empieza con (enter salta): ");
        String no = scanner.nextLine();

        System.out.print("tipo doc (dni/le/lc/pasaporte/otro) o enter: ");
        String t = scanner.nextLine();
        TipoDocumento tipo = parseTipo(t);

        System.out.print("numero doc (enter salta): ");
        String nro = scanner.nextLine();

        // busca en servicio (lee de txt)
        List<Huesped> lista = huespedService.buscar(ap, no, tipo, nro);

        if (lista.isEmpty()) {
            System.out.println("no existen concordancias para los criterios de busqueda.");
            cu09(); // alta directa si no hay resultados
            return;
        }

        // muestra lista
        for (int i = 0; i < lista.size(); i++) {
            System.out.printf("[%d] %s%n", i + 1, lista.get(i));
        }

        System.out.print("seleccione nro. de huesped (enter vacio = dar de alta): ");
        String sel = scanner.nextLine();
        if (sel.isBlank()) {
            cu09(); // alta si el usuario no selecciona ninguno
            return;
        }

        try {
            int idx = Integer.parseInt(sel) - 1;
            Huesped seleccionado = lista.get(idx);
            System.out.println("seleccionado: " + seleccionado);
            // desde la busqueda lo mando a modificar
            cu10(); 

        } catch (Exception e) {
            System.out.println("seleccion invalida: alta.");
            cu09();
        }
    }

    // cu09 - alta de huésped
    static void cu09() {
        boolean flag = true;
        // variables para leer
        String nombres, apellido, nroDoc, cuit, posicion, calle, numero, depto, piso, cp, loc, prov, pais, tel, email, ocu, nac;
        TipoDocumento tipo; 
        PosicionIVA pos; 
        LocalDate fNac;
        while (flag) {
            boolean camposIncompletos = true;
            DireccionDAO ddao = new DireccionDAOImp();
            // usa el mismo dao global de huesped
            HuespedDAO dao = huespedDAO;
            Validacion v1 = new ValidacionCampos();
            Validacion v2 = new ValidacionDocumentoUnico(dao);
            Huesped h = null; 
            DireccionDTO dir = null;
            // gestor que coordina dao + validaciones
            GestorHuesped g = new GestorHuesped(ddao, dao, v1, v2);

            while (camposIncompletos) {
                System.out.println("\ncu09 - alta de huesped");

                // datos personales
                System.out.print("nombres: "); nombres = scanner.nextLine().trim();
                System.out.print("apellido: "); apellido = scanner.nextLine().trim();

                System.out.print("tipo doc (dni/le/lc/pasaporte/otro): ");
                tipo = parseTipo(scanner.nextLine());

                System.out.print("numero doc: "); nroDoc = scanner.nextLine().trim();
                System.out.print("cuit (enter si no tiene): "); cuit = scanner.nextLine().trim();
                if (cuit.isEmpty()) cuit = null;

                System.out.print("posicion iva (responsableinscripto/monotributista/exento/consumidorfinal): "); 
                posicion = scanner.nextLine().trim();
                if (posicion.isEmpty()) posicion = "ConsumidorFinal";
                pos = parsePos(posicion);

                System.out.print("fecha de nacimiento (yyyy-mm-dd o dd/mm/yyyy): ");
                fNac = parseFecha(scanner.nextLine());

                // direccion
                System.out.println("\n--- direccion ---");
                System.out.print("calle: "); calle = scanner.nextLine().trim();
                System.out.print("numero: "); numero = scanner.nextLine().trim();
                System.out.print("departamento: "); depto = scanner.nextLine().trim();
                System.out.print("piso: "); piso = scanner.nextLine().trim();
                System.out.print("codigo postal: "); cp = scanner.nextLine().trim();
                System.out.print("localidad: "); loc = scanner.nextLine().trim();
                System.out.print("provincia: "); prov = scanner.nextLine().trim();
                System.out.print("pais: "); pais = scanner.nextLine().trim();
                DireccionDTO diraux = new DireccionDTO(calle, numero, depto, piso, cp, loc, prov, pais);
                
                // contacto y otros
                System.out.println("\n--- contacto y otros ---");
                System.out.print("telefono: "); tel = scanner.nextLine().trim();
                System.out.print("email (enter si no tiene): "); email = scanner.nextLine().trim();
                if (email.isEmpty()) email = null;
                System.out.print("ocupacion: "); ocu = scanner.nextLine().trim();
                System.out.print("nacionalidad: "); nac = scanner.nextLine().trim();
                
                System.out.print("seleccione 1 para continuar o 2 para cancelar: "); 
                String decision = scanner.nextLine().trim();
                while (!decision.equals("1") && !decision.equals("2")) {
                    decision = scanner.nextLine().trim();
                }
                if (decision.equals("1")) {
                    // construir entidad 
                    Huesped haux = new Huesped(
                        nombres, apellido, tipo, nroDoc, cuit,
                        pos, fNac, diraux, tel, email, ocu, nac
                    );
                    
                    if (!v1.validar(haux)) {
                        System.out.println(v1.getMensajeError());
                    } else {
                        if (!g.ValidacionDocumentoUnico(haux)) {
                            System.out.println(v2.getMensajeError());
                            System.out.print("seleccione 1 para aceptar igualmente o 2 para corregir: "); 
                            decision = scanner.nextLine().trim();
                            while (!decision.equals("1") && !decision.equals("2")) {
                                decision = scanner.nextLine().trim();
                            }
                            if (decision.equals("1")) {
                                camposIncompletos = false; 
                                h = haux; 
                                dir = diraux;
                            } else {
                                // hacer foco en tipodocumento
                            }
                        } else { 
                            System.out.println("--ambas validaciones fueron exitosas. guardando.--");
                            camposIncompletos = false; 
                            h = haux;
                            dir = diraux;
                        }
                    }
                } else {
                    flag = false; 
                    camposIncompletos = false;
                }
            }
            try {
                g.crearDireccion(dir);
                g.crearHuesped(h);
                System.out.println("---huesped dado de alta correctamente.---");
                System.out.print("¿desea cargar otro? (si/no): "); 
                String decision = scanner.nextLine().trim().toUpperCase();
                while (!decision.equals("SI") && !decision.equals("NO")) {
                    decision = scanner.nextLine().trim().toUpperCase();
                }
                if (decision.equals("NO")) {
                    flag = false;
                }
            } catch (Exception e) {
                System.out.println("---error al guardar el huésped: " + e.getMessage());
            }
        }
    }
    
    // cu10 - modificar huesped
    static void cu10() {
        System.out.println("\ncu10 - modificar huesped");

        // busca primero (mismo estilo q cu02)
        System.out.print("apellido empieza con (enter salta): ");
        String ap = scanner.nextLine();

        System.out.print("nombres empieza con (enter salta): ");
        String no = scanner.nextLine();

        System.out.print("tipo doc (dni/le/lc/pasaporte/otro) o enter: ");
        String t = scanner.nextLine();
        TipoDocumento tipoBusq = parseTipo(t);

        System.out.print("numero doc (enter salta): ");
        String nroBusq = scanner.nextLine();

        List<Huesped> lista = huespedService.buscar(ap, no, tipoBusq, nroBusq);
        if (lista.isEmpty()) {
            System.out.println("no se encontraron huespedes para modificar.");
            return;
        }

        for (int i = 0; i < lista.size(); i++) {
            System.out.printf("[%d] %s%n", i + 1, lista.get(i));
        }

        System.out.print("seleccione nro de huesped a modificar: ");
        String sel = scanner.nextLine();
        int idx;
        try {
            idx = Integer.parseInt(sel) - 1;
        } catch (Exception e) {
            System.out.println("seleccion invalida.");
            return;
        }
        if (idx < 0 || idx >= lista.size()) {
            System.out.println("seleccion fuera de rango.");
            return;
        }

        Huesped orig = lista.get(idx);

        // clave original (la que está en el txt)
        TipoDocumento tipoOriginal = orig.getTipoDocumento();
        String nroOriginal = orig.getNumeroDocumento();

        System.out.println("deje vacio para mantener el valor actual.");

        // datos nuevos
       String nuevoNombre   = leerConDefault("nombres", orig.getNombres());
        String nuevoApellido = leerConDefault("apellido", orig.getApellido());

        System.out.print("tipo doc (" + mostrarTipo(orig.getTipoDocumento()) + "): ");
        String tdNuevoStr = scanner.nextLine();
        TipoDocumento nuevoTipo = tdNuevoStr.isBlank() ? orig.getTipoDocumento() : parseTipo(tdNuevoStr);

        System.out.print("numero doc (" + noNull(orig.getNumeroDocumento()) + "): ");
        String nroNuevoStr = scanner.nextLine();
        String nuevoNro = nroNuevoStr.isBlank() ? orig.getNumeroDocumento() : nroNuevoStr.trim();

        String nuevoCuit = leerConDefault("cuit", noNull(orig.getCuit()));

        System.out.print("posicion iva (" + noNull(orig.getPosicionIVA() != null ? orig.getPosicionIVA().name() : null) + "): ");
        String posStr = scanner.nextLine();
        PosicionIVA nuevaPos = posStr.isBlank() ? orig.getPosicionIVA() : parsePos(posStr);

        System.out.print("fecha de nacimiento (" + (orig.getFechaNacimiento() != null ? orig.getFechaNacimiento().toString() : "") + "): ");
        String fStr = scanner.nextLine();
        LocalDate nuevaFecha = fStr.isBlank() ? orig.getFechaNacimiento() : parseFecha(fStr);

        // direccion
        DireccionDTO dirOrig = orig.getDireccion();
        if (dirOrig == null) {
            dirOrig = new DireccionDTO("","","","","","","","");
        }

        String calle = leerConDefault("calle", dirOrig.getCalle());
        String numero = leerConDefault("numero", dirOrig.getNumero());
        String depto = leerConDefault("departamento", dirOrig.getDepartamento());
        String piso = leerConDefault("piso", dirOrig.getPiso());
        String cp = leerConDefault("codigo postal", dirOrig.getCodigoPostal());
        String loc = leerConDefault("localidad", dirOrig.getLocalidad());
        String prov = leerConDefault("provincia", dirOrig.getProvincia());
        String pais = leerConDefault("pais", dirOrig.getPais());
        DireccionDTO dirNueva = new DireccionDTO(calle, numero, depto, piso, cp, loc, prov, pais);

        String tel = leerConDefault("telefono", noNull(orig.getTelefono()));
        String email = leerConDefault("email", noNull(orig.getEmail()));
        String ocu = leerConDefault("ocupacion", noNull(orig.getOcupacion()));
        String nac = leerConDefault("nacionalidad", noNull(orig.getNacionalidad()));

        // armo el huesped actualizado
       Huesped actualizado = new Huesped(
                nuevoNombre,
                nuevoApellido,
                nuevoTipo,
                nuevoNro,
                nuevoCuit,
                nuevaPos,
                nuevaFecha,
                dirNueva,
                tel,
                email,
                ocu,
                nac
        );

        // validacion basica
        Validacion v1 = new ValidacionCampos();
        if (!v1.validar(actualizado)) {
            System.out.println(v1.getMensajeError());
            return;
        }

        // si cambió la clave (tipo+num), controlo unicidad
        boolean cambioClave = !(igualesTipo(tipoOriginal, nuevoTipo) && igualesStr(nroOriginal, nuevoNro));
        if (cambioClave) {
            Validacion v2 = new ValidacionDocumentoUnico(huespedDAO);
            if (!v2.validar(actualizado)) {
                System.out.println(v2.getMensajeError());
                return;
            }
        } 

        System.out.println("seleccione una opcion:");
        System.out.println("1) guardar modificacion");
        System.out.println("2) cancelar modificacion");
        System.out.println("3) borrar huesped");
        System.out.print("> ");
        String valorSel = scanner.nextLine();
        switch (valorSel) {
            case "1" -> {
                try {
                    huespedDAO.modificarHuesped(tipoOriginal, nroOriginal, actualizado);
                    System.out.println("huesped modificado correctamente.");
                } catch (Exception e) {
                    System.out.println("error al modificar el huesped: " + e.getMessage());
                }
            }
            case "2" -> { return; }
            case "3" -> {
                // llamo al cu11 para cargar estadia
                cu11(actualizado);
            }
            default -> System.out.println("opcion invalida");
        }
    }

    // cu11 - borrar huesped
    static void cu11(Huesped h) {
        System.out.println("\ncu11 - dar de baja huesped");

        Estadia estadiaAux = new Estadia();
        EstadiaDAOImp eDAO = new EstadiaDAOImp();
        List<Estadia> listaE = new ArrayList();
        List<Huesped> listaH = new ArrayList();
        HuespedDAOImp hDAO2 = new HuespedDAOImp();
        List<Huesped> listaHuespedComp = new ArrayList();
        Boolean encontrado = false;
        Huesped hAux = new Huesped();
        
        listaE = eDAO.obtenerTodas(); //obtiene todas las estadias y las agrega a listaE
        int i=0;
        
        while(!encontrado && i < listaE.size()){ //recorre mientras el huesped no tenga estadia
            estadiaAux = listaE.get(i);
            listaHuespedComp = hDAO2.obtenerTodos();
            
            
            for(int j=0; j<5; j++){
                hAux = listaH.get(j);
                if(h.getNumeroDocumento().equals(hAux.getNumeroDocumento())){
                    encontrado=true;
                }
            }
                        
            i++;
            
            return;
        }
                
        if(!encontrado){
            HuespedDAOImp hDAO = new HuespedDAOImp();
            hDAO.eliminarHuesped(h);
            System.out.println("Huesped " + h.getNumeroDocumento() + " eliminado.");
        }
        
    }

    // helpers

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

    private static String leerConDefault(String label, String actual) {
        System.out.print(label + " (" + noNull(actual) + "): ");
        String in = scanner.nextLine();
        return in.isBlank() ? actual : in.trim();
    }

    private static String noNull(String s) {
        return (s == null) ? "" : s;
    }

    private static String mostrarTipo(TipoDocumento t) {
        return t == null ? "" : t.name();
    }

    private static boolean igualesStr(String a, String b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return a.equalsIgnoreCase(b);
    }

    private static boolean igualesTipo(TipoDocumento a, TipoDocumento b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return a == b;
    }

    private static LocalDate parseFecha(String s) {
        if (s == null || s.isBlank()) return null;
        String v = s.trim();
        try { return LocalDate.parse(v); } // yyyy-mm-dd
        catch (Exception e) {
            try {
                DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                return LocalDate.parse(v, f);
            } catch (Exception ex) { return null; }
        }
    }
}
