
package isi.deso.DAO;

import isi.deso.Modelo.Estadia;
import java.util.List;


public interface EstadiaDAO {
    void crearEstadia(Estadia e);
    void modificarEstadia(String idEstadia, Estadia eActualizado);
    Estadia obtenerEstadia(String idEstadia);
    List<Estadia> obtenerTodas();
}
