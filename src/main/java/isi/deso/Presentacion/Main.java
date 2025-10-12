/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.Presentacion;

import isi.deso.Modelo.Archivo;
import isi.deso.Modelo.Usuario;
import isi.deso.Negocio.UsuarioServicio;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author USUARIO
 */
public class Main {
    public static void main(String[] args) {
        
        /* LO DEJO COMENTADO PARA QUE PUEDAN PROBAR SIN INICIAR SESION
        //LECTURA DE TXT CON CONSERJES
        Archivo a = new Archivo();
        
        String arch = a.leerTxt("D:\\USUARIO\\Documents\\txt\\usuariosCargados.txt");
        
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
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingresar nombre de usuario: ");
        String nombreUs = scanner.nextLine();
        System.out.println("Ingresar contrasena: ");
        String contrasenia = scanner.nextLine();
        
        if(servicio.validarUser(nombreUs, contrasenia)){
            System.out.println("Usuario validado");
        } else { System.out.println("Usuario no encontrado");}
        */
    }
}
