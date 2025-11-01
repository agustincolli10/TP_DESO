package isi.deso.Excepcion; 

/** Excepción personalizada. */
public class MainException extends RuntimeException { 
    public MainException(String m){ 
        super(m);
    }
    
    public MainException(String m, Throwable t){ 
        super(m,t);
    } 
}