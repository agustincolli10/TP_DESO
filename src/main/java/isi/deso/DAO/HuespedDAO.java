package isi.deso.DAO;

import isi.deso.Modelo.Huesped;
import isi.deso.Modelo.TipoDocumento;
import java.util.List;

/**
 * Interfaz del DAO para huespedes.
 * <p>
 * Define la base del DAO que se encargara
 * de el CRUD de huespedes en el sistema.
 * </p>
 */
public interface HuespedDAO {
    void crearHuesped(Huesped h);
    void modificarHuesped(TipoDocumento tipoOriginal, String numOriginal, Huesped hActualizado);
    void eliminarHuesped(Huesped h);
    Huesped obtenerHuesped(TipoDocumento tipo, String num);
    List<Huesped> obtenerTodos();
}
