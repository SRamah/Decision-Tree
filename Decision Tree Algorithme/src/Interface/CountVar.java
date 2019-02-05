/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.util.LinkedList;

/**
 *
 * @author salaheddine
 */
public class CountVar {
    
    public LinkedList<LinkedList> CountVar(LinkedList<LinkedList> list , int var){
        //System.out.println("CountVar..............com\n");
        
        LinkedList<LinkedList> tempContVar = new LinkedList();
        
        tempContVar=AnalyseColumn(VariableColumn(list ,var));
        //tempContVar=AnalyseRapidColumn(list ,var);
        //tempContVar=AnalyseUltraRapid(VariableColumn(list ,var));
        
        System.out.println(tempContVar+"\n");
        return tempContVar;
        
        
        //System.out.println("CountVar..............fin\n");
    }
    
    public LinkedList<LinkedList> AnalyseUltraRapid(LinkedList<String> templist){
        LinkedList<LinkedList> analyselist = new LinkedList();
       int i=0;
       LinkedList<Integer> tempint = new LinkedList();
       while(i<templist.size())
       {
           LinkedList<String> temp = new LinkedList();
           
           if(i==0){
                temp.add(templist.get(i));
                //temp.add(""+(templist.size()-1));
                tempint.add((templist.size()-1));
                analyselist.add(temp);
           }
           else{
               for (int l = 0; l < analyselist.size(); l++) { 
                    if(templist.get(i).equals(analyselist.get(l).get(0))){
                        int inc = (int) tempint.get(l);
                        tempint.set(l,inc);
                    }
                    else{
                        temp.add(templist.get(i));
                        tempint.add(0);
                        analyselist.add(temp);
                    }
               }
           }
           
           i++;
       }
        for (int j = 0; j < analyselist.size(); j++) {
            analyselist.get(j).addLast(""+tempint.get(j));
        }
       return analyselist;
    }
    
   public LinkedList<String>  VariableColumn(LinkedList<LinkedList> list , int var){
       //System.out.println("VariableColumn..............com\n");
       LinkedList<String> templist = new LinkedList();
       int i=0;
       for(i=0;i<list.size();i++){
           templist.add((String) ((LinkedList) list.get(i)).get(var));
       }
       
      // System.out.println(templist);
       //System.out.println("VariableColumn..............fin\n");
       return templist;
       
       
   
   }
   
   
   public LinkedList<LinkedList>  AnalyseRapidColumn(LinkedList<LinkedList> list , int var ){
       //System.out.println("AnalyseColumn..............com\n");
       LinkedList<LinkedList> analyselist = new LinkedList();
       int i=0;
       while(i<list.size())
       {
           LinkedList<String> temp = new LinkedList();
           if(i==0){
                temp.add((String) ((LinkedList) list.get(i)).get(var));
                temp.add(""+(list.size()-1));
           }
           else{
               //if(Existe(analyselist,templist.get(i))){
               if(Existe(analyselist, (String) ((LinkedList) list.get(i)).get(var))){
                    temp.add((String) ((LinkedList) list.get(i)).get(var));
                    temp.add(""+NewCard(list,i,var));
               }
           }
           if(temp.size()!=0)
                analyselist.add(temp);
           
           i++;
       }
       //System.out.println(analyselist);
       //System.out.println("AnalyseColumn..............fin\n");
       return analyselist;
       
   
   }
   
   public int NewCard(LinkedList<LinkedList> list,int l,int c){
       int j=0,nb=0;
       while(j<list.size()){
           if(((String) ((LinkedList) list.get(j)).get(c)).equals((String) ((LinkedList) list.get(l)).get(c))){
               nb++;
           }
           j++;
       }
       
       
       return nb;
       
   }
   
   
   public LinkedList<LinkedList>  AnalyseColumn(LinkedList<String> templist){
       //System.out.println("AnalyseColumn..............com\n");
       LinkedList<LinkedList> analyselist = new LinkedList();
       int i=0;
       while(i<templist.size())
       {
           LinkedList<String> temp = new LinkedList();
           if(i==0){
                temp.add(templist.get(i));
                temp.add(""+(templist.size()-1));
           }
           else{
               if(Existe(analyselist,templist.get(i))){
                    temp.add(templist.get(i));
                    temp.add(""+Card(templist,i));
               }
           }
           if(temp.size()!=0)
                analyselist.add(temp);
           
           i++;
       }
       //System.out.println(analyselist);
       //System.out.println("AnalyseColumn..............fin\n");
       return analyselist;
       
   
   }
   
   public boolean Existe(LinkedList<LinkedList> analyselist,String mot){
       int j=0;
       while(j<analyselist.size()){
           if(((LinkedList) analyselist.get(j)).get(0).equals(mot)){
              return false;
           }
           j++;
       }
       
       
       return true;
   }
   
   
   public int Card(LinkedList<String> templist,int i){
       int j=0,nb=0;
       while(j<templist.size()){
           if(templist.get(i).equals(templist.get(j))){
               nb++;
           }
           j++;
       }
       
       
       return nb;
       
   }
    
}
