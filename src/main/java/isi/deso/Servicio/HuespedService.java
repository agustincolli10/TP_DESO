package isi.deso.Servicio;

import isi.deso.DAO.HuespedDAO;
import isi.deso.Modelo.Huesped;
import isi.deso.Modelo.TipoDocumento;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Servicio encargado de la busqueda de huespedes.
 * <p>
 * Implementa la logica para la busqueda de huespedes,
 * utiliza una instancia de {@link isi.deso.DAO.HuespedDAO} para acceder a los huespedes registrados.
 * </p>
 * 
 * @see isi.deso.DAO.HuespedDAO
 */
public class HuespedService {

  private final HuespedDAO dao;

  /**
   * Crea el Servicio de busqueda de huespedes con un DAO de Huesped.
   *
   * @param dao instancia de {@link isi.deso.DAO.HuespedDAO} utilizado para consultar los huespedes registrados
   */
  public HuespedService(HuespedDAO dao) {
    this.dao = Objects.requireNonNull(dao, "dao no puede ser null");
  }

  /**
   * Metodo encargado de la busqueda de huespedes.
   *
   * @param apellidoEmpiezaCon {@code String} con la o las primeras letras del apellido del huesped a buscar
   * @param nombresEmpiezaCon {@code String} con la o las primeras letras del nombre del huesped a buscar
   * @param tipo {@link isi.deso.Modelo.TipoDocumento} tipo de documento al que corresponde el numero
   * @param nro {@code String} numero del documento
   * @return una coleccion que contiene el o los huespedes que corresponden con los datos
   */
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

  /**
   * Estandariza una cadena de caracteres, borra espacios innecesarios y pasa todo a mayusculas.
   *
   * @param s {@code String} cadena a normalizar
   * @return la cadena normalizada, o una cadena vacía si el parámetro es {@code null}
   */
  private static String normalize(String s) {
    return (s == null) ? "" : s.trim().toUpperCase();
  }
}
