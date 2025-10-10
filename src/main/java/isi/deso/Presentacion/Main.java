/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.Presentacion;

import isi.deso.Modelo.Usuario;
import java.util.Scanner;

/**
 *
 * @author USUARIO
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcionA; //Es el que ingresa en el switch de opciones
        
        do{
            System.out.println("\nSeleccionar una opcion: ");
            System.out.println("\t1. Iniciar Sesion");
            System.out.println("\t2. Agregar Usuario nuevo");
            System.out.println("Eleccion: ");
            opcionA = scanner.nextInt();
        
            switch(opcionA){
                case 1: 
                    System.out.println("Aca iria el inicio de sesion");
                    break;
                case 2: 
                    System.out.println("Aca iria la creacion de usuario");
                    break;
            }
        } while(opcionA > 2);
    }
}
