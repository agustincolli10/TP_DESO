
package isi.deso.DAO;

import isi.deso.Modelo.Estadia;
import isi.deso.Modelo.Huesped;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
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

            for (int i = 0; i < 5; i++) {
                if (huespedes != null && i < huespedes.size()) {
                    // MAXI CAMBIO 2 DESDE ACÁ
                    Huesped h = huespedes.get(i);
                    
                    String doc = (h.getTipoDocumento() != null ? h.getTipoDocumento().name() : "")
                            + "|" +
                            (h.getNumeroDocumento() != null ? h.getNumeroDocumento() : "");
                    sb.append(doc);

                    // MAXI CAMBIO 2 HASTA ACÁ
                }
        
                if (i < 5 - 1) {
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
   public void modificarEstadia(String idEstadia, Estadia eActualizado) {
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
                    //bw.write(lineaFromEstadia(eActualizado));
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
        return obtenerTodas().stream()
                .filter(e -> (e.getIdEstadia() == null ? idEstadia == null : e.getIdEstadia().equals(idEstadia)))
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public List<Estadia> obtenerTodas(){
    List<Estadia> listaE = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_E))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            if (linea.isBlank()) continue;

            String[] datos = linea.split(SEPARADOR, -1);
            // esperamos: 6 campos fijos + 5 huéspedes = 11
            if (datos.length < 11) {
                continue;
            }

            String idEst = datos[0];
            String codRes = datos[1];
            String codFac = datos[2];
            String costo  = datos[3];

            LocalDate fIn  = datos[4].isBlank() ? null : LocalDate.parse(datos[4]);
            LocalDate fOut = datos[5].isBlank() ? null : LocalDate.parse(datos[5]);

            // lista de huespedes SOLO para esta estadia
            List<Huesped> huespedesDeEsta = new ArrayList<>();

            // campos 6..10 = 5 posibles huespedes
            for (int i = 6; i <= 10; i++) {
                String docStr = datos[i];
                if (docStr == null || docStr.isBlank()) continue;

                // viene "TIPO|NUM"
                String[] partes = docStr.split("\\|", -1);
                if (partes.length != 2) continue;

                String tipoStr = partes[0];
                String numStr  = partes[1];

                if (numStr.isBlank()) continue;

                TipoDocumento tipo = null;
                try {
                    if (!tipoStr.isBlank()) {
                        tipo = TipoDocumento.valueOf(tipoStr);
                    }
                } catch (Exception ex) {
                    tipo = null;
                }

                if (tipo != null) {
                    Huesped h = this.huespedDAO.obtenerHuesped(tipo, numStr);
                    if (h != null) {
                        huespedesDeEsta.add(h);
                    }
                }
            }

            Estadia est = new Estadia(
                idEst,
                codRes,
                codFac,
                costo,
                fIn,
                fOut,
                huespedesDeEsta
            );

            listaE.add(est);
        }
    } catch (FileNotFoundException e) {
        // si no hay archivo, devolvemos lista vacía
    } catch (IOException e) {
        e.printStackTrace();
    }

    return listaE;
}
}



