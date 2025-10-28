package isi.deso.Servicio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import isi.deso.Modelo.Huesped;
import isi.deso.Modelo.TipoDocumento;

public class HuespedService {

  private static final String HUESPEDES_CSV = "data/huespedes.csv";


  private List<Huesped> cargar() {
    List<Huesped> list = new ArrayList<>();
    try (InputStream is = getClass().getClassLoader().getResourceAsStream(HUESPEDES_CSV)) {
      if (is == null) return list; 
      try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
        String line;
        boolean header = true;
        while ((line = br.readLine()) != null) {
          if (line.isBlank() || line.startsWith("#")) continue;
          if (header) { header = false; continue; }

          String[] c = line.split(";", -1);
          if (c.length < 4) c = line.split(",", -1); 
          if (c.length < 4) continue;             

          String tipo = safe(c[0]);
          String nro  = safe(c[1]);
          String ap   = safe(c[2]);
          String no   = safe(c[3]);

          TipoDocumento td = parseTipo(tipo);
          if (td == null) continue; 

          list.add(new Huesped(ap, no, td, nro));
        }
      }
    } catch (IOException e) {
      
    }
    return list;
  }

  /** cu02 streams lambdas AND */
  public List<Huesped> buscar(String apellidoEmpiezaCon,
                              String nombresEmpiezaCon,
                              TipoDocumento tipo,
                              String nro) {

    String ap = normalize(apellidoEmpiezaCon);
    String no = normalize(nombresEmpiezaCon);
    String nd = normalize(nro);

    Predicate<Huesped> p = h -> true;

    if (!ap.isEmpty()) {
      p = p.and(h -> h.getApellido() != null &&
                     h.getApellido().toUpperCase().startsWith(ap));
    }
    if (!no.isEmpty()) {
      p = p.and(h -> h.getNombres() != null &&
                     h.getNombres().toUpperCase().startsWith(no));
    }
    if (tipo != null) {
      p = p.and(h -> h.getTipoDocumento() == tipo);
    }
    if (!nd.isEmpty()) {
      p = p.and(h -> h.getNumeroDocumento() != null &&
                     nd.equals(h.getNumeroDocumento()));
    }

    return cargar().stream()
        .filter(p)
        .sorted(
            Comparator.comparing(Huesped::getApellido, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER))
                      .thenComparing(Huesped::getNombres, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER))
        )
        .collect(Collectors.toList());
  }



  private static String normalize(String s) {
    return (s == null) ? "" : s.trim().toUpperCase();
  }
  private static String safe(String s) {
    return (s == null) ? "" : s.trim();
  }
  private static TipoDocumento parseTipo(String s) {
    if (s == null || s.isBlank()) return null;
    try { return TipoDocumento.valueOf(s.trim().toUpperCase()); }
    catch (Exception e) { return null; }
  }
}
