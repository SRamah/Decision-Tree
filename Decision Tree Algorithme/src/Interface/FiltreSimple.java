/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author salaheddine
 */
public class FiltreSimple extends FileFilter{ 
   //Description et extension acceptées par le filtre 
   private String description; 
   private String extension; 
   //Constructeur à partir de la description et de l'extension acceptées 
   public FiltreSimple(String description, String extension){ 
      if(description == null || extension ==null){ 
         throw new NullPointerException("La description (ou extension) ne peut être null."); 
      } 
      this.description = description; 
      this.extension = extension; 
   } 
   //Implémentation de FileFilter 
   @Override
   public boolean accept(File file){ 
      if(file.isDirectory()) {  
         return true;  
      }  
      String nomFichier = file.getName().toLowerCase();  
  
      return nomFichier.endsWith(extension); 
   } 
   @Override
      public String getDescription(){ 
      return description; 
   } 
}