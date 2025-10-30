
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
     // MAXI DESDE ACÁ 1
    private final HuespedDAO huespedDAO;

    public EstadiaDAOImp() {
        this.huespedDAO = new HuespedDAOImp();
    }

    public EstadiaDAOImp(HuespedDAO huespedDAO) {
        this.huespedDAO = huespedDAO;
    }
    // MAXI HASTA ACÁ 2
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
            
          
            List<Huesped> huespedes = e.getHuespedes(); // Obtenemos la lista desde el objeto Estadia

            for (int i = 0; i < MAX_HUESPEDES; i++) {
                if (huespedes != null && i < huespedes.size()) {
                    // MAXI CAMBIO 2 DESDE ACÁ
                    Huesped h = huespedes.get(i);
                    
                    String doc = (h.getTipoDocumento() != null ? h.getTipoDocumento().name() : "")
                            + "|" +
                            (h.getNumeroDocumento() != null ? h.getNumeroDocumento() : "");
                    sb.append(doc);

                    // MAXI CAMBIO 2 HASTA ACÁ
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
   ublic void modificarEstadia(String idEstadia, Estadia eActualizado) {
        File origen = new File(ARCHIVO_E);
        File tmp = new File("estadiasCargadas_tmp.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(origen));
             BufferedWriter bw = new BufferedWriter(new FileWriter(tmp))) {

            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.isBlank()) continue;

                String[] datos = linea.split(SEPARADOR, -1);
                String idLinea = datos[0];

                if (idLinea.equals(idEstadia)) {
                    // escribo la línea nueva
                    bw.write(lineaFromEstadia(eActualizado));
                } else {
                    // dejo la que estaba
                    bw.write(linea);
                }
                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!origen.delete()) {
            System.out.println("No se pudo borrar el archivo original de estadias.");
        }
        if (!tmp.renameTo(origen)) {
            System.out.println("No se pudo renombrar el archivo temporal de estadias.");
        }
    }
    
    @Override
    public Estadia obtenerEstadia(String idEstadia){
        
        
    }
    
    @Override
    public List<Estadia> obtenerTodas(){
        
    }
}

