package FARCHD;

/**
 * <p>Title: Individual</p>
 *
 * <p>Description: This class contains the representation of the individuals of the population</p>
 *
 * <p>Copyright: Copyright KEEL (c) 2007</p>
 *
 * <p>Company: KEEL </p>
 *
 * @author Written by Jesus Alcal� (University of Granada) 09/02/2010
 * @version 1.0
 * @since JDK1.5
 */

import org.core.Randomize;

public class Individual implements Comparable{
  double[] gene;
  int[] geneR;
  double fitness, accuracy, w1;
  int n_e, nGenes, numGenLat, numGenExp;
  RuleBase ruleBase;
  double[][] gInt;//min and max de of the genes

  public Individual() {
  }

  public Individual(RuleBase ruleBase, DataBase dataBase, double w1) {
        this.ruleBase = ruleBase;
        this.w1 = w1;
	this.fitness = Double.NEGATIVE_INFINITY;
	this.accuracy = 0.0;
	this.n_e = 0;
        this.numGenLat = dataBase.getnLabelsReal();
        this.numGenExp = this.ruleBase.train.getnClasses();
	this.nGenes = this.numGenLat + this.numGenExp;

        if (this.nGenes > 0){
            this.gene = new double[this.nGenes];
            this.gInt = new double[this.nGenes][];
            for (int i = 0; i < this.numGenLat; i++){
                this.gInt[i] = new double[2];
                //lateral tuning
                this.gInt[i][0] = 0.00;
                this.gInt[i][1] = 1.00;  
            }
            for (int i = this.numGenLat; i < this.numGenLat+this.numGenExp; i++){
                //exponential cardinality
                this.gInt[i] = new double[2];
                this.gInt[i][0] = 0.01;
                this.gInt[i][1] = 1.99;
            }
        }
        this.geneR = new int[this.ruleBase.size()];
    //    for (int i = 0; i < this.geneR.length; i++)   this.geneR[i] = 1;
  }

  public Individual clone(){
        Individual ind = new Individual();
    
	ind.ruleBase = this.ruleBase;
	ind.w1 = this.w1;
	ind.fitness = this.fitness;
	ind.accuracy = this.accuracy;
	ind.n_e = this.n_e;
	ind.nGenes = this.nGenes;
        ind.numGenLat = this.numGenLat;
        ind.numGenExp = this.numGenExp;

	if (this.nGenes > 0)  {
		ind.gene = new double[this.nGenes];
                ind.gInt = new double[this.nGenes][];
		for (int j = 0; j < this.nGenes; j++){
                    ind.gene[j] = this.gene[j];
                    ind.gInt[j] = new double[2];
                    ind.gInt[j][0] = this.gInt[j][0];
                    ind.gInt[j][1] = this.gInt[j][1];
                }
	}

	ind.geneR = new int[this.geneR.length];
      //for (int j = 0; j < this.geneR.length; j++)  ind.geneR[j] = this.geneR[j];
      System.arraycopy(this.geneR, 0, ind.geneR, 0, this.geneR.length);
    
	return ind;
  }


  public void reset() {
    if (this.nGenes > 0) {
		for (int i = 0; i < this.numGenLat; i++){
                    this.gene[i] = 0.5;
                }
                for (int i = this.numGenLat; i < this.numGenLat+this.numGenExp; i++){
                    this.gene[i] = 1.0;
                }
    }
    for (int i = 0; i < this.geneR.length; i++)  this.geneR[i] = 1;
  }


  public void randomValues () {
        if (this.nGenes > 0) {
            for (int i = 0; i < this.nGenes; i++){
                this.gene[i] = this.gInt[i][0] + (this.gInt[i][1]-this.gInt[i][0])*Randomize.Rand();
            }
        }

	for (int i = 0; i < this.geneR.length; i++){
            if (Randomize.Rand() < 0.5)  this.geneR[i] = 0;
                else  this.geneR[i] = 1;
        }
  }


  public int size(){
    return this.geneR.length;
  }

  public int getnSelected() {
	  int i, count;

	  count = 0;
	  for (i=0; i < this.geneR.length; i++) {
		  if (this.geneR[i] > 0)  count++;
	  }
	  
	  return (count);
  }


  public boolean isNew () {
	  return (this.n_e == 1);
  }

  public void onNew () {
	  this.n_e = 1;
  }

  public void offNew () {
	  this.n_e = 0;
  }

  public void setw1 (double value) {
	  this.w1 = value;
  }

  public double getAccuracy() {
	  return  this.accuracy;
  }

  public double getFitness() {
	  return  this.fitness;
  }



/*************************************************************************/
/* Translations between string representation and floating point vectors */
/*************************************************************************/
private int StringRep(Individual indiv, int BITS_GEN) {
  int i, j, pos, length, count;
  long n;
  char last;
  double INCREMENTO;
  char[] stringIndiv1;
  char[] stringIndiv2;
  char[] stringAux;

  length = this.nGenes * BITS_GEN;
  stringIndiv1 = new char[length];
  stringIndiv2 = new char[length];
  stringAux = new char[length];

  INCREMENTO = 1.0 / (Math.pow(2.0, (double) BITS_GEN) - 1.0);

  pos = 0;
  for (i=0; i < this.nGenes; i++) {
      n = (int) (this.gene[i] / INCREMENTO + 0.5);

	  for (j = BITS_GEN - 1; j >=0 ; j--) {
		  stringAux[j] = (char) ('0' + (n & 1));
		  n >>= 1;
	  }
	  
	  last = '0';
	  for (j=0; j < BITS_GEN; j++, pos++) {
		  if (stringAux[j] != last)  stringIndiv1[pos] = (char) ('0' + 1);
		  else  stringIndiv1[pos] = (char) ('0' + 0);
		  last = stringAux[j];
	  }
  }

  pos = 0;
  for (i=0; i < this.nGenes; i++) {
      n = (int) (indiv.gene[i] / INCREMENTO + 0.5);

	  for (j = BITS_GEN - 1; j >=0 ; j--) {
		  stringAux[j] = (char) ('0' + (n & 1));
		  n >>= 1;
	  }
	  
	  last = '0';
	  for (j=0; j < BITS_GEN; j++, pos++) {
		  if (stringAux[j] != last)  stringIndiv2[pos] = (char) ('0' + 1);
		  else  stringIndiv2[pos] = (char) ('0' + 0);
		  last = stringAux[j];
	  }
  }

  count = 0;
  for (i=0; i < length; i++) {
	  if (stringIndiv1[i] != stringIndiv2[i])  count++;
  }

  return  count;
}


  public int distHamming(Individual ind, int BITS_GEN) {
	  int i, count;

	  count = 0;
	  for (i=0; i < this.geneR.length; i++) {
		  if (this.geneR[i] != ind.geneR[i])  count++;
	  }

      if (this.nGenes > 0)  count += StringRep(ind, BITS_GEN);

	  return (count);
  }


  public void Hux(Individual indiv) {
	 int i, dist, random, aux, nPos;
	 int [] position;

	 position = new int[this.geneR.length];
	 dist = 0;

	 for (i = 0; i < this.geneR.length; i++) {
		 if (this.geneR[i] != indiv.geneR[i]) {
			 position[dist] = i;
			 dist++;
		 }
	 }

	 nPos = dist / 2;
		 
	 for (i = 0; i < nPos; i++) {
		 random = Randomize.Randint(0, dist);

		 aux = this.geneR[position[random]];
		 this.geneR[position[random]] = indiv.geneR[position[random]];
		 indiv.geneR[position[random]] = aux;
			 
		 dist--;
			 
		 aux = position[dist];
		 position[dist] = position[random];
		 position[random] = aux;
	 }
  }

  public void xPC_BLX(Individual indiv, double d) {
		double I, A1, C1;
		int i;
		
		for (i=0; i < this.nGenes; i++) {
			I = d * Math.abs(gene[i] - indiv.gene[i]);
			
			A1 = gene[i] - I; if (A1 < this.gInt[i][0]) A1 = this.gInt[i][0];
			C1 = gene[i] + I; if (C1 > this.gInt[i][1]) C1 = this.gInt[i][1];
			gene[i] = A1 + Randomize.Rand() * (C1 - A1);
			
			A1 = indiv.gene[i] - I; if (A1 < this.gInt[i][0]) A1 = this.gInt[i][0];
			C1 = indiv.gene[i] + I; if (C1 > this.gInt[i][1]) C1 = this.gInt[i][1];
			indiv.gene[i] = A1 + Randomize.Rand() * (C1 - A1);
		}
  }


  public RuleBase generateRB() {
	  int i, bestRule;
	  RuleBase ruleBase1 = this.ruleBase.clone();

	  ruleBase1.evaluate(this.gene, this.geneR);
	  ruleBase1.setDefaultRule();

	  for (i=geneR.length - 1; i >= 0; i--) {
		  if (geneR[i] < 1)  ruleBase1.remove(i);
	  }  

	  return ruleBase1;
  }


  public void evaluate() {

//	  if (this.ruleBase.hasClassUncovered(this.geneR)) {
//		  this.fitness = Double.NEGATIVE_INFINITY;
//		  this.accuracy = 0.0;
//	  }
//	  else {
		  this.ruleBase.evaluate(this.gene, this.geneR);
		  this.accuracy = this.ruleBase.getAccuracy();

                  this.fitness = this.accuracy;
		  //this.fitness = this.accuracy - (this.w1 / (this.ruleBase.size() - this.getnSelected() + 1.0)) - (5.0 * this.ruleBase.getUncover()) - (5.0 * this.ruleBase.hasClassUncovered(this.geneR));
//		  this.fitness = this.accuracy - this.w1 * this.getnSelected();

//		  System.out.println ("Fitness: " + this.fitness);


//		  if (this.ruleBase.hasUncover())  this.fitness = Double.NEGATIVE_INFINITY;
//		  this.fitness = this.accuracy - this.w1 * this.getnSelected() - (5.0 * this.ruleBase.getUncover()) - (5.0 * this.ruleBase.hasClassUncovered(this.geneR));
//		  this.fitness = this.accuracy - this.w1 * this.getnSelected();
//	  }
  }


  public int compareTo(Object a) {
    if ( ( (Individual) a).fitness < this.fitness) {
      return -1;
    }
    if ( ( (Individual) a).fitness > this.fitness) {
      return 1;
    }
    return 0;
  }

}
