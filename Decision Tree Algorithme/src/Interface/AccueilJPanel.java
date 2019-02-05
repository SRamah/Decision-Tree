/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileFilter;
import sun.awt.DesktopBrowse;

/**
 *
 * @author salaheddine
 */
public class AccueilJPanel extends javax.swing.JPanel {

    /**
     * Creates new form AccueilJPanel
     */
    TraitementFichier tf = new TraitementFichier();
    TraitementFichier tf1 = new TraitementFichier();
    String tree = new String();
    String errorRules = new String();
    LinkedList<LinkedList> Matrice = new LinkedList();
    LinkedList<String> list = new LinkedList();
    LinkedList<Integer> var_in_put = new LinkedList();
    LinkedList<Integer> var_out_put = new LinkedList();
    LinkedList<VarOccurence> feuillesTerminale = new LinkedList();
    LinkedList<LinkedList<LinkedList<Verifier>>> MatriceOfRules = new LinkedList();
    double tauxEreur =0.0;
    
    int count=0;
    
    
    public AccueilJPanel() {
        initComponents();
        
    }
    
    
    public JPanel accueilJPanel(JPanel p) {
        initComponents();
        DragDropFile();
        
        
        jPanelSelectFile.setVisible(true);
        //inputjPanel.setVisible(false);
        jPanelVarInput.setVisible(false);
        jPanelVarOutput.setVisible(false);
        jPanelSeparator.setVisible(false);
        jPanelRulesFile.setVisible(false);
        jPanelReport.setVisible(false);
        pathLabel.setText(null);
        
        p.add("Center", this);
        return p;
    }

    public void choosefile(){
        FileFilter txt = new FiltreSimple("Fichiers txt",".txt"); 
        
        JFileChooser chooser = new JFileChooser("~"); 
        chooser.addChoosableFileFilter(txt); 
        
        chooser.showOpenDialog(null);
        try {
            if(chooser.getSelectedFile().getName().endsWith(".txt")){
            System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
            System.out.println("path : " + chooser.getSelectedFile().getPath());
            pathLabel.setForeground(new java.awt.Color(0, 0, 0)); 
            pathLabel.setText(chooser.getSelectedFile().getPath());
            }else{
                pathLabel.setForeground(new java.awt.Color(255, 51, 51)); 
                pathLabel.setText("Please select a file type .txt");
            }
        } 
        catch (Exception e) {
            System.out.println("imposible de choisir un fichier "+e);
        }
        
        /*TraitementFichier tf = new TraitementFichier();
        LinkedList<LinkedList> Matrix = new LinkedList(tf.TraitementFichier(chooser.getSelectedFile().getPath()));
        
        CountVar cv = new CountVar();
        
        cv.CountVar(Matrix, 4);*/
    
    }
    
    
    
   public void DragDropFile(){
       //List<File> dropppedFiles = (List<File>)jTextArea1.getTransferData(DataFlavor.javaFileListFlavor);
       new  FileDrop( dropjPanel, new FileDrop.Listener()
      {   public void  filesDropped( java.io.File[] files )
          {   
              // handle file drop
              try
                    {   
                        if(files[0].getName().endsWith(".txt")){
                            pathLabel.setForeground(new java.awt.Color(0, 0, 0)); 
                            pathLabel.setText(files[0].getCanonicalPath() );
                        }else{
                                pathLabel.setForeground(new java.awt.Color(255, 51, 51)); 
                                pathLabel.setText("Please select a file type .txt");
                            }
                    }   // end try
                    catch( java.io.IOException e ) {}
          }   // end filesDropped
      }); // end FileDrop.Listener
   }
   
   
   
   public void PasseToVarInput(){
       // list.removeAll(list);
        if(pathLabel.getText()==null || pathLabel.getText().equals("Your path is empty !!!") || pathLabel.getText().equals("Please select a file type .txt")){
            pathLabel.setForeground(new java.awt.Color(255, 51, 51));
            pathLabel.setText("Your path is empty !!!");
        }
        else{
            jPanelSelectFile.setVisible(false);
            jPanelVarInput.setVisible(true);
            
            System.out.println("You chose to open this file: \n");
            System.out.println("path : " + pathLabel.getText() +"\n");

            
            LinkedList<LinkedList> Matrix= tf.TraitementFichier(pathLabel.getText() , getSeparatorjTextField.getText());
            Matrice=Matrix;
            LinkedList<String> listinput = new LinkedList();
            
            
            //////////////GRAPH//////////////////////
                for (int i = 0; i < Matrix.get(0).size(); i++) {
                    listinput.add(i+"-"+(String) Matrix.get(0).get(i));
                }
                System.out.println("Variables list est : "+listinput);
            /////////////GRAPH//////////////////////
           
            
             var_in_put.removeAll(var_in_put);

            
            for (int i = 0; i < listinput.size(); i++) {
               // JLabel var = new JLabel();
                JCheckBox var = new JCheckBox();
                var.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                StringBuffer mystring =new StringBuffer();
                mystring.append(var.getText().split("-")[0]);
                if(var.isSelected()){
                    if(!var_in_put.contains(Integer.parseInt(mystring.toString())))
                        var_in_put.add(  Integer.parseInt(mystring.toString())  );  
                }
                if(!var.isSelected()){
                    int p=var_in_put.indexOf(Integer.parseInt(mystring.toString())  ); 
                    try {
                        var_in_put.remove(p);
                    } catch (Exception e) {
                        System.out.println("erreur de remove : "+e);
                    }

                }
                
                }
            });
                
                var.setText((String) listinput.get(i));
                inputjPanel.add(var);
                
            }
            
           // list=listinput;
            
        }
       
   } 
   
   
   
   public void PasseToVarOutput(){
       //list.removeAll(list); 
       if(var_in_put==null || var_in_put.size()==0){
            jLabelCond.setText("Please check at least one variable");
        }
        else{
            //jPanelVarOutput.removeAll();
            jLabelCond.setText("");
            jPanelSelectFile.setVisible(false);
            jPanelVarInput.setVisible(false);
            jPanelVarOutput.setVisible(true);
            jPanelRulesFile.setVisible(false);
            
            LinkedList<String> listoutput = new LinkedList();
            
            
            //////////////GRAPH//////////////////////
                for (int i = 0; i < Matrice.get(0).size(); i++) {
                    listoutput.add(i+"-"+(String) Matrice.get(0).get(i));
                }
            /////////////GRAPH//////////////////////
            
            for (int i = 0; i < listoutput.size(); i++) {
                if( !var_in_put.contains( Integer.parseInt( listoutput.get(i).split("-")[0] ) ) ){


                    JCheckBox var = new JCheckBox();
                    buttonGroupVarCible.add(var);
                    var.addMouseListener(new java.awt.event.MouseAdapter() {
                   public void mouseClicked(java.awt.event.MouseEvent evt) {
                    StringBuffer mystring =new StringBuffer();
                    mystring.append(var.getText().split("-")[0]);
                    if(var.isSelected()){
                        if(!var_out_put.contains(Integer.parseInt(mystring.toString())))
                            var_out_put.add(  Integer.parseInt(mystring.toString())  );  
                            if(var_out_put.size()==2) var_out_put.remove(0);
                    }
                    if(!var.isSelected()){
                        int p=var_out_put.indexOf(Integer.parseInt(mystring.toString())  ); 
                        var_out_put.remove(p);
                    }

                    }
                  });
                    
                    var.setText((String) listoutput.get(i));
                    outputjPanel1.add(var);
                    

                }
            }
            
          //list=listoutput;  
            
        }
   }
   
    
   
   public void PasseToRules(){
        
       jLabelwarning.setText("");
        if( !var_out_put.isEmpty() && var_out_put.get(0)!=null){
            
            jPanelSelectFile.setVisible(false);
            jPanelVarInput.setVisible(false);
            jPanelVarOutput.setVisible(false);
            jPanelRulesFile.setVisible(true);
            
            TraitementFichier tf1 = new TraitementFichier();
            this.tf1=tf1;
            tf1.GainDeArbre(pathLabel.getText(), getSeparatorjTextField.getText() ,var_in_put,var_out_put.get(0), Integer.parseInt(ErrorRateBound.getText().split("%")[0]));
            //tf1.afficheMatriceVarOccurence(tf1.arbre);
            /////////////////////////////////////////////////////////
            
            String Regles = new String("<header><style type=\"text/css\"> body{ background-color: #f1f1f1; } b{ color: red; } </style></header><body>");
            Regles=Regles+"<h3 style=\" color: green \">  Régles avec un taux d'erreur de 0.00% :</h3><ul>";
            String ReglesNonExact = new String("");
            int errorRate=0;
            
            for (int i = 0; i < tf1.arbre.size(); i++) {
                for (int j = 0; j < tf1.arbre.get(i).size(); j++) {
                    System.out.print(tf1.arbre.get(i).get(j).varName.Condition_name+" : "+tf1.arbre.get(i).get(j).info.Condition_name+" || "+tf1.arbre.get(i).get(j).listnb+"         ");
                    
                }
                System.out.println("\n");
                
            }
            ////////////////////////donnee////////////////////////////////
            LinkedList<LinkedList<LinkedList<Verifier>>> SaveRules = new LinkedList();
            for (int i = 0; i < tf1.arbre.get(0).get(0).conds.size(); i++) {
                LinkedList<Verifier> temp = new LinkedList();  Verifier vf = new Verifier();   vf.Condition_name=tf1.arbre.get(0).get(0).conds.get(i).get(0).Condition_name;   temp.add(vf);
                LinkedList<LinkedList<Verifier>> tempSaveRules = new LinkedList();
                tempSaveRules.add(temp);
                SaveRules.add(tempSaveRules);
                
            }
            ////////////////////////donnee////////////////////////////////
            
            for (int k = tf1.arbre.size()-1; k > 0; k--) {
                System.out.println(tf1.arbre.get(k).get(0).varName.Condition_name+" : "+tf1.arbre.get(k).get(0).info.Condition_name+" || "+tf1.arbre.get(k).get(0).listnb);
                
                int cd=-1,somme=0,calc=0,pcalc=-1,drp=0;
                DecimalFormat f = new DecimalFormat();
                f.setMaximumFractionDigits(2);
                
                for (int i = 0; i < tf1.arbre.get(k).get(0).listnb.size(); i++) {
                    somme=somme+tf1.arbre.get(k).get(0).listnb.get(i);    
                }
                
                for (int i = 0; i < tf1.arbre.get(k).get(0).listnb.size(); i++) {
                    if(tf1.arbre.get(k).get(0).listnb.get(i)==somme && somme!=0 ) {cd=i;}
                    else if(calc<tf1.arbre.get(k).get(0).listnb.get(i)){
                        calc=tf1.arbre.get(k).get(0).listnb.get(i);//Maximum
                        pcalc=i;
                        System.out.println(tf1.arbre.get(k).get(0).varName.Condition_name+" : "+tf1.arbre.get(k).get(0).info.Condition_name+" ===> Maximum : "+calc+" ["+pcalc+"]");
                    }/*else if (calc==tf1.arbre.get(k).get(0).listnb.get(i) && calc!=0){
                        drp++;
                    }*/
                }
                
                /*if(drp==tf1.arbre.get(k).get(0).listnb.size()){
                    
                }*/


                if(tf1.arbre.get(k).size()==1 && cd!=-1){
                    
                    LinkedList<Verifier> donnee = new LinkedList();
                    
                    for (int i = 0; i < 4; i++) {
                        
                        if(i==0)Regles=Regles+"<li>Si ";
                        else if(i==1)Regles=Regles+"<b>"+tf1.arbre.get(k).get(0).varName.Condition_name+"</b>";
                        else if(i==2)Regles=Regles+" est ";
                        else Regles=Regles+"<b>"+tf1.arbre.get(k).get(0).info.Condition_name+"</b>";
                        
                    }
                    
                    ////////////////////////donnee////////////////////////////////
                    donnee.add(tf1.arbre.get(k).get(0).info);
                    for (int b = 0; b < tf1.arbre.get(k).get(0).listnb.size(); b++) {
                        if(b!=pcalc && pcalc!=-1){
                            errorRate=errorRate+tf1.arbre.get(k).get(0).listnb.get(b);
                        }
                    }
                    
                    ////////////////////////donnee////////////////////////////////

                    System.out.print("==> SI  "+tf1.arbre.get(k).get(0).varName.Condition_name+"  EST  "+tf1.arbre.get(k).get(0).info.Condition_name);
                    VarOccurence cherche =tf1.arbre.get(k).get(0);
                    for (int i = k; i > 0; i--) {

                        for (int j = tf1.arbre.get(i).size()-1; j >0 ; j--) {

                            if(cherche.equals(tf1.arbre.get(i).get(j))){

                                for (int d = 0; d < 4; d++) {
                                    
                                    if(d==0)Regles=Regles+" et ";
                                    else if(d==1)Regles=Regles+"<b>"+tf1.arbre.get(i).get(0).varName.Condition_name+"</b>";
                                    else if(d==2)Regles=Regles+" est ";
                                    else Regles=Regles+"<b>"+tf1.arbre.get(i).get(0).info.Condition_name+"</b>";
                                    
                                }
                                
                                ////////////////////////donnee////////////////////////////////
                                donnee.add(tf1.arbre.get(i).get(0).info);
                                ////////////////////////donnee////////////////////////////////

                                System.out.print("  ET  "+tf1.arbre.get(i).get(0).varName.Condition_name+"  EST  "+tf1.arbre.get(i).get(0).info.Condition_name);
                                cherche =tf1.arbre.get(i).get(0);
                            }
                        }
                    }

                    for (int d = 0; d < 4; d++) {
                                    
                                    if(d==0)Regles=Regles+" alors ";
                                    else if(d==1)Regles=Regles+"<b>"+tf1.arbre.get(0).get(0).varName.Condition_name+"</b>";
                                    else if(d==2)Regles=Regles+" est ";
                                    else Regles=Regles+"<b>"+tf1.arbre.get(0).get(0).conds.get(cd).get(0).Condition_name+"</b>.</li>";
                                    
                    }
                    ////////////////////////donnee////////////////////////////////
                    donnee.add(tf1.arbre.get(0).get(0).conds.get(cd).get(0));
                    for (int i = 0; i < tf1.arbre.get(0).get(0).conds.size(); i++) {
                        if (tf1.arbre.get(0).get(0).conds.get(i).get(0).Condition_name.equals(tf1.arbre.get(0).get(0).conds.get(cd).get(0).Condition_name)) {
                            SaveRules.get(i).addLast(donnee);
                        }
                        
                    }
                    ////////////////////////donnee////////////////////////////////

                    
                    System.out.print("  ALORS  "+tf1.arbre.get(0).get(0).varName.Condition_name+"  EST  "+tf1.arbre.get(0).get(0).conds.get(cd).get(0).Condition_name+"\n");
                }
                /**/else if(tf1.arbre.get(k).size()==1 && pcalc!=-1){
                        LinkedList<Verifier> donnee = new LinkedList();
                    System.out.println("********************************\n "+tf1.arbre.get(k).get(0).info.Condition_name+"\n ***********************************");
                
                    for (int i = 0; i < 4; i++) {
                        
                        if(i==0)ReglesNonExact=ReglesNonExact+"<li>Si ";
                        else if(i==1)ReglesNonExact=ReglesNonExact+"<b>"+tf1.arbre.get(k).get(0).varName.Condition_name+"</b>";
                        else if(i==2)ReglesNonExact=ReglesNonExact+" est ";
                        else ReglesNonExact=ReglesNonExact+"<b>"+tf1.arbre.get(k).get(0).info.Condition_name+"</b>";
                        
                    }
                    
                    ////////////////////////donnee////////////////////////////////
                    donnee.add(tf1.arbre.get(k).get(0).info);
                    for (int b = 0; b < tf1.arbre.get(k).get(0).listnb.size(); b++) {
                        if(b!=pcalc && pcalc!=-1){
                            errorRate=errorRate+tf1.arbre.get(k).get(0).listnb.get(b);
                        }
                    }
                    ////////////////////////donnee////////////////////////////////

                    System.out.print("¤¤¤> SI  "+tf1.arbre.get(k).get(0).varName.Condition_name+"  EST  "+tf1.arbre.get(k).get(0).info.Condition_name);
                    VarOccurence cherche =tf1.arbre.get(k).get(0);
                    for (int i = k; i > 0; i--) {

                        for (int j = tf1.arbre.get(i).size()-1; j >0 ; j--) {

                            if(cherche.equals(tf1.arbre.get(i).get(j))){

                                for (int d = 0; d < 4; d++) {
                                    
                                    if(d==0)ReglesNonExact=ReglesNonExact+" et ";
                                    else if(d==1)ReglesNonExact=ReglesNonExact+"<b>"+tf1.arbre.get(i).get(0).varName.Condition_name+"</b>";
                                    else if(d==2)ReglesNonExact=ReglesNonExact+" est ";
                                    else ReglesNonExact=ReglesNonExact+"<b>"+tf1.arbre.get(i).get(0).info.Condition_name+"</b>";
                                    
                                }
                                
                                ////////////////////////donnee////////////////////////////////
                                donnee.add(tf1.arbre.get(i).get(0).info);
                                ////////////////////////donnee////////////////////////////////
                                
                                System.out.print("  ET  "+tf1.arbre.get(i).get(0).varName.Condition_name+"  EST  "+tf1.arbre.get(i).get(0).info.Condition_name);
                                cherche =tf1.arbre.get(i).get(0);
                            }
                        }
                    }

                    for (int d = 0; d < 4; d++) {
                                    
                                    if(d==0)ReglesNonExact=ReglesNonExact+" alors ";
                                    else if(d==1)ReglesNonExact=ReglesNonExact+"<b>"+tf1.arbre.get(0).get(0).varName.Condition_name+"</b>";
                                    else if(d==2)ReglesNonExact=ReglesNonExact+" est ";
                                    else {
                                        double doub = 0.0 + calc;
                                        double pors = 1.0 - doub/somme;
                                        ReglesNonExact=ReglesNonExact+"<b>"+tf1.arbre.get(0).get(0).conds.get(pcalc).get(0).Condition_name+"</b> : <u>"+f.format(pors*100)+" %"+"</u></li>";
                                    }
                                    
                    }
                    
                    
                    ////////////////////////donnee////////////////////////////////
                    donnee.add(tf1.arbre.get(0).get(0).conds.get(pcalc).get(0));
                    for (int i = 0; i < tf1.arbre.get(0).get(0).conds.size(); i++) {
                        if (tf1.arbre.get(0).get(0).conds.get(i).get(0).Condition_name.equals(tf1.arbre.get(0).get(0).conds.get(pcalc).get(0).Condition_name)) {
                            SaveRules.get(i).addLast(donnee);
                        }
                        
                    }
                    ////////////////////////donnee////////////////////////////////


                    
                    System.out.print("  ALORS  "+tf1.arbre.get(0).get(0).varName.Condition_name+"  EST  "+tf1.arbre.get(0).get(0).conds.get(pcalc).get(0).Condition_name+"\n");
                    
                    
                }/**/
            }
            Regles=Regles+"</ul>";
            Regles=Regles+"<h3 style=\" color: green \">  Régles avec un taux d'erreur en pourcentage % :</h3><ul>";
            
           /* FeuillesFinale();
            tf1.afficheListVarOccurence(feuillesTerminale);
            for (int i = 0; i < feuillesTerminale.size(); i++) {
                
                ///////////////////////////////////////////////////
                int somme=0,calc=0,pcalc=-1;
                DecimalFormat f = new DecimalFormat();
                f.setMaximumFractionDigits(2);
                for (int m = 0; m < feuillesTerminale.get(i).listnb.size(); m++) {
                    somme=somme+feuillesTerminale.get(i).listnb.get(m);    
                }
                
                for (int b = 0; b < feuillesTerminale.get(i).listnb.size(); b++) {
                    if(calc<feuillesTerminale.get(i).listnb.get(b)){
                        calc=feuillesTerminale.get(i).listnb.get(b);//Maximum
                        pcalc=b;
                        //System.out.println(FeuillesFinale().get(i).varName.Condition_name+" : "+FeuillesFinale().get(i).info.Condition_name+" ===> Maximum : "+calc+" ["+pcalc+"]");
                    }
                }
                
                double doub = 0.0 + calc;
                double pors = 1.0 - doub/somme;
                ///////////////////////////////////////////////////
                
                if(pors!=0.0){
                
                for (int e = 0; e < 4; e++) {
                            if(e==0)ReglesNonExact=ReglesNonExact+"<li>Si ";
                            else if(e==1)ReglesNonExact=ReglesNonExact+"<b>"+feuillesTerminale.get(i).varName.Condition_name+"</b>";
                            else if(e==2)ReglesNonExact=ReglesNonExact+" est ";
                            else ReglesNonExact=ReglesNonExact+"<b>"+feuillesTerminale.get(i).info.Condition_name+"</b>";
                }
                
                int index=feuillesTerminale.get(i).conds.get(0).indexOf(feuillesTerminale.get(i).info);
                
                for (int l = index-1; l > 0; l--) {
                    for (int d = 0; d < 4; d++) {
                                    
                                    if(d==0)ReglesNonExact=ReglesNonExact+" et ";
                                    else if(d==1)ReglesNonExact=ReglesNonExact+"<b>"+Matrice.get(0).get(feuillesTerminale.get(i).conds.get(0).get(l).emplacement)+"</b>";
                                    else if(d==2)ReglesNonExact=ReglesNonExact+" est ";
                                    else ReglesNonExact=ReglesNonExact+"<b>"+feuillesTerminale.get(i).conds.get(0).get(l).Condition_name+"</b>";
                                    
                    }
                        
                }
                
                
                
                for (int d = 0; d < 4; d++) {
                                    
                                    if(d==0)ReglesNonExact=ReglesNonExact+" alors ";
                                    else if(d==1)ReglesNonExact=ReglesNonExact+"<b>"+Matrice.get(0).get(feuillesTerminale.get(i).conds.get(0).get(0).emplacement)+"</b>";
                                    else if(d==2)ReglesNonExact=ReglesNonExact+" est ";
                                    else {
                                        
                                       if(pcalc!=-1) ReglesNonExact=ReglesNonExact+"<b>"+feuillesTerminale.get(i).conds.get(pcalc).get(0).Condition_name+"</b> : <u>"+f.format(pors)+" %"+"</u></li>";
                                       else ReglesNonExact=ReglesNonExact+"<b> Rien </b> : <u>"+f.format(pors)+" %"+"</u></li>";
                                    }
                                    
                }
            }    
           
        }
            *///////////////$$$$$$$$$$$$$$$$:////////////

            MatriceOfRules=SaveRules;
            for (int i = 0; i < SaveRules.size(); i++) {
                for (int j = 0; j < SaveRules.get(i).size(); j++) {
                    for (int v = 0; v < SaveRules.get(i).get(j).size(); v++) {
                        System.out.print(SaveRules.get(i).get(j).get(v).Condition_name+"  ");
                    }
                    System.out.println("");
                }
            }
            LinkedList<Integer> calucl = new LinkedList();
            calucl=confusion();
            String confu = new String("");
            
            DecimalFormat f = new DecimalFormat();
            f.setMaximumFractionDigits(4);
            
           int varYes1 = calucl.get(0);
           int tot1 = tf1.arbre.get(0).get(0).conds.get(0).get(0).totale;
           int varNo1 = tot1-varYes1;
           
           
           int varNo2 = calucl.get(1);
           int tot2 = tf1.arbre.get(0).get(0).conds.get(1).get(0).totale;
           int varYes2 = tot2-varNo2;
           int sumYes = varYes1+varYes2;
           int sumNo = varNo1+varNo2;
           int sumSum = tf1.arbre.get(0).get(0).varName.totale;
           
           double varMin1 = (0.0+varYes2)/sumYes;
           double varMin2 = (0.0+varNo1)/sumNo;
           
           if(varYes1<varYes2){ varMin1 = (0.0+varYes1)/sumYes; }
           if(varNo2<varNo1){ varMin2 = (0.0+varNo2)/sumNo; }
           
           double varMax1 = (0.0+varYes1)/tot1;
           double varMax2 = (0.0+varNo2)/tot2;
           
           if(varYes1<varNo1){ varMax1 = (0.0+varNo1)/tot1; }
           if(varNo2<varYes2){ varMax2 = (0.0+varYes2)/tot2; }
           
           
           
            confu="<table>\n" +
                    "<tr>\n" +
                    "<td> </td>\n" +
                    "<td style=\"background-color:#7e9dd6\">  "+SaveRules.get(0).get(0).get(0).Condition_name+"  </td>\n" +
                    "<td style=\"background-color:#7e9dd6\">  "+SaveRules.get(1).get(0).get(0).Condition_name+"  </td>\n" +
                    "<td style=\"background-color:#7cc5b3\">  Sum  </td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td style=\"background-color:#7e9dd6\">  "+SaveRules.get(0).get(0).get(0).Condition_name+"  </td>\n" +
                    "<td style=\"background-color:#b1cbed\">  "+varYes1+"  </td>\n" +
                    "<td style=\"background-color:#b1cbed\">  "+varNo1+"  </td>\n" +
                    "<td style=\"background-color:#7cc5b3\">  "+tot1+"  </td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td style=\"background-color:#7e9dd6\">  "+SaveRules.get(1).get(0).get(0).Condition_name+"  </td>\n" +
                    "<td style=\"background-color:#b1cbed\">  "+varYes2+"  </td>\n" +
                    "<td style=\"background-color:#b1cbed\">  "+varNo2+"  </td>\n" +
                    "<td style=\"background-color:#7cc5b3\">  "+tot2+"  </td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td style=\"background-color:#7cc5b3\">  Sum  </td>\n" +
                    "<td style=\"background-color:#7cc5b3\">  "+sumYes+"  </td>\n" +
                    "<td style=\"background-color:#7cc5b3\">  "+sumNo+"  </td>\n" +
                    "<td style=\"background-color:#7cc5b3\">  "+sumSum+"  </td>\n" +
                    "</tr>\n" +
                    "</table>"+"<br/><h3>Values prediction</h3>\n" +
                    "<table>\n" +
                    "<tr>\n" +
                    "<td style=\"background-color:#7e9dd6\">   Value   </td>\n" +
                    "<td style=\"background-color:#7e9dd6\">   Recall   </td>\n" +
                    "<td style=\"background-color:#7e9dd6\">   1-Precision   </td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td style=\"background-color:#b1cbed\">   "+SaveRules.get(0).get(0).get(0).Condition_name+"   </td>\n" +
                    "<td style=\"background-color:#b1cbed\">"+f.format(varMax1)+"</td>\n" +
                    "<td style=\"background-color:#b1cbed\">"+f.format(varMin1)+"</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td style=\"background-color:#b1cbed\">   "+SaveRules.get(1).get(0).get(0).Condition_name+"   </td>\n" +
                    "<td style=\"background-color:#b1cbed\">"+f.format(varMax2)+"</td>\n" +
                    "<td style=\"background-color:#b1cbed\">"+f.format(varMin2)+"</td>\n" +
                    "</tr>\n" +
                    "</table>";
            
            
            Regles=Regles+ReglesNonExact;
            f.setMaximumFractionDigits(2);
            Regles=Regles+"</ul>";
            Regles=Regles+"<br/><table><tr><td style=\"background-color:#7cc5b3\">   Error Rate   </td><td style=\"background-color:#7cc5b3\">   "+f.format(((0.0+errorRate)/sumSum)*100)+"%   </td></tr></table><br/>";
            Regles=Regles+"<h3>Matrice de confusion</h3>"+confu;
            
            jTextPane2.setText(Regles+"</body>");
        }
        else {
            jLabelwarning.setForeground(new java.awt.Color(255, 51, 51));
            jLabelwarning.setText("Please check one variable");
            
        }
        System.out.println("matrice used : "+tf1.used);
   }
   
   
   public LinkedList<Integer> confusion(){
       int yesOrNo=-1;
       LinkedList<Integer> ocurrence = new LinkedList();
       
       for (int j = 0; j < tf1.arbre.get(0).get(0).conds.size(); j++) {
           ocurrence.add(0);
       }
       
       
       
       for (int i = 1; i < tf1.fichierVerifier.size(); i++) {
            
            for (int j = 0; j < tf1.arbre.get(0).get(0).conds.size(); j++) {
                int empyesorno= tf1.arbre.get(0).get(0).conds.get(j).get(0).emplacement;
                String nameR = new String();
                nameR=tf1.arbre.get(0).get(0).conds.get(j).get(0).Condition_name;
                String nameF = new String();
                nameF=tf1.fichierVerifier.get(i).get(empyesorno).Condition_name;
                if(nameR.equals(nameF)){
                    yesOrNo = j; 
                    
                }
                //else System.out.println(nameR +" = "+nameF);
            }
           
            for (int k = 1; k < MatriceOfRules.get(yesOrNo).size(); k++) {
                if( coreCxe(yesOrNo, k, tf1.fichierVerifier.get(i)) ){ int incr= ocurrence.get(yesOrNo)+1;  ocurrence.set(yesOrNo, incr); break; }
            }
            
            
               
           
       }
       
       System.out.println("conta : "+ocurrence);
       return ocurrence;
       
   }
   
   public boolean coreCxe(int yesOrNo, int lingeR, LinkedList<Verifier> lingeF){
        for (int i = 0; i < MatriceOfRules.get(yesOrNo).get(lingeR).size()-1; i++) {
            int emp = MatriceOfRules.get(yesOrNo).get(lingeR).get(i).emplacement;
            String str = new String(MatriceOfRules.get(yesOrNo).get(lingeR).get(i).Condition_name);
            if( !lingeF.get(emp).Condition_name.equals(str) || lingeF.get(emp).emplacement!=emp ){  return false;  }
        }
        
        return true;
   
   }
   
   
   public void ecrire(String texte){
			File f = new File("./CartTree.html");
                        f.deleteOnExit();
			//String adressedufichier = System.getProperty("user.dir") + "/"+ nomFic;
                        String adressedufichier = "./CartTree.html";
		
			try
			{
				
				FileWriter fw = new FileWriter(adressedufichier, true);
				
				BufferedWriter output = new BufferedWriter(fw);
				
				output.write(texte);
				
				output.flush();
				
				output.close();
				
				System.out.println("fichier créé");
			}
			catch(IOException ioe){
				System.out.print("Erreur : ");
				ioe.printStackTrace();
				}

		}
   
   public String TauxErreur(VarOccurence var){
       ////////////////////////////////////////////
           
        int somme=0,min=0,calc=0,pcalc=-1;
        DecimalFormat f = new DecimalFormat();
        f.setMaximumFractionDigits(2);
        
        for (int m = 0; m < var.listnb.size(); m++) {
            somme=somme+var.listnb.get(m);    
        }

        for (int b = 0; b < var.listnb.size(); b++) {
            if(calc<var.listnb.get(b)){
                calc=var.listnb.get(b);//Maximum
                pcalc=b;
                //System.out.println(FeuillesFinale().get(i).varName.Condition_name+" : "+FeuillesFinale().get(i).info.Condition_name+" ===> Maximum : "+calc+" ["+pcalc+"]");
            }
        }
        
        for (int b = 0; b < var.listnb.size(); b++) {
            if(b!=pcalc && pcalc!=-1){
                min=min+var.listnb.get(b);
            }
        }
        
        double doub = 0.0 + calc;
        double pors = 1.0 - doub/somme;      
         
        tauxEreur=tauxEreur+min;
        
        return f.format(pors*100)+"%";
           ////////////////////////////////////////////
   }
   
   
    public void AddSonToTree(VarOccurence chercherFils){
        int drap=0;
       for (int k = 0; k < tf1.arbre.size(); k++) {
           
                    if( chercherFils.equals(tf1.arbre.get(k).get(0)) && tf1.arbre.get(k).size()>1 ){
                        tree=tree+"<ul>";
                        for (int l = 1; l < tf1.arbre.get(k).size(); l++) {
                            tree=tree+"<li><a href=\"#\">"+tf1.arbre.get(k).get(l).varName.Condition_name+" : "+tf1.arbre.get(k).get(l).info.Condition_name+"<br/>"+tf1.arbre.get(k).get(l).listnb+"</a>";
                            AddSonToTree(tf1.arbre.get(k).get(l));
                            tree=tree+"</li>";
                        }
                        tree=tree+"</li></ul>";
                        
                    }
                    else if (chercherFils.equals(tf1.arbre.get(k).get(0)) && tf1.arbre.get(k).size()==1) {
                        System.out.println("Feuille terminal : "+tf1.arbre.get(k).get(0).varName.Condition_name+" : "+tf1.arbre.get(k).get(0).info.Condition_name+"  "+tf1.arbre.get(k).get(0).listnb);
                        tree=tree+"<ul><a href=\"#\">"+TauxErreur(tf1.arbre.get(k).get(0))+"</a></ul>";
                    }
                    else if(!chercherFils.equals(tf1.arbre.get(k).get(0))) { 
                        drap++;
                        if(drap == tf1.arbre.size()){   
                            System.out.println("Feuille terminal : "+chercherFils.varName.Condition_name+" : "+chercherFils.info.Condition_name+"  "+chercherFils.listnb);
                            tree=tree+"<ul><a href=\"#\">"+TauxErreur(chercherFils)+"</a></ul>";
                         }
                    }
        }
       
       
    }
   
   
    public String GenerateCartTree(){
        DecimalFormat f = new DecimalFormat();
        f.setMaximumFractionDigits(2);
        int somme=tf1.arbre.get(0).get(0).varName.totale;
        
        
        tree="";
       LinkedList<String> list = new LinkedList();
        for (int i = 0; i < tf1.arbre.get(0).get(0).conds.size(); i++) {
            list.add(tf1.arbre.get(0).get(0).conds.get(i).get(0).Condition_name);
        }
        
        tree=tree+"<ul><li><a href=\"#\">"+tf1.arbre.get(0).get(0).varName.Condition_name+"<br/>"+list+"</a>";
        for (int j = 1; j < tf1.arbre.get(0).size(); j++) {
            if(j==1) tree=tree+"<ul>";
            tree=tree+"<li><a href=\"#\">"+tf1.arbre.get(0).get(j).varName.Condition_name+" : "+tf1.arbre.get(0).get(j).info.Condition_name+"<br/>"+tf1.arbre.get(0).get(j).listnb+"</a>";
            AddSonToTree(tf1.arbre.get(0).get(j));
        }
       tree=tree+"</li></ul>";
       
       tree=tree+" <br/><h3> Taux erreur : "+tauxEreur+"/"+somme+" = "+f.format((tauxEreur/somme)*100)+"% </h3> ";
       
       return tree;
    }
    
    
    public void FeuillesFinale(){
        feuillesTerminale.removeAll(feuillesTerminale);
        
        LinkedList<VarOccurence> list = new LinkedList();
        for (int j = 1; j < tf1.arbre.get(0).size(); j++) {
            AddFeuilleFinale(tf1.arbre.get(0).get(j));
        }
        
    }
    
    public void AddFeuilleFinale(VarOccurence feuille){
       
       int drap=0;
       for (int k = 0; k < tf1.arbre.size(); k++) {
                    
                    if( feuille.equals(tf1.arbre.get(k).get(0)) && tf1.arbre.get(k).size()>1 ){
                        
                        for (int l = 1; l < tf1.arbre.get(k).size(); l++) {
                            AddFeuilleFinale(tf1.arbre.get(k).get(l));
                        }
                        
                        
                    }
                    else if (feuille.equals(tf1.arbre.get(k).get(0)) && tf1.arbre.get(k).size()==1) {
                        VarOccurence temp = new VarOccurence();
                        temp.varName=tf1.arbre.get(k).get(0).varName;
                        temp.info=tf1.arbre.get(k).get(0).info;
                        temp.conds=tf1.arbre.get(k).get(0).conds;
                        temp.listnb=tf1.arbre.get(k).get(0).listnb;
                        temp.indexGini=tf1.arbre.get(k).get(0).indexGini;
                        feuillesTerminale.add(temp);
                        
                        System.out.println("*Feuille terminal : "+tf1.arbre.get(k).get(0).varName.Condition_name+" : "+tf1.arbre.get(k).get(0).info.Condition_name+"  "+tf1.arbre.get(k).get(0).listnb);
                    }
                    else if(!feuille.equals(tf1.arbre.get(k).get(0))) { 
                        drap++;
                        if(drap == tf1.arbre.size()){
                            
                            VarOccurence temp = new VarOccurence();
                            temp.varName=feuille.varName;
                            temp.info=feuille.info;
                            temp.conds=feuille.conds;
                            temp.listnb=feuille.listnb;
                            temp.indexGini=feuille.indexGini;
                            feuillesTerminale.add(temp);
                            
                            System.out.println("**Feuille terminal : "+feuille.varName.Condition_name+" : "+feuille.info.Condition_name+"  "+feuille.listnb);
                        }
                    }
        }
       
       
    }
    
    
    public String CSS3(){
        String css = new String();
        css="<style type=\"text/css\">\n" +
            "\n" +
            "* {margin: 0; padding: 0;}\n" +
            "\n" +
            ".tree ul {\n" +
            "	padding-top: 20px; position: relative;\n" +
            "	\n" +
            "	transition: all 0.5s;\n" +
            "	-webkit-transition: all 0.5s;\n" +
            "	-moz-transition: all 0.5s;\n" +
            "	\n" +
            "}\n" +
            "\n" +
            ".tree li {\n" +
            "	float: left; text-align: center;\n" +
            "	list-style-type: none;\n" +
            "	position: relative;\n" +
            "	padding: 20px 5px 0 5px;\n" +
            "	\n" +
            "	transition: all 0.5s;\n" +
            "	-webkit-transition: all 0.5s;\n" +
            "	-moz-transition: all 0.5s;\n" +
            "}\n" +
            "\n" +
            "/*We will use ::before and ::after to draw the connectors*/\n" +
            "\n" +
            ".tree li::before, .tree li::after{\n" +
            "	content: '';\n" +
            "	position: absolute; top: 0; right: 50%;\n" +
            "	border-top: 1px solid #ccc;\n" +
            "	width: 50%; height: 20px;\n" +
            "}\n" +
            ".tree li::after{\n" +
            "	right: auto; left: 50%;\n" +
            "	border-left: 1px solid #ccc;\n" +
            "}\n" +
            "\n" +
            "/*We need to remove left-right connectors from elements without \n" +
            "any siblings*/\n" +
            ".tree li:only-child::after, .tree li:only-child::before {\n" +
            "	display: none;\n" +
            "}\n" +
            "\n" +
            "/*Remove space from the top of single children*/\n" +
            ".tree li:only-child{ padding-top: 0;}\n" +
            "\n" +
            "/*Remove left connector from first child and \n" +
            "right connector from last child*/\n" +
            ".tree li:first-child::before, .tree li:last-child::after{\n" +
            "	border: 0 none;\n" +
            "}\n" +
            "/*Adding back the vertical connector to the last nodes*/\n" +
            ".tree li:last-child::before{\n" +
            "	border-right: 1px solid #ccc;\n" +
            "	border-radius: 0 5px 0 0;\n" +
            "	-webkit-border-radius: 0 5px 0 0;\n" +
            "	-moz-border-radius: 0 5px 0 0;\n" +
            "}\n" +
            ".tree li:first-child::after{\n" +
            "	border-radius: 5px 0 0 0;\n" +
            "	-webkit-border-radius: 5px 0 0 0;\n" +
            "	-moz-border-radius: 5px 0 0 0;\n" +
            "}\n" +
            "\n" +
            "/*Time to add downward connectors from parents*/\n" +
            ".tree ul ul::before{\n" +
            "	content: '';\n" +
            "	position: absolute; top: 0; left: 50%;\n" +
            "	border-left: 1px solid #ccc;\n" +
            "	width: 0; height: 20px;\n" +
            "}\n" +
            "\n" +
            ".tree li a{\n" +
            "	border: 1px solid #ccc;\n" +
            "	padding: 5px 10px;\n" +
            "	text-decoration: none;\n" +
            "	color: #666;\n" +
            "	font-family: arial, verdana, tahoma;\n" +
            "	font-size: 11px;\n" +
            "	display: inline-block;\n" +
            "	\n" +
            "	border-radius: 5px;\n" +
            "	-webkit-border-radius: 5px;\n" +
            "	-moz-border-radius: 5px;\n" +
            "	\n" +
            "	transition: all 0.5s;\n" +
            "	-webkit-transition: all 0.5s;\n" +
            "	-moz-transition: all 0.5s;\n" +
            "}\n" +
            "\n" +
            "/*Time for some hover effects*/\n" +
            "/*We will apply the hover effect the the lineage of the element also*/\n" +
            ".tree li a:hover, .tree li a:hover+ul li a {\n" +
            "	background: #c8e4f8; color: #000; border: 1px solid #94a0b4;\n" +
            "}\n" +
            "/*Connector styles on hover*/\n" +
            ".tree li a:hover+ul li::after, \n" +
            ".tree li a:hover+ul li::before, \n" +
            ".tree li a:hover+ul::before, \n" +
            ".tree li a:hover+ul ul::before{\n" +
            "	border-color:  #94a0b4;\n" +
            "}\n" +
            "\n" +
            "\n" +
            "</style> <meta charset=\"UTF-8\">";
        return css;
    }
   
   
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupSeparator = new javax.swing.ButtonGroup();
        buttonGroupVarCible = new javax.swing.ButtonGroup();
        jPanelSelectFile = new javax.swing.JPanel();
        jPaneltop = new javax.swing.JPanel();
        dropjPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        pathLabel = new javax.swing.JLabel();
        cadrejLabelTop = new javax.swing.JLabel();
        jPanelDown = new javax.swing.JPanel();
        addFile = new javax.swing.JLabel();
        chooseAFile = new javax.swing.JLabel();
        deletJLabel = new javax.swing.JLabel();
        okjLabel = new javax.swing.JLabel();
        cadrejLabelDown = new javax.swing.JLabel();
        jPanelCenter = new javax.swing.JPanel();
        Default_Separator = new javax.swing.JCheckBox();
        jPanelSeparator = new javax.swing.JPanel();
        Other_Separator = new javax.swing.JCheckBox();
        getSeparatorjTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        ErrorRateBound = new javax.swing.JFormattedTextField();
        jPanelVarOutput = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        outputjPanel1 = new javax.swing.JPanel();
        jLabelPasser1 = new javax.swing.JLabel();
        jLabelBack1 = new javax.swing.JLabel();
        jLabelwarning = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanelRulesFile = new javax.swing.JPanel();
        jPaneltitle1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanelRules = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();
        jPanelDown1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        goTreejLabel = new javax.swing.JLabel();
        backjLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanelVarInput = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        inputjPanel = new javax.swing.JPanel();
        jLabelPasser = new javax.swing.JLabel();
        jLabelBack = new javax.swing.JLabel();
        jLabelCond = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanelReport = new javax.swing.JPanel();
        jPaneltitle2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        backjLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelSelectFile.setLayout(new java.awt.BorderLayout());

        jPaneltop.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dropjPanel.setBackground(new java.awt.Color(255, 255, 255));
        dropjPanel.setOpaque(false);
        dropjPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/teme/filedrop1.png"))); // NOI18N
        dropjPanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 110, -1));

        jPaneltop.add(dropjPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 130, 110));

        pathLabel.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        jPaneltop.add(pathLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 360, 30));

        cadrejLabelTop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/teme/cadretop0.png"))); // NOI18N
        jPaneltop.add(cadrejLabelTop, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanelSelectFile.add(jPaneltop, java.awt.BorderLayout.PAGE_START);

        jPanelDown.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        addFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/teme/+green.png"))); // NOI18N
        jPanelDown.add(addFile, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 65, -1, -1));

        chooseAFile.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        chooseAFile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chooseAFileMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                chooseAFileMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                chooseAFileMouseEntered(evt);
            }
        });
        jPanelDown.add(chooseAFile, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 68, 240, 30));

        deletJLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/teme/deletGray.png"))); // NOI18N
        deletJLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deletJLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deletJLabelMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                deletJLabelMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                deletJLabelMouseEntered(evt);
            }
        });
        jPanelDown.add(deletJLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 110, -1, 70));

        okjLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/teme/okGray.png"))); // NOI18N
        okjLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        okjLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                okjLabelMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                okjLabelMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                okjLabelMouseEntered(evt);
            }
        });
        jPanelDown.add(okjLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, 70));

        cadrejLabelDown.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/teme/cadredown0.png"))); // NOI18N
        jPanelDown.add(cadrejLabelDown, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 190));

        jPanelSelectFile.add(jPanelDown, java.awt.BorderLayout.PAGE_END);

        jPanelCenter.setBackground(new java.awt.Color(241, 241, 241));
        jPanelCenter.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        buttonGroupSeparator.add(Default_Separator);
        Default_Separator.setSelected(true);
        Default_Separator.setText("Default Separator ( space )");
        Default_Separator.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Default_SeparatorMouseClicked(evt);
            }
        });
        jPanelCenter.add(Default_Separator, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, -1, -1));

        jPanelSeparator.setBackground(new java.awt.Color(241, 241, 241));
        jPanelSeparator.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        buttonGroupSeparator.add(Other_Separator);
        Other_Separator.setText("Other Separator");
        jPanelSeparator.add(Other_Separator, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getSeparatorjTextField.setBackground(new java.awt.Color(241, 241, 241));
        getSeparatorjTextField.setFont(new java.awt.Font("Lucida Grande", 1, 10)); // NOI18N
        jPanelSeparator.add(getSeparatorjTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 3, 70, 20));

        jPanelCenter.add(jPanelSeparator, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 23, 270, 30));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/teme/warning.png"))); // NOI18N
        jLabel7.setText("Error rate");
        jPanelCenter.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 53, -1, -1));

        ErrorRateBound.setBackground(new java.awt.Color(230, 230, 230));
        ErrorRateBound.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        ErrorRateBound.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0%"))));
        ErrorRateBound.setText("0%");
        ErrorRateBound.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jPanelCenter.add(ErrorRateBound, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 55, 60, 20));

        jPanelSelectFile.add(jPanelCenter, java.awt.BorderLayout.CENTER);

        add(jPanelSelectFile, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 580));

        jPanelVarOutput.setBackground(new java.awt.Color(241, 241, 241));
        jPanelVarOutput.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane5.setBackground(new java.awt.Color(202, 222, 228));
        jScrollPane5.setBorder(null);
        jScrollPane5.setOpaque(false);

        outputjPanel1.setBackground(new java.awt.Color(202, 222, 228));
        outputjPanel1.setLayout(new javax.swing.BoxLayout(outputjPanel1, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane5.setViewportView(outputjPanel1);

        jPanelVarOutput.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 170, 190, 340));

        jLabelPasser1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/teme/passer.png"))); // NOI18N
        jLabelPasser1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelPasser1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelPasser1MouseClicked(evt);
            }
        });
        jPanelVarOutput.add(jLabelPasser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 510, 70, -1));

        jLabelBack1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/teme/revenir.png"))); // NOI18N
        jLabelBack1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelBack1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelBack1MouseClicked(evt);
            }
        });
        jPanelVarOutput.add(jLabelBack1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 510, -1, -1));
        jPanelVarOutput.add(jLabelwarning, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 530, 190, 20));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/teme/varOutput.png"))); // NOI18N
        jPanelVarOutput.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 580));

        add(jPanelVarOutput, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 580));

        jPanelRulesFile.setLayout(new java.awt.BorderLayout());

        jPaneltitle1.setBackground(new java.awt.Color(241, 241, 241));
        jPaneltitle1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/teme/titre_Rules.png"))); // NOI18N
        jPaneltitle1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanelRulesFile.add(jPaneltitle1, java.awt.BorderLayout.PAGE_START);

        jPanelRules.setBackground(new java.awt.Color(241, 241, 241));
        jPanelRules.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBackground(new java.awt.Color(241, 241, 241));
        jScrollPane1.setBorder(null);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(400, 300));

        jTextPane2.setBackground(new java.awt.Color(241, 241, 241));
        jTextPane2.setBorder(null);
        jTextPane2.setContentType("text/html"); // NOI18N
        jScrollPane1.setViewportView(jTextPane2);

        jPanelRules.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 330));

        jPanelRulesFile.add(jPanelRules, java.awt.BorderLayout.CENTER);

        jPanelDown1.setBackground(new java.awt.Color(241, 241, 241));
        jPanelDown1.setPreferredSize(new java.awt.Dimension(400, 180));
        jPanelDown1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(241, 241, 241));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        goTreejLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/teme/tree.png"))); // NOI18N
        goTreejLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        goTreejLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                goTreejLabelMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                goTreejLabelMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                goTreejLabelMouseEntered(evt);
            }
        });
        jPanel3.add(goTreejLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, -1, 70));

        backjLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/teme/reload0.png"))); // NOI18N
        backjLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        backjLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backjLabel2MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backjLabel2MouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backjLabel2MouseEntered(evt);
            }
        });
        jPanel3.add(backjLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 70));

        jLabel6.setText("Get Report");
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 60, -1, 40));

        jPanelDown1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 400, 110));

        jPanelRulesFile.add(jPanelDown1, java.awt.BorderLayout.PAGE_END);

        add(jPanelRulesFile, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 580));

        jPanelVarInput.setBackground(new java.awt.Color(241, 241, 241));
        jPanelVarInput.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane2.setBorder(null);
        jScrollPane2.setOpaque(false);

        inputjPanel.setBackground(new java.awt.Color(202, 222, 228));
        inputjPanel.setLayout(new javax.swing.BoxLayout(inputjPanel, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane2.setViewportView(inputjPanel);

        jPanelVarInput.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 170, 190, 340));

        jLabelPasser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/teme/passer.png"))); // NOI18N
        jLabelPasser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelPasser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelPasserMouseClicked(evt);
            }
        });
        jPanelVarInput.add(jLabelPasser, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 510, 70, -1));

        jLabelBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/teme/revenir.png"))); // NOI18N
        jLabelBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelBackMouseClicked(evt);
            }
        });
        jPanelVarInput.add(jLabelBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 510, -1, -1));

        jLabelCond.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabelCond.setForeground(new java.awt.Color(255, 51, 51));
        jPanelVarInput.add(jLabelCond, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 540, 190, 20));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/teme/varInput.png"))); // NOI18N
        jPanelVarInput.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 580));

        add(jPanelVarInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 580));

        jPanelReport.setLayout(new java.awt.BorderLayout());

        jPaneltitle2.setBackground(new java.awt.Color(241, 241, 241));
        jPaneltitle2.setPreferredSize(new java.awt.Dimension(400, 70));
        jPaneltitle2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/teme/rapport.png"))); // NOI18N
        jPaneltitle2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanelReport.add(jPaneltitle2, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(241, 241, 241));
        jPanel4.setPreferredSize(new java.awt.Dimension(376, 90));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        backjLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/teme/greenback0.png"))); // NOI18N
        backjLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        backjLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backjLabel3MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backjLabel3MouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backjLabel3MouseEntered(evt);
            }
        });
        jPanel4.add(backjLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, 70));

        jPanelReport.add(jPanel4, java.awt.BorderLayout.PAGE_END);

        jScrollPane3.setBackground(new java.awt.Color(241, 241, 241));
        jScrollPane3.setBorder(null);

        jTextPane1.setBackground(new java.awt.Color(241, 241, 241));
        jTextPane1.setBorder(null);
        jTextPane1.setContentType("text/html"); // NOI18N
        jScrollPane3.setViewportView(jTextPane1);

        jPanelReport.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        add(jPanelReport, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 580));
    }// </editor-fold>//GEN-END:initComponents

    private void chooseAFileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chooseAFileMouseClicked
        choosefile();
    }//GEN-LAST:event_chooseAFileMouseClicked

    private void chooseAFileMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chooseAFileMouseExited
        addFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/teme/+green.png")));
    }//GEN-LAST:event_chooseAFileMouseExited

    private void chooseAFileMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chooseAFileMouseEntered
        addFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/teme/+.png")));
    }//GEN-LAST:event_chooseAFileMouseEntered

    private void deletJLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deletJLabelMouseClicked
        pathLabel.setText(null);
    }//GEN-LAST:event_deletJLabelMouseClicked

    private void deletJLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deletJLabelMouseExited
        deletJLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/teme/deletGray.png")));
    }//GEN-LAST:event_deletJLabelMouseExited

    private void deletJLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deletJLabelMouseEntered
        deletJLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/teme/deletRed.png")));
    }//GEN-LAST:event_deletJLabelMouseEntered

    private void okjLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_okjLabelMouseClicked
        PasseToVarInput();
    }//GEN-LAST:event_okjLabelMouseClicked

    private void okjLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_okjLabelMouseExited
        okjLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/teme/okGray.png")));
    }//GEN-LAST:event_okjLabelMouseExited

    private void okjLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_okjLabelMouseEntered
        okjLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/teme/okGreen.png")));
    }//GEN-LAST:event_okjLabelMouseEntered

    private void Default_SeparatorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Default_SeparatorMouseClicked
        if(count%2==0){
        Other_Separator.setSelected(true);
        jPanelSeparator.setVisible(true);
        count++;
        }else{
            Default_Separator.setSelected(true);
            jPanelSeparator.setVisible(false);
            getSeparatorjTextField.setText("");
            count++;
        }
    }//GEN-LAST:event_Default_SeparatorMouseClicked

    private void jLabelPasserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelPasserMouseClicked
        PasseToVarOutput();
          // tf.GainDeArbre(pathLabel.getText(),4);
          //tf.afficheMatriceVarOccurence(tf.arbre);
          //System.out.println("\n"+tf.used);
    }//GEN-LAST:event_jLabelPasserMouseClicked

    private void jLabelPasser1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelPasser1MouseClicked
        System.out.println("list input choisit est : "+var_in_put);
        System.out.println("list output choisit est : "+var_out_put);
        
        PasseToRules();
        
       
        
    }//GEN-LAST:event_jLabelPasser1MouseClicked

    private void jLabelBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelBackMouseClicked
        inputjPanel.removeAll();
        jPanelSelectFile.setVisible(true);
        jPanelVarInput.setVisible(false);
        jPanelVarOutput.setVisible(false);
        jPanelRulesFile.setVisible(false);
        jPanelReport.setVisible(false);
    }//GEN-LAST:event_jLabelBackMouseClicked

    private void jLabelBack1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelBack1MouseClicked
        outputjPanel1.removeAll();
        
        inputjPanel.repaint();
        inputjPanel.revalidate();
        jPanelSelectFile.setVisible(false);
        jPanelVarInput.setVisible(true);
        jPanelVarOutput.setVisible(false);
        jPanelRulesFile.setVisible(false);
        jPanelReport.setVisible(false);
    }//GEN-LAST:event_jLabelBack1MouseClicked

    private void backjLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backjLabel2MouseClicked
        //jTextPane2.removeAll();
        inputjPanel.removeAll();
        outputjPanel1.removeAll();
        var_out_put.removeAll(var_out_put);
        jPanelSelectFile.setVisible(true);
        jPanelVarInput.setVisible(false);
        jPanelRulesFile.setVisible(false);
        jPanelVarOutput.setVisible(false);
        jPanelReport.setVisible(false);
        jPanelVarOutput.repaint();
        this.revalidate();
        
    }//GEN-LAST:event_backjLabel2MouseClicked

    private void backjLabel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backjLabel2MouseEntered
        backjLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/teme/reload1.png")));
    }//GEN-LAST:event_backjLabel2MouseEntered

    private void goTreejLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_goTreejLabelMouseEntered
        goTreejLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/teme/tree1.png")));
    }//GEN-LAST:event_goTreejLabelMouseEntered

    private void backjLabel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backjLabel2MouseExited
        backjLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/teme/reload0.png")));
    }//GEN-LAST:event_backjLabel2MouseExited

    private void goTreejLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_goTreejLabelMouseExited
        goTreejLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/teme/tree.png")));
    }//GEN-LAST:event_goTreejLabelMouseExited

    private void backjLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backjLabel3MouseClicked
        inputjPanel.removeAll();
        outputjPanel1.removeAll();
        var_out_put.removeAll(var_out_put);
        jPanelSelectFile.setVisible(false);
        jPanelVarInput.setVisible(false);
        jPanelRulesFile.setVisible(true);
        jPanelVarOutput.setVisible(false);
        jPanelReport.setVisible(false);
        
        this.revalidate();
    }//GEN-LAST:event_backjLabel3MouseClicked

    private void backjLabel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backjLabel3MouseExited
        backjLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/teme/greenback0.png")));
    }//GEN-LAST:event_backjLabel3MouseExited

    private void backjLabel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backjLabel3MouseEntered
        backjLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Photos/teme/greenback1.png")));
    }//GEN-LAST:event_backjLabel3MouseEntered

    private void goTreejLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_goTreejLabelMouseClicked
        String fontfamily = jTextPane1.getFont().getFamily();
        tauxEreur=0.0;
        jTextPane1.setText("<html><body style=\"font-family: " + fontfamily + "\"<h1>Arbre Généré avec succès</h1></html>");
        ecrire("<html><head>"+CSS3()+"</head><div class=\"tree\">"+GenerateCartTree()+"</div><div><br/><br/><br/></div></html>");
        
        try {
            Desktop bureau =  Desktop.getDesktop();
            bureau.open(new File("./CartTree.html"));
        } catch (IOException ex) {
            System.out.println("erreur d'overture le fichier "+ex);
            Logger.getLogger(AccueilJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_goTreejLabelMouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
         String fontfamily = jTextPane1.getFont().getFamily();
        jTextPane1.setText("<html><head><style type=\"text/css\"> body{ background-color: #f1f1f1; }</style></head><body style=\"font-family: " + fontfamily + "\"><b>Calculs des Gain :</b><br/><br/>"+tf1.rapport+"</body></html>");
        
        
        
        jPanelSelectFile.setVisible(false);
        jPanelVarInput.setVisible(false);
        jPanelVarOutput.setVisible(false);
        jPanelRulesFile.setVisible(false);
        jPanelReport.setVisible(true);
    }//GEN-LAST:event_jLabel6MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox Default_Separator;
    private javax.swing.JFormattedTextField ErrorRateBound;
    private javax.swing.JCheckBox Other_Separator;
    private javax.swing.JLabel addFile;
    private javax.swing.JLabel backjLabel2;
    private javax.swing.JLabel backjLabel3;
    private javax.swing.ButtonGroup buttonGroupSeparator;
    private javax.swing.ButtonGroup buttonGroupVarCible;
    private javax.swing.JLabel cadrejLabelDown;
    private javax.swing.JLabel cadrejLabelTop;
    private javax.swing.JLabel chooseAFile;
    private javax.swing.JLabel deletJLabel;
    private javax.swing.JPanel dropjPanel;
    private javax.swing.JTextField getSeparatorjTextField;
    private javax.swing.JLabel goTreejLabel;
    private javax.swing.JPanel inputjPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelBack;
    private javax.swing.JLabel jLabelBack1;
    private javax.swing.JLabel jLabelCond;
    private javax.swing.JLabel jLabelPasser;
    private javax.swing.JLabel jLabelPasser1;
    private javax.swing.JLabel jLabelwarning;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelCenter;
    private javax.swing.JPanel jPanelDown;
    private javax.swing.JPanel jPanelDown1;
    private javax.swing.JPanel jPanelReport;
    private javax.swing.JPanel jPanelRules;
    private javax.swing.JPanel jPanelRulesFile;
    private javax.swing.JPanel jPanelSelectFile;
    private javax.swing.JPanel jPanelSeparator;
    private javax.swing.JPanel jPanelVarInput;
    private javax.swing.JPanel jPanelVarOutput;
    private javax.swing.JPanel jPaneltitle1;
    private javax.swing.JPanel jPaneltitle2;
    private javax.swing.JPanel jPaneltop;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane2;
    private javax.swing.JLabel okjLabel;
    private javax.swing.JPanel outputjPanel1;
    private javax.swing.JLabel pathLabel;
    // End of variables declaration//GEN-END:variables
}
