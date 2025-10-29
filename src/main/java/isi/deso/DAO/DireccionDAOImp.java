/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.DAO;

import isi.deso.Modelo.DireccionDTO;
import java.io.*;


public class DireccionDAOImp implements DireccionDAO{
    private static final String ARCHIVO = "direccionesCargadas.txt";
    private static final String SEPARADOR = ";";
    @Override
    public void crearHuesped(DireccionDTO d) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true))) {

            StringBuilder sb = new StringBuilder();
            sb.append(d.getCalle()).append(SEPARADOR)
              .append(d.getNumero()).append(SEPARADOR)
              .append(d.getDepartamento()).append(SEPARADOR)
              .append(d.getPiso()).append(SEPARADOR)
              .append(d.getCodigoPostal()).append(SEPARADOR)
              .append(d.getLocalidad()).append(SEPARADOR)
              .append(d.getProvincia()).append(SEPARADOR)
              .append(d.getPais()).append(SEPARADOR);
            bw.write(sb.toString());
            bw.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
