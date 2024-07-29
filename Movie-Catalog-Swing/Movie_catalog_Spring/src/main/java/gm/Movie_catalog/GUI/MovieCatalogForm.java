package gm.Movie_catalog.GUI;

import gm.Movie_catalog.modelo.Pelicula;
import gm.Movie_catalog.servicio.IPeliculaServicio;
import gm.Movie_catalog.servicio.PeliculaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

@Component
public class MovieCatalogForm extends JFrame {
    private JPanel panelPrincipal;
    private JTable peliculasTabla;
    IPeliculaServicio peliculaServicio;
    private DefaultTableModel tablaModeloPeliculas;

    @Autowired
    public MovieCatalogForm(PeliculaServicio peliculaServicio) {
        this.peliculaServicio = peliculaServicio;
        iniciarForma();
    }

    private void iniciarForma() {
        this.setContentPane(panelPrincipal);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(900, 700);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.tablaModeloPeliculas = new DefaultTableModel(0,4);
        String [] cabeceros = {"Id", "Nombre", "Año", "Puntuación"};
        this.tablaModeloPeliculas.setColumnIdentifiers(cabeceros);
        this.peliculasTabla = new JTable(this.tablaModeloPeliculas);
        //Cargar listado de peliculas
        listarPeliculas();
    }

    private void listarPeliculas() {
        this.tablaModeloPeliculas.setRowCount(0);
        List<Pelicula> peliculas = this.peliculaServicio.listarPeliculas();
        peliculas.forEach(pelicula -> {
            Object[] renglonPelicula = {
                    pelicula.getIdPelicula(),
                    pelicula.getNombre(),
                    pelicula.getYear(),
                    pelicula.getPuntuacion()
            };
            this.tablaModeloPeliculas.addRow(renglonPelicula);
        });
    }
}
