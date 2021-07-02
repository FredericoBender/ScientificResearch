package FARCHD;

/**
 * <p>Title: Rule</p>
 *
 * <p>Description: Codifies a Fuzzy Rule</p>
 *
 * <p>Copyright: KEEL Copyright (c) 2008</p>
 *
 * <p>Company: KEEL </p>
 *
 * @author Written by Jesus Alcala (University of Granada) 09/02/2010
 * @version 1.0
 * @since JDK1.5
 */

	 
import java.util.*;
import org.core.Randomize;

public class Rule implements Comparable {

  int[] antecedent;
  int clas, nAnts;
  double conf, supp, wracc;
  DataBase dataBase;

  

  public Rule(Rule r) {
    this.antecedent = new int[r.antecedent.length];
    for (int k = 0; k < this.antecedent.length; k++) {
      this.antecedent[k] = r.antecedent[k];
    }

	this.clas = r.clas;
    this.dataBase = r.dataBase;
	this.conf = r.conf;
	this.supp = r.supp;
	this.nAnts = r.nAnts;
	this.wracc = r.wracc;
  }

  public Rule(DataBase dataBase) {
    this.antecedent = new int[dataBase.numVariables()];
    for (int i = 0; i < this.antecedent.length; i++)  this.antecedent[i] = -1;  // Don't care
    this.clas = -1;
    this.dataBase = dataBase;
	this.conf = 0.0;
	this.supp = 0.0;
	this.nAnts = 0;
	this.wracc = 0.0;
  }


  public Rule clone() {
    Rule r = new Rule(this.dataBase);
    r.antecedent = new int[antecedent.length];
    for (int i = 0; i < this.antecedent.length; i++) {
      r.antecedent[i] = this.antecedent[i];
    }

	r.clas = this.clas;
	r.dataBase = this.dataBase;
	r.conf = this.conf;
	r.supp = this.supp;
	r.nAnts = this.nAnts;
	r.wracc = this.wracc;

    return (r);
  }

  public void asignaAntecedente(int [] antecedent){
	this.nAnts = 0;
    for (int i = 0; i < antecedent.length; i++) {
		this.antecedent[i] = antecedent[i];
		if (this.antecedent[i] > -1)  this.nAnts++;
	}
  }

  public void setConsequent(int clas) {
    this.clas = clas;
  }


  public double matching(double[] example) {
    return (this.degreeProduct(example));
  }

  private double degreeProduct(double[] example) {
    double degree;

    degree = 1.0;
    for (int i = 0; i < antecedent.length && degree > 0.0; i++) {
        //if(antecedent[i] > 0){
          //System.out.println("in no __ "+i);
          degree *= dataBase.matching(i, antecedent[i], example[i]);
          //}
    }

    return (degree * this.conf);
  }

  public void setConfidence(double conf) {
    this.conf = conf;
  }

  public void setSupport(double supp) {
    this.supp = supp;
  }

  public void setWracc(double wracc) {
    this.wracc = wracc;
  }

  public double getConfidence() {
    return (this.conf);
  }

  public double getSupport() {
    return (this.supp);
  }

  public double getWracc() {
    return (this.wracc);
  }

  public int getClas() {
    return (this.clas);
  }

  public boolean isSubset(Rule a) {
	  if ((this.clas != a.clas) || (this.nAnts > a.nAnts))  return (false);
	  else {
		  for (int k = 0; k < this.antecedent.length; k++) {
			  if (this.antecedent[k] > -1) {
				  if (this.antecedent[k] != a.antecedent[k])  return (false);
			  }
		  }
	      return (true);
	  }
  }

  public void calculateWracc (myDataset train, ArrayList<ExampleWeight> exampleWeight) {
	  int i;
	  double n_A, n_AC, n_C, degree;
	  ExampleWeight ex;

	  n_A = n_AC = 0.0;
	  n_C = 0.0;
	  
	  for (i=0; i < train.size(); i++) {
		  ex = exampleWeight.get(i);

		  if (ex.isActive()) {
			  degree = this.matching(train.getExample(i));
			  if (degree > 0.0) {
				  degree *= ex.getWeight();
				  n_A += degree;
				  
				  if (train.getOutputAsInteger(i) == this.clas) {
					  n_AC += degree;
					  n_C += ex.getWeight();
				  }
			  }
			  else if (train.getOutputAsInteger(i) == this.clas)  n_C += ex.getWeight();
		  }
	  }

	  if ((n_A < 0.0000000001) || (n_AC < 0.0000000001) || (n_C < 0.0000000001))  this.wracc = -1.0;
	  else  this.wracc = (n_AC / n_C) * ((n_AC / n_A) - train.frecuentClass(this.clas));
  }


  public int reduceWeight (myDataset train, ArrayList<ExampleWeight> exampleWeight) {
	  int i, count;
	  ExampleWeight ex;

	  count = 0;

//	  System.out.println("Numero de ejemplos cubiertos por la regla: " + this.covered.size());

	  for (i=0; i < train.size(); i++) {
		  ex = exampleWeight.get(i);
//	      System.out.println("Reduce. Numero de ejemplos de train: " + train.size() + " Numero de ExampleWeight: " + exampleWeight.size() + " Posicion cubierta por la regla: " + c.getPos());

		  if (ex.isActive()) {
			  if (this.matching(train.getExample(i)) > 0.0) {
				  ex.incCount();
				  if ((!ex.isActive()) && (train.getOutputAsInteger(i) == this.clas))  count++;
			  }
		  }
	  }

	  return (count);
  }



  public void setLabel(int pos, int label) {
	if ((antecedent[pos] < 0) && (label > -1))  this.nAnts++;
	if ((antecedent[pos] > -1) && (label < 0))  this.nAnts--;
	this.antecedent[pos] = label;
  }

  public int compareTo(Object a) {
	  if (((Rule) a).wracc < this.wracc)  return -1;
	  if (((Rule) a).wracc > this.wracc)  return 1;
	  return 0;
  }

}
