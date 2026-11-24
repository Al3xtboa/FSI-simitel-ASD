import java.awt.Image;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class FMedicos extends javax.swing.JFrame {

    Conexion2 cnx;
    String todo = "SELECT * FROM medicos";
    ImageIcon foto;
    String archivoFoto = null;

    public FMedicos() {
        initComponents();
        setLocationRelativeTo(this);
        TConsultas.setRowHeight(100);

        cnx = new Conexion2();
        if (cnx.conectar("localhost", "simitel", "root", "") == 1) {
            cnx.entablar(todo, TConsultas);
        } else {
            JOptionPane.showMessageDialog(this, "Sin conexion a la BD");
            System.exit(0);
        }
    }

    private void ejecutarAccionMedico(String accion) {
        try {
            byte[] bytesFoto = null;
            if (archivoFoto != null && !archivoFoto.isEmpty()) {
                bytesFoto = Files.readAllBytes(Path.of(archivoFoto));
            } else if (accion.equals("INSERT")) {
                JOptionPane.showMessageDialog(this, "Selecciona una foto antes de registrar.");
                return;
            }

            Object[] res = cnx.ejecutarCRUD(
                    "gestionar_medico",
                    new Object[]{
                        accion,
                        TCedula.getText(),
                        TNombre.getText(),
                        CBEspecialidad.getSelectedItem().toString(),
                        CBSexo.getSelectedItem().toString(),
                        TFecha.getText(), // Formato YYYY-MM-DD
                        CBEstado.getSelectedItem().toString(),
                        bytesFoto
                    }
            );

            JOptionPane.showMessageDialog(this, "Estado: " + res[0] + "\nMensaje: " + res[1]);
            if (Integer.parseInt(res[0].toString()) == 1) {
                cnx.entablar(todo, TConsultas);
                limpiarCampos();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo leer el archivo de imagen.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al ejecutar operación: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        TCedula.setText("");
        TNombre.setText("");
        TFecha.setText("");
        archivoFoto = null;
        LFoto.setIcon(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        BInsertar = new javax.swing.JButton();
        BActualizar = new javax.swing.JButton();
        BEliminar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TConsultas = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        TCedula = new javax.swing.JTextField();
        TNombre = new javax.swing.JTextField();
        CBEspecialidad = new javax.swing.JComboBox<>();
        CBSexo = new javax.swing.JComboBox<>();
        TFecha = new javax.swing.JTextField();
        CBEstado = new javax.swing.JComboBox<>();
        LFoto = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GESTIÓN DE MÉDICOS - Procedimientos Almacenados");

        jToolBar1.setRollover(true);

        BInsertar.setText("INSERTAR");
        BInsertar.setFocusable(false);
        BInsertar.setMaximumSize(new java.awt.Dimension(120, 60));
        BInsertar.setPreferredSize(new java.awt.Dimension(120, 60));
        BInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BInsertarActionPerformed(evt);
            }
        });
        jToolBar1.add(BInsertar);

        BActualizar.setText("ACTUALIZAR");
        BActualizar.setFocusable(false);
        BActualizar.setMaximumSize(new java.awt.Dimension(120, 60));
        BActualizar.setPreferredSize(new java.awt.Dimension(120, 60));
        BActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BActualizarActionPerformed(evt);
            }
        });
        jToolBar1.add(BActualizar);

        BEliminar.setText("ELIMINAR");
        BEliminar.setFocusable(false);
        BEliminar.setMaximumSize(new java.awt.Dimension(120, 60));
        BEliminar.setPreferredSize(new java.awt.Dimension(120, 60));
        BEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEliminarActionPerformed(evt);
            }
        });
        jToolBar1.add(BEliminar);

        TConsultas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Cédula", "Nombre", "Especialidad", "Sexo", "Fecha", "Estado", "Foto"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TConsultas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TConsultasMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(TConsultas);

        jLabel2.setText("CÉDULA:");
        jLabel3.setText("NOMBRE:");
        jLabel4.setText("ESPECIALIDAD:");
        jLabel5.setText("SEXO:");
        jLabel6.setText("FECHA (YYYY-MM-DD):");
        jLabel7.setText("ESTADO:");

        CBEspecialidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
            "Medicina General", "Cardiología", "Pediatría", "Dermatología", "Ginecología", "Neurología"
        }));

        CBSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
            "Masculino", "Femenino"
        }));

        CBEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
            "Activo", "Inactivo", "Vacaciones"
        }));

        LFoto.setBackground(new java.awt.Color(0, 102, 102));
        LFoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LFoto.setForeground(new java.awt.Color(255, 255, 255));
        LFoto.setText("Clic para foto");
        LFoto.setOpaque(true);
        LFoto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                LFotoMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(TCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TNombre)
                            .addComponent(CBEspecialidad, 0, 280, Short.MAX_VALUE)
                            .addComponent(CBSexo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(TFecha)
                            .addComponent(CBEstado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(60, 60, 60)
                        .addComponent(LFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(TCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(TNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(CBEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(CBSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(TFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(CBEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(LFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TConsultasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TConsultasMousePressed
        int fila = TConsultas.getSelectedRow();
        if (fila >= 0) {
            TCedula.setText(TConsultas.getValueAt(fila, 0).toString());
            TNombre.setText(TConsultas.getValueAt(fila, 1).toString());
            CBEspecialidad.setSelectedItem(TConsultas.getValueAt(fila, 2).toString());
            CBSexo.setSelectedItem(TConsultas.getValueAt(fila, 3).toString());
            TFecha.setText(TConsultas.getValueAt(fila, 4).toString());
            CBEstado.setSelectedItem(TConsultas.getValueAt(fila, 5).toString());
            
            // Reiniciar ruta de archivo para que si no elige foto nueva en UPDATE, envíe NULL y conserve la actual
            archivoFoto = null; 
            
            Object imgObj = TConsultas.getValueAt(fila, 6);
            if (imgObj instanceof ImageIcon) {
                Image img = ((ImageIcon) imgObj).getImage();
                LFoto.setText("");
                LFoto.setIcon(new ImageIcon(Conexion2.escalarImagen(img, LFoto.getWidth(), LFoto.getHeight())));
            } else {
                LFoto.setIcon(null);
                LFoto.setText("Sin foto");
            }
        }
    }//GEN-LAST:event_TConsultasMousePressed

    private void BInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BInsertarActionPerformed
        ejecutarAccionMedico("INSERT");
    }//GEN-LAST:event_BInsertarActionPerformed

    private void BActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BActualizarActionPerformed
        ejecutarAccionMedico("UPDATE");
    }//GEN-LAST:event_BActualizarActionPerformed

    private void BEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEliminarActionPerformed
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar al médico con cédula " + TCedula.getText() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            ejecutarAccionMedico("DELETE");
        }
    }//GEN-LAST:event_BEliminarActionPerformed

    private void LFotoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LFotoMousePressed
        JFileChooser fc = new JFileChooser(System.getProperty("user.dir") + "/imagenes/");
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            archivoFoto = fc.getSelectedFile().getPath();
            foto = new ImageIcon(archivoFoto);
            LFoto.setText("");
            LFoto.setIcon(new ImageIcon(Conexion2.escalarImagen(foto.getImage(), LFoto.getWidth(), LFoto.getHeight())));
        }
    }//GEN-LAST:event_LFotoMousePressed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(FProcedimientos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FMedicos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BActualizar;
    private javax.swing.JButton BEliminar;
    private javax.swing.JButton BInsertar;
    private javax.swing.JComboBox<String> CBEspecialidad;
    private javax.swing.JComboBox<String> CBEstado;
    private javax.swing.JComboBox<String> CBSexo;
    private javax.swing.JLabel LFoto;
    private javax.swing.JTextField TCedula;
    private javax.swing.JTable TConsultas;
    private javax.swing.JTextField TFecha;
    private javax.swing.JTextField TNombre;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}