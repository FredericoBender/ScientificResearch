package FARCHD;

/**
 * <p>Title: Apriori</p>
 *
 * <p>Description: This class mines the frecuent fuzzy itemsets and the fuzzy classification associacion rules</p>
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


public class Apriori {
  ArrayList<Itemset> L2;
  double minsup, minconf;
  double[] minSupps;
  int nClasses, nVariables, depth, tipoFM, opFM;
  long ruleStage1;
  RuleBase ruleBase;
  RuleBase ruleBaseClase;
  myDataset train;
  DataBase dataBase;

  public Apriori() {
  }

  public Apriori(RuleBase ruleBase, DataBase dataBase, myDataset train, double minsup, double minconf, int depth, int tipoFM, int opFM) {
	  this.train = train;
	  this.dataBase = dataBase;
	  this.ruleBase = ruleBase;
	  this.minconf = minconf;
	  this.depth = depth;
	  this.nClasses = this.train.getnClasses();
	  this.nVariables = this.train.getnInputs();
          this.tipoFM = tipoFM;
          this.opFM = opFM;

	  this.L2 = new ArrayList<> ();
	  minSupps = new double[this.nClasses];
	  for (int i=0; i < this.nClasses; i++)  minSupps[i] = this.train.frecuentClass(i) * minsup;
  }


  public void generateRB () {
	  int i, j, uncover;

	  ruleStage1 = 0;

	  this.ruleBaseClase = new RuleBase(this.dataBase, this.train, this.ruleBase.getK(), this.ruleBase.getTypeInference(), this.tipoFM, this.opFM);

	  for (i=0; i < this.nClasses; i++) {
		  this.minsup = minSupps[i]; 
		  this.generateL2(i);
		  this.generateLarge (this.L2, i);
		  
		  this.ruleBaseClase.reduceRules(i);
//		  uncover = this.ruleBaseClase.hasUncoverClass(i);
//		  if (uncover > 0)  System.out.println("Ejemplos de la clase " + i + " no cubiertos por la RBC Completa: " + uncover);

		  this.ruleBase.add(this.ruleBaseClase);
		  this.ruleBaseClase.clear();
		  //System.gc();
	  } 
  }

  private void generateL2(int clas) {
	  int i, j, k, uncover;
	  Item item;
	  Itemset itemset;
	  
	  this.L2.clear();
	  itemset = new Itemset(clas);

	  for (i=0; i < this.nVariables; i++) {
		  if (this.dataBase.numLabels(i) > 1) {
			  for (j=0; j < this.dataBase.numLabels(i); j++) {
				  item = new Item(i, j);
				  itemset.add(item);
				  itemset.calculateSupports(this.dataBase, this.train);
				  if (itemset.getSupportClass() >= this.minsup)  this.L2.add(itemset.clone());
				  itemset.remove(0);
			  }
		  }
	  }

	  this.generateRules(this.L2, clas);
/*	  uncover = this.hasUncoverClass(clas);
	  if (uncover > 0)  System.out.println("Ejemplos de la clase " + clas + " no cubiertos por L2: " + uncover);
	  uncover = this.ruleBaseClase.hasUncoverClass(clas);
	  if (uncover > 0)  System.out.println("Ejemplos de la clase " + clas + " no cubiertos por la BR: " + uncover);
*/
  }


  public int hasUncoverClass(int clas) {
    int uncover;
	double degree;
	Itemset itemset;
	boolean stop;
	
	uncover = 0;
    for (int j = 0; j < train.size(); j++) {
		if (this.train.getOutputAsInteger(j) == clas) {
			stop = false;
			for (int i=0; i < L2.size() && !stop; i++) {
				itemset = L2.get(i);
				degree = itemset.degree(this.dataBase, this.train.getExample(j));
				if (degree > 0.0)  stop = true;
			}

			if (!stop)  uncover++;
		}
    }

	return uncover;
  }


  private void generateLarge (ArrayList<Itemset> Lk, int clas) {
	  int i, j, size;
	  ArrayList<Itemset> Lnew;
	  Itemset newItemset, itemseti, itemsetj;

	  size = Lk.size();

	  if (size > 1) {
		  if (((Lk.get(0)).size() < this.nVariables) && ((Lk.get(0)).size() < this.depth)) {
			  Lnew = new ArrayList<> ();

			  for (i = 0; i < size-1; i++) {
				  itemseti = Lk.get(i);
				  for (j = i+1; j < size; j++) {
					  itemsetj = Lk.get(j);
					  if (this.isCombinable(itemseti, itemsetj)) {
						  newItemset = itemseti.clone();
						  newItemset.add((itemsetj.get(itemsetj.size()-1)).clone());
						  newItemset.calculateSupports(this.dataBase, this.train);
						  if (newItemset.getSupportClass() >= this.minsup)  Lnew.add(newItemset);
					  }
				  }
				  
				  this.generateRules(Lnew, clas);
				  this.generateLarge(Lnew, clas);
				  Lnew.clear();
			      //System.gc();
			  }
		  }
	  }
  }

  private boolean isCombinable(Itemset itemseti, Itemset itemsetj) {
	  int i;
	  Item itemi, itemj;
	  Itemset itemset;

//	  if (itemseti.getClas() != itemsetj.getClas())  return (false);

	  itemi = itemseti.get(itemseti.size()-1);
	  itemj = itemsetj.get(itemseti.size()-1);
	  if (itemi.getVariable() >= itemj.getVariable())  return (false);

	  return (true);
  }

  public long getRulesStage1() {
	  return (ruleStage1);
  }

  private void generateRules(ArrayList<Itemset> Lk, int clas) {
	  int i, uncover;
	  Itemset itemset;
	  double confidence;

	  for (i=Lk.size() - 1; i >= 0; i--) {
		  itemset = Lk.get(i);
		  if (itemset.getSupport() > 0.0)  confidence = itemset.getSupportClass() / itemset.getSupport();
		  else  confidence = 0.0;
		  if (confidence > 0.4) {
			  this.ruleBaseClase.add(itemset);
			  ruleStage1++;
		  }
		  if (confidence > this.minconf)  Lk.remove(i);
//		  if (confidence > this.minconf)  this.ruleBaseClase.add(itemset);
	  }

	  if (this.ruleBaseClase.size() > 500000) {
		  this.ruleBaseClase.reduceRules(clas);
//		  uncover = this.ruleBaseClase.hasUncoverClass(clas);
//		  if (uncover > 0)  System.out.println("Ejemplos de la clase " + clas + " no cubiertos por la BR: " + uncover);
		  System.out.println("Number of rules: " + this.ruleBase.size());
		  //System.gc();
	  }
  }
}
