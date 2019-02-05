/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.LinkedList;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author salaheddine
 */
public class ViewJFrame extends javax.swing.JFrame {

    /**
     * Creates new form ViewJFrame
     */
    JPanel jPanel_menu = new JPanel();
    JPanel jPanel_accueil = new JPanel();
    JPanel jPanel_variable = new JPanel();
    
    int cont=0;
    
    
    public ViewJFrame() {
        initComponents();
        this.setSize(new java.awt.Dimension(420, 685));
        accueilPanel();
       
       jPanel_menu.setVisible(false);
       
       
        
    }
    
    
    public void accueilPanel(){
        
        jPanel_accueil.setVisible(true);
        jPanel_variable.setVisible(false);
        
        jPanel_accueil.setLayout(new BorderLayout());
        
        /////***** Creation d'un Objet addnav du classe JPanel_nav ******//////
        AccueilJPanel addbody = new AccueilJPanel();
        /////***** Appel au Constricteur Surcharger de la classe JPanal_nav et le REVALIDER *****//////
        addbody.accueilJPanel(jPanel_accueil).revalidate();
        
        /////***** Fixé la taille du Zone où le nouveau JPanel (menu) dans JFrame *****//////
        //jPanel_menu.add(menu);
        jPanelCenter.add(jPanel_accueil, java.awt.BorderLayout.CENTER);
	/////***** Refaire le dessin de la JFrame ******//////
        this.repaint();
        /////***** Afficher la JFrame ******//////
        setVisible(true);
    
    }
    
    
    
  
  
    {   
   /* public LinkedList gettextaria() throws IOException{
        javax.swing.JTextArea jTextArea1 = new javax.swing.JTextArea();
        LinkedList list = new LinkedList();
        
		try {
                               
				String text,row,word;
                                text=jTextArea1.getText();
                                
				
                                    System.out.println(text);
                                  
                                    
                                    StringTokenizer ling = new StringTokenizer(text,"\n");
                                    while (ling.hasMoreElements()) {
                                        
                                        row=ling.nextToken();
                                        System.out.println("row :" +row+"\n");
                                        LinkedList<String> lin = new LinkedList<String>();
                                        
                                        
                                        StringTokenizer mot = new StringTokenizer(row," ");
                                        while (mot.hasMoreElements()) {
                                        
                                            word=mot.nextToken();
                                            System.out.println("word :" +word);

                                            lin.add(word);
                                        
                                    
                                        }
                                        list.add(lin);
                                    
                                    }
                                    System.out.println("affiche "+((LinkedList)list.get(1)).get(3));
                         
		}
		catch(Exception e) {
			System.out.println("Erreur : " + e.getMessage());
		}
        
        return list;
        
    
    }

    public void choosefile(){
        FileFilter txt = new FiltreSimple("Fichiers txt",".txt"); 
        
        JFileChooser chooser = new JFileChooser("~"); 
        chooser.addChoosableFileFilter(txt); 
        
        chooser.showOpenDialog(null);
        System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
        System.out.println("path : " + chooser.getSelectedFile().getPath());
        pathLabel.setText(chooser.getSelectedFile().getPath());
        /*TraitementFichier tf = new TraitementFichier();
        LinkedList<LinkedList> Matrix = new LinkedList(tf.TraitementFichier(chooser.getSelectedFile().getPath()));
        
        CountVar cv = new CountVar();
        
        cv.CountVar(Matrix, 4);
    
    }
    
   public void DragDropFile(){
       //List<File> dropppedFiles = (List<File>)jTextArea1.getTransferData(DataFlavor.javaFileListFlavor);
       new  FileDrop( dropjPanel, new FileDrop.Listener()
      {   public void  filesDropped( java.io.File[] files )
          {   
              // handle file drop
              try
                    {   pathLabel.setForeground(new java.awt.Color(0, 0, 0)); 
                        pathLabel.setText(files[0].getCanonicalPath() );
                       
                    }   // end try
                    catch( java.io.IOException e ) {}
          }   // end filesDropped
      }); // end FileDrop.Listener
   }
*/
} 
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelFooter = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanelTitle = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanelCenter = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Decision Tree");
        setResizable(false);

        jPanelFooter.setBackground(new java.awt.Color(241, 241, 241));

        jLabel1.setText("© Devlopped by Salah-Eddine RAMAH");

        javax.swing.GroupLayout jPanelFooterLayout = new javax.swing.GroupLayout(jPanelFooter);
        jPanelFooter.setLayout(jPanelFooterLayout);
        jPanelFooterLayout.setHorizontalGroup(
            jPanelFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFooterLayout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelFooterLayout.setVerticalGroup(
            jPanelFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        getContentPane().add(jPanelFooter, java.awt.BorderLayout.PAGE_END);

        jPanelTitle.setBackground(new java.awt.Color(241, 241, 241));
        jPanelTitle.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(241, 241, 241));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/teme/title.png"))); // NOI18N
        jPanel1.add(jLabel2, java.awt.BorderLayout.CENTER);

        jPanelTitle.add(jPanel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanelTitle, java.awt.BorderLayout.PAGE_START);

        jPanelCenter.setBackground(new java.awt.Color(241, 241, 241));
        jPanelCenter.setPreferredSize(new java.awt.Dimension(400, 570));
        getContentPane().add(jPanelCenter, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(ViewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelCenter;
    private javax.swing.JPanel jPanelFooter;
    private javax.swing.JPanel jPanelTitle;
    // End of variables declaration//GEN-END:variables
}
