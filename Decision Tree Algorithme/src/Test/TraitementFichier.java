/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import Interface.*;
import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 *
 * @author salaheddine
 */
public class TraitementFichier {
    
    public LinkedList<LinkedList<VarOccurence>> arbre = new LinkedList() ;
    public LinkedList<VarOccurence> used = new LinkedList() ;

    
    
    public LinkedList<LinkedList> TraitementFichier(String path){
        
                LinkedList<LinkedList> list = new LinkedList();
		try {
			File f = new File(path);
			if (f.exists()) {
				FileReader fr = new FileReader(f);
				LineNumberReader in = new LineNumberReader(fr);
				String row;
                                
				while ( (row = in.readLine()) != null) {
                                    
                                    //System.out.println(row);
                                   // LinkedList<String> ling = new LinkedList();
                                    LinkedList ling = new LinkedList();
                                    
                                    StringTokenizer st = new StringTokenizer(row);
                                    while (st.hasMoreTokens()) {
                                        ling.add(st.nextToken());
                                        //System.out.println("variable :" + st.nextToken());
                                        
                                    }
                                    
                                    list.add(ling);
                                    
				}
                                
                                //System.out.println("affiche(0,4) "+((LinkedList)list.get(0)).get(4));
                                
                                
				
			}
                        else System.out.println("Le fichier choisis est introuvable..... ");
		}
		catch(Exception e) {
			System.out.println("Erreur : " + e.getMessage());
		}
        return list;
                
}
    
    
    public LinkedList<VarOccurence> lingtest(LinkedList<LinkedList> Matrice ,LinkedList<LinkedList<Verifier>> InfoMatrice ,LinkedList<LinkedList<Verifier>> condition  ){
        
        
        LinkedList<VarOccurence> resultatlist = new LinkedList();
        
        int i=0,j=0,l=0,c=1,condling=0,drap,racine=0;/* l == ling InfoMatrice ||  c == column InfoMatrice */
        /*For etch row of InfoMatrice <==> var1(x1,x2,...) par var2(y1,y2,,...)*/
        for(l=0;l<InfoMatrice.size();l++){
            boolean testRacine=true ;
            for(racine=0;racine<condition.get(0).size();racine++){
                if( l==condition.get(0).get(racine).emplacement && testRacine ){ testRacine=false;   }
            }
           
            System.out.println(testRacine);
            if(testRacine){
                for(c=1;c<InfoMatrice.get(l).size();c++){

                    LinkedList<LinkedList> templist = new LinkedList();
                    LinkedList<LinkedList> truelist = new LinkedList();

                    for (i=0;i<Matrice.size();i++){


                            if(  ((LinkedList<String>) Matrice.get(i)).get((((LinkedList<Verifier>) InfoMatrice.get(l)).get(c)).emplacement).equals( (((LinkedList<Verifier>) InfoMatrice.get(l)).get(c)).Condition_name )  ){

                                templist.add(Matrice.get(i));
                            }

                    }
                    
                    /* ==> templist ===> contient ligne de la Matrice du variable passée en paramétre */
                    VarOccurence sousVar = new VarOccurence();
                    LinkedList<Integer> listnb= new LinkedList();
                    listnb.clear();
                    i=0;
                    
                    for(condling=0;condling<condition.size();condling++){
                        //System.out.println("//////////////////////////condling !!! "+condling);
                  /* 1er ligne de la condition représente le 1er cas des conditions (exp: racine true) et la 2eme cas des condition (exp: racine false)...*/
                        for(i=0;i<templist.size();i++){
                            int cond=0;  drap=1;
                            
                            while(cond<condition.get(condling).size() && drap==1){
                                
                                if( !( ((LinkedList) templist.get(i)).get(condition.get(condling).get(cond).emplacement).equals( condition.get(condling).get(cond).Condition_name)  ) ){
                                     drap=0;
                                 }
                                cond++;
                                
                            }
                           //System.out.println("drap = "+drap+"   cond=condition.get(condling).size() <===> "+cond+"="+condition.get(condling).size());
                            
                            if (drap==1 && cond==condition.get(condling).size()) {
                                truelist.add(templist.get(i));
                            }
                            
                        }
                        
                        //savegarder les données calculer (nb) avant recommencer !!!
                        
                        if (truelist.isEmpty()) {
                            listnb.add(0);
                        } 
                        else {
                            listnb.add(truelist.size());
                        }
                        
                        //listnb.add(truelist.size());
                        
                        truelist.clear();
                        
                    }
                    {LinkedList<LinkedList<Verifier>> newCondition = new LinkedList(condition);
                    
                        
                        
                        LinkedList<LinkedList<Verifier>> matvefr = new LinkedList();
                        for (int a = 0; a < newCondition.size(); a++) {
                            LinkedList<Verifier> listvefr = new LinkedList();
                           for (int k = 0; k < newCondition.get(a).size(); k++) {
                                Verifier vefr = new Verifier();
                                vefr.Condition_name=newCondition.get(a).get(k).Condition_name;
                                vefr.emplacement=newCondition.get(a).get(k).emplacement;
                                vefr.totale=newCondition.get(a).get(k).totale;
                                listvefr.add(vefr);
                            }
                           matvefr.add(listvefr);
                        }
                        
        
                    
                    
                    sousVar.listnb=listnb;
                    sousVar.conds=matvefr;// savegarder le cas des conditions
                    sousVar.varName=((LinkedList<Verifier>) InfoMatrice.get(l)).get(0);
                    sousVar.info=((LinkedList<Verifier>) InfoMatrice.get(l)).get(c);
                    if(Gini(sousVar.listnb, sousVar.info)==1){}
                    else{sousVar.indexGini=Gini(sousVar.listnb, sousVar.info);}
                    
                    
                    
                    resultatlist.add(sousVar);}
                    


                }
            }
        }
        
       return resultatlist; 
    }
    
    
    public LinkedList<LinkedList<Verifier>> InfoMatrice(LinkedList<LinkedList> Matrice){
        System.out.println("\n=========== Began of InfoMatrice = List[List[Verifier]] =========\n");
        LinkedList<LinkedList<Verifier>> newMatrice = new LinkedList();
        CountVar cv = new CountVar();
        int j=0;
        for(j=0;j<Matrice.get(0).size();j++){
            LinkedList<LinkedList> matColumn = new LinkedList();
            LinkedList<Verifier> tempMatrice = new LinkedList();
            
            matColumn = cv.CountVar(Matrice, j);
            int i=0;
            for(i=0;i<matColumn.size();i++){
                Verifier ver = new Verifier();
                ver.Condition_name=(String)matColumn.get(i).get(0);
                ver.emplacement=j;
                ver.totale=Integer.parseInt((String)matColumn.get(i).get(1));
                tempMatrice.add(ver);
            }
            newMatrice.add(tempMatrice);
            
        }
       
        System.out.println("\n=========== End of InfoMatrice = List[List[Verifier]] =========\n");
        return newMatrice; //matrice dans les linges un ensemble d'objet de type Verifier qui reprisente le variable de chaque column
    }

    void afficheMatVerifier(LinkedList<LinkedList<Verifier>> InfoMatrice) {
        System.out.println("=========== affiche object =========\n");
        int i=0,j=0;
        for(j=0;j<InfoMatrice.size();j++){
            System.out.println("\n=======Linge :"+j+"========\n");
            for(i=0;i<InfoMatrice.get(j).size();i++){
                System.out.print("Nom de variable ::"+InfoMatrice.get(j).get(i).Condition_name+" :+: ");
                System.out.print("nb column ::"+InfoMatrice.get(j).get(i).emplacement+" :+: ");
                System.out.print("Totale ::"+InfoMatrice.get(j).get(i).totale);
                System.out.println("\n=============================\n");
            }
        }
    }
    
    void afficheListVarOccurence(LinkedList<VarOccurence> resultatlist){
    
        int index=0;
        for(index=0;index<resultatlist.size();index++){
            System.out.println("============================");
            System.out.println(resultatlist.get(index).varName.Condition_name+"|<<<|"+resultatlist.get(index).info.Condition_name);
            System.out.println("codition list +++++++++");
            //afficheMatVerifier(resultatlist.get(index).conds);
            System.out.println(resultatlist.get(index).info.Condition_name+" "+resultatlist.get(index).listnb);
            System.out.println("I("+resultatlist.get(index).info.Condition_name+") = "+resultatlist.get(index).indexGini);
            System.out.println("============================");
        }
    
    }
    
    void afficheMatriceVarOccurence( LinkedList<LinkedList<VarOccurence>> arbre ){
        for (int i = 0; i < arbre.size(); i++) {
            for (int j = 0; j < arbre.get(i).size(); j++) {
                if(i==0 && j==0){
                    System.out.println("\tRACINE :::::::::: "+arbre.get(i).get(j).varName.Condition_name+":::::::::::");
                    System.out.println("\t\t\t|||||||||||||||");
                }
                else if (j==0){
                    System.out.print("\t["+arbre.get(i).get(j).varName.Condition_name+"] -->>-- "+arbre.get(i).get(j).info.Condition_name+"\t");
                }
                else if(j!=0 && i!=0) {
                    System.out.print("");
                }
            }
        }
    }
    
    public double Gini(LinkedList<Integer> listnb,Verifier info){
        
        int vide=0;
        for (int nb = 0; nb < listnb.size(); nb++) {
            if(listnb.get(nb)==0){
                vide=vide+1;
            } 
        }
        if(vide==listnb.size()){
            return 1;//I de Gini est impossible
        }
       else{
       
            int totale=0;
            double somme=0.0;
            for (int i = 0; i < listnb.size(); i++) {
                totale=totale+listnb.get(i);
            }
            //if(totale==info.totale){
                for (int i = 0; i < listnb.size(); i++) {
                    somme=somme+Carre((double)listnb.get(i)/totale);
                }
                somme=1.0-somme;
            //}
            return somme;
        }
    }
    
    public double Carre(double base){
        return base*base;
    }
    
    public LinkedList<LinkedList<Verifier>> RacineCondition(LinkedList<LinkedList<Verifier>> InfoMatrice , int racineEmplacement){
        LinkedList<LinkedList<Verifier>> condition = new LinkedList();
        
        for (int i = 1; i < InfoMatrice.get(racineEmplacement).size(); i++) {
            
           LinkedList<Verifier> temp = new LinkedList();
           temp.add(InfoMatrice.get(racineEmplacement).get(i));
           condition.add(temp);
           
           
        }
        
        System.out.println("Matrice de condition : "+ condition);
        return condition;
    }
    
    public double RacineGini(LinkedList<LinkedList<Verifier>> InfoMatrice , int racineEmplacement){
       double  I=0.0;
       int Total=InfoMatrice.get(racineEmplacement).get(0).totale;
       
       
       
       
        for (int i = 1; i < InfoMatrice.get(racineEmplacement).size(); i++) {
            double P= 0.0+InfoMatrice.get(racineEmplacement).get(i).totale;
            I=I+Carre(P/Total);
        }
         System.out.println("I("+InfoMatrice.get(racineEmplacement).get(0).Condition_name+")= "+(1-I));
        return (1-I);
    
    }
    
    public LinkedList<LinkedList<Verifier>> MatriceCondition(VarOccurence varchoisi ){
        LinkedList<LinkedList<Verifier>> condition = new LinkedList(varchoisi.conds);
        
        
        
        /* 
        for (int i = 0; i < varchoisi.conds.size(); i++) {
            LinkedList<Verifier> conditionCalce = new LinkedList();
            conditionCalce=varchoisi.conds.get(i);
            condition.add(conditionCalce);
        }
        */
        for(int i=0;i<condition.size();i++){
            condition.get(i).add(varchoisi.info);
        }
        System.out.println("MatriceCondition : end of call ");
        //afficheMatVerifier(condition);
        return condition;
    }
    
    public VarOccurence Racine(LinkedList<LinkedList<Verifier>> InfoMatrice , int racineEmplacement){
        
        VarOccurence racine = new VarOccurence();
        {LinkedList<LinkedList<Verifier>> tempCondition = new LinkedList(RacineCondition(InfoMatrice, racineEmplacement));
        racine.varName=InfoMatrice.get(racineEmplacement).get(0);
        racine.info=InfoMatrice.get(racineEmplacement).get(0);
        racine.conds=tempCondition;
        racine.indexGini=RacineGini(InfoMatrice, racineEmplacement);
        }
        return racine;
    }
    
    public LinkedList<LinkedList<VarOccurence>> RegoupeSousVar(LinkedList<VarOccurence> listIndice , VarOccurence racine){
        LinkedList<LinkedList<VarOccurence>> matriceGain =new LinkedList();
        LinkedList<VarOccurence> giniDeRacine =new LinkedList();
        /*/////
        VarOccurence racine = new VarOccurence();
        racine.varName=InfoMatrice.get(racineEmplacement).get(0);
        racine.conds=RacineCondition(InfoMatrice, racineEmplacement);
        racine.indexGini=RacineGini(InfoMatrice, racineEmplacement);
        /////*/
        giniDeRacine.add(racine);
        matriceGain.add(giniDeRacine);
        
        for (int i = 0; i < listIndice.size(); i++) {
            int drp=1;
            for (int n = 1; n < matriceGain.size(); n++){ 
                for (int m = 0; m < matriceGain.get(n).size(); m++){
                    if(listIndice.get(i).info.equals(matriceGain.get(n).get(m).info)){
                        drp=0;
                    }
                    
                }
            }
            if(drp==1){
                        LinkedList<VarOccurence> tempMatriceGain =new LinkedList();
                        for (int j = 0; j < listIndice.size(); j++) {
                            if(listIndice.get(i).varName.equals(listIndice.get(j).varName)){
                                tempMatriceGain.add(listIndice.get(j));
                            }
                        }
                        //System.out.println(tempMatriceGain.size());
                        matriceGain.add(tempMatriceGain);
                    }
            
        }
        //System.out.println(matriceGain.size());
        return matriceGain;
    }
    
    public LinkedList<VarGain> ListDeGain(LinkedList<LinkedList<VarOccurence>> matriceDeGain){
        VarGain var =new VarGain();
        LinkedList<VarGain> list =new LinkedList();
        double giniDeRacine = matriceDeGain.get(0).get(0).indexGini;
        int totale=matriceDeGain.get(0).get(0).info.totale;
        System.out.println("===>>  I(RACINE)= "+giniDeRacine);
        var.variableColumn=matriceDeGain.get(0).get(0);
        list.add(var);
        System.out.println("===================================");
        for (int i = 1; i < matriceDeGain.size(); i++) {
            System.out.println("Variable : "+matriceDeGain.get(i).get(0).varName.Condition_name);
            VarGain tempvar =new VarGain();
            tempvar.variableColumn=matriceDeGain.get(i).get(0);
            tempvar.sousVariables=matriceDeGain.get(i);
            double somme=0.0;
            for (int j = 0; j < matriceDeGain.get(i).size(); j++) {
                double I=matriceDeGain.get(i).get(j).indexGini;
                //System.out.println("listnb ="+matriceDeGain.get(i).get(j).listnb);
                System.out.println("I ( "+matriceDeGain.get(i).get(j).info.Condition_name+" ) = "+I);
                double tot=0.0;
                
                for (int k = 0; k < matriceDeGain.get(i).get(j).listnb.size(); k++) {
                    tot=tot+(int)matriceDeGain.get(i).get(j).listnb.get(k);
                }
                
                //System.out.println("tot="+tot+" / totale="+totale+"   ==>"+(tot/totale));
                //System.out.println("(tot/totale)*I = "+((tot/totale)*I));
                
               somme=somme+((tot/totale)*I);
                //System.out.println("somme = "+somme);
                
            }
            tempvar.gain=(giniDeRacine-somme);
            list.add(tempvar);
            System.out.println("Gain ( "+matriceDeGain.get(i).get(0).varName.Condition_name+" ) = "+(giniDeRacine-somme));
            System.out.println("===================================");
        }
        
        return list;
    }
    
    public LinkedList<VarOccurence> GainDeArbre(String path , int empRacine){
        LinkedList<LinkedList> Matrix =new LinkedList(TraitementFichier(path));
        LinkedList<LinkedList<Verifier>> infoMat =new LinkedList(InfoMatrice(Matrix));
        LinkedList<LinkedList<Verifier>> condition = new LinkedList(RacineCondition(infoMat,empRacine));//!!!!
        LinkedList<VarOccurence> resultatlist = new LinkedList(lingtest(Matrix, infoMat, condition));//!!!!
        VarOccurence rac =new VarOccurence();
        rac=Racine(infoMat, empRacine);
        
        LinkedList<VarOccurence> sousVariables = new LinkedList();
        //RacineGini(infoMat, empRacine);
        //afficheMatVerifier(condition);
        
        /*
        ///////////////////////////test////////////////////////
        
        LinkedList<LinkedList<VarOccurence>> tempp =new LinkedList(RegoupeSousVar(resultatlist,rac));
        System.out.println("LinkedList<LinkedList<VarOccurence>> RegoupeSousVar ++++  avant");
        for (int b = 0; b < tempp.size(); b++) {
            afficheListVarOccurence(tempp.get(b));
        }
        
        
        ///////////////////////////test////////////////////////
        */
        
        used.add(rac); /// ajouter le variable au list des variables utilisées
        sousVariables=MaxGain(ListDeGain(RegoupeSousVar(resultatlist,rac)));
        
        //began save
        LinkedList<VarOccurence> temparbre =new LinkedList();
        temparbre.add(rac);
        
        for (int i = 0; i < sousVariables.size(); i++) {
            temparbre.add(sousVariables.get(i));
        }
        arbre.add(temparbre);
        //end save
        RacineArbre(sousVariables,path);
        
        /*
        ///////////////////////////test////////////////////////
        
        //LinkedList<LinkedList<VarOccurence>> tempp =new LinkedList(RegoupeSousVar(resultatlist,rac));
        System.out.println("LinkedList<LinkedList<VarOccurence>> RegoupeSousVar ++++  apres");
        for (int b = 0; b < tempp.size(); b++) {
            afficheListVarOccurence(tempp.get(b));
        }
        
         
        ///////////////////////////test////////////////////////
        */
        
        
        
        return sousVariables;
        
        //condition=RacineCondition(infoMat,empRacine);
        //resultatlist=lingtest(Matrix, infoMat, condition);
    
    }
    
    public LinkedList<VarOccurence> MaxGain(LinkedList<VarGain> listGain){
        LinkedList<VarOccurence> sousVariables = new LinkedList();
        double gain=0.0; int emplacement=0;
        for (int i = 0; i < listGain.size(); i++) {
            if(i>0){
                //for (int j = 0; j < listGain.get(i).sousVariables.size(); j++) {
                    if(listGain.get(i).gain>gain && testUsed(used ,listGain.get(i).variableColumn) ){//&& testUsed(used,listGain.get(i).variableColumn)

                    gain=listGain.get(i).gain;
                    emplacement=i;
                    }
                //}
            }else{
                if(listGain.get(i).gain>gain){//&& testUsed(used,listGain.get(i).variableColumn)

                    gain=listGain.get(i).gain;
                    emplacement=i;
                }
            }
            
        }
        System.out.println("Le Gain supérieur est : "+gain);
        for (int k = 0; k < listGain.get(emplacement).sousVariables.size(); k++) {
            used.add(listGain.get(emplacement).sousVariables.get(k)); /// ajouter le variable au list des variables utilisées
        }
        
        sousVariables=listGain.get(emplacement).sousVariables;
        //System.out.println("varName = "+listGain.get(1).sousVariables.get(0).varName.Condition_name+" |||| info = "+listGain.get(1).sousVariables.get(0).info.Condition_name+" |||| column = "+listGain.get(1).sousVariables.get(0).info.emplacement);
        
        return sousVariables;
        
    }
    
    public void RacineArbre(LinkedList<VarOccurence> sousVariables, String path){
        
        LinkedList<LinkedList> Matrice = new LinkedList(TraitementFichier(path)) ;
        LinkedList<LinkedList<Verifier>> InfoMatrice = new LinkedList(InfoMatrice(Matrice)) ;
        LinkedList<LinkedList<LinkedList<Verifier>>> totaleCondition = new LinkedList(Listcondition(sousVariables,path));
        //LinkedList<VarOccurence> sousVars =new LinkedList(sousVariables);
        for (int i = 0; i < totaleCondition.size(); i++) {
            //if(testUsed(used,sousVariables.get(i))){
                ///////used.add(sousVariables.get(i)); /// ajouter le variable au list des variables utilisées
                //afficheMatVerifier(sousVariables.get(i).conds);
                //LinkedList<LinkedList<Verifier>> condition = new LinkedList();
                afficheMatVerifier(totaleCondition.get(i));
                if(sousVariables.get(i).indexGini!=0){
                LinkedList<LinkedList<Verifier>> condition = new LinkedList(totaleCondition.get(i));
                //afficheMatVerifier(condition);

                    LinkedList<VarOccurence> newSousVariables = new LinkedList();

                    newSousVariables=MaxGain(ListDeGain(RegoupeSousVar(lingtest(Matrice ,InfoMatrice ,condition),sousVariables.get(i))));
                    ///began save
                    LinkedList<VarOccurence> temparbre =new LinkedList();
                    temparbre.add(sousVariables.get(i));    //used.add(sousVariables.get(i));
                    for (int j = 0; j < newSousVariables.size(); j++) {
                        temparbre.add(newSousVariables.get(j));     //used.add(newSousVariables.get(i));
                    }
                    arbre.add(temparbre);
                    ///end save
                    RacineArbre(newSousVariables,path);
                }else{
                    LinkedList<VarOccurence> temparbre =new LinkedList();
                    temparbre.add(sousVariables.get(i));        //used.add(sousVariables.get(i));
                    arbre.add(temparbre);
                }


            //}
        }
    }
    
    public LinkedList<LinkedList<LinkedList<Verifier>>> Listcondition(LinkedList<VarOccurence> sousVariables, String path){
        
        LinkedList<LinkedList> Matrice = new LinkedList(TraitementFichier(path)) ;
        LinkedList<LinkedList<Verifier>> InfoMatrice = new LinkedList(InfoMatrice(Matrice)) ;
        LinkedList<LinkedList<LinkedList<Verifier>>> totaleCondition = new LinkedList();
        //LinkedList<VarOccurence> sousVars =new LinkedList(sousVariables);
        
        
        for (int i = 0; i < sousVariables.size(); i++) {
            
            //afficheMatVerifier(sousVariables.get(i).conds);
            //LinkedList<LinkedList<Verifier>> condition = new LinkedList();
           totaleCondition.add(MatriceCondition(sousVariables.get(i)));
           //afficheMatVerifier(sousVariables.get(1).conds);
            //afficheMatVerifier(totaleCondition.get(i));
        }
        //afficheMatVerifier(totaleCondition.get(0));
        return totaleCondition;
    }
    
    public boolean testUsed(LinkedList<VarOccurence> use , VarOccurence var){
        System.out.println("vartest : "+var.info.Condition_name+" >>>> Emplacement : "+var.info.emplacement);
        for (int i = 0; i < use.size(); i++) {
            System.out.println("used : "+use.get(i).info.Condition_name+" >>>> Emplacement : "+use.get(i).info.emplacement);
            String name = var.info.Condition_name ;
            String pp = use.get(i).info.Condition_name ;
            int ee = var.info.emplacement ;
            int rr = use.get(i).info.emplacement ;
            
            if ( name.equals(pp)  && ee == rr ) {  //&& var.varName.equals(use.get(i).varName)
                System.out.println("False");
                return false;
                
            } 
        }
        return true;
    }
    
}