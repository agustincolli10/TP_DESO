package isi.deso.DAO;

import isi.deso.Modelo.Huesped;
import java.util.ArrayList;
import java.util.List;

public class HuespedDAOImp implements HuespedDAO{
    
    private final String archivo = "huespedes.json";
    private final ObjectMapper mapper = new ObjectMapper();
    private List<Huesped> listaHuespedes;

}
