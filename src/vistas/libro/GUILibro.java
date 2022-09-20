/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package vistas.libro;

import vistas.estudiantes.*;
import DAOs.EstudianteDAO;
import DAOs.LibroDAO;
import DAOs.TemaDAO;
import entidades.Estudiante;
import entidades.Libro;
import entidades.Tema;
import excepcionesPropias.ExcepcionPropia;
import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import listasPropias.ListaLibros;
import listasPropias.ListaTemas;
import vistas.tema.GUIRegistroTema;
import vistas.principal.GUIPrincipal;

/**
 *
 * @author Alexander
 */
public class GUILibro extends javax.swing.JInternalFrame {

    /**
     * Creates new form GUILibro
     */
    
    LibroDAO libroDAO = new LibroDAO();
    TemaDAO materiaDAO = new TemaDAO();
    ListaLibros listaLibros;
    ListaTemas listaMaterias;
    
    public GUILibro() {
        initComponents();
        configurarEncabezadoJTable();
        llenarJTableLibros();
        cargarCboBoxMateria();
    }
    
    private void limpiar(){
        txtBusquedaTitulo.setText("");
        lblCodigo.setText("");
        txtTitulo.setText("");
        txtAutor.setText("");
        txtIsbn.setText("");
        txtNumEjemplar.setText("");
        cboMateria.setSelectedIndex(0);
    }
    
    private void establecerPorDefecto(){
        limpiar();
        llenarJTableLibros();
        cargarCboBoxMateria();
    }
    
    private void configurarEncabezadoJTable(){
        jTableLibros.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 14));
        jTableLibros.getTableHeader().setOpaque(false);
        jTableLibros.getTableHeader().setForeground(Color.BLACK);
        
    }
    
    private void cargarCboBoxMateria(){
        
        try {
            listaMaterias = materiaDAO.obtenerTodosTemas();
            //materiaDAO.ordenarTemasXDescripcionASC(listaMaterias, 0, listaMaterias.size() - 1);
            
            for (int i = 0; i < listaMaterias.tamanio(); i++) {
                cboMateria.addItem(listaMaterias.obtenerTema(i).getId()+ " - " + listaMaterias.obtenerTema(i).getDescripcion());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    
    private void llenarJTableLibros(){
        DefaultTableModel dtm = new DefaultTableModel();
        
        listaLibros = new ListaLibros();
        
        try {
            listaLibros = libroDAO.obtenerLibros();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Hubo un problema al cargar los datos de libros.");
        }
        
        String[] cabecera = {"Código", "ISBN", "Título", "Autor", "Materia", "Año", "Nº ejemplar"}; 
        dtm.setColumnIdentifiers(cabecera);
        dtm.setColumnCount(cabecera.length);
        Object[] dataLibros = new Object[dtm.getColumnCount()];
        
        for (int i = 0; i < listaLibros.tamanio(); i++) {
            dataLibros[0] = listaLibros.obtenerLibro(i).getId();
            dataLibros[1] = listaLibros.obtenerLibro(i).getIsbn();
            dataLibros[2] = listaLibros.obtenerLibro(i).getTitulo();
            dataLibros[3] = listaLibros.obtenerLibro(i).getAutores();
            dataLibros[4] = listaLibros.obtenerLibro(i).getDescripcion_materia();
            dataLibros[5] = listaLibros.obtenerLibro(i).getAniopublicacion();
            dataLibros[6] = listaLibros.obtenerLibro(i).getNumejemplar();
            dtm.addRow(dataLibros);
        }
        jTableLibros.setModel(dtm);
    }
    
    private void llenarJTableLibros(ListaLibros libros){
        
        DefaultTableModel dtm = new DefaultTableModel();
        
        String[] cabecera = {"Código", "ISBN", "Título", "Autor", "Materia", "Año", "Nº ejemplar"}; 
        dtm.setColumnIdentifiers(cabecera);
        dtm.setColumnCount(cabecera.length);
        Object[] dataLibros = new Object[dtm.getColumnCount()];
        
        for (int i = 0; i < libros.tamanio(); i++) {
            dataLibros[0] = libros.obtenerLibro(i).getId();
            dataLibros[1] = libros.obtenerLibro(i).getIsbn();
            dataLibros[2] = libros.obtenerLibro(i).getTitulo();
            dataLibros[3] = libros.obtenerLibro(i).getAutores();
            dataLibros[4] = libros.obtenerLibro(i).getDescripcion_materia();
            dataLibros[5] = libros.obtenerLibro(i).getAniopublicacion();
            dataLibros[6] = libros.obtenerLibro(i).getNumejemplar();
            dtm.addRow(dataLibros);
        }
        jTableLibros.setModel(dtm);
    }
    
    public void irAGUIRegistrarLibro(){
        GUIPrincipal.vacearDktpPane();
        
        GUIRegistroLibro guiRegistroLibro = new GUIRegistroLibro();
        GUIPrincipal.agregarJframe(guiRegistroLibro);
        guiRegistroLibro.setVisible(true);
    }
    
    public void irAGUIAgregarEjemplar(){
        GUIPrincipal.vacearDktpPane();
        
        GUIAgregarNuevoEjemplar guiAgregarNuevoEjemplar = new GUIAgregarNuevoEjemplar();
        GUIPrincipal.agregarJframe(guiAgregarNuevoEjemplar);
        guiAgregarNuevoEjemplar.setVisible(true);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGrpOrdenamiento = new javax.swing.ButtonGroup();
        jPaneEstudiante = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        txtBusquedaTitulo = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableLibros = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        lblCodigo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtIsbn = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtAutor = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cboMateria = new javax.swing.JComboBox<>();
        btnEliminar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnNuevoEjemplar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txtAnio = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtNumEjemplar = new javax.swing.JTextField();
        btnNuevoLibro = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(900, 530));

        jPaneEstudiante.setPreferredSize(new java.awt.Dimension(900, 530));
        jPaneEstudiante.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buscar.png"))); // NOI18N
        jLabel31.setText("Buscar título:");
        jPaneEstudiante.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, -1, -1));

        txtBusquedaTitulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBusquedaTituloKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBusquedaTituloKeyReleased(evt);
            }
        });
        jPaneEstudiante.add(txtBusquedaTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 20, 420, -1));

        jTableLibros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTableLibros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableLibrosMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTableLibros);

        jPaneEstudiante.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 122, 600, 340));

        lblCodigo.setBackground(new java.awt.Color(204, 204, 204));
        lblCodigo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCodigo.setOpaque(true);
        jPaneEstudiante.add(lblCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 250, 20));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Datos de libro");
        jPaneEstudiante.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("ISBN");
        jPaneEstudiante.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Código");
        jPaneEstudiante.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));
        jPaneEstudiante.add(txtIsbn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 250, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Título");
        jPaneEstudiante.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, -1));
        jPaneEstudiante.add(txtTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 250, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Autor");
        jPaneEstudiante.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));
        jPaneEstudiante.add(txtAutor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 250, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Tema");
        jPaneEstudiante.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, -1));

        cboMateria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMateriaActionPerformed(evt);
            }
        });
        jPaneEstudiante.add(cboMateria, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 250, -1));

        btnEliminar.setText("Eliminar");
        btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarMouseClicked(evt);
            }
        });
        jPaneEstudiante.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 390, 130, 40));

        btnGuardar.setText("Guardar");
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarMouseClicked(evt);
            }
        });
        jPaneEstudiante.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 120, 40));

        btnNuevoEjemplar.setText("Nuevo ejemplar");
        btnNuevoEjemplar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNuevoEjemplarMouseClicked(evt);
            }
        });
        jPaneEstudiante.add(btnNuevoEjemplar, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 440, 130, 40));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Año de publicación");
        jPaneEstudiante.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, -1, -1));
        jPaneEstudiante.add(txtAnio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 250, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Número de ejemplar");
        jPaneEstudiante.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, -1, -1));
        jPaneEstudiante.add(txtNumEjemplar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 250, -1));

        btnNuevoLibro.setText("Nuevo libro");
        btnNuevoLibro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNuevoLibroMouseClicked(evt);
            }
        });
        jPaneEstudiante.add(btnNuevoLibro, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 120, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPaneEstudiante, javax.swing.GroupLayout.DEFAULT_SIZE, 890, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPaneEstudiante, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBusquedaTituloKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaTituloKeyReleased
        
    }//GEN-LAST:event_txtBusquedaTituloKeyReleased

    private void jTableLibrosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableLibrosMouseClicked
        int filaSeleccionada = 0;
        
        if(jTableLibros.getSelectedRow() != -1){
            filaSeleccionada = jTableLibros.getSelectedRow();
            lblCodigo.setText(jTableLibros.getValueAt(filaSeleccionada, 0) + "");
            txtIsbn.setText(jTableLibros.getValueAt(filaSeleccionada, 1) + "");
            txtTitulo.setText(jTableLibros.getValueAt(filaSeleccionada, 2) + "");
            txtAutor.setText(jTableLibros.getValueAt(filaSeleccionada, 3) + "");
            txtAnio.setText(jTableLibros.getValueAt(filaSeleccionada, 5) + "");
            txtNumEjemplar.setText(jTableLibros.getValueAt(filaSeleccionada, 6) + "");
        }
    }//GEN-LAST:event_jTableLibrosMouseClicked

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        // TODO add your handling code here:
        try {
            Libro objLibro = new Libro(); 

            objLibro.setId(Integer.parseInt(lblCodigo.getText()));
            objLibro.setIsbn(txtIsbn.getText().trim());
            objLibro.setTitulo(txtTitulo.getText().trim());
            objLibro.setAutores(txtAutor.getText().trim());
            
            String detalle_materia[] = cboMateria.getSelectedItem().toString().split(" - ");
            
            objLibro.setIdmateria(Integer.parseInt(detalle_materia[0]));
            
            objLibro.setAniopublicacion(Integer.parseInt(txtAnio.getText().trim()));
            objLibro.setNumejemplar(Integer.parseInt(txtNumEjemplar.getText().trim()));

            libroDAO.actualizar(objLibro);

            JOptionPane.showMessageDialog(this, String.format("Datos del libro %s actualizados correctamente.", objLibro.getTitulo()));

            establecerPorDefecto();
        } catch (ExcepcionPropia | SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(this, "Asegúrese de seleccionar un registro de la tabla.");
            JOptionPane.showMessageDialog(this, "Asegúrese de seleccionar un registro de la tabla y colocada adecuadamente los datos en los campos.");
        }
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseClicked
        // TODO add your handling code here:
        
        int opcion = JOptionPane.showConfirmDialog(this,"¿Está seguro de que desea eliminar este registro?", "Confirmación de eliminación de datos de estudiante", JOptionPane.YES_NO_OPTION);
        
        if(opcion == JOptionPane.YES_OPTION){
            try {
                Libro objLibro = new Libro();

                objLibro.setId(Integer.parseInt(lblCodigo.getText()));
                objLibro.setTitulo(txtTitulo.getText().trim());

                libroDAO.eliminar(objLibro);

                JOptionPane.showMessageDialog(this, String.format("Datos del libro %s eliminados correctamente.", objLibro.getTitulo()));

                establecerPorDefecto();
            } catch (ExcepcionPropia | SQLException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Asegúrese de seleccionar un registro de la tabla.");
            }
        }
    }//GEN-LAST:event_btnEliminarMouseClicked

    private void txtBusquedaTituloKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaTituloKeyPressed
        // TODO add your handling code here:
        if(!(txtBusquedaTitulo.getText().equals(""))){
            try {
                llenarJTableLibros(libroDAO.obtenerLibrosXTitulo(txtBusquedaTitulo.getText().trim()));
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }//GEN-LAST:event_txtBusquedaTituloKeyPressed

    private void btnNuevoEjemplarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoEjemplarMouseClicked
        // TODO add your handling code here:
        irAGUIAgregarEjemplar();
    }//GEN-LAST:event_btnNuevoEjemplarMouseClicked

    private void btnNuevoLibroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoLibroMouseClicked
        // TODO add your handling code here:
        irAGUIRegistrarLibro();
    }//GEN-LAST:event_btnNuevoLibroMouseClicked

    private void cboMateriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMateriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboMateriaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar;
    private javax.swing.ButtonGroup btnGrpOrdenamiento;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevoEjemplar;
    private javax.swing.JButton btnNuevoLibro;
    private javax.swing.JComboBox<String> cboMateria;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPaneEstudiante;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTableLibros;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JTextField txtAnio;
    private javax.swing.JTextField txtAutor;
    private javax.swing.JTextField txtBusquedaTitulo;
    private javax.swing.JTextField txtIsbn;
    private javax.swing.JTextField txtNumEjemplar;
    private javax.swing.JTextField txtTitulo;
    // End of variables declaration//GEN-END:variables
}
