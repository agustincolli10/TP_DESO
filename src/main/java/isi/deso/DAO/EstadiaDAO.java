
package isi.deso.DAO;

import isi.deso.Modelo.Estadia;
import java.util.List;

/**
 * Interfaz del DAO para estadias.
 * <p>
 * Define la base del DAO que se encargara crear, actualizar
 * y consultar estadias registradas en el sistema.
 * </p>
 */
public interface EstadiaDAO {
    void crearEstadia(Estadia e);
    void modificarEstadia(String idEstadia, Estadia eActualizado);
    Estadia obtenerEstadia(String idEstadia);
    List<Estadia> obtenerTodas();
}
