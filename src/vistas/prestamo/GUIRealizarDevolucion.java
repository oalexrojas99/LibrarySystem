/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package vistas.prestamo;

import vistas.libro.*;
import vistas.estudiantes.*;
import DAOs.EstudianteDAO;
import DAOs.LibroDAO;
import DAOs.TemaDAO;
import DAOs.PrestamoDAO;
import entidades.Estudiante;
import entidades.Libro;
import entidades.Tema;
import entidades.Prestamo;
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
import listasPropias.ListaEstudiantes;
import listasPropias.ListaLibros;
import listasPropias.ListaPrestamos;
import vistas.tema.GUIRegistroTema;
import vistas.principal.GUIPrincipal;

/**
 *
 * @author Alexander
 */
public class GUIRealizarDevolucion extends javax.swing.JInternalFrame {

    /**
     * Creates new form GUIAgregarNuevoEjemplar
     */
    
    LibroDAO libroDAO = new LibroDAO();
    EstudianteDAO estudianteDAO = new EstudianteDAO();
    PrestamoDAO prestamoDAO = new PrestamoDAO();
    ListaLibros listaLibros;
    ListaEstudiantes listaEstudiantes;
    ListaPrestamos listaPrestamos;
    
    public GUIRealizarDevolucion() {
        initComponents();
        configurarEncabezadoJTable();
        cargarCboEstudiantes();
    }

    private void limpiar(){
        txtCodigo.setText("");
        txtTitulo.setText("");
    }
    
    private void establecerPorDefecto(){
        limpiar();
        jTablePrestamo.removeAll();
        cboEstudiantes.setSelectedIndex(0);
    }
    
    private void cargarCboEstudiantes(){
        try {
            listaEstudiantes = new ListaEstudiantes();
            listaEstudiantes = estudianteDAO.obtenerEstudiantesActivos();
            //estudianteDAO.ordenarEstudiantesXApellidosASC(listaEstudiantes, 0, listaEstudiantes.size() - 1);
            
            for (int i = 0; i < listaEstudiantes.tamanio(); i++) {
                cboEstudiantes.addItem(listaEstudiantes.obtenerEstudiante(i).getId()+ " - " + listaEstudiantes.obtenerEstudiante(i).getNombres()
                        + " " + listaEstudiantes.obtenerEstudiante(i).getApellidoPaterno() + " " + listaEstudiantes.obtenerEstudiante(i).getApellidoMaterno());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    
    private void configurarEncabezadoJTable(){
        jTablePrestamo.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 14));
        jTablePrestamo.getTableHeader().setOpaque(false);
        jTablePrestamo.getTableHeader().setForeground(Color.BLACK);
        
    }
    
    private void llenarJTableLibros(ListaPrestamos prestamos){
        
        DefaultTableModel dtm = new DefaultTableModel();
        
        String[] cabecera = {"Código", "Libro", "Fecha del préstamo", "Finalización préstamo"}; 
        dtm.setColumnIdentifiers(cabecera);
        dtm.setColumnCount(cabecera.length);
        Object[] dataLibros = new Object[dtm.getColumnCount()];
        
        for (int i = 0; i < prestamos.tamanio(); i++) {
            dataLibros[0] = prestamos.obtenerPrestamo(i).getId();
            dataLibros[1] = prestamos.obtenerPrestamo(i).getTitulo_libro();
            dataLibros[2] = prestamos.obtenerPrestamo(i).getFechaPrestamo();
            dataLibros[3] = prestamos.obtenerPrestamo(i).getFechaFinalizacionPrestamo();
            dtm.addRow(dataLibros);
        }
        jTablePrestamo.setModel(dtm);
    }
    
    public void irAGUIPrestamo(){
        GUIPrincipal.vacearDktpPane();
        
        GUIPrestamo guiPrestamo = new GUIPrestamo();
        GUIPrincipal.agregarJframe(guiPrestamo);
        guiPrestamo.setVisible(true);
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
        jScrollPane5 = new javax.swing.JScrollPane();
        jTablePrestamo = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();
        btnRegresar = new javax.swing.JButton();
        btnGenerarDevolucion = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cboEstudiantes = new javax.swing.JComboBox<>();

        setPreferredSize(new java.awt.Dimension(900, 530));

        jPaneEstudiante.setPreferredSize(new java.awt.Dimension(900, 530));
        jPaneEstudiante.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTablePrestamo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTablePrestamo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablePrestamoMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTablePrestamo);

        jPaneEstudiante.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 50, 600, 430));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Proceso de devolución de libro");
        jPaneEstudiante.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Título del libro:");
        jPaneEstudiante.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        txtTitulo.setEditable(false);
        jPaneEstudiante.add(txtTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 250, -1));

        btnRegresar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnRegresar.setText("Regresar");
        btnRegresar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRegresarMouseClicked(evt);
            }
        });
        jPaneEstudiante.add(btnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, 130, 40));

        btnGenerarDevolucion.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnGenerarDevolucion.setText("Realizar devolución");
        btnGenerarDevolucion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGenerarDevolucionMouseClicked(evt);
            }
        });
        jPaneEstudiante.add(btnGenerarDevolucion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 240, 40));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Código del préstamo:");
        jPaneEstudiante.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        txtCodigo.setEditable(false);
        jPaneEstudiante.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 250, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Seleccionar estudiante:");
        jPaneEstudiante.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 170, -1));

        cboEstudiantes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboEstudiantesItemStateChanged(evt);
            }
        });
        jPaneEstudiante.add(cboEstudiantes, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 10, 420, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPaneEstudiante, javax.swing.GroupLayout.DEFAULT_SIZE, 888, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPaneEstudiante, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTablePrestamoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePrestamoMouseClicked
        int filaSeleccionada = 0;
        
        if(jTablePrestamo.getSelectedRow() != -1){
            filaSeleccionada = jTablePrestamo.getSelectedRow();
            
            txtCodigo.setText(jTablePrestamo.getValueAt(filaSeleccionada, 0) + "");
            txtTitulo.setText(jTablePrestamo.getValueAt(filaSeleccionada, 1) + "");
        }
    }//GEN-LAST:event_jTablePrestamoMouseClicked

    private void btnRegresarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegresarMouseClicked
        // TODO add your handling code here:
        irAGUIPrestamo();
    }//GEN-LAST:event_btnRegresarMouseClicked

    private void btnGenerarDevolucionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGenerarDevolucionMouseClicked
        // TODO add your handling code here:
        try {
            if(txtCodigo.getText().equals("") || txtTitulo.getText().equals("")){
                throw new ExcepcionPropia("Seleccionar un préstamo de la tabla.");
            }
            
            int idPrestamo = Integer.parseInt(txtCodigo.getText());

            prestamoDAO.modificarEstadoPrestamo(idPrestamo, 0);
            
            JOptionPane.showMessageDialog(this, String.format("Se han realizado cambios en el sistema."));
            establecerPorDefecto();
        } catch (ExcepcionPropia | SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnGenerarDevolucionMouseClicked

    private void cboEstudiantesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboEstudiantesItemStateChanged
        // TODO add your handling code here:
        try {
            
            String dataEstudiante[] = cboEstudiantes.getSelectedItem().toString().split(" - ");
            
            int idEstudiante = Integer.parseInt(dataEstudiante[0]);

            llenarJTableLibros(prestamoDAO.obtenerPrestamosXEstadoYEstudiante(1, idEstudiante));
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_cboEstudiantesItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGenerarDevolucion;
    private javax.swing.ButtonGroup btnGrpOrdenamiento;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<String> cboEstudiantes;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPaneEstudiante;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTablePrestamo;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtTitulo;
    // End of variables declaration//GEN-END:variables
}
