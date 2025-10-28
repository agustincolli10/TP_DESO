  /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.Presentacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import isi.deso.Modelo.Archivo;
import isi.deso.Modelo.Usuario;
import isi.deso.Negocio.UsuarioServicio;
import isi.deso.Servicio.HuespedService;
import isi.deso.Modelo.TipoDocumento;
/**
 *
 * @author USUARIO
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
  private static final HuespedService huespedService = new HuespedService();
    public static void main(String[] args) {
        // LO DEJO COMENTADO PARA QUE PUEDAN PROBAR SIN INICIAR SESION
        try {
            //LECTURA DEL TXT DE CONSERJES
            Archivo a = new Archivo();
        
            String arch = a.leerTxt("C:\\Users\\santi\\Escritorio\\desarrollo\\TP_DESO\\usuariosCargados.txt");
        
            String[] datosUsers = arch.split(" ");
            List<Usuario> listaDeUsuarios = new ArrayList<>();
        
            UsuarioServicio servicio = new UsuarioServicio(); //Lista de usuarios
        
            for (int i = 0; i < datosUsers.length; i += 4) {
                String nTemp = datosUsers[i];
                String apTemp = datosUsers[i + 1];
                String uTemp = datosUsers[i + 2];
                String cTemp = datosUsers[i + 3];
            
                Usuario nuevoUsuario = new Usuario(nTemp, apTemp, uTemp, cTemp);
                servicio.registrarUsuario(nuevoUsuario);
            }
        
        // PARA VER LA LISTA - BORRAR DSP
       // System.out.println("\n--- Lista actual de usuarios ---");
       // servicio.listarTodos().forEach(System.out::println);
            
            //PANTALLA PPAL
            
            System.out.println("BIENVENIDO");
            System.out.println("Ingrese sus datos para continuar\n");
            
            System.out.println("Ingresar nombre de usuario: ");
            String nombreUs = scanner.nextLine();
            System.out.println("Ingresar contrasena: ");
            String contrasenia = scanner.nextLine();
        
            if(servicio.validarUser(nombreUs, contrasenia)){
                System.out.println("Usuario validado");
            } else { System.out.println("Usuario no encontrado");}
        
            
            while(true){
                System.out.println();
                System.out.println("Menú:");
                System.out.println("1) CU02 - Buscar huésped");
                System.out.println("0) Salir");
                System.out.print("> ");
                String op =scanner.nextLine();
                switch(op){
                    case "1" -> System.out.println("cu2");//cu02();
                    case "0" -> { return; }
                    default -> System.out.println("Opción inválida");
                }
            }

        } catch (Exception e){
        System.err.println("ERROR: " + e.getMessage());
        }
        System.out.println("Fin.");
    }
        
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

    var lista = huespedService.buscar(ap, no, tipo, nro);

    if (lista.isEmpty()) {
        System.out.println("No existen concordancias para los criterios de búsqueda.");
      
        return;
    }

    for (int i = 0; i < lista.size(); i++) {
        System.out.printf("[%d] %s%n", i + 1, lista.get(i));
    }

    
    System.out.print("Seleccione Nº (Enter vacío = alta): ");
    String sel = scanner.nextLine();
    if (sel.isBlank()) {
        System.out.println("Simular CU09: Alta de huésped.");
    } else {
        try {
            int idx = Integer.parseInt(sel) - 1;
            System.out.println("Seleccionado: " + lista.get(idx));
            System.out.println("Simular CU10: Modificar huésped.");
        } catch (Exception e) {
            System.out.println("Selección inválida → alta.");
        }
    }
}

private static TipoDocumento parseTipo(String s) {
    if (s == null || s.isBlank()) return null;
    try { return TipoDocumento.valueOf(s.trim().toUpperCase()); }
    catch (Exception e) { return null; }
}

}

