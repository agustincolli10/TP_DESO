
package isi.deso.DAO;

import isi.deso.Modelo.Estadia;
import isi.deso.Modelo.Huesped;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class EstadiaDAOImp implements EstadiaDAO {
    private static final String ARCHIVO_E = "estadiasCargadas.txt";
    private static final String SEPARADOR = ";";
    
    @Override
    public void crearEstadia(Estadia e){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_E, true))) {

            StringBuilder sb = new StringBuilder();
            sb.append(e.getIdEstadia()).append(SEPARADOR)
              .append(e.getCodReserva()).append(SEPARADOR)
              .append(e.getCodFactura()).append(SEPARADOR)
              .append(e.getCosto()).append(SEPARADOR)                    
              .append(e.getFechaIngreso()).append(SEPARADOR)                    
              .append(e.getFechaSalida()).append(SEPARADOR);
            
            final int MAX_HUESPEDES = 5;
            List<Huesped> huespedes = e.getHuespedes(); // Obtenemos la lista desde el objeto Estadia

            for (int i = 0; i < MAX_HUESPEDES; i++) {
                if (i < huespedes.size()) {
                    Huesped huespedActual = huespedes.get(i);
                    sb.append(huespedActual.getNumeroDocumento());
                }
        
                if (i < MAX_HUESPEDES - 1) {
                    sb.append(SEPARADOR);
                }
            }

            bw.write(sb.toString());
            bw.newLine();

        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
    
    @Override
    public void modificarEstadia(String idEstadia, Estadia eActualizado){
        
    }
    
    @Override
    public Estadia obtenerEstadia(String idEstadia){
        
        
    }
    
    @Override
    public List<Estadia> obtenerTodas(){
        
    }
}
