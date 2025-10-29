package isi.deso.DAO;

import isi.deso.Modelo.Huesped;
import isi.deso.Modelo.DireccionDTO;
import isi.deso.Modelo.PosicionIVA;
import isi.deso.Modelo.TipoDocumento;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.time.LocalDate;
public class HuespedDAOImp implements HuespedDAO{
    
    private static final String ARCHIVO = "huespedesCargados.txt";
    private static final String SEPARADOR = ";";
    @Override
    public void crearHuesped(Huesped h) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true))) {

            StringBuilder sb = new StringBuilder();
            sb.append(h.getApellido()).append(SEPARADOR)
              .append(h.getNombres()).append(SEPARADOR)
              .append(h.getTipoDocumento()).append(SEPARADOR)
              .append(h.getNumeroDocumento()).append(SEPARADOR)
              .append(h.getCuit() != null ? h.getCuit() : "").append(SEPARADOR)
              .append(h.getPosicionIVA()).append(SEPARADOR)
              .append(h.getFechaNacimiento()).append(SEPARADOR);

            DireccionDTO d = h.getDireccion();
            sb.append(d.getCalle()).append(SEPARADOR)
              .append(d.getNumero()).append(SEPARADOR)
              .append(d.getDepartamento()).append(SEPARADOR)
              .append(d.getPiso()).append(SEPARADOR)
              .append(d.getCodigoPostal()).append(SEPARADOR)
              .append(d.getLocalidad()).append(SEPARADOR)
              .append(d.getProvincia()).append(SEPARADOR)
              .append(d.getPais()).append(SEPARADOR);

            sb.append(h.getTelefono()).append(SEPARADOR)
              .append(h.getEmail() != null ? h.getEmail() : "").append(SEPARADOR)
              .append(h.getOcupacion()).append(SEPARADOR)
              .append(h.getNacionalidad());

            bw.write(sb.toString());
            bw.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     @Override
    public List<Huesped> obtenerTodos() {
        List<Huesped> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(SEPARADOR, -1); // -1 para conservar vacíos
                if (datos.length >= 20) {
                    // Reconstrucción del objeto
                    DireccionDTO dir = new DireccionDTO(
                        datos[7], datos[8], datos[9], datos[10],
                        datos[11], datos[12], datos[13], datos[14]
                    );

                    Huesped h = new Huesped(
                        datos[1], // nombre
                        datos[0], // apellido
                        TipoDocumento.valueOf(datos[2].toUpperCase()), // tipo doc
                        datos[3], // nro doc
                        datos[4], // cuit
                        PosicionIVA.valueOf(datos[5]), // pos IVA
                        LocalDate.parse(datos[6]), // fecha nacimiento
                        dir,
                        datos[15], // telefono
                        datos[16], // email
                        datos[17], // ocupacion
                        datos[18]  // nacionalidad
                    );

                    lista.add(h);
                }
            }
        } catch (FileNotFoundException e) {
            // Si el archivo no existe, se crea vacío al guardar el primero
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lista;
    }
    @Override
    public Huesped obtenerHuesped(TipoDocumento tipo, String num) {
        return obtenerTodos().stream()
                .filter(h -> h.getTipoDocumento() == tipo
                        && h.getNumeroDocumento().equalsIgnoreCase(num))
                .findFirst()
                .orElse(null);
    }
    @Override
    public void eliminarHuesped(Huesped h) {
    File archivo = new File("huespedesCargados.txt");
    File archivoTemp = new File("huespedesCargados_temp.txt");

    try (BufferedReader br = new BufferedReader(new FileReader(archivo));
         BufferedWriter bw = new BufferedWriter(new FileWriter(archivoTemp))) {

        String linea;
        while ((linea = br.readLine()) != null) {
            String[] datos = linea.split(";", -1); // separar por tu separador
            if (datos.length >= 20) { // ajustar según cantidad de campos
                String tipoDoc = datos[2];
                String numDoc  = datos[3];

                // Solo copiamos la línea si NO es el huésped a eliminar
                if (!(tipoDoc.equalsIgnoreCase(h.getTipoDocumento().toString()) &&
                      numDoc.equalsIgnoreCase(h.getNumeroDocumento()))) {
                    bw.write(linea);
                    bw.newLine();
                }
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    //Se reemplaza el archivo original por el temporal
    if (!archivo.delete()) {
        System.out.println("No se pudo borrar el archivo original.");
    }
    if (!archivoTemp.renameTo(archivo)) {
        System.out.println("No se pudo renombrar el archivo temporal.");
    }
}
}
