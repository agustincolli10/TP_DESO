
package isi.deso.Modelo;

import java.io.BufferedReader;
import java.io.FileReader;

public class Archivo {
    
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
