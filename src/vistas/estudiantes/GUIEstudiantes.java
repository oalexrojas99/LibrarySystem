/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package vistas.estudiantes;

import DAOs.EstudianteDAO;
import entidades.Estudiante;
import entidades.Tema;
import excepcionesPropias.ExcepcionPropia;
import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import listasPropias.ListaEstudiantes;
import vistas.tema.GUIRegistroTema;
import vistas.principal.GUIPrincipal;

/**
 *
 * @author Alexander
 */
public class GUIEstudiantes extends javax.swing.JInternalFrame{

    /**
     * Creates new form GUIEstudiantes
     */
    
    EstudianteDAO estudianteDAO = new EstudianteDAO();
    ListaEstudiantes listaEstudiantes;
    
    public GUIEstudiantes() {
        initComponents();
        configurarEncabezadoJTable();
        llenarJTableEstudiantes();
    }

    private void limpiar(){
        txtBusquedaEstudiante.setText("");
        lblCodigo.setText("");
        txtAPaterno.setText("");
        txtAMaterno.setText("");
        txtNombres.setText("");
        cboEstadoEstudiante.setSelectedIndex(0);
    }
    
    private void establecerPorDefecto(){
        //cargarCodigo();
        limpiar();
        llenarJTableEstudiantes();
    }
    
    private void configurarEncabezadoJTable(){
        jTableEstudiantes.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 14));
        jTableEstudiantes.getTableHeader().setOpaque(false);
        jTableEstudiantes.getTableHeader().setForeground(Color.BLACK);
        
    }
    private void llenarJTableEstudiantes(){
        DefaultTableModel dtm = new DefaultTableModel();
        
        listaEstudiantes = new ListaEstudiantes();
        
        try {
            listaEstudiantes = estudianteDAO.obtenerEstudiantes();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Hubo un problema al cargar los datos de estudiantes.");
        }
        
        String[] cabecera = {"Código", "Nombres", "A. Paterno", "A. Materno", "Estado"}; 
        dtm.setColumnIdentifiers(cabecera);
        dtm.setColumnCount(cabecera.length);
        Object[] dataCliente = new Object[dtm.getColumnCount()];
        
        for (int i = 0; i < listaEstudiantes.tamanio(); i++) {
            dataCliente[0] = listaEstudiantes.obtenerEstudiante(i).getId();
            dataCliente[1] = listaEstudiantes.obtenerEstudiante(i).getNombres();
            dataCliente[2] = listaEstudiantes.obtenerEstudiante(i).getApellidoPaterno();
            dataCliente[3] = listaEstudiantes.obtenerEstudiante(i).getApellidoMaterno();
            dataCliente[4] = listaEstudiantes.obtenerEstudiante(i).getEstado();
            
            dtm.addRow(dataCliente);
        }
        
        jTableEstudiantes.setModel(dtm);
    }
    
    private void llenarJTableEstudiantes(ListaEstudiantes estudiantes){
        
        DefaultTableModel dtm = new DefaultTableModel();
        
        String[] cabecera = {"Código", "Nombres", "A. Paterno", "A. Materno", "Estado"}; 
        dtm.setColumnIdentifiers(cabecera);
        dtm.setColumnCount(cabecera.length);
        Object[] dataCliente = new Object[dtm.getColumnCount()];
        
        for (int i = 0; i < estudiantes.tamanio(); i++) {
            dataCliente[0] = estudiantes.obtenerEstudiante(i).getId();
            dataCliente[1] = estudiantes.obtenerEstudiante(i).getNombres();
            dataCliente[2] = estudiantes.obtenerEstudiante(i).getApellidoPaterno();
            dataCliente[3] = estudiantes.obtenerEstudiante(i).getApellidoMaterno();
            dataCliente[4] = estudiantes.obtenerEstudiante(i).getEstado();
            dtm.addRow(dataCliente);
        }
        
        jTableEstudiantes.setModel(dtm);
    }
    
    public void irAGUIRegistrarEstudiante(){
        GUIPrincipal.vacearDktpPane();
        
        GUIRegistroEstudiante guiRegistroEstudiante = new GUIRegistroEstudiante();
        GUIPrincipal.agregarJframe(guiRegistroEstudiante);
        guiRegistroEstudiante.setVisible(true);
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
        txtBusquedaEstudiante = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableEstudiantes = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        lblCodigo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNombres = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtAPaterno = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtAMaterno = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cboEstadoEstudiante = new javax.swing.JComboBox<>();
        btnEliminar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnNuevoEstudiante = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(900, 530));

        jPaneEstudiante.setPreferredSize(new java.awt.Dimension(900, 530));
        jPaneEstudiante.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buscar.png"))); // NOI18N
        jLabel31.setText("Buscar");
        jPaneEstudiante.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, -1, -1));

        txtBusquedaEstudiante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBusquedaEstudianteKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBusquedaEstudianteKeyReleased(evt);
            }
        });
        jPaneEstudiante.add(txtBusquedaEstudiante, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 20, 420, -1));

        jTableEstudiantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTableEstudiantes.setFillsViewportHeight(true);
        jTableEstudiantes.setFocusable(false);
        jTableEstudiantes.setShowGrid(true);
        jTableEstudiantes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableEstudiantesMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTableEstudiantes);

        jPaneEstudiante.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 122, 600, 340));

        lblCodigo.setBackground(new java.awt.Color(204, 204, 204));
        lblCodigo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCodigo.setOpaque(true);
        jPaneEstudiante.add(lblCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 250, 20));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Datos de estudiante");
        jPaneEstudiante.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Nombres");
        jPaneEstudiante.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Código");
        jPaneEstudiante.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Código");
        jPaneEstudiante.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));
        jPaneEstudiante.add(txtNombres, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 250, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Apellido paterno");
        jPaneEstudiante.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));
        jPaneEstudiante.add(txtAPaterno, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 250, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Apellido materno");
        jPaneEstudiante.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, -1, -1));
        jPaneEstudiante.add(txtAMaterno, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 250, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Estado");
        jPaneEstudiante.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, -1, -1));

        cboEstadoEstudiante.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Inactivo", "Activo" }));
        jPaneEstudiante.add(cboEstadoEstudiante, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 250, -1));

        btnEliminar.setText("Eliminar");
        btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarMouseClicked(evt);
            }
        });
        jPaneEstudiante.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 310, 100, 40));

        btnGuardar.setText("Guardar");
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarMouseClicked(evt);
            }
        });
        jPaneEstudiante.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 100, 40));

        btnNuevoEstudiante.setText("Nuevo estudiante");
        btnNuevoEstudiante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNuevoEstudianteMouseClicked(evt);
            }
        });
        jPaneEstudiante.add(btnNuevoEstudiante, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 390, 150, 60));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPaneEstudiante, javax.swing.GroupLayout.DEFAULT_SIZE, 888, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPaneEstudiante, javax.swing.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBusquedaEstudianteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaEstudianteKeyReleased
        
    }//GEN-LAST:event_txtBusquedaEstudianteKeyReleased

    private void jTableEstudiantesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableEstudiantesMouseClicked
        int filaSeleccionada = 0;
        
        if(jTableEstudiantes.getSelectedRow() != -1){
            filaSeleccionada = jTableEstudiantes.getSelectedRow();
            lblCodigo.setText(jTableEstudiantes.getValueAt(filaSeleccionada, 0) + "");
            txtNombres.setText(jTableEstudiantes.getValueAt(filaSeleccionada, 1) + "");
            txtAPaterno.setText(jTableEstudiantes.getValueAt(filaSeleccionada, 2) + "");
            txtAMaterno.setText(jTableEstudiantes.getValueAt(filaSeleccionada, 3) + "");
            
            if(jTableEstudiantes.getValueAt(filaSeleccionada, 4).equals("Activo")){
                cboEstadoEstudiante.setSelectedIndex(1);
            } else{
                cboEstadoEstudiante.setSelectedIndex(0);
            }
        }
    }//GEN-LAST:event_jTableEstudiantesMouseClicked

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        // TODO add your handling code here:
        try {
            Estudiante objEstudiante = new Estudiante();

            objEstudiante.setId(Integer.parseInt(lblCodigo.getText()));
            objEstudiante.setNombres(txtNombres.getText().trim());
            objEstudiante.setApellidoPaterno(txtAPaterno.getText().trim());
            objEstudiante.setApellidoMaterno(txtAMaterno.getText().trim());
            objEstudiante.setEstado(cboEstadoEstudiante.getSelectedIndex());
            
            objEstudiante.setEstado(cboEstadoEstudiante.getSelectedIndex());

            estudianteDAO.actualizar(objEstudiante);

            JOptionPane.showMessageDialog(this, String.format("Datos del estudiante %s actualizados correctamente.", objEstudiante.getNombres()));

            establecerPorDefecto();
        } catch (ExcepcionPropia | SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(this, "Asegúrese de seleccionar un registro de la tabla.");
            JOptionPane.showMessageDialog(this, "Asegúrese de selccionar un registro de la tabla.");
        }
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseClicked
        // TODO add your handling code here:
        
        int opcion = JOptionPane.showConfirmDialog(this,"¿Está seguro de que desea eliminar este registro?", "Confirmación de eliminación de datos de estudiante", JOptionPane.YES_NO_OPTION);
        
        if(opcion == JOptionPane.YES_OPTION){
            try {
                Estudiante objEstudiante = new Estudiante();

                objEstudiante.setId(Integer.parseInt(lblCodigo.getText()));
                objEstudiante.setNombres(txtNombres.getText().trim());
                objEstudiante.setApellidoPaterno(txtAPaterno.getText().trim());
                objEstudiante.setApellidoMaterno(txtAMaterno.getText().trim());

                estudianteDAO.eliminar(objEstudiante);

                JOptionPane.showMessageDialog(this, String.format("Datos del estudiante %s eliminados correctamente.", objEstudiante.getNombres()));

                establecerPorDefecto();
            } catch (ExcepcionPropia | SQLException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Asegúrese de selccionar un registro de la tabla.");
            }
        }
    }//GEN-LAST:event_btnEliminarMouseClicked

    private void txtBusquedaEstudianteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaEstudianteKeyPressed
        // TODO add your handling code here:
        if(!(txtBusquedaEstudiante.getText().equals(""))){
            try {
                llenarJTableEstudiantes(estudianteDAO.obtenerEstudiantesXNombresApellidos(txtBusquedaEstudiante.getText().trim()));
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }//GEN-LAST:event_txtBusquedaEstudianteKeyPressed

    private void btnNuevoEstudianteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoEstudianteMouseClicked
        // TODO add your handling code here:
        irAGUIRegistrarEstudiante();
    }//GEN-LAST:event_btnNuevoEstudianteMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar;
    private javax.swing.ButtonGroup btnGrpOrdenamiento;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevoEstudiante;
    private javax.swing.JComboBox<String> cboEstadoEstudiante;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPaneEstudiante;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTableEstudiantes;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JTextField txtAMaterno;
    private javax.swing.JTextField txtAPaterno;
    private javax.swing.JTextField txtBusquedaEstudiante;
    private javax.swing.JTextField txtNombres;
    // End of variables declaration//GEN-END:variables
}