package FARCHD;

/**
 * <p>Title: RuleBase</p>
 *
 * <p>Description: This class contains the representation of a Rule Set</p>
 *
 * <p>Copyright: Copyright KEEL (c) 2007</p>
 *
 * <p>Company: KEEL </p>
 *
 * @author Written by Jesus Alcala (University of Granada) 09/02/2010
 * @version 1.0
 * @since JDK1.5
 */

import java.util.*;
import org.core.*;

public class RuleBase {
  ArrayList<Rule> ruleBase;
  DataBase dataBase;
  myDataset train;
  int n_variables, K, nUncover, typeInference, defaultRule, tipoFM, opFM;
  int[] nUncoverClas;
  double fitness;

  public boolean BETTER(int a, int b) {
    return  a > b;
  }

  public RuleBase() {
  }

  public RuleBase(DataBase dataBase, myDataset train, int K, int typeInference, int tipoFM, int opFM) {
    this.ruleBase = new ArrayList<> ();
    this.dataBase = dataBase;
    this.train = train;
    this.n_variables = dataBase.numVariables();
	this.fitness = 0.0;
	this.K = K;
	this.typeInference = typeInference;
	this.defaultRule = -1;
	this.nUncover = 0;
	this.nUncoverClas = new int[this.train.getnClasses()];
        this.tipoFM = tipoFM;
        this.opFM = opFM;
  }

  public RuleBase clone() {
    RuleBase br = new RuleBase();
    br.ruleBase = new ArrayList<> ();
    for (int i = 0; i < this.ruleBase.size(); i++)  br.ruleBase.add((this.ruleBase.get(i)).clone());

    br.dataBase = this.dataBase;
    br.train = this.train;
    br.n_variables = this.n_variables;
	br.fitness = this.fitness;
	br.K = this.K;
	br.typeInference = this.typeInference;
	br.defaultRule = this.defaultRule;
	br.nUncover = this.nUncover;
	br.nUncoverClas = new int[this.train.getnClasses()];
        br.tipoFM = this.tipoFM;
        br.opFM = this.opFM;
      //for (int i = 0; i < this.train.getnClasses(); i++)  br.nUncoverClas[i] = this.nUncoverClas[i];
      System.arraycopy(this.nUncoverClas, 0, br.nUncoverClas, 0, this.train.getnClasses());

	return (br);
  }


  public void add(Rule rule) {
	  this.ruleBase.add(rule);
  }

  public void add(RuleBase ruleBase) {
	  int i;

	  for (i=0; i<ruleBase.size(); i++) {
		  this.ruleBase.add(ruleBase.get(i).clone());
	  }
  }


  public void add(Itemset itemset) {
	  int i;
	  Item item;

	  int[] antecedent = new int[n_variables];
	  for (i=0; i < n_variables; i++)  antecedent[i] = -1;  // Don't care

	  for (i=0; i < itemset.size(); i++) {
		  item = itemset.get(i);
		  antecedent[item.getVariable()] = item.getValue();
	  }
	  
	  Rule r = new Rule(this.dataBase);
      r.asignaAntecedente(antecedent);
	  r.setConsequent(itemset.getClas());
	  r.setConfidence(itemset.getSupportClass() / itemset.getSupport());
	  r.setSupport(itemset.getSupportClass());
      this.ruleBase.add(r);
  }

  public Rule get(int pos) {
	  return (this.ruleBase.get(pos));
  }

  public int size() {
	  return (this.ruleBase.size());
  }

  public void sort () {
	  Collections.sort(this.ruleBase);
  }

  public Rule remove(int pos) {
	  return (this.ruleBase.remove(pos));
  }

  public void clear() {
	  this.ruleBase.clear();
	  this.fitness = 0.0;
  }

  public int getTypeInference() {
    return  (this.typeInference);
  }

  public double getAccuracy() {
    return  (this.fitness);
  }

  public void setDefaultRule() {
	  int i, bestRule;

	  bestRule = 0;
/*	  if (this.nUncover > 0) {
		  for (i=1; i < this.train.getnClasses(); i++) {
			  if (this.nUncoverClas[bestRule] < this.nUncoverClas[i])  bestRule = i;
		  }
	  }
	  else {
*/		  for (i=1; i < this.train.getnClasses(); i++) {
			  if (this.train.numberInstances(bestRule) < this.train.numberInstances(i))  bestRule = i;
		  }
//	  }

	  this.defaultRule = bestRule;
  }


  public boolean hasUncover() {
    return  (this.nUncover > 0);
  }

  public int getUncover() {
    return  (this.nUncover);
  }

  public int getK() {
    return  (this.K);
  }
/*
  public int hasUncoverClass(int clas) {
    int uncover;
	int prediction;
	
	uncover = 0;
    for (int j = 0; j < train.size(); j++) {
		if (this.train.getOutputAsInteger(j) == clas) {
			prediction = this.FRM(train.getExample(j));
			if (prediction < 0)  uncover++;
		}
    }

	return uncover;
  }
*/

    public void evaluate(){
        int nHits, prediction;//,cont;

        //prediction = new int[2];
        
        //cont = 0;
        nHits = 0;
        this.nUncover = 0;
        for (int j = 0; j < this.train.getnClasses(); j++) {
            this.nUncoverClas[j] = 0;
        }

        for (int j = 0; j < train.size(); j++) {
            prediction = this.FRM(train.getExample(j));
            if (this.train.getOutputAsInteger(j) == prediction) {
                nHits++;
            }
            if (prediction < 0) {
                this.nUncover++;
                this.nUncoverClas[this.train.getOutputAsInteger(j)]++;
            }
           // cont += prediction[1];
        }
       // System.out.println("Porcentaje de ejemplos con solo una regla disparada" + (double) cont / train.size());
        this.fitness = (100.0 * nHits) / (1.0 * this.train.size());
    }
    
     public double evaluate(myDataset ds){
        int nHits, prediction;//,cont;

        //prediction = new int[2];
        
        //cont = 0;
        nHits = 0;
        this.nUncover = 0;
        for (int j = 0; j < ds.getnClasses(); j++) {
            this.nUncoverClas[j] = 0;
        }

        for (int j = 0; j < ds.size(); j++) {
            prediction = this.FRM(ds.getExample(j));
            if (ds.getOutputAsInteger(j) == prediction) {
                nHits++;
            }
            if (prediction < 0) {
                this.nUncover++;
                this.nUncoverClas[ds.getOutputAsInteger(j)]++;
            }
           // cont += prediction[1];
        }
       // System.out.println("Porcentaje de ejemplos con solo una regla disparada" + (double) cont / train.size());
        return (100.0 * nHits) / (1.0 * ds.size());
    }



  public void evaluate(double[] gene, int[] selected){
    int nHits, prediction;// cont;

    //prediction = new int[2];
    
	this.dataBase.decode(gene);
	
        //cont = 0;
	nHits = 0;
	this.nUncover = 0;
	for (int j = 0; j < this.train.getnClasses(); j++)  this.nUncoverClas[j] = 0;

	for (int j = 0; j < train.size(); j++) {
            prediction = this.FRM(train.getExample(j), selected);
            if (this.train.getOutputAsInteger(j) == prediction)  nHits++;
                if (prediction < 0) {
                        this.nUncover++;
                        this.nUncoverClas[this.train.getOutputAsInteger(j)]++;
                }
            //cont += prediction[1];
        }
        //System.out.println("Porcentaje de ejemplos con solo una regla disparada" + (double)cont/train.size());
	this.fitness = (100.0 * nHits) / (1.0 * this.train.size());
  }

  public int FRM(double[] example){
    if (this.typeInference == 0){
        return FRM_WR(example);

    }

    else if (this.typeInference == 2){ 
        return FRM_Choquet(example);
    }    
    
    else if (this.typeInference == 3){
        //sSystem.out.println("INFERENCIA 3 ");
        return FRM_Prob_Sum(example);    
    }
    
    else{  
        return FRM_AC(example);
    }
  }

  public int FRM(double[] example, int[] selected){
    if (this.typeInference == 0){
        return FRM_WR(example, selected);

    }
        
    else if (this.typeInference == 2){ 
        return FRM_Choquet(example, selected);
    }
    
    else if (this.typeInference == 3){
        //System.out.println("INFERENCIA 3");
        return FRM_Prob_Sum(example, selected);
    } 
    
    else{  
        return FRM_AC(example, selected);
    }
  }

  
  private int FRM_Choquet(double[] example, int[] selected){
      int i, clas, cont;//, cont1;
    Double maxDegree;
    Double degree, agregated;

    ArrayList<ArrayList<Double>> gAsoc = new ArrayList<>();
    ArrayList<ArrayList<Integer>> firedRules = new ArrayList<>();
    
    //clas = new int[2];
    clas = defaultRule;

    //degreeClass = new double[this.train.getnClasses()][2];
    for (i=0; i < this.train.getnClasses(); i++){
        gAsoc.add(new ArrayList<Double>());
        firedRules.add(new ArrayList<Integer>());
    }

    for (i = 0; i < this.ruleBase.size(); i++) {
        if (selected[i] > 0) {    
            Rule r = this.ruleBase.get(i);
            degree = r.matching(example);
            if (degree > 0) {
                gAsoc.get(r.getClas()).add(degree);
                firedRules.get(r.getClas()).add(i);
            }
        }
    }

    maxDegree = 0.0;
    cont = 0;
    //cont1 = 0;
    for (i = 0; i < this.train.getnClasses(); i++) {
      //agregated[0] = agChoquet(gAsocLow.get(i), firedRules.get(i));
      //agregated[1] = agChoquet(gAsocUp.get(i), firedRules.get(i));
      if (!gAsoc.get(i).isEmpty()){
        agregated = agChoquet(gAsoc.get(i), firedRules.get(i), this.dataBase.exp[i]);
          /*asL.add(0.8);
          asL.add(0.2);
          asL.add(0.3);
          asU.add(0.9);
          asU.add(0.7);
          asU.add(0.5);
          nR.add(1);
          nR.add(2);
          nR.add(3);
          agregated = agChoquet(asL, asU, nR);*/
      }else{
          agregated = 0.0;
          //cont1 += 1;
      }
      if (agregated > maxDegree) {
            maxDegree = agregated;
            clas = i;
            cont = 0;
      }
      else{
          if (Objects.equals(agregated, maxDegree))  cont++;
      }
    }

    if (cont > 0)  clas = defaultRule;
   // if (cont1==(this.train.getnClasses()-1)) clas[1]=1;
    return clas;
  }
  
  private int FRM_Choquet(double[] example){
    int i, clas, cont;//, cont1;
    Double maxDegree;
    Double degree, agregated;
    double threshold;
    
    ArrayList<ArrayList<Double>> gAsoc = new ArrayList<>();
    ArrayList<ArrayList<Integer>> firedRules = new ArrayList<>();
    
    //clas = new int[2];
    clas = defaultRule;

    //degreeClass = new double[this.train.getnClasses()][2];
    for (i=0; i < this.train.getnClasses(); i++){
        gAsoc.add(new ArrayList<Double>());
        firedRules.add(new ArrayList<Integer>()); 
    }

    for (i = 0; i < this.ruleBase.size(); i++) {
            Rule r = this.ruleBase.get(i);

            degree = r.matching(example);
            
            if (degree > 0) {
                gAsoc.get(r.getClas()).add(degree);
                firedRules.get(r.getClas()).add(i);
            }            
    }

    maxDegree = 0.0;
    cont = 0;
    //cont1 = 0;
    for (i = 0; i < this.train.getnClasses(); i++) {
      //agregated[0] = agChoquet(gAsocLow.get(i), firedRules.get(i));
      //agregated[1] = agChoquet(gAsocUp.get(i), firedRules.get(i));
      if (!gAsoc.get(i).isEmpty()){
        agregated = agChoquet(gAsoc.get(i), firedRules.get(i), this.dataBase.exp[i]);//this.dataBase.exp[i]);

      }else{
          agregated = 0.0;
          //cont1 += 1;
      }
      if (agregated > maxDegree) {
            maxDegree = agregated;
            clas = i;
            cont = 0;
      }
      else{
          if (Objects.equals(agregated, maxDegree))  cont++;
      }
    }

	if (cont > 0)  clas = defaultRule;
        //if (cont1==(this.train.getnClasses()-1)) clas[1]=1;
    return clas;
  }
    
  private Double agChoquet(ArrayList<Double> asociations, ArrayList<Integer> firRul, double exp){
      Double agr;
      double valorFuzzyMeasure, sumando, suma;
      double w[] = new double[asociations.size()];
      int jotaDirac;
      double sumaBi = 0;
      Object valores[],reglas[];
      ArrayList<Double> valAux = new ArrayList<>();
      ArrayList<Integer> regAux = new ArrayList<>();
      //ArrayList<Integer> regAuxIntChoquet = new ArrayList<>();

      valores = asociations.toArray();
      reglas = firRul.toArray();

      //PARA ORDENAR LOS VALORES A AGREGAR
      List<Integer> indices = sortValues(asociations);

      Collections.sort(asociations);

      for(int p = 0; p < valores.length; p++) {
            valAux.add((Double)valores[indices.get(p)]);
            regAux.add((Integer)reglas[indices.get(p)]);
      }

      //for the delta of Dirac's fuzzy measure
      jotaDirac = regAux.get((int)Math.floor(asociations.size()/2));
      
      if ((this.tipoFM == 3) || (this.tipoFM == 4)){
            //for both owa and weighted mean fuzzy measures
            Random r = new Random(123456789);
            //Randomize.setSeed(123456789);
            suma = 0.0;
            for (int i = 0; i < asociations.size(); i++){
                w[i] = r.nextDouble();//Randomize.Rand();
                suma += w[i];
            }
            for (int i = 0; i < asociations.size(); i++){
                w[i] /= suma;
            }
      }
      
      else if((this.tipoFM == 6) || (this.tipoFM == 7) || (this.tipoFM == 8) || (this.tipoFM == 9)){
           for (int i = 0; i < asociations.size(); i++){
               sumaBi += asociations.get(i);
           }
      }
      sumando = 0.0;
      regAux.remove(0);
      agr = valAux.get(0);
      double x, y;
      
      double lambda=0;
      if(this.tipoFM == 50){
        Sugeno sugeno = new Sugeno();    
        lambda = sugeno.lambda(valAux);
      }

      for (int i=1; i<valAux.size(); i++){            
          x = valAux.get(i) - valAux.get(i-1);
          y = fuzzyMeasure(regAux, indices, i, jotaDirac, w, exp, asociations, lambda, valAux);
          //y=sugeno.calcula(lambda,valAux.get(i),valAux.get(i-1));
          if (this.opFM == 1){
              //producto
              sumando = (x * y);
          }
          
          else if (this.opFM == 2){
             //COPULA Minimun
             sumando = Math.min(valAux.get(i), y) - Math.min(valAux.get(i-1), y);
          }
          
          else if (this.opFM == 3){
              //Hamacher product
              if ((x == 0.0) && (y==0.0)){
                  sumando = 0.0;
              }else{
                  sumando = (x * y)/(x + y - (x * y));
              }
          }
        else if (this.opFM == 4){
            //GM - FBPC
            sumando = Math.sqrt(valAux.get(i) * y) - (valAux.get(i-1) * Math.pow(y, 2));
        }        
        else if (this.opFM == 5){
            //GM - Luk
            sumando = Math.sqrt(valAux.get(i) * y) - Math.max(0, valAux.get(i-1) + y -1);
        }
        else if (this.opFM == 6){
            //FNA
            if(x < y){
                sumando = x;
            }
            else{
                sumando = Math.min(x/2, y);
            }
            sumando = Math.min(1, sumando);
        }        
        else {
            //FNA2
            if(x == 0){
                sumando =0;
            }
            else if((x > 0) && (x <= y)){
                sumando = (x + y)/2;
            }
            else{
                sumando = Math.min(x/2, y);
            }
            sumando = Math.min(1, sumando);
        }

          agr += sumando;
          //regAuxIntChoquet.add(regAux.get(0));
          regAux.remove(0);
      }
      
      return agr;
}
    
  private double fuzzyMeasure(ArrayList<Integer> rulesIndex, List<Integer> indices, int indAg, int jotaDirac, double w[], double exp, ArrayList<Double> asociations, double lambda, ArrayList<Double> valAux){
      double measure;
      double wi = 0;
      int numFiredRules = asociations.size();
      //double w[] = new double[numFiredRules];
      
      measure = 0.0;
      if (this.tipoFM == 1){
            measure = (double) rulesIndex.size()/numFiredRules;//cardinalidad
      }
      else if (this.tipoFM == 2){
            //delta Dirac
            if (rulesIndex.contains(jotaDirac)) 
                measure = 1.0;
            else
                measure = 0.0;
      }
      else if (this.tipoFM == 3){
          //weighted mean
            for (int j = indAg; j<numFiredRules;j++)
                measure += w[indices.get(j)];
      }
      else if (this.tipoFM == 4){
            //OWA
            for (int j = numFiredRules-1; j>indAg;j--)
                measure += w[indices.get(j)];
      }
      else if (this.tipoFM == 50){   
            //Sugeno
          Sugeno sugeno = new Sugeno();
          measure = sugeno.calcula(lambda,valAux.get(indAg-1),valAux.get(indAg));
      }
      else {
          //CardGA
          measure = Math.pow((double) rulesIndex.size()/numFiredRules, exp);//cardinalidad GA
       }
 
      //System.out.println("measure = "+measure);
      return measure;
  }

    
  private List<Integer> sortValues(ArrayList<Double> valores){
      TreeMap<Double, List<Integer>> map = new TreeMap<>();
        for(int p = 0; p < valores.size(); p++) {
            //aux[p] = (Double)ordenado[p];
            List<Integer> ind = map.get(valores.get(p));
            if(ind == null){
                ind = new ArrayList<>();
                map.put(valores.get(p), ind);
            }
            ind.add(p);
        }
        // Now flatten the list
       List<Integer> indices = new ArrayList<Integer>();
        for(List<Integer> arr : map.values()) {
            indices.addAll(arr);
        }
        
        return indices;
  }
    
  private int  FRM_WR(double[] example, int[] selected) {
    int clas;
    double max, degree;
		
	max = 0.0;
        //clas = new int[2];
	clas = defaultRule;

	for (int i = 0; i < this.ruleBase.size(); i++) {
		if (selected[i] > 0) {
			Rule r = this.ruleBase.get(i);
			degree = r.matching(example);
			
			if (degree > max) {
				max = degree;
				clas = r.getClas();
			}
		}
	}

    return clas;
  }


  private int FRM_WR(double[] example) {
    int clas;
    double max, degree;
		
	max = 0.0;
        //clas = new int[2];
	clas = defaultRule;

      /*for (int i = 0; i < this.ruleBase.size(); i++) {
      Rule r = this.ruleBase.get(i);
      degree = r.matching(example);
      if (degree > max) {
      max = degree;
      clas = r.getClas();
      }
      }*/
      for (Rule r : this.ruleBase) {
          degree = r.matching(example);
          
          if (degree > max) {
              max = degree;
              clas = r.getClas();
          }
      }

    return clas;
  }


  private int FRM_AC(double[] example, int[] selected) {
    int i, clas, cont;
	double degree, maxDegree;
	double[] degreeClass;
        
       // clas = new int[2];
	clas = defaultRule;

    degreeClass = new double[this.train.getnClasses()];
	for (i=0; i < this.train.getnClasses(); i++)  degreeClass[i] = 0.0;

	for (i = 0; i < this.ruleBase.size(); i++) {
		if (selected[i] > 0) {
			Rule r = this.ruleBase.get(i);
			
			degree = r.matching(example);
			degreeClass[r.getClas()] += degree;
		}
    }

    maxDegree = 0.0;
	cont = 0;
    for (i = 0; i < this.train.getnClasses(); i++) {
      if (degreeClass[i] > maxDegree) {
        maxDegree = degreeClass[i];
        clas = i;
		cont = 0;
      }
	  else if (degreeClass[i] == maxDegree)  cont++;
    }

	if (cont > 0)  clas = defaultRule;
    return clas;
  }


  private int FRM_AC(double[] example) {
    int i, clas, cont;
	double degree, maxDegree;
	double[] degreeClass;

       // clas = new int[2];
	clas = defaultRule;

    degreeClass = new double[this.train.getnClasses()];
	for (i=0; i < this.train.getnClasses(); i++)  degreeClass[i] = 0.0;

	for (i = 0; i < this.ruleBase.size(); i++) {
		Rule r = this.ruleBase.get(i);
			
		degree = r.matching(example);
		degreeClass[r.getClas()] += degree;
    }

    maxDegree = 0.0;
	cont = 0;
    for (i = 0; i < this.train.getnClasses(); i++) {
      if (degreeClass[i] > maxDegree) {
        maxDegree = degreeClass[i];
        clas = i;
		cont = 0;
      }
	  else if (degreeClass[i] == maxDegree)  cont++;
    }

	if (cont > 0)  clas = defaultRule;
    return clas;
  }

  
  ///////////////////////////////////////////////////////PROB_SUM/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  
  private int FRM_Prob_Sum(double[] example, int[] selected) {
      //System.out.println("==================================== PROB_SUM(double[] example, int[]selected) ============================================");  
      
      int i, clas, cont;//, cont1;
        Double maxDegree, sum;
        Double degree;
        double[] degreeClass;
        
        ArrayList<ArrayList<Double>> gAsoc = new ArrayList<>();
       
       
        clas = defaultRule;
        degreeClass = new double[this.train.getnClasses()];
        
        for (i=0; i < this.train.getnClasses(); i++){
            gAsoc.add(new ArrayList<Double>());
        }
        
        //System.out.println("gAsoc.size = "+gAsoc.size());

        for (i = 0; i < this.ruleBase.size(); i++) {
                if (selected[i] > 0) {    
                    Rule r = this.ruleBase.get(i);
                    degree = r.matching(example);
            
                    if (degree > 0) {
                        gAsoc.get(r.getClas()).add(degree);
                        //firedRules.get(r.getClas()).add(i);
                    }
                }
        }
        //ASSUMINDO QUE TENHA ELEMENTOS NA LISTA
        for (int j = 0; j < gAsoc.size(); j++) {
            //sum = gAsoc.get(j).get(0) + gAsoc.get(j).get(1) - gAsoc.get(j).get(0) * gAsoc.get(j).get(1); 
            if (!gAsoc.get(j).isEmpty()){
                //System.out.println("SEGUNDO FOR gAsoc("+j+")");
                sum = gAsoc.get(j).get(0);
                for (int k = 1; k < gAsoc.get(j).size(); k++) {
                    sum = sum + gAsoc.get(j).get(k) - sum * gAsoc.get(j).get(k);
                }
            }
            else{
                sum = 0.0;
            }
            degreeClass[j] = sum;
        }
        
        maxDegree = 0.0;
        cont = 0;
        for (i = 0; i < this.train.getnClasses(); i++) {
            if (degreeClass[i] > maxDegree) {
                maxDegree = degreeClass[i];
                clas = i;
		cont = 0;
            }
            else if (degreeClass[i] == maxDegree){
                cont++;
            }
        }
	if (cont > 0)  clas = defaultRule;
        
        return clas;
  }
  
  private int FRM_Prob_Sum(double[] example) {
       //System.out.println("===============22222222222222222============= PROB_SUM(double[] example, int[]selected) ============================================");  

        int i, clas, cont;//, cont1;
        Double maxDegree, sum;
        Double degree;
        double[] degreeClass;
        
        ArrayList<ArrayList<Double>> gAsoc = new ArrayList<>();
        //ArrayList<ArrayList<Integer>> firedRules = new ArrayList<>();

        //clas = new int[2];
        clas = defaultRule;
        degreeClass = new double[this.train.getnClasses()];
        
        //degreeClass = new double[this.train.getnClasses()][2];
        for (i=0; i < this.train.getnClasses(); i++){
            gAsoc.add(new ArrayList<Double>());
            //firedRules.add(new ArrayList<Integer>()); 
        }

        for (i = 0; i < this.ruleBase.size(); i++) {
                Rule r = this.ruleBase.get(i);

                degree = r.matching(example);
                if (degree > 0) {
                    gAsoc.get(r.getClas()).add(degree);
                    //firedRules.get(r.getClas()).add(i);
                }
        }

        //ASSUMINDO QUE TENHA ELEMENTOS NA LISTA
        for (int j = 0; j < gAsoc.size(); j++) {
            //sum = gAsoc.get(j).get(0) + gAsoc.get(j).get(1) - gAsoc.get(j).get(0) * gAsoc.get(j).get(1); 
            if (!gAsoc.get(j).isEmpty()){
                sum = gAsoc.get(j).get(0);
                for (int k = 1; k < gAsoc.get(j).size(); k++) {
                    sum = sum + gAsoc.get(j).get(k) - sum * gAsoc.get(j).get(k);
                }
            }
            else{
                sum = 0.0;
            }
            degreeClass[j] = sum;
        }
        
        maxDegree = 0.0;
        cont = 0;
        for (i = 0; i < this.train.getnClasses(); i++) {
            if (degreeClass[i] > maxDegree) {
                maxDegree = degreeClass[i];
                clas = i;
		cont = 0;
            }
            else if (degreeClass[i] == maxDegree){
                cont++;
            }
        }
	if (cont > 0)  clas = defaultRule;
        
        return clas;
  }
 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 

  
  
  public int hasClassUncovered (int[] selected) {
	  int i, count;
	  int[] cover;
	  
	  cover = new int[this.train.getnClasses()];
	  for (i=0; i < cover.length; i++) {
		  if (this.train.numberInstances(i) > 0)  cover[i] = 0;
		  else  cover[i] = 1;
	  }
	  
	  for (i = 0; i < this.ruleBase.size(); i++) {
		  if (selected[i] > 0) {
			  cover[this.ruleBase.get(i).getClas()]++;
		  }
	  }

	  count = 0;
	  for (i=0; i < cover.length; i++) {
		  if (cover[i] == 0)  count++;
	  }

	  return count;
  }


  public void reduceRules(int clas) {
	  ArrayList<ExampleWeight> exampleWeight;
	  int i, posBestWracc, nExamples, nRuleSelect; 
	  double bestWracc;
	  int[] selected;
	  Rule rule;

	  exampleWeight = new ArrayList<> ();
	  for (i=0; i < this.train.size(); i++)  exampleWeight.add(new ExampleWeight(this.K));  

	  selected = new int[this.ruleBase.size()];
	  for (i=0; i < this.ruleBase.size(); i++)  selected[i] = 0;

//	  for (i=0; i < this.ruleBase.size(); i++)  this.ruleBase.get(i).iniCover(this.train);

	  nExamples = this.train.numberInstances(clas);
	  nRuleSelect = 0;

	  System.out.println("Entra en reducir reglas para la clase : " + clas + " con reglas: " + this.ruleBase.size());


//	  uncover = this.hasUncoverClass(clas);
//	  if (uncover > 0)  System.out.println("Entra en reduce Reglas para la clase " + clas + " no cubiertos por la BR: " + uncover);

	  do {
		  bestWracc = -1.0;
		  posBestWracc = -1;
		  
		  for (i=0; i < this.ruleBase.size(); i++) {
			  if (selected[i] == 0) {
				  rule = this.ruleBase.get(i);
				  rule.calculateWracc(this.train, exampleWeight);

				  if (rule.getWracc() > bestWracc) {
					  bestWracc = rule.getWracc();
					  posBestWracc = i;
				  }
			  }
		  }

		  if (posBestWracc > -1) {
			  selected[posBestWracc] = 1;
			  nRuleSelect++;

			  rule = this.ruleBase.get(posBestWracc);
			  nExamples -= rule.reduceWeight(this.train, exampleWeight);
		  }
	  } while ((nExamples > 0) && (nRuleSelect < this.ruleBase.size()) && (posBestWracc > -1));

	  System.out.println("Sale de reduce Reglas para la clase: Numero examples: " + nExamples + "/" + this.train.numberInstances(clas) + " Numero de reglas: " + nRuleSelect + "/" + this.ruleBase.size() + " Valor de K: " + this.K);

	  for (i=this.ruleBase.size() - 1; i >= 0; i--) {
		  if (selected[i] == 0)  this.ruleBase.remove(i);
	  }

//	  uncover = this.hasUncoverClass(clas);
//	  if (uncover > 0)  System.out.println("Sale de reduce Reglas para la clase " + clas + " no cubiertos por la BR: " + uncover);

	  exampleWeight.clear();
	  //System.gc();
  }


  public String printString() {
    int i, j, ant;
    String [] names = this.train.names();
    String [] clases = this.train.clases();
    String stringOut = "";

	ant = 0;
    for (i = 0; i < this.ruleBase.size(); i++) {
      Rule r = this.ruleBase.get(i);
      stringOut += (i+1)+": ";
      for (j = 0; j < n_variables && r.antecedent[j] < 0; j++);
	  if (j < n_variables && r.antecedent[j] >= 0) {
		  stringOut += names[j]+" IS " + r.dataBase.print(j,r.antecedent[j]);
		  ant++;
	  }
      for (j++; j < n_variables-1; j++) {
		if (r.antecedent[j] >=0) {
			stringOut += " AND " + names[j]+" IS " + r.dataBase.print(j,r.antecedent[j]);
		    ant++;
		}
      }
      if (j < n_variables && r.antecedent[j] >= 0)  {
		  stringOut += " AND " + names[j]+" IS " + r.dataBase.print(j,r.antecedent[j]) + ": " + clases[r.clas];
  		  ant++;
	  }
	  else  stringOut += ": " + clases[r.clas];

	  stringOut += " CF: " + r.getConfidence() + "\n";
    }

	stringOut += "\n\n";
    stringOut += "@supp and CF:\n\n";
    for (i = 0; i < this.ruleBase.size(); i++) {
    	Rule rule = this.ruleBase.get(i);
    	stringOut += (i+1)+": ";
    	stringOut += "supp: " + rule.getSupport() + " AND CF: " + rule.getConfidence() + "\n";
	}

    stringOut = "@Number of rules: " + this.ruleBase.size() + " Number of Antecedents by rule: " + ant * 1.0 / this.ruleBase.size() + "\n\n" + stringOut;
	return (stringOut);
  }

  public void saveFile(String filename) {
    String stringOut = "";
    stringOut = printString();
    Files.writeFile(filename, stringOut);
  }

}
