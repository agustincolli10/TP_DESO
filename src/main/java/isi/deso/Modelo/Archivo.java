
package isi.deso.Modelo;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Clase para operaciones con archivos de texto.
 */
public class Archivo {
    
    /**
     * Lee el contenido completo de un archivo de texto.
     * 
     * @param direccion es la ruta del archivo a leer
     * @return todo el contenido del archivo en una cadena de caracteres
     * 
     * @throws Exception si el archivo no fue encontrado
     */
    public String leerTxt(String direccion){ //direccion: archivo
        
        String texto = "";
        
        try{
          BufferedReader bf = new BufferedReader(new FileReader(direccion));
          String temp = "";
          String bfRead;
          while((bfRead = bf.readLine()) != null){ //mientras el archivo tenga texto
              temp = temp + bfRead; //se guarda el texto del archivo
          }
          
          texto = temp;
          
        }catch(Exception e){ System.out.println("No se encontró archivo"); }
    
        return texto;
        
    }
    
}
