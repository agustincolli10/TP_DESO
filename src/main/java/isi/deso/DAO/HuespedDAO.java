package isi.deso.DAO;

import isi.deso.Modelo.Huesped;

public interface HuespedDAO {
    void crearHuesped(Huesped h);
    void modificarHuesped(Huesped h);
    void eliminarHuesped(Huesped h);
    Huesped obtenerHuesped(String Doc, Integer num);
}
