package isi.deso.DAO;

import isi.deso.Modelo.Huesped;
import isi.deso.Modelo.TipoDocumento;
import java.util.List;
public interface HuespedDAO {
    void crearHuesped(Huesped h);
    void modificarHuesped(TipoDocumento tipoOriginal, String numOriginal, Huesped hActualizado);
    void eliminarHuesped(Huesped h);
    Huesped obtenerHuesped(TipoDocumento tipo, String num);
    List<Huesped> obtenerTodos();
}
