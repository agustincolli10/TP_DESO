/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.Presentacion;

import isi.deso.Modelo.Usuario;
import isi.deso.Negocio.UsuarioServicio;
import java.util.Scanner;

/**
 *
 * @author USUARIO
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UsuarioServicio servicio = new UsuarioServicio();
        int opcionA; //Es el que ingresa en el switch de opciones
        
        //CARGA DEL USUARIO GENERAL
        Usuario user089 = new Usuario("Agustin", "Colli", "AColli089", "Tate1989");
        servicio.registrarUsuario(user089);
        
        do{
            System.out.println("\nSeleccionar una opcion: ");
            System.out.println("\t1. Iniciar Sesion");
            System.out.println("\t2. Agregar Usuario nuevo");
            System.out.println("Eleccion: ");
            opcionA = scanner.nextInt();
        
            switch(opcionA){
                case 1: 
                    System.out.println("Aca iria la creacion de usuario");
                    break;
                case 2:
                    scanner.nextLine();
                    System.out.println("\nIngrese su nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.println("\nIngrese su apellido: ");
                    String apellido = scanner.nextLine();
                    System.out.println("\nIngrese una ID: ");
                    String nombreUs = scanner.nextLine();
                    System.out.println("\nIngrese una contraseña: ");
                    String contrasenia = scanner.nextLine();
                    Usuario user001 = new Usuario(nombre, apellido, nombreUs, contrasenia);
                   
                    System.out.println("\nUsuario cargado correctamente");
                    System.out.println(user001);
                    
                    servicio.registrarUsuario(user001);
                    
                    // 3. (Opcional) Verificamos el contenido de la lista para confirmar
            System.out.println("\n--- Lista actual de usuarios ---");
            servicio.listarTodos().forEach(System.out::println);

            }
        } while(opcionA > 2);
    }
}
