
package isi.deso.Servicio;

import java.io.BufferedReader;
import java.io.IOException;
 import java.io.InputStream;
 import java.io.InputStreamReader;
 import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import isi.deso.Modelo.Huesped;
import isi.deso.Modelo.TipoDocumento;
/** CU02 Buscar huésped (sin DAO): lee resources/data/huespedes.csv con columnas: tipo;nro;apellido;nombres */
public class HuespedService {
  private List<Huesped> cargar(){
    List<Huesped> list = new ArrayList<>();
    try (InputStream is = getClass().getClassLoader().getResourceAsStream("data/huespedes.csv")) {
      if (is == null) return list;
      try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
        String line; boolean header=true;
        while ((line = br.readLine()) != null) {
          if (line.isBlank() || line.startsWith("#")) continue;
          if (header) { header=false; continue; }
          String[] c = line.split(";", -1);
          if (c.length < 4) c = line.split(",", -1); // por si el separador es coma
          String tipo = c[0], nro = c[1], ap = c[2], no = c[3];
          Huesped h = new Huesped(ap, no, TipoDocumento.valueOf(tipo), nro);
          list.add(h);
        }
      }
    } catch (IOException e) {
      // en avance 1, solo ignoramos y devolvemos lista vacía
    }
    return list;
  }


 public List<Huesped> buscar(String apellidoEmpiezaCon,
                            String nombresEmpiezaCon,
                            TipoDocumento tipo,
                            String nro) {

    String ap = normalize(apellidoEmpiezaCon);
    String no = normalize(nombresEmpiezaCon);
    String nd = normalize(nro);

    // Predicado que vamos encadenando (AND) según haya criterios
    java.util.function.Predicate<Huesped> p = h -> true;

    if (!ap.isEmpty()) {
        p = p.and(h -> h.getApellido() != null
                   && h.getApellido().toUpperCase().startsWith(ap));
    }
    if (!no.isEmpty()) {
        p = p.and(h -> h.getNombres() != null
                   && h.getNombres().toUpperCase().startsWith(no));
    }
    if (tipo != null) {
        p = p.and(h -> h.getTipoDocumento() == tipo);
    }
    if (!nd.isEmpty()) {
        p = p.and(h -> nd.equals(h.getNumeroDocumento()));
    }

    return cargar().stream()
            .filter(p) // <- lambdas/streams
            .sorted(java.util.Comparator
                    .comparing(Huesped::getApellido, String.CASE_INSENSITIVE_ORDER)
                    .thenComparing(Huesped::getNombres, String.CASE_INSENSITIVE_ORDER))
            .collect(Collectors.toList());
}

private static String normalize(String s) {
    return (s == null) ? "" : s.trim().toUpperCase();
}

}

