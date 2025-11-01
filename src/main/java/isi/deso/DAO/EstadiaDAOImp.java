
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

/**
 * Implementacion de EstadiaDAO que utiliza archivos de texto como
 * medio de persistencia.
 * <p>
 * Se encarga de implementar los metodos para almacenar, modificar
 * y consultar estadias utilizando el archivo estadiasCargadas.txt
 * </p>
 * 
 * @see isi.deso.DAO.EstadiaDAO
 * @see isi.deso.Modelo.Estadia
 */
public class EstadiaDAOImp implements EstadiaDAO {
    private static final String ARCHIVO_E = "estadiasCargadas.txt";
    private static final String SEPARADOR = ";";
    private final HuespedDAO huespedDAO;

    /**
     * Crea una instancia de EstadiaDaoImp y una de HuespedDAOImp
     * 
     * @see isi.deso.DAO.HuespedDAOImp
     */
    public EstadiaDAOImp() {
        this.huespedDAO = new HuespedDAOImp();
    }

    /**
     * Crea una instancia de EstadiaDaoImp y recibe una instancia de HuespedDAO
     * 
     * @see isi.deso.DAO.HuespedDAO
     */
    public EstadiaDAOImp(HuespedDAO huespedDAO) {
        this.huespedDAO = huespedDAO;
    }
    
    /**
     * Almacena una estadia en el archivo
     * 
     * @param e {@link isi.deso.Modelo.Estadia} datos de la estadia
     */
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
    
    /**
     * Modifica la estadia almacenada en el archivo.
     *
     * @param idEstadia id de la estadia a actualizar
     * @param eActualizado nuevos datos de la estadia
     */
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
    
    /**
     * Busca la estadia a la que corresponde el id en caso de encontrarla.
     *
     * @param idEstadia id de la estadia a consultar
     * @return estadia a la que corresponde el id, caso contrario {@code null}
     */
    @Override
    public Estadia obtenerEstadia(String idEstadia){
        return obtenerTodas().stream()
                .filter(e -> (e.getIdEstadia() == null ? idEstadia == null : e.getIdEstadia().equals(idEstadia)))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Lee el archivo entero y devuelve una lista con todas las estadias almacenadas
     *
     * @return coleccion de estadias
     */
    @Override
    public List<Estadia> obtenerTodas(){
        List<Estadia> listaE = new ArrayList<>();
        List<Huesped> huespedConEstadia = new ArrayList();

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_E))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(SEPARADOR, -1); // -1 para conservar vacíos
                if (datos.length >= 10) {
                    // Reconstrucción del objeto
                    
                    HuespedDAOImp hDAO1 = new HuespedDAOImp();
                    List<Huesped> listaHDAO = hDAO1.obtenerTodos();
                    for(int i=0; i<listaHDAO.size(); i++){
                        Huesped actual = listaHDAO.get(i);
                        if(actual.getNumeroDocumento().equals(datos[6]) ||
                                actual.getNumeroDocumento().equals(datos[7]) ||
                                actual.getNumeroDocumento().equals(datos[8]) ||
                                actual.getNumeroDocumento().equals(datos[9]) ||
                                actual.getNumeroDocumento().equals(datos[10])){
                            
                            huespedConEstadia.add(actual);
                        }
                    }
                    
                    
                    Estadia est = new Estadia(
                    datos[0], //idEstadia
                    datos[1], //codigoReserva
                    datos[2], //codigoFactura
                    datos[3], //costo
                    LocalDate.parse(datos[4]), //fechaInicio
                    LocalDate.parse(datos[5]), //fechaFinal
                    huespedConEstadia);                   

                    listaE.add(est);
                }
            }
        } catch (FileNotFoundException e) {
            // Si el archivo no existe, se crea vacío al guardar el primero
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listaE;
    }
}


