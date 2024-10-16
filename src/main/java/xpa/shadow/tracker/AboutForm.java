/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xpa.shadow.tracker;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author ShadowXPA
 */
public class AboutForm extends javax.swing.JFrame {

    JFrame owner;
    
    /**
     * Creates new form CreditsForm
     * @param owner
     */
    public AboutForm(JFrame owner) {
        initComponents();
        mJPanel mp = new mJPanel();
        mp.setSize(Constants.EJO_IMG_WIDTH, Constants.EJO_IMG_HEIGHT);
        mp.setLocation((this.getWidth() - mp.getWidth()) / 2, (this.getHeight() - mp.getHeight()) / 2);
        this.getContentPane().add(mp);
        this.owner = owner;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AboutTitle = new javax.swing.JLabel();
        AboutText = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(Constants.APP_JOIN_NAME + Constants.ABOUT);
        setAutoRequestFocus(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage(TrackerForm.class.getClassLoader().getResource("ejo.gif")));
        setLocation(new java.awt.Point(0, 0));
        setName(Constants.ABOUT); // NOI18N
        setPreferredSize(new java.awt.Dimension(417, 440));
        setResizable(false);
        setSize(new java.awt.Dimension(417, 440));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        AboutTitle.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 22)); // NOI18N
        AboutTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AboutTitle.setText(Constants.ABOUT);

        AboutText.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 12)); // NOI18N
        AboutText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AboutText.setText("<html><style>.center{text-align:center;}</style><body class=\"center\"><p>This application lets you track a <b>Star Wars Jedi Knight: Jedi Academy server</b>.</p><p>Originally only available for the <b>" + Constants.SERVER_NAME + "</b> server.</p><p>Change server on the <b>\"Options\"</b> tab.</p><br/><p>Application made by <b>ShadøwXPA</b>.</p><p>Feel free to distribute this software, as long as you <b>do not</b> profit from it.</p><br/><p><b>Widescreen levelshots 1.1</b> (map images) by: <b>Slash</b><br/><a href=\"https://jkhub.org/files/file/4179-widescreen-levelshots/\">https://jkhub.org/files/file/4179-widescreen-levelshots/</a></p></body></html>");
        AboutText.setMaximumSize(new java.awt.Dimension(347, 42));

        jLabel1.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(127, 0, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("<html><p>EJO Tracker Universal - version <b>" + Constants.APP_VERSION + "</b></p></html>");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(AboutTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AboutText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AboutTitle)
                .addGap(18, 18, 18)
                .addComponent(AboutText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addContainerGap(304, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        OwnerEnable(false);
        requestFocus();
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        OwnerEnable(true);
        this.dispose();
    }//GEN-LAST:event_formWindowClosing

    private void OwnerEnable(boolean enable) {
        owner.setFocusable(enable);
        owner.setEnabled(enable);
    }
    
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(CreditsForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(CreditsForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(CreditsForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(CreditsForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new CreditsForm().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AboutText;
    private javax.swing.JLabel AboutTitle;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
