package gm.Movie_catalog.GUI;

import gm.Movie_catalog.modelo.Pelicula;
import gm.Movie_catalog.servicio.IPeliculaServicio;
import gm.Movie_catalog.servicio.PeliculaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

@Component
public class MovieCatalogForm extends JFrame {
    private JPanel panelPrincipal;
    private JTable peliculasTabla;
    private JLabel nombre;
    private JTextField nombreTexto;
    private JButton guardarButton;
    private JLabel year;
    private JTextField yearTexto;
    private JTextField puntuacionTexto;
    private JButton eliminarButton;
    private JButton limpiarButton;
    IPeliculaServicio peliculaServicio;
    private DefaultTableModel tablaModeloPeliculas;

    @Autowired
    public MovieCatalogForm(PeliculaServicio peliculaServicio) {
        this.peliculaServicio = peliculaServicio;
        iniciarForma();
        guardarButton.addActionListener(e ->  guardarPelicula());
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

    private void guardarPelicula() {
        if(nombreTexto.getText().equals("")){
            mostrarMensaje("Proporciona un nombre.");
            nombreTexto.requestFocusInWindow();
            return;
        }
        Pelicula peliculaGuardar = new Pelicula();
        String nombrePelicula = this.nombreTexto.getText();
        int yearPelicula = Integer.parseInt(this.yearTexto.getText());
        Double puntuacionPelicula = Double.parseDouble(this.puntuacionTexto.getText());
        peliculaGuardar.setNombre(nombrePelicula);
        peliculaGuardar.setYear(yearPelicula);
        peliculaGuardar.setPuntuacion(puntuacionPelicula);
        peliculaServicio.guardarPelicula(peliculaGuardar);
        mostrarMensaje("Película guardada con éxito.");
        limpiarTexto();
        listarPeliculas();



    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    private void limpiarTexto() {
        this.nombreTexto.setText("");
        this.yearTexto.setText("");
        this.puntuacionTexto.setText("");
    }
}
