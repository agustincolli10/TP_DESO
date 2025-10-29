package isi.deso.Servicio;

import isi.deso.DAO.HuespedDAO;
import isi.deso.Modelo.Huesped;
import isi.deso.Modelo.TipoDocumento;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class HuespedService {

  private final HuespedDAO dao;

  public HuespedService(HuespedDAO dao) {
    this.dao = Objects.requireNonNull(dao, "dao no puede ser null");
  }

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
                     nd.equalsIgnoreCase(h.getNumeroDocumento()));
    }

    return dao.obtenerTodos().stream()
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
}
