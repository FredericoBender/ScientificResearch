package FARCHD;

/**
 * <p>Title: DataBase</p>
 *
 * <p>Description: Fuzzy Data Base</p>
 *
 * <p>Copyright: Copyright KEEL (c) 2008</p>
 *
 * <p>Company: KEEL </p>
 *
 * @author Written by Jesus Alcal� (University of Granada) 09/02/2010
 * @version 1.0
 * @since JDK1.5
 */

import org.core.Files;

public class DataBase {
  int n_variables, partitions, n_classes;
  int[] nLabels;
  boolean[] varReal;
  Fuzzy[][] dataBase;
  Fuzzy[][] dataBaseIni;
  double[] exp; //array to store the exponents used to raise the results of the fuzzy measure
  String names[];

  public DataBase() {
  }

  public DataBase(int nLabels, myDataset train) {
    double mark, value, rank, labels;
	double[][] ranks = train.returnRanks();

	this.n_variables = train.getnInputs();
	this.names = (train.names()).clone();
	this.nLabels = new int[this.n_variables];
	this.varReal = new boolean[this.n_variables];
    this.dataBase = new Fuzzy[this.n_variables][];
    this.dataBaseIni = new Fuzzy[this.n_variables][];
    this.n_classes = train.getnClasses();
    
    this.exp = new double[this.n_classes];
            
    for (int i = 0; i < this.n_classes; i++){
        this.exp[i] = 1.0;
    }

    for (int i = 0; i < this.n_variables; i++) {
	  rank = Math.abs(ranks[i][1] - ranks[i][0]);

	  this.varReal[i] = false;

	  if (train.isNominal(i))  this.nLabels[i] = ((int) rank) + 1;
	  else if (train.isInteger(i) && ((rank + 1) <= nLabels))  this.nLabels[i] = ((int) rank) + 1;
	  else {
		  this.nLabels[i] = nLabels;
		  this.varReal[i] = true;
	  }

	  this.dataBase[i] = new Fuzzy[this.nLabels[i]];
	  this.dataBaseIni[i] = new Fuzzy[this.nLabels[i]];

	  mark = rank / (this.nLabels[i] - 1.0);
	  for (int j = 0; j < this.nLabels[i]; j++) {
		  this.dataBase[i][j] = new Fuzzy();
		  this.dataBaseIni[i][j] = new Fuzzy();
		  value = ranks[i][0] + mark * (j - 1);
		  this.dataBaseIni[i][j].x0 = this.dataBase[i][j].x0 = this.setValue(value, ranks[i][0], ranks[i][1]);
		  value = ranks[i][0] + mark * j;
		  this.dataBaseIni[i][j].x1 = this.dataBase[i][j].x1 = this.setValue(value, ranks[i][0], ranks[i][1]);
		  value = ranks[i][0] + mark * (j + 1);
		  this.dataBaseIni[i][j].x3 = this.dataBase[i][j].x3 = this.setValue(value, ranks[i][0], ranks[i][1]);
		  this.dataBaseIni[i][j].y = this.dataBase[i][j].y = 1.0;
		  this.dataBase[i][j].name = "L_" + j + "(" + this.nLabels[i] + ")";
		  this.dataBaseIni[i][j].name = "L_" + j + "(" + this.nLabels[i] + ")";
	  }
    }
  }

  private double setValue(double val, double min, double tope) {
    if (val > min - 1E-4 && val < min + 1E-4)  return (min);
    if (val > tope - 1E-4 && val < tope + 1E-4)  return (tope);
    return (val);
  }

  public void decode(double[] gene) {
	  int i, j, pos;
	  double displacement, aux1;

	  pos = 0;

	  for (i=0; i < n_variables; i++) {
		  if (varReal[i]) {
			  for (j=0; j < this.nLabels[i]; j++, pos++) {
				  if (j == 0)  displacement = (gene[pos] - 0.5) * (this.dataBaseIni[i][j+1].x1 - this.dataBaseIni[i][j].x1);
				  else if (j == (this.nLabels[i]-1))  displacement = (gene[pos] - 0.5) * (this.dataBaseIni[i][j].x1 - this.dataBaseIni[i][j-1].x1);
				  else {
					  if ((gene[pos] - 0.5) < 0.0)  displacement = (gene[pos] - 0.5) * (this.dataBaseIni[i][j].x1 - this.dataBaseIni[i][j-1].x1);
					  else  displacement = (gene[pos] - 0.5) * (this.dataBaseIni[i][j+1].x1 - this.dataBaseIni[i][j].x1);
				  }
				  
				  this.dataBase[i][j].x0 = this.dataBaseIni[i][j].x0 + displacement;
				  this.dataBase[i][j].x1 = this.dataBaseIni[i][j].x1 + displacement;
				  this.dataBase[i][j].x3 = this.dataBaseIni[i][j].x3 + displacement;
			  }
		  }
	  }
          
          j=0;
          for (i=pos; i < pos+this.n_classes; i++){
              aux1 = gene[i];
              if (aux1>1.0) aux1 = (1.0/(2.0-aux1));
              this.exp[j] = aux1;
              j++;
          }
  }

  public int numVariables() {
    return (this.n_variables);
  }

  public int getnLabelsReal() {
	  int i, count;

	  count = 0;

	  for (i=0; i < n_variables; i++) {
		  if (varReal[i])  count += this.nLabels[i];
	  }

	  return (count);
  }

  public int numLabels(int variable) {
    return (this.nLabels[variable]);
  }

  public int[] getnLabels() {
    return (this.nLabels);
  }

  public double matching(int variable, int label, double value) {
	if ((variable < 0) || (label < 0))  return (1);  // Don't care
    else  return (this.dataBase[variable][label].Fuzzifica(value));
  }

  public String print_triangle(int var, int label) {
    String cadena = "";

	Fuzzy d = this.dataBase[var][label];

    cadena = d.name + ": \t" + d.x0 + "\t" + d.x1 + "\t" + d.x3 + "\n";
    return cadena;
  }

  public String print(int var, int label) {
	return (this.dataBase[var][label].getName());
  }

  public String printString() {
    String string = "@Using Triangular Membership Functions as antecedent fuzzy sets";
    for (int i = 0; i < this.n_variables; i++) {
      string += "\n\n@Number of Labels in Variable " + (i+1) + ": " + this.nLabels[i];
      string += "\n" + this.names[i] + ":\n";
      for (int j = 0; j < this.nLabels[i]; j++) {
        string += this.dataBase[i][j].name + ": (" + this.dataBase[i][j].x0 + "," + this.dataBase[i][j].x1 + "," + this.dataBase[i][j].x3 + ")\n";
      }
    }
    
    string += "\n\n@Values of the exponents of the classes:\n";
    
    for (int i = 0; i < this.n_classes; i++){
        string += "Class " + (i+1) + ": " + this.exp[i] + "\n";
    }
    
    return string;
  }

  public void saveFile(String filename) {
    String stringOut = "";
    stringOut = printString();
    Files.writeFile(filename, stringOut);
  }

}
