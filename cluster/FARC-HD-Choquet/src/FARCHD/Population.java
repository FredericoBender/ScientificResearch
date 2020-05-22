package FARCHD;

/**
 * <p>Title: Population</p>
 *
 * <p>Description: This class contains the population for the CHC algorithm</p>
 *
 * <p>Copyright: KEEL Copyright (c) 2010</p>
 *
 * <p>Company: KEEL </p>
 *
 * @author Written by Jesus Alcal� (University of Granada) 09/02/2010
 * @version 1.2
 * @since JDK1.5
 */

import java.util.*;
import org.core.*;

public class Population {
    ArrayList<Individual> Population;
    double alpha, w1, L, Lini;
    int n_variables, pop_size, maxTrials, nTrials, BITS_GEN;
    double best_fitness, best_accuracy;
    int [] selected;
    String evolution;
    myDataset train;
    DataBase dataBase;
    RuleBase ruleBase;

    public boolean BETTER(double a, double b) {
        return a > b;
    }

    public Population() {
    }

    public Population(myDataset train, DataBase dataBase, RuleBase ruleBase, int size, int BITS_GEN, int maxTrials, double alpha) {
        this.dataBase = dataBase;
        this.train = train;
        this.ruleBase = ruleBase;
        this.BITS_GEN = BITS_GEN;

        this.n_variables = dataBase.numVariables();
        this.pop_size = size;
        this.alpha = alpha;
        this.maxTrials = maxTrials;
        this.Lini = ((dataBase.getnLabelsReal() * BITS_GEN) + ruleBase.size()) / 4.0;
        this.L = this.Lini;
//		this.ruleBase.evaluate();
        this.w1 = this.alpha * (double) ruleBase.size();

        Population = new ArrayList<>();
        selected = new int[this.pop_size];
        evolution = "";
    }

    public void Generation() {
        init();
        this.evaluate(0);

        do {
            this.selection();
            this.crossover();
            this.evaluate(this.pop_size);
            this.elitist();
            if (!this.hasNew()) {
                this.L--;
                //this.L-=30;
                if (this.L < 0.0) {
                        System.out.println("Restart");
                        this.restart();
                }
            }
        } while (this.nTrials < this.maxTrials & this.best_fitness<100.0);
    }


    private void init() {
        Individual ind;

        ind = new Individual(this.ruleBase, this.dataBase, this.w1);
        ind.reset();
        Population.add(ind);
        for (int i = 1; i < this.pop_size; i++) {
            ind = new Individual(this.ruleBase, this.dataBase, this.w1);
            ind.randomValues();
            Population.add(ind);
        }

        this.best_fitness = 0.0;
        this.nTrials = 0;
    }


	private void evaluate (int pos) {
        for (int i = pos; i < this.Population.size(); i++)  this.Population.get(i).evaluate();
		this.nTrials += (this.Population.size() - pos);
    }


    private void selection() {
        int i, aux, random;

        for (i=0; i < this.pop_size; i++)  this.selected[i] = i;

        for (i=0; i < this.pop_size; i++) {
                random = Randomize.Randint(0, this.pop_size);
                aux = this.selected[random];
                this.selected[random] = selected[i];
                this.selected[i] = aux;
        }
    }

    private void xPC_BLX(double d, Individual son1, Individual son2) {
		son1.xPC_BLX(son2, d);
	}

    private void Hux(Individual son1, Individual son2) {
		son1.Hux(son2);
	}

    private void crossover() {
		int i;
		double dist;
		Individual dad, mom, son1, son2;

		for (i = 0; i < this.pop_size; i+=2) {
			dad = this.Population.get(this.selected[i]);
			mom = this.Population.get(this.selected[i + 1]);

			dist = (double) dad.distHamming(mom, BITS_GEN);
			dist /= 2.0;

			if (dist > this.L) {
				son1 = dad.clone();
				son2 = mom.clone();
				
				this.xPC_BLX(1.0, son1, son2);
				this.Hux(son1, son2);
				
				son1.onNew();
				son2.onNew();
				
				this.Population.add(son1);
				this.Population.add(son2);
			}
        }
    }


    private void elitist() {
        Collections.sort(this.Population);
        while (this.Population.size() > this.pop_size)  this.Population.remove(this.pop_size);

        this.best_fitness = this.Population.get(0).getFitness();
        this.evolution += "Accuracy / Fitness in the evaluacion " + this.nTrials + ": " + this.Population.get(0).getAccuracy() + " / " + this.best_fitness + "\n";

        System.out.println("Accuracy / Fitness in the evaluacion " + this.nTrials + ": " + this.Population.get(0).getAccuracy() + " / " + this.best_fitness);
    }

    public String getEvolution() {
		return (this.evolution);
	}

    private boolean hasNew() {
		int i;
		boolean state;
		Individual ind;

		state = false;
		
		for (i=0; i < this.pop_size; i++) {
			ind = this.Population.get(i);
			if (ind.isNew()) {
				ind.offNew();
				state = true;
			}
		}

		return (state);
	}


    private void restart() {
        int i, dist;
        Individual ind;

        this.w1 = 0.0;

        Collections.sort(this.Population);
        ind = this.Population.get(0).clone();
        ind.setw1(this.w1);

        this.Population.clear();
        this.Population.add(ind);

        for (i = 1; i < this.pop_size; i++) {
            ind = new Individual(this.ruleBase, this.dataBase, this.w1);
            ind.randomValues();
            Population.add(ind);
        }

        this.evaluate(0);
//        this.evaluate(1);
        this.L = this.Lini;
    }

    public RuleBase getBestRB() {
        RuleBase ruleBase1;

        Collections.sort(this.Population);
        ruleBase1 = Population.get(0).generateRB();

        return ruleBase1;
    }
}
