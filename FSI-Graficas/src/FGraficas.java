/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author ASUS
 */
public class FGraficas extends javax.swing.JFrame {

    Conexion cnx = new Conexion();

    /**
     * Creates new form FGraficas
     */
    public FGraficas() {
        initComponents();

        PGrafica.setLayout(new BorderLayout());

        cnx.conectar("localhost", "insulina", "root", "");

        cargarPacientes();
        cargarEstadisticas();
    }

    private void cargarPacientes() {
        CBPaci.removeAllItems();

        ArrayList<ArrayList<String>> datos
                = cnx.consultar(
                        "SELECT curp, nombrep FROM pacientes ORDER BY nombrep"
                );

        if (datos == null || datos.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No se encontraron pacientes.");
            return;
        }

        for (ArrayList<String> fila : datos) {
            CBPaci.addItem(new Paciente(fila.get(0), fila.get(1)));
        }
    }

    private void cargarEstadisticas() {
        CBConsul.removeAllItems();

        CBConsul.addItem("Promedio mensual de insulina");
        CBConsul.addItem("Promedio mensual de glucosa");
        CBConsul.addItem("Aplicaciones de insulina por mes");
    }

    private void generarGrafica() {

        Paciente paciente
                = (Paciente) CBPaci.getSelectedItem();

        int estadistica = CBConsul.getSelectedIndex();

        if (paciente == null || estadistica == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un paciente y una estadística.");
            return;
        }

        String curp = paciente.getCurp().replace("'", "''");

        String consulta = "";
        String titulo = "";
        String ejeY = "";
        String columna = "";

        switch (estadistica) {

            case 0:
                consulta
                        = "SELECT DATE_FORMAT(fecha,'%Y-%m'), "
                        + "AVG(apli_insulina) "
                        + "FROM mediciones "
                        + "WHERE pacientes_curp='" + curp + "' "
                        + "AND apli_insulina IS NOT NULL "
                        + "GROUP BY YEAR(fecha), MONTH(fecha) "
                        + "ORDER BY YEAR(fecha), MONTH(fecha)";

                titulo = "Promedio mensual de insulina";
                ejeY = "Promedio de insulina";
                columna = "Promedio de insulina";
                break;

            case 1:
                consulta
                        = "SELECT DATE_FORMAT(fecha,'%Y-%m'), "
                        + "AVG(nvl_glucosa) "
                        + "FROM mediciones "
                        + "WHERE pacientes_curp='" + curp + "' "
                        + "GROUP BY YEAR(fecha), MONTH(fecha) "
                        + "ORDER BY YEAR(fecha), MONTH(fecha)";

                titulo = "Promedio mensual de glucosa";
                ejeY = "Promedio de glucosa";
                columna = "Promedio de glucosa";
                break;

            case 2:
                consulta
                        = "SELECT DATE_FORMAT(fecha,'%Y-%m'), "
                        + "COUNT(*) "
                        + "FROM mediciones "
                        + "WHERE pacientes_curp='" + curp + "' "
                        + "AND apli_insulina IS NOT NULL "
                        + "GROUP BY YEAR(fecha), MONTH(fecha) "
                        + "ORDER BY YEAR(fecha), MONTH(fecha)";

                titulo = "Aplicaciones de insulina por mes";
                ejeY = "Cantidad de aplicaciones";
                columna = "Aplicaciones";
                break;
        }

        ArrayList<ArrayList<String>> datos
                = cnx.consultar(consulta);

        if (datos == null || datos.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay datos para esta estadística.");
            PGrafica.removeAll();
            PGrafica.revalidate();
            PGrafica.repaint();
            return;
        }

        DefaultCategoryDataset dataset
                = new DefaultCategoryDataset();

        DefaultTableModel modelo
                = new DefaultTableModel(
                        new Object[]{"Mes", columna}, 0
                );

        for (ArrayList<String> fila : datos) {

            String mes = fila.get(0);
            double valor = Double.parseDouble(fila.get(1));

            dataset.addValue(valor, columna, mes);

            modelo.addRow(new Object[]{mes, valor});
        }

        TResultados.setModel(modelo);

        JFreeChart grafica = ChartFactory.createBarChart(
                titulo,
                "Mes",
                ejeY,
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        ChartPanel chartPanel = new ChartPanel(grafica);



        PGrafica.removeAll();
        PGrafica.setLayout(new BorderLayout());
        PGrafica.add(chartPanel, BorderLayout.CENTER);

        PGrafica.revalidate();
        PGrafica.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PGrafica = new javax.swing.JPanel();
        CBPaci = new javax.swing.JComboBox<>();
        CBConsul = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TResultados = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout PGraficaLayout = new javax.swing.GroupLayout(PGrafica);
        PGrafica.setLayout(PGraficaLayout);
        PGraficaLayout.setHorizontalGroup(
            PGraficaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        PGraficaLayout.setVerticalGroup(
            PGraficaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 286, Short.MAX_VALUE)
        );

        CBPaci.setModel(new javax.swing.DefaultComboBoxModel<Paciente>());
        CBPaci.setBorder(javax.swing.BorderFactory.createTitledBorder("Paciente:"));
        CBPaci.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CBPaciActionPerformed(evt);
            }
        });

        CBConsul.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        CBConsul.setBorder(javax.swing.BorderFactory.createTitledBorder("Consulta:"));

        jButton1.setText("GENERAR GRAFICA");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        TResultados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(TResultados);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(CBPaci, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(CBConsul, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jButton1)
                .addContainerGap(54, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(PGrafica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CBPaci, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CBConsul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PGrafica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        generarGrafica();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void CBPaciActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBPaciActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CBPaciActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FGraficas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FGraficas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FGraficas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FGraficas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FGraficas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CBConsul;
    private javax.swing.JComboBox<Paciente> CBPaci;
    private javax.swing.JPanel PGrafica;
    private javax.swing.JTable TResultados;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
