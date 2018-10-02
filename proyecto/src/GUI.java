
import RecepcionDatos.Red;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GUI extends javax.swing.JFrame {

    Red red = null;

    public void setTablaSniffer(JTable TablaSniffer) {
        this.TablaSniffer = TablaSniffer;
    }

    public GUI() {
        initComponents();
        red = new Red(rp);
        inicio.enable(false);
        red.llenarComboBoxDispositivos(dispositivosCB);
        promiscuo.setSelected(true);
        
        
        //ContadorBits cb = new ContadorBits(rp);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ModoCapura = new javax.swing.ButtonGroup();
        jScrollPane2 = new javax.swing.JScrollPane();
        inicio = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        botonIniciar = new javax.swing.JButton();
        dispositivosCB = new javax.swing.JComboBox<>();
        lblSeleccionesDisp = new javax.swing.JLabel();
        promiscuo = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        no_promiscuo = new javax.swing.JRadioButton();
        contenedorOdometro = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaSniffer = new javax.swing.JTable();
        botonRegresar = new javax.swing.JButton();
        botonContinuar = new javax.swing.JButton();
        botonDeneter = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaDatos = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        rp = new Odometro.RotatePanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        botonIniciar.setText("Iniciar");
        botonIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonIniciarActionPerformed(evt);
            }
        });

        lblSeleccionesDisp.setText("Selecciones el dispositivo");

        ModoCapura.add(promiscuo);
        promiscuo.setText("Promiscuo");

        jLabel2.setText("Seleccionar modo de captura de datos");

        ModoCapura.add(no_promiscuo);
        no_promiscuo.setText("No promiscuo");

        javax.swing.GroupLayout contenedorOdometroLayout = new javax.swing.GroupLayout(contenedorOdometro);
        contenedorOdometro.setLayout(contenedorOdometroLayout);
        contenedorOdometroLayout.setHorizontalGroup(
            contenedorOdometroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 196, Short.MAX_VALUE)
        );
        contenedorOdometroLayout.setVerticalGroup(
            contenedorOdometroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 165, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(no_promiscuo)
                            .addComponent(jLabel2)
                            .addComponent(promiscuo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 373, Short.MAX_VALUE)
                        .addComponent(contenedorOdometro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(198, 198, 198))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSeleccionesDisp, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dispositivosCB, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(612, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(contenedorOdometro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(lblSeleccionesDisp, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dispositivosCB, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(botonIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(promiscuo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(no_promiscuo)))
                .addContainerGap(150, Short.MAX_VALUE))
        );

        inicio.addTab("Inicio", jPanel2);

        TablaSniffer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaSnifferMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TablaSniffer);

        botonRegresar.setText("REGRESAR");
        botonRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRegresarActionPerformed(evt);
            }
        });

        botonContinuar.setText("CONTINUAR");
        botonContinuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonContinuarActionPerformed(evt);
            }
        });

        botonDeneter.setText("DETENER");
        botonDeneter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonDeneterActionPerformed(evt);
            }
        });

        tablaDatos.setMaximumSize(new java.awt.Dimension(300, 64));
        jScrollPane4.setViewportView(tablaDatos);

        javax.swing.GroupLayout rpLayout = new javax.swing.GroupLayout(rp);
        rp.setLayout(rpLayout);
        rpLayout.setHorizontalGroup(
            rpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 218, Short.MAX_VALUE)
        );
        rpLayout.setVerticalGroup(
            rpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rp, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rp, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(botonDeneter, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(229, 229, 229)
                        .addComponent(botonContinuar, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 280, Short.MAX_VALUE)
                        .addComponent(botonRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 733, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(16, 16, 16))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonRegresar)
                    .addComponent(botonContinuar)
                    .addComponent(botonDeneter))
                .addGap(24, 24, 24))
        );

        inicio.addTab("Sniffer", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(inicio)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(inicio, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonIniciarActionPerformed
        inicio.setSelectedIndex(1);
        //
        botonContinuar.setEnabled(false);
        botonDeneter.setEnabled(true);
        modelarTablaDeSniffer();
        modelarTablaDeDatos();
        
        //red.continuarLLenadoDeTabla();
        

    }//GEN-LAST:event_botonIniciarActionPerformed

    private void botonDeneterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonDeneterActionPerformed
        red.detenerLlenadoDeTabla();

        botonContinuar.setEnabled(true);
        botonDeneter.setEnabled(false);
    }//GEN-LAST:event_botonDeneterActionPerformed

    private void botonContinuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonContinuarActionPerformed
        botonContinuar.setEnabled(false);
        botonDeneter.setEnabled(true);
        red.continuarLLenadoDeTabla();
    }//GEN-LAST:event_botonContinuarActionPerformed

    private void botonRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRegresarActionPerformed

        red.detenerLlenadoDeTabla();
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        inicio.setSelectedIndex(0);
        red.terminarLlenadoDeTabla();
    }//GEN-LAST:event_botonRegresarActionPerformed

    private void TablaSnifferMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaSnifferMouseClicked

        DefaultTableModel model = (DefaultTableModel) tablaDatos.getModel();
        model.setRowCount(0);
        llenarDatosPaquete(tablaDatos);
    }//GEN-LAST:event_TablaSnifferMouseClicked
//*
    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new GUI().setVisible(true);
        });

    }
//*/
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup ModoCapura;
    private javax.swing.JTable TablaSniffer;
    private javax.swing.JButton botonContinuar;
    private javax.swing.JButton botonDeneter;
    private javax.swing.JButton botonIniciar;
    private javax.swing.JButton botonRegresar;
    private javax.swing.JPanel contenedorOdometro;
    private javax.swing.JComboBox<String> dispositivosCB;
    private javax.swing.JTabbedPane inicio;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblSeleccionesDisp;
    private javax.swing.JRadioButton no_promiscuo;
    private javax.swing.JRadioButton promiscuo;
    private Odometro.RotatePanel rp;
    private javax.swing.JTable tablaDatos;
    // End of variables declaration//GEN-END:variables

    private void modelarTablaDeSniffer() {

        DefaultTableModel model = new DefaultTableModel();
        this.TablaSniffer.setModel(model);
        TablaSniffer.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Número", "Tiempo", "Fuente", "Destino", "Protocolo", "Tamaño", "Información"
                }
        ));
        String dispositivoSeleccionado = dispositivosCB.getSelectedItem().toString();//PARA BUSCAR EL DISPOSITIVO SELECIONADO

        red.llenarTabla(TablaSniffer, dispositivoSeleccionado, promiscuo.isSelected());
    }
    
    void llenarDatosPaquete(JTable tablaDatos)
    {
       int fila = TablaSniffer.getSelectedRow(); // saco la parte presionada    
       red.conseguirDaticos(fila, tablaDatos); // sale vector enterito      
    }

    private void modelarTablaDeDatos() {
        DefaultTableModel model = new DefaultTableModel();
        this.tablaDatos.setModel(model);
        tablaDatos.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Campo", "Contenido"
                }
        ));
    }
    
}
