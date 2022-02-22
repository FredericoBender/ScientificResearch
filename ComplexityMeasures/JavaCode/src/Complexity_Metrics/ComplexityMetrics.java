// 
// Decompiled by Procyon v0.5.36
// 

package Complexity_Metrics;

import java.io.PrintWriter;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import keel.Dataset.Attributes;
import java.util.StringTokenizer;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Random;
import keel.Dataset.InstanceSet;

public class ComplexityMetrics
{
    private InstanceSet dSet;
    private int numberOfExamples;
    private int numberOfAttributes;
    private double[][] example;
    private int[] classOfExample;
    private int numberOfClasses;
    private int[] numExamplesPerClass;
    private double[][][] examplesPerClass;
    private int[][] indexExamplesPerClass;
    private Statistics stats;
    private String datasetName;
    private String outputFileName;
    private double F1;
    private double F2;
    private double F3;
    private double N1;
    private double N2;
    private double N3;
    private double N4;
    private double L1;
    private double L2;
    private double L3;
    private double T1;
    private double T2;
    private Random rndObject;
    private double seed;
    private boolean computeF1;
    private boolean computeF2;
    private boolean computeF3;
    private boolean computeN1;
    private boolean computeN2;
    private boolean computeN3;
    private boolean computeN4;
    private boolean computeL1;
    private boolean computeL2;
    private boolean computeL3;
    private boolean computeT1;
    private boolean computeT2;
    final double C = 0.05;
    final double TOLERANCE = 0.001;
    final double EPSILON = 0.001;
    
    ComplexityMetrics(final String configFileName, String dataSetName, String class1, String class2) {
        System.out.println(" > Creating the complexity metrics object with the configuration file: " + configFileName);
        this.numExamplesPerClass = null;
        this.seed = 1.0;
        this.rndObject = new Random();
        final boolean computeF1 = true;
        this.computeN4 = computeF1;
        this.computeN3 = computeF1;
        this.computeN2 = computeF1;
        this.computeN1 = computeF1;
        this.computeF3 = computeF1;
        this.computeF2 = computeF1;
        this.computeF1 = computeF1;
        final boolean computeL1 = true;
        this.computeT2 = computeL1;
        this.computeT1 = computeL1;
        this.computeL3 = computeL1;
        this.computeL2 = computeL1;
        this.computeL1 = computeL1;
        final double n = -1.0;
        this.T2 = n;
        this.T1 = n;
        this.L3 = n;
        this.L2 = n;
        this.L1 = n;
        this.N4 = n;
        this.N3 = n;
        this.N2 = n;
        this.N1 = n;
        this.F3 = n;
        this.F2 = n;
        this.F1 = n;
        final String s = null;
        this.datasetName = s;
        this.outputFileName = s;
        this.parseConfigFile(configFileName, dataSetName, class1, class2);
        this.rndObject.setSeed((long)this.seed);
        this.dSet = new InstanceSet();
        try {
            this.dSet.readSet(this.datasetName, true);
        }
        catch (Exception e) {
            System.out.println("  > The data set could not be correctly loaded ");
            e.printStackTrace();
        }
        this.extractDatasetInformation();
        (this.stats = new Statistics(this.dSet, this.numberOfClasses)).run(this.example, this.classOfExample, this.numberOfExamples, this.numberOfAttributes);
    }
    
    ComplexityMetrics(final String configFileName, String dataSetName) {
        System.out.println(" > Creating the complexity metrics object with the configuration file: " + configFileName);
        this.numExamplesPerClass = null;
        this.seed = 1.0;
        this.rndObject = new Random();
        final boolean computeF1 = true;
        this.computeN4 = computeF1;
        this.computeN3 = computeF1;
        this.computeN2 = computeF1;
        this.computeN1 = computeF1;
        this.computeF3 = computeF1;
        this.computeF2 = computeF1;
        this.computeF1 = computeF1;
        final boolean computeL1 = true;
        this.computeT2 = computeL1;
        this.computeT1 = computeL1;
        this.computeL3 = computeL1;
        this.computeL2 = computeL1;
        this.computeL1 = computeL1;
        final double n = -1.0;
        this.T2 = n;
        this.T1 = n;
        this.L3 = n;
        this.L2 = n;
        this.L1 = n;
        this.N4 = n;
        this.N3 = n;
        this.N2 = n;
        this.N1 = n;
        this.F3 = n;
        this.F2 = n;
        this.F1 = n;
        final String s = null;
        this.datasetName = s;
        this.outputFileName = s;
        this.parseConfigFile(configFileName, dataSetName, "0", "0");
        this.rndObject.setSeed((long)this.seed);
        this.dSet = new InstanceSet();
        try {
            this.dSet.readSet(this.datasetName, true);
        }
        catch (Exception e) {
            System.out.println("  > The data set could not be correctly loaded ");
            e.printStackTrace();
        }
        this.extractDatasetInformation();
        (this.stats = new Statistics(this.dSet, this.numberOfClasses)).run(this.example, this.classOfExample, this.numberOfExamples, this.numberOfAttributes);
    }
    

    private void parseConfigFile(final String configFileName, String dataSetName, String class1, String class2) {
        try {
            System.out.println(" > Parsing the file: " + configFileName);
            final BufferedReader fin = new BufferedReader(new FileReader(configFileName));
            for (String line = fin.readLine(); line != null; line = fin.readLine()) {
                if (line.length() != 0) {
                    final StringTokenizer st = new StringTokenizer(line);
                    final String varName = st.nextToken();
                    st.nextToken();
                    if (!varName.equalsIgnoreCase("algorithm")) {
                        if (varName.equalsIgnoreCase("inputdata")) {
                            final String aux = st.nextToken();
                            //this.datasetName = aux.substring(1, aux.length() - 1);
                            //this.datasetName = "./data/iris_1_vs_2.dat";
                            if(class1=="0"){
                                this.datasetName = "./data/" + dataSetName + ".dat";
                            }
                            else{
                                this.datasetName = "./data/" + dataSetName + "_" + class1 + "_vs_" + class2 + ".dat";
                            }
                            System.out.println("   > Input data set:  " + this.datasetName);
                        }
                        else if (varName.equalsIgnoreCase("outputdata")) {
                            final String aux = st.nextToken();
                            this.outputFileName = aux.substring(1, aux.length() - 1);
                            System.out.println("   > Output data set:  " + this.outputFileName);
                        }
                        else if (varName.equalsIgnoreCase("runF1")) {
                            this.computeF1 = Boolean.parseBoolean(st.nextToken());
                            System.out.println("   > Run F1:  " + this.computeF1);
                        }
                        else if (varName.equalsIgnoreCase("runF2")) {
                            this.computeF2 = Boolean.parseBoolean(st.nextToken());
                            System.out.println("   > Run F2:  " + this.computeF2);
                        }
                        else if (varName.equalsIgnoreCase("runF3")) {
                            this.computeF3 = Boolean.parseBoolean(st.nextToken());
                            System.out.println("   > Run F3:  " + this.computeF3);
                        }
                        else if (varName.equalsIgnoreCase("runN1")) {
                            this.computeN1 = Boolean.parseBoolean(st.nextToken());
                            System.out.println("   > Run N1:  " + this.computeN1);
                        }
                        else if (varName.equalsIgnoreCase("runN2")) {
                            this.computeN2 = Boolean.parseBoolean(st.nextToken());
                            System.out.println("   > Run N2:  " + this.computeN2);
                        }
                        else if (varName.equalsIgnoreCase("runN3")) {
                            this.computeN3 = Boolean.parseBoolean(st.nextToken());
                            System.out.println("   > Run N3:  " + this.computeN3);
                        }
                        else if (varName.equalsIgnoreCase("runN4")) {
                            this.computeN4 = Boolean.parseBoolean(st.nextToken());
                            System.out.println("   > Run N4:  " + this.computeN4);
                        }
                        else if (varName.equalsIgnoreCase("runL1")) {
                            this.computeL1 = Boolean.parseBoolean(st.nextToken());
                            System.out.println("   > Run L1:  " + this.computeL1);
                        }
                        else if (varName.equalsIgnoreCase("runL2")) {
                            this.computeL2 = Boolean.parseBoolean(st.nextToken());
                            System.out.println("   > Run L2:  " + this.computeL2);
                        }
                        else if (varName.equalsIgnoreCase("runL3")) {
                            this.computeL3 = Boolean.parseBoolean(st.nextToken());
                            System.out.println("   > Run L3:  " + this.computeL3);
                        }
                        else if (varName.equalsIgnoreCase("runT1")) {
                            this.computeT1 = Boolean.parseBoolean(st.nextToken());
                            System.out.println("   > Run T1:  " + this.computeT1);
                        }
                        else if (varName.equalsIgnoreCase("runT2")) {
                            this.computeT2 = Boolean.parseBoolean(st.nextToken());
                            System.out.println("   > Run T2:  " + this.computeT2);
                        }
                        else if (varName.equalsIgnoreCase("seed")) {
                            this.seed = Double.parseDouble(st.nextToken());
                            System.out.println("   > Seed:  " + this.seed);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            System.err.println("Error reading the configuration file");
            e.printStackTrace();
        }
    }
        
    private void extractDatasetInformation() {
        this.numberOfExamples = this.dSet.getNumInstances();
        this.numberOfAttributes = Attributes.getInputNumAttributes();
        this.example = new double[this.numberOfExamples][this.numberOfAttributes];
        this.classOfExample = new int[this.numberOfExamples];
        this.numberOfClasses = 2;
        for (int i = 0; i < this.numberOfExamples; ++i) {
            this.example[i] = this.dSet.getInstance(i).getNormalizedInputValues();
            this.classOfExample[i] = (int)this.dSet.getInstance(i).getNormalizedOutputValues()[0];
            
            System.out.println("exemplo:  " + this.dSet.getInstance(i));
            System.out.println("exemplo normalizado:  " + Arrays.toString(this.example[i]));
            if (this.numberOfClasses < this.classOfExample[i] + 1) {
                this.numberOfClasses = this.classOfExample[i] + 1;
            }
        }
        
        System.out.println(" \n\n\n ");
        System.out.println("  > The attributes number (without counting the class attribute) is: " + this.numberOfAttributes);
        System.out.println("  > The examples number is: " + this.numberOfExamples);
    }
    
    void run() {
        if (this.computeF1) {
            this.runF1();
        }
        if (this.computeF2) {
            this.runF2();
        }
        if (this.computeF3) {
            this.runF3();
        }
        if (this.computeN1) {
            this.runN1();
        }
        if (this.computeN2) {
            this.runN2();
        }
        if (this.computeN3) {
            this.runN3();
        }
        if (this.computeN4) {
            this.runN4();
        }
        if (this.computeL1) {
            this.runL1();
        }
        if (this.computeL2) {
            this.runL2();
        }
        if (this.computeL3) {
            this.runL3();
        }
        if (this.computeT1) {
            this.runT1();
        }
        if (this.computeT2) {
            this.runT2();
        }
        this.dumpResultsToScreen();
        this.writeMetricsToFile();
    }
    
    void dumpResultsToScreen() {
        System.out.println(" \n\n > Results of the complexity metrics: ");
        if (this.computeF1) {
            System.out.println(" F1: " + this.F1);
        }
        if (this.computeF2) {
            System.out.println(" F2: " + this.F2);
        }
        if (this.computeF3) {
            System.out.println(" F3: " + this.F3);
        }
        if (this.computeN1) {
            System.out.println(" N1: " + this.N1);
        }
        if (this.computeN2) {
            System.out.println(" N2: " + this.N2);
        }
        if (this.computeN3) {
            System.out.println(" N3: " + this.N3);
        }
        if (this.computeN4) {
            System.out.println(" N4: " + this.N4);
        }
        if (this.computeL1) {
            System.out.println(" L1: " + this.L1);
        }
        if (this.computeL2) {
            System.out.println(" L2: " + this.L2);
        }
        if (this.computeL3) {
            System.out.println(" L3: " + this.L3);
        }
        if (this.computeT1) {
            System.out.println(" T1: " + this.T1);
        }
        if (this.computeT2) {
            System.out.println(" T2: " + this.T2);
        }
    }
    
    void writeMetricsToFile() {
        PrintWriter fout = null;
        try {
            fout = new PrintWriter(new BufferedWriter(new FileWriter(this.outputFileName)));
            fout.println("Find below the results of the complexity metrics: \n");
            if (this.computeF1) {
                fout.println(" F1: " + this.F1);
            }
            if (this.computeF2) {
                fout.println(" F2: " + this.F2);
            }
            if (this.computeF3) {
                fout.println(" F3: " + this.F3);
            }
            if (this.computeN1) {
                fout.println(" N1: " + this.N1);
            }
            if (this.computeN2) {
                fout.println(" N2: " + this.N2);
            }
            if (this.computeN3) {
                fout.println(" N3: " + this.N3);
            }
            if (this.computeN4) {
                fout.println(" N4: " + this.N4);
            }
            if (this.computeL1) {
                fout.println(" L1: " + this.L1);
            }
            if (this.computeL2) {
                fout.println(" L2: " + this.L2);
            }
            if (this.computeL3) {
                fout.println(" L3: " + this.L3);
            }
            if (this.computeT1) {
                fout.println(" T1: " + this.T1);
            }
            if (this.computeT2) {
                fout.println(" T2: " + this.T2);
            }
            fout.close();
        }
        catch (Exception e) {
            System.err.println(" > [ERROR]: Printing the results to the output file: " + this.outputFileName);
        }
    }
    
    double runF1() {
        this.F1 = Double.MIN_VALUE;
        if (this.numberOfClasses > 2) {
            System.out.println(" >> [WARNING] This metric is devised for two-class problems. Only the two first classes of the problem will be considered ");
        }
        for (int i = 0; i < this.numberOfAttributes; ++i) {
            final double fisher = Math.pow(this.stats.getMean(i, 0) - this.stats.getMean(i, 1), 2.0) / (this.stats.getVariance(i, 0) + this.stats.getVariance(i, 1));
            if (fisher > this.F1) {
                this.F1 = fisher;
            }
        }
        System.out.println(" F1: " + this.F1);
        return this.F1;
    }
    
    double runF2() {
        if (this.numberOfClasses > 2) {
            System.out.println(" >> [WARNING] This metric is devised for two-class problems. Only the two first classes of the problem will be considered ");
        }
        this.F2 = 1.0;
        for (int i = 0; i < this.numberOfAttributes; ++i) {
            final double minmin = Math.min(this.stats.getMin(i, 0), this.stats.getMin(i, 1));
            final double minmax = Math.min(this.stats.getMax(i, 0), this.stats.getMax(i, 1));
            final double maxmin = Math.max(this.stats.getMin(i, 0), this.stats.getMin(i, 1));
            final double maxmax = Math.max(this.stats.getMax(i, 0), this.stats.getMax(i, 1));
            this.F2 *= (minmax - maxmin) / (maxmax - minmin);
        }
        System.out.println(" F2: " + this.F2);
        return this.F2;
    }
    
    double runF3() {
        boolean finish = false;
        int mostDiscrAtt = 0;
        double discPowerOfTheBest = 0.0;
        if (this.numberOfClasses != 2) {
            System.out.println(" > [WARNING in Feature Efficiency] Applying Maximum efficiency to a " + this.numberOfClasses + "-class data set. ");
        }
        this.organizePerClass();
        int numAttRemain = this.numberOfAttributes;
        int numExRemain = this.numberOfExamples;
        final double[] initialDiscPower = new double[this.numberOfAttributes];
        final double[] discPower = new double[this.numberOfAttributes];
        final double[] cumulDiscPower = new double[this.numberOfAttributes];
        final int[] order = new int[this.numberOfAttributes];
        for (int i = 0; i < this.numberOfAttributes; ++i) {
            discPower[i] = 0.0;
            cumulDiscPower[i] = (initialDiscPower[i] = 0.0);
            order[i] = i;
        }
        final boolean[] pointDisc = new boolean[this.numberOfExamples];
        for (int i = 0; i < this.numberOfExamples; ++i) {
            pointDisc[i] = false;
        }
        while (!finish) {
            finish = this.getDiscriminativePowerOfAttributes(discPower, order, numAttRemain, pointDisc);
            if (numAttRemain == this.numberOfAttributes) {
                for (int i = 0; i < this.numberOfAttributes; ++i) {
                    initialDiscPower[i] = discPower[i];
                }
            }
            this.quickSort(discPower, order, 0, numAttRemain - 1);
            cumulDiscPower[order[numAttRemain - 1]] = discPower[numAttRemain - 1];
            if (numAttRemain == this.numberOfAttributes) {
                mostDiscrAtt = order[numAttRemain - 1];
                discPowerOfTheBest = discPower[numAttRemain - 1] / this.numberOfExamples;
            }
            --numAttRemain;
            for (int i = 0; i < numAttRemain; ++i) {
                discPower[i] = 0.0;
            }
            final int bestAtt = order[numAttRemain];
            final double overlapMin = Math.max(this.stats.getMin(bestAtt, 0), this.stats.getMin(bestAtt, 1));
            final double overlapMax = Math.min(this.stats.getMax(bestAtt, 0), this.stats.getMax(bestAtt, 1));
            for (int i = 0; i < this.numberOfExamples; ++i) {
                if (!pointDisc[i] && (this.example[i][bestAtt] < overlapMin || this.example[i][bestAtt] > overlapMax)) {
                    pointDisc[i] = true;
                    --numExRemain;
                }
            }
            if (numExRemain == 0 || numAttRemain == 0) {
                finish = true;
            }
        }
        this.F3 = discPowerOfTheBest;
        System.out.println(" F3: " + this.F3);
        return this.F3;
    }
    
    private boolean getDiscriminativePowerOfAttributes(final double[] discPower, final int[] order, final int numAttRemain, final boolean[] pointDisc) {
        boolean finish = false;
        for (int j = 0; j < numAttRemain; ++j) {
            final int att = order[j];
            double overlapMin = this.stats.getMin(att, 0);
            double overlapMax = this.stats.getMax(att, 0);
            for (int i = 1; i < 2; ++i) {
                if (this.stats.getMin(att, i) > overlapMin) {
                    overlapMin = this.stats.getMin(att, i);
                }
                if (this.stats.getMax(att, i) < overlapMax) {
                    overlapMax = this.stats.getMax(att, i);
                }
            }
            if (overlapMin > overlapMax) {
                discPower[j] = this.numberOfExamples;
                for (int i = 0; i < this.numberOfAttributes; ++i) {
                    if (i != j) {
                        final int n = j;
                        discPower[n] -= discPower[i];
                    }
                }
                finish = true;
            }
            else {
                for (int i = 0; i < this.numberOfExamples; ++i) {
                    if (!pointDisc[i] && (this.example[i][att] < overlapMin || this.example[i][att] > overlapMax)) {
                        final int n2 = j;
                        ++discPower[n2];
                    }
                }
            }
        }
        return finish;
    }
    
    double runN1() {
        int different = 0;
        int[][] spanTree = new int[this.numberOfExamples - 1][2];
        for (int i = 0; i < this.numberOfExamples - 1; ++i) {
            for (int j = 0; j < 2; ++j) {
                spanTree[i][j] = 0;
            }
        }
        spanTree = this.computePrim();
        final int[] node = new int[this.numberOfExamples];
        for (int i = 0; i < this.numberOfExamples; ++i) {
            node[i] = -1;
        }
        for (int i = 0; i < this.numberOfExamples - 1; ++i) {
            if (this.classOfExample[spanTree[i][0]] != this.classOfExample[spanTree[i][1]]) {
                node[spanTree[i][0]] = 0;
                node[spanTree[i][1]] = 0;
            }
        }
        for (int i = 0; i < this.numberOfExamples; ++i) {
            if (node[i] == 0) {
                ++different;
            }
        }
        this.N1 = different / (double)this.numberOfExamples;
        System.out.println(" N1: " + this.N1);
        return this.N1;
    }
    
    private int selectMinNotTreated(final int[] neig, final double[] edge) {
        int min = -1;
        double distMin = Double.MAX_VALUE;
        for (int i = 0; i < this.numberOfExamples; ++i) {
            if (neig[i] != -1 && edge[i] < distMin) {
                distMin = edge[i];
                min = i;
            }
        }
        return min;
    }
    
    private double getApproximateDistance(final int ex1, final int ex2) {
        double dist = 0.0;
        for (int att = 0; att < this.numberOfAttributes; ++att) {
            dist += Math.pow(this.example[ex1][att] - this.example[ex2][att], 2.0);
        }
        return dist;
    }
    
    private int[][] computePrim() {
        int spanTreeIndex = 0;
        final int[][] spanTree = new int[this.numberOfExamples - 1][2];
        final int[] neig = new int[this.numberOfExamples];
        final double[] edge = new double[this.numberOfExamples];
        int currentNode = 0;
        neig[currentNode] = -1;
        edge[currentNode] = 0.0;
        for (int i = 1; i < this.numberOfExamples; ++i) {
            neig[i] = currentNode;
            edge[i] = this.getApproximateDistance(currentNode, i);
        }
        for (int i = 1; i < this.numberOfExamples; ++i) {
            currentNode = this.selectMinNotTreated(neig, edge);
            spanTree[spanTreeIndex][0] = currentNode;
            spanTree[spanTreeIndex][1] = neig[currentNode];
            ++spanTreeIndex;
            neig[currentNode] = -1;
            for (int j = 0; j < this.numberOfExamples; ++j) {
                if (neig[j] != -1 && edge[j] > this.getApproximateDistance(currentNode, j)) {
                    neig[j] = currentNode;
                    edge[j] = this.getApproximateDistance(currentNode, j);
                }
            }
        }
        return spanTree;
    }
    
    double runN2() {
        double distIntraClass = 0.0;
        double distInterClass = 0.0;
        for (int i = 0; i < this.numberOfExamples; ++i) {
            double distAux = 0.0;
            int neigInter = -1;
            int neigIntra = -1;
            double minDistInter = Double.MAX_VALUE;
            double minDistIntra = Double.MAX_VALUE;
            for (int j = 0; j < this.numberOfExamples; ++j) {
                if (j != i) {
                    distAux = this.getDistance(i, j);

                    System.out.println("distaux: " + distAux);
                    if (this.classOfExample[j] == this.classOfExample[i] && distAux < minDistIntra) {
                        neigIntra = j;
                        minDistIntra = distAux;
                    }
                    else if (this.classOfExample[j] != this.classOfExample[i] && distAux < minDistInter) {
                        neigInter = j;
                        minDistInter = distAux;
                    }
                }
            }
            if (neigInter == -1) {
                minDistInter = 0.0;
            }
            if (neigIntra == -1) {
                minDistIntra = 0.0;
            }
            System.out.println("min Intra: " + minDistIntra);
            System.out.println("min IntER: " + minDistInter);
            distIntraClass += minDistIntra;
            distInterClass += minDistInter;
        }
        if (distInterClass != 0.0) {
            System.out.println("Intra: " + distIntraClass);
            System.out.println("Inter: " + distInterClass);
            this.N2 = distIntraClass / distInterClass;
            System.out.println(" N2: " + this.N2);
            return this.N2;
        }
        else {
            System.out.println(" Error: ");
            return -1;
        }
    }
    
    private int getNearestNeighborOfExample(final int example, double minDist) {
        int neig = -1;
        minDist = Double.MAX_VALUE;
        for (int i = 0; i < this.numberOfExamples; ++i) {
            if (i != example && this.getDistance(example, i) < minDist) {
                neig = i;
                minDist = this.getDistance(example, i);
            }
        }
        return neig;
    }
    
    double runN3() {
        System.out.println("\n\n  > Running N3: Testing the KNN with the train instances \n");
        this.N3 = this.runKNN(1, this.example, this.classOfExample, this.numberOfExamples, true);
        System.out.println(" N3: " + this.N3);
        return this.N3;
    }
    
    double runKNN(final int k, final double[][] testExamples, final int[] classOfTestExamples, final int numberOfTestExamples, final boolean isTrain) {
        double minDist = 1.0E7;
        int minIndex = 0;
        int numCorrect = 0;
        for (int i = 0; i < numberOfTestExamples; ++i) {
            minDist = 1.0E7;
            minIndex = 0;
            for (int j = 0; j < this.numberOfExamples; ++j) {
                if (!isTrain || i != j) {
                    final double dist = this.getApproximateDistance(testExamples[i], this.example[j]);
                    if (dist < minDist) {
                        minIndex = j;
                        minDist = dist;
                    }
                }
            }
            if (classOfTestExamples[i] == this.classOfExample[minIndex]) {
                ++numCorrect;
            }
        }
        return 1.0 - numCorrect / (double)numberOfTestExamples;
    }
    
    double getApproximateDistance(final double[] ex1, final double[] ex2) {
        double dist = 0.0;
        System.out.println("Ponto 1: " + Arrays.toString(ex1));
        System.out.println("Ponto 2: " + Arrays.toString(ex2));
        for (int i = 0; i < this.numberOfAttributes; ++i) {
            dist += Math.pow(ex1[i] - ex2[i], 2.0);
        }
        return dist;
    }
    
    double getDistance(final double[] ex1, final double[] ex2) {
        return Math.pow(this.getApproximateDistance(ex1, ex2), 0.5);
    }
    
    double getDistance(final int ex1, final int ex2) {
        return Math.pow(this.getApproximateDistance(this.example[ex1], this.example[ex2]), 0.5);
    }
    
    double runN4() {
        final int numInstToGeneratePerClass = 1000;
        this.organizePerClass();
        for (int cClass = 0; cClass < this.numberOfClasses; ++cClass) {
            if (this.numExamplesPerClass[cClass] < 1) {
                System.err.println("      > [ERROR in N4] Error in computing the nonlinearity of the KNN classifier. ");
                System.err.println("        >> Class " + cClass + " has 0 instances. ");
                this.N4 = -1.0;
                return -1;
            }
        }
        System.out.println("      > Generating " + numInstToGeneratePerClass + " by means of interpolation ");
        final double[][] testExamples = new double[numInstToGeneratePerClass * this.numberOfClasses][];
        final int[] classOfTestExamples = new int[numInstToGeneratePerClass * this.numberOfClasses];
        this.createExamplesByInterpolation(testExamples, classOfTestExamples, numInstToGeneratePerClass, false);
        System.out.println("      > Testing the KNN with the test instances ");
        this.N4 = this.runKNN(1, testExamples, classOfTestExamples, numInstToGeneratePerClass * this.numberOfClasses, false);
        System.out.println(" N4: " + this.N4);
        return this.N4;
    }
    
    void organizePerClass() {
        if (this.numExamplesPerClass != null) {
            System.out.println(" Examples already organized per class ");
            return;
        }
        System.out.println(" Organizing instances per class ");
        this.numExamplesPerClass = new int[this.numberOfClasses];
        final int[] counterInstPerClass = new int[this.numberOfClasses];
        for (int i = 0; i < this.numberOfClasses; ++i) {
            counterInstPerClass[i] = (this.numExamplesPerClass[i] = 0);
        }
        for (int i = 0; i < this.numberOfExamples; ++i) {
            final int[] numExamplesPerClass = this.numExamplesPerClass;
            final int n = this.classOfExample[i];
            ++numExamplesPerClass[n];
        }
        this.examplesPerClass = new double[this.numberOfClasses][][];
        this.indexExamplesPerClass = new int[this.numberOfClasses][];
        for (int i = 0; i < this.numberOfClasses; ++i) {
            System.out.println(" Number of instances of class " + i + ": " + this.numExamplesPerClass[i]);
            this.examplesPerClass[i] = new double[this.numExamplesPerClass[i]][];
            this.indexExamplesPerClass[i] = new int[this.numExamplesPerClass[i]];
        }
        for (int i = 0; i < this.numberOfExamples; ++i) {
            final int whichClass = this.classOfExample[i];
            this.examplesPerClass[whichClass][counterInstPerClass[whichClass]] = this.example[i];
            this.indexExamplesPerClass[whichClass][counterInstPerClass[whichClass]] = i;
            final int[] array = counterInstPerClass;
            final int n2 = whichClass;
            ++array[n2];
        }
    }
    
    void createExamplesByInterpolation(final double[][] testExamples, final int[] classOfTestExamples, final int numExamplesTestPerClass, final boolean isSMO) {
        int inst = 0;
        this.organizePerClass();
        for (int cClass = 0; cClass < this.numberOfClasses; ++cClass) {
            System.out.println(" Generating instances of class: " + cClass);
            for (int i = 0; i < numExamplesTestPerClass; ++i) {
                testExamples[inst] = new double[this.numberOfAttributes];
                int ex1;
                int ex2;
                do {
                    ex1 = this.rndObject.nextInt(this.numExamplesPerClass[cClass]);
                    ex2 = this.rndObject.nextInt(this.numExamplesPerClass[cClass]);
                } while (ex1 == ex2 && this.numExamplesPerClass[cClass] > 1);
                ex1 = this.indexExamplesPerClass[cClass][ex1];
                ex2 = this.indexExamplesPerClass[cClass][ex2];
                for (int j = 0; j < this.numberOfAttributes; ++j) {
                    final double rnd = this.rndObject.nextDouble();
                    testExamples[inst][j] = this.example[ex1][j] * rnd + this.example[ex2][j] * (1.0 - rnd);
                }
                if (isSMO && cClass == 0) {
                    classOfTestExamples[inst] = -1;
                }
                else {
                    classOfTestExamples[inst] = cClass;
                }
                ++inst;
            }
        }
    }
    
    double runT1() {
        boolean overlappedExamples = false;
        this.organizePerClass();
        final int[][] neigh = new int[this.numberOfClasses][];
        final double[][] distNeigh = new double[this.numberOfClasses][];
        final int[][] adherenceOrder = new int[this.numberOfClasses][];
        final int[] maxAdherenceOrder = new int[this.numberOfClasses];
        for (int i = 0; i < this.numberOfClasses; ++i) {
            neigh[i] = new int[this.numExamplesPerClass[i]];
            distNeigh[i] = new double[this.numExamplesPerClass[i]];
            adherenceOrder[i] = new int[this.numExamplesPerClass[i]];
        }
        final double[] refParameter = new double[2];
        this.searchNearestNeighborsOfAnotherClass(neigh, distNeigh, refParameter);
        final double globalMinDist = refParameter[0];
        overlappedExamples = (refParameter[1] > 0.0);
        final double epsilon = 0.55 * globalMinDist;
        this.calculateAdherenceSubsets(adherenceOrder, maxAdherenceOrder, distNeigh, overlappedExamples, epsilon);
        this.eliminateAdherenceSetsIncluded(adherenceOrder, maxAdherenceOrder, epsilon);
        final double[] valuesReturn = this.getStatisticsPretopology(adherenceOrder, maxAdherenceOrder);
        this.T1 = valuesReturn[0] / this.numberOfExamples;
        System.out.println(" T1: " + this.T1);
        return this.T1;
    }
    
    void searchNearestNeighborsOfAnotherClass(final int[][] neigh, final double[][] distNeigh, final double[] refParameter) {
        double globalMinDist = refParameter[0];
        boolean overlappedExamples = false;
        globalMinDist = Double.MAX_VALUE;
        System.out.println("      > Searching the nearest neighbors of another class ");
        for (int cClass = 0; cClass < this.numberOfClasses; ++cClass) {
            for (int i = 0; i < this.numExamplesPerClass[cClass]; ++i) {
                distNeigh[cClass][i] = Double.MAX_VALUE;
            }
            for (int oClass = 0; oClass < this.numberOfClasses; ++oClass) {
                if (oClass != cClass) {
                    for (int i = 0; i < this.numExamplesPerClass[cClass]; ++i) {
                        for (int j = 0; j < this.numExamplesPerClass[oClass]; ++j) {
                            final double dist = this.getDistance(this.indexExamplesPerClass[cClass][i], this.indexExamplesPerClass[oClass][j]);
                            if (dist < distNeigh[cClass][i]) {
                                neigh[cClass][i] = this.indexExamplesPerClass[oClass][j];
                                distNeigh[cClass][i] = dist;
                            }
                        }
                        if (distNeigh[cClass][i] == 0.0) {
                            overlappedExamples = true;
                        }
                        else if (globalMinDist > distNeigh[cClass][i]) {
                            globalMinDist = distNeigh[cClass][i];
                        }
                    }
                }
            }
        }
        refParameter[0] = globalMinDist;
        refParameter[1] = (overlappedExamples ? 1.0 : -1.0);
    }
    
    void calculateAdherenceSubsets(final int[][] adherenceOrder, final int[] maxAdherenceOrder, final double[][] distNeigh, final boolean overlappedExamples, final double epsilon) {
        System.out.println("      > Calculating adherence subsets ");
        for (int cClass = 0; cClass < this.numberOfClasses; ++cClass) {
            maxAdherenceOrder[cClass] = 0;
            for (int i = 0; i < this.numExamplesPerClass[cClass]; ++i) {
                if (overlappedExamples && distNeigh[cClass][i] == 0.0) {
                    adherenceOrder[cClass][i] = 0;
                }
                else {
                    adherenceOrder[cClass][i] = (int)(distNeigh[cClass][i] / epsilon) - 1;
                }
                if (adherenceOrder[cClass][i] > maxAdherenceOrder[cClass]) {
                    maxAdherenceOrder[cClass] = adherenceOrder[cClass][i];
                }
            }
        }
    }
    
    void eliminateAdherenceSetsIncluded(final int[][] adherenceOrder, final int[] maxAdherenceOrder, final double epsilon) {
        System.out.println("      > Eliminating adherence subsets that are included in others ");
        for (int cClass = 0; cClass < this.numberOfClasses; ++cClass) {
            int nextMaximum;
            for (int maximum = maxAdherenceOrder[cClass]; maximum >= 0; maximum = nextMaximum) {
                for (int i = 0; i < this.numExamplesPerClass[cClass]; ++i) {
                    if (adherenceOrder[cClass][i] == maximum) {
                        for (int j = 0; j < this.numExamplesPerClass[cClass]; ++j) {
                            final double difOfOrder = (float)(adherenceOrder[cClass][i] - adherenceOrder[cClass][j]) * epsilon;
                            final double dist = this.getDistance(this.indexExamplesPerClass[cClass][i], this.indexExamplesPerClass[cClass][j]);
                            if (dist < difOfOrder) {
                                adherenceOrder[cClass][j] = -1;
                            }
                        }
                    }
                }
                nextMaximum = -1;
                for (int i = 0; i < this.numExamplesPerClass[cClass]; ++i) {
                    if (adherenceOrder[cClass][i] != -1 && adherenceOrder[cClass][i] < maximum && adherenceOrder[cClass][i] > nextMaximum) {
                        nextMaximum = adherenceOrder[cClass][i];
                    }
                }
            }
        }
    }
    
    double[] getStatisticsPretopology(final int[][] adherenceOrder, final int[] maxAdherenceOrder) {
        float sum = 0.0f;
        float sumsqr = 0.0f;
        float numOrders = 0.0f;
        final double[] stats = new double[5];
        for (int cClass = 0; cClass < this.numberOfClasses; ++cClass) {
            for (int i = 0; i < this.numExamplesPerClass[cClass]; ++i) {
                if (adherenceOrder[cClass][i] >= 0) {
                    sum += adherenceOrder[cClass][i];
                    sumsqr += adherenceOrder[cClass][i] * adherenceOrder[cClass][i];
                    ++numOrders;
                }
            }
        }
        stats[0] = numOrders;
        stats[1] = sum / numOrders;
        stats[2] = Math.sqrt((sumsqr - sum * sum / numOrders) / (numOrders - 1.0f));
        stats[3] = maxAdherenceOrder[0];
        stats[4] = maxAdherenceOrder[1];
        System.out.println("  > Results T1: " + stats[0] + " " + stats[1] + " " + stats[2] + " " + stats[3] + " " + stats[4]);
        return stats;
    }
    
    double runT2() {
        this.T2 = this.numberOfExamples / (double)this.numberOfAttributes;
        System.out.println(" T2: " + this.T2);
        return this.T2;
    }
    
    private void quickSort(final double[] vector, final int[] order, final int inf, final int sup) {
        if (inf < sup) {
            final int pivot = this.partition(vector, order, inf, sup);
            this.quickSort(vector, order, inf, pivot - 1);
            this.quickSort(vector, order, pivot + 1, sup);
        }
    }
    
    private int partition(final double[] vector, final int[] order, final int inf, final int sup) {
        final int pivotPosition = inf;
        int lastSmallerValue = inf;
        for (int firstUnknown = inf + 1; firstUnknown <= sup; ++firstUnknown) {
            if (vector[firstUnknown] < vector[pivotPosition]) {
                ++lastSmallerValue;
                final double tempF = vector[firstUnknown];
                vector[firstUnknown] = vector[lastSmallerValue];
                vector[lastSmallerValue] = tempF;
                final int tempI = order[firstUnknown];
                order[firstUnknown] = order[lastSmallerValue];
                order[lastSmallerValue] = tempI;
            }
        }
        final double tempF = vector[inf];
        vector[inf] = vector[lastSmallerValue];
        vector[lastSmallerValue] = tempF;
        final int tempI = order[inf];
        order[inf] = order[lastSmallerValue];
        order[lastSmallerValue] = tempI;
        return lastSmallerValue;
    }
    
    double runL1() {
        final double[] B = { 0.0 };
        if (this.numberOfClasses != 2) {
            System.out.println("  > [ERROR in L1] Nonlinearity of the linear classifier can be applied to only 2-class data sets ");
            this.L1 = -1.0;
            return -1;
        }
        System.out.println("      > Changing classes to -1, 1 ");
        final double[] w = this.trainSMO(B);
        for (int i = 0; i < this.numberOfExamples; ++i) {
            if (this.classOfExample[i] == 0) {
                this.classOfExample[i] = -1;
            }
        }
        System.out.println("      > Testing SVM with the train instances ");
        this.L1 = this.getDistanceObjectiveFunction(w, B[0], this.example, this.classOfExample, this.numberOfExamples);
        for (int i = 0; i < this.numberOfExamples; ++i) {
            if (this.classOfExample[i] == -1) {
                this.classOfExample[i] = 0;
            }
        }
        System.out.println(" L1: " + this.L1);
        return this.L1;
    }
    
    double runL2() {
        final double[] B = { 0.0 };
        if (this.numberOfClasses != 2) {
            System.out.println("  > [ERROR in L2] Nonlinearity of the linear classifier can be applied to only 2-class data sets ");
            this.L2 = -1.0;
            return -1;
        }
        System.out.println("      > Changing classes to -1, 1 ");
        final double[] w = this.trainSMO(B);
        for (int i = 0; i < this.numberOfExamples; ++i) {
            if (this.classOfExample[i] == 0) {
                this.classOfExample[i] = -1;
            }
        }
        System.out.println("      > Testing SVM with the train instances ");
        this.L2 = this.testSMO(w, B[0], this.example, this.classOfExample, this.numberOfExamples);
        for (int i = 0; i < this.numberOfExamples; ++i) {
            if (this.classOfExample[i] == -1) {
                this.classOfExample[i] = 0;
            }
        }
        System.out.println(" L2: " + this.L2);
        return this.L2;
    }
    
    double runL3() {
        final double[] B = { 0.0 };
        if (this.numberOfClasses != 2) {
            System.err.println("  > [ERROR in L3] Nonlinearity of the linear classifier can be applied to only 2-class data sets. ");
            return -1;
        }
        this.organizePerClass();
        for (int cClass = 0; cClass < this.numberOfClasses; ++cClass) {
            if (this.numExamplesPerClass[cClass] < 1) {
                System.out.println("      > [ERROR in L3] Error in computing the nonlinearity of the Linear Classifier. ");
                System.out.println("        >> Class " + cClass + " has 0 instances. ");
                this.L3 = -1.0;
                return -1;
            }
        }
        final int numInstToGeneratePerClass = 1000;
        System.out.println("      > Generating " + numInstToGeneratePerClass + " by means of interpolation ");
        final double[][] testExamples = new double[numInstToGeneratePerClass * this.numberOfClasses][];
        final int[] classOfTestExamples = new int[numInstToGeneratePerClass * this.numberOfClasses];
        this.createExamplesByInterpolation(testExamples, classOfTestExamples, numInstToGeneratePerClass, false);
        System.out.println("      > Changing classes to -1, 1 ");
        for (int i = 0; i < numInstToGeneratePerClass * this.numberOfClasses; ++i) {
            if (classOfTestExamples[i] == 0) {
                classOfTestExamples[i] = -1;
            }
        }
        final double[] w = this.trainSMO(B);
        for (int i = 0; i < this.numberOfExamples; ++i) {
            if (this.classOfExample[i] == 0) {
                this.classOfExample[i] = -1;
            }
        }
        System.out.println("      > Testing SVM with the test instances ");
        this.L3 = this.testSMO(w, B[0], testExamples, classOfTestExamples, numInstToGeneratePerClass * this.numberOfClasses);
        for (int i = 0; i < this.numberOfExamples; ++i) {
            if (this.classOfExample[i] == -1) {
                this.classOfExample[i] = 0;
            }
        }
        System.out.println(" L3: " + this.L3);
        return this.L3;
    }
    
    double testSMO(final double[] w, final double b, final double[][] testExamples, final int[] classOfTestExamples, final int numTestExamples) {
        int numError = 0;
        for (int i = 0; i < numTestExamples; ++i) {
            if (this.learnedFunction(testExamples[i], w, b) > 0.0 != classOfTestExamples[i] > 0) {
                ++numError;
            }
        }
        return numError / (double)numTestExamples;
    }
    
    double getDistanceObjectiveFunction(final double[] w, final double b, final double[][] testExamples, final int[] classOfTestExamples, final int numTestExamples) {
        double dist = 0.0;
        for (int i = 0; i < numTestExamples; ++i) {
            dist += Math.abs(this.learnedFunction(testExamples[i], w, b) - classOfTestExamples[i]);
        }
        return dist / numTestExamples;
    }
    
    double kernelFunction(final int i1, final int i2) {
        double dot = 0.0;
        for (int j = 0; j < this.numberOfAttributes; ++j) {
            dot += this.example[i1][j] * this.example[i2][j];
        }
        return dot;
    }
    
    double learnedFunction(final int k, final double[] w, final double b) {
        double s = 0.0;
        for (int i = 0; i < this.numberOfAttributes; ++i) {
            s += w[i] * this.example[k][i];
        }
        s -= b;
        return s;
    }
    
    double learnedFunction(final double[] testExample, final double[] w, final double b) {
        double s = 0.0;
        for (int i = 0; i < this.numberOfAttributes; ++i) {
            s += w[i] * testExample[i];
        }
        s -= b;
        return s;
    }
    
    int takeStep(final int i1, final int i2, final double[] B, final double[] alpha, final double[] w, final double[] errorCache) {
        double b = B[0];
        if (i1 == i2) {
            B[0] = b;
            return 0;
        }
        final double alpha2 = alpha[i1];
        final int y1 = this.classOfExample[i1];
        double E1;
        if (alpha2 > 0.0 && alpha2 < 0.05) {
            E1 = errorCache[i1];
        }
        else {
            E1 = this.learnedFunction(i1, w, b) - y1;
        }
        final double alpha3 = alpha[i2];
        final int y2 = this.classOfExample[i2];
        double E2;
        if (alpha3 > 0.0 && alpha3 < 0.05) {
            E2 = errorCache[i2];
        }
        else {
            E2 = this.learnedFunction(i2, w, b) - y2;
        }
        final int s = y1 * y2;
        double L;
        double H;
        if (y1 == y2) {
            final double gamma = alpha2 + alpha3;
            if (gamma > 0.05) {
                L = gamma - 0.05;
                H = 0.05;
            }
            else {
                L = 0.0;
                H = gamma;
            }
        }
        else {
            final double gamma = alpha2 - alpha3;
            if (gamma > 0.0) {
                L = 0.0;
                H = 0.05 - gamma;
            }
            else {
                L = -gamma;
                H = 0.05;
            }
        }
        if (L == H) {
            B[0] = b;
            return 0;
        }
        final double k11 = this.kernelFunction(i1, i1);
        final double k12 = this.kernelFunction(i1, i2);
        final double k13 = this.kernelFunction(i2, i2);
        final double eta = 2.0 * k12 - k11 - k13;
        double a2;
        if (eta < 0.0) {
            a2 = alpha3 + y2 * (E2 - E1) / eta;
            if (a2 < L) {
                a2 = L;
            }
            else if (a2 > H) {
                a2 = H;
            }
        }
        else {
            final double c1 = eta / 2.0;
            final double c2 = y2 * (E1 - E2) - eta * alpha3;
            final double Lobj = c1 * L * L + c2 * L;
            final double Hobj = c1 * H * H + c2 * H;
            if (Lobj > Hobj + 0.001) {
                a2 = L;
            }
            else if (Lobj < Hobj - 0.001) {
                a2 = H;
            }
            else {
                a2 = alpha3;
            }
        }
        if (Math.abs(a2 - alpha3) < 0.001 * (a2 + alpha3 + 0.001)) {
            B[0] = b;
            return 0;
        }
        double a3 = alpha2 - s * (a2 - alpha3);
        if (a3 < 0.0) {
            a2 += s * a3;
            a3 = 0.0;
        }
        else if (a3 > 0.05) {
            final double t = a3 - 0.05;
            a2 += s * t;
            a3 = 0.05;
        }
        double bnew;
        if (a3 > 0.0 && a3 < 0.05) {
            bnew = b + E1 + y1 * (a3 - alpha2) * k11 + y2 * (a2 - alpha3) * k12;
        }
        else if (a2 > 0.0 && a2 < 0.05) {
            bnew = b + E2 + y1 * (a3 - alpha2) * k12 + y2 * (a2 - alpha3) * k13;
        }
        else {
            final double b2 = b + E1 + y1 * (a3 - alpha2) * k11 + y2 * (a2 - alpha3) * k12;
            final double b3 = b + E2 + y1 * (a3 - alpha2) * k12 + y2 * (a2 - alpha3) * k13;
            bnew = (b2 + b3) / 2.0;
        }
        final double delta_b = bnew - b;
        b = bnew;
        double t2 = y1 * (a3 - alpha2);
        double t3 = y2 * (a2 - alpha3);
        for (int j = 0; j < this.numberOfAttributes; ++j) {
            final int n = j;
            w[n] += this.example[i1][j] * t2 + this.example[i2][j] * t3;
        }
        t2 = y1 * (a3 - alpha2);
        t3 = y2 * (a2 - alpha3);
        for (int j = 0; j < this.numberOfExamples; ++j) {
            if (0.0 < alpha[j] && alpha[j] < 0.05) {
                final int n2 = j;
                errorCache[n2] += t2 * this.kernelFunction(i1, j) + t3 * this.kernelFunction(i2, j) - delta_b;
                errorCache[i2] = (errorCache[i1] = 0.0);
            }
        }
        alpha[i1] = a3;
        alpha[i2] = a2;
        B[0] = b;
        return 1;
    }
    
    int argmaxE1E2(final int i1, final double E1, final double[] B, final double[] alpha, final double[] w, final double[] errorCache) {
        final double b = B[0];
        int i2 = -1;
        double tmax = 0.0;
        for (int k = 0; k < this.numberOfExamples; ++k) {
            if (alpha[k] > 0.0 && alpha[k] < 0.05) {
                final double E2 = errorCache[k];
                final double temp = Math.abs(E1 - E2);
                if (temp > tmax) {
                    tmax = temp;
                    i2 = k;
                }
            }
        }
        if (i2 >= 0) {
            B[0] = b;
            if (this.takeStep(i1, i2, B, alpha, w, errorCache) == 1) {
                return 1;
            }
        }
        B[0] = b;
        return 0;
    }
    
    int iterateNonBoundExamples(final int i1, final double[] B, final double[] alpha, final double[] w, final double[] errorCache) {
        double b = B[0];
        int j;
        for (int k0 = j = (int)(this.rndObject.nextDouble() * this.numberOfExamples); j < this.numberOfExamples + k0; ++j) {
            final int i2 = j % this.numberOfExamples;
            if (alpha[i2] > 0.0 && alpha[i2] < 0.05) {
                B[0] = b;
                if (this.takeStep(i1, i2, B, alpha, w, errorCache) == 1) {
                    b = B[0];
                    return 1;
                }
                b = B[0];
            }
        }
        B[0] = b;
        return 0;
    }
    
    int iterateEntireTrainingSet(final int i1, final double[] B, final double[] alpha, final double[] w, final double[] errorCache) {
        double b = B[0];
        int j;
        for (int k0 = j = (int)(this.rndObject.nextDouble() * this.numberOfExamples); j < this.numberOfExamples + k0; ++j) {
            final int i2 = j % this.numberOfExamples;
            B[0] = b;
            if (this.takeStep(i1, i2, B, alpha, w, errorCache) == 1) {
                return 1;
            }
            b = B[0];
        }
        B[0] = b;
        return 0;
    }
    
    int examineExample(final int i1, final double[] B, final double[] alpha, final double[] w, final double[] errorCache) {
        double b = B[0];
        final double y1 = this.classOfExample[i1];
        final double alpha2 = alpha[i1];
        double E1;
        if (alpha2 > 0.0 && alpha2 < 0.05) {
            E1 = errorCache[i1];
        }
        else {
            E1 = this.learnedFunction(i1, w, b) - y1;
        }
        final double r1 = y1 * E1;
        if ((r1 < -0.001 && alpha2 < 0.05) || (r1 > 0.001 && alpha2 > 0.0)) {
            if (this.argmaxE1E2(i1, E1, B, alpha, w, errorCache) == 1) {
                return 1;
            }
            b = B[0];
            if (this.iterateNonBoundExamples(i1, B, alpha, w, errorCache) == 1) {
                return 1;
            }
            b = B[0];
            if (this.iterateEntireTrainingSet(i1, B, alpha, w, errorCache) == 1) {
                return 1;
            }
            b = B[0];
        }
        B[0] = b;
        return 0;
    }
    
    double[] trainSMO(final double[] B) {
        int numChanged = 0;
        int examineAll = 1;
        int iter = 0;
        final double b = B[0];
        final double[] alpha = new double[this.numberOfExamples];
        final double[] errorCache = new double[this.numberOfExamples];
        final double[] w = new double[this.numberOfAttributes];
        System.out.println("      > Changing classes to -1, 1 ");
        for (int i = 0; i < this.numberOfExamples; ++i) {
            if (this.classOfExample[i] == 0) {
                this.classOfExample[i] = -1;
            }
        }
        for (int i = 0; i < this.numberOfExamples; ++i) {
            errorCache[i] = (alpha[i] = 0.0);
        }
        for (int i = 0; i < this.numberOfAttributes; ++i) {
            w[i] = 0.0;
        }
        System.out.println("      > Building the Support Vector Machine [progress line] ");
        for (int maxIterations = (this.numberOfExamples < 25000) ? 100000 : (4 * this.numberOfExamples); (numChanged > 0 || examineAll == 1) && iter < maxIterations; ++iter) {
            System.out.print(".");
            numChanged = 0;
            if (examineAll == 1) {
                for (int k = 0; k < this.numberOfExamples; ++k) {
                    numChanged += this.examineExample(k, B, alpha, w, errorCache);
                }
            }
            else {
                for (int k = 0; k < this.numberOfExamples; ++k) {
                    if (alpha[k] > 0.0 && alpha[k] < 0.05) {
                        numChanged += this.examineExample(k, B, alpha, w, errorCache);
                    }
                }
            }
            if (examineAll == 1) {
                examineAll = 0;
            }
            else if (numChanged == 0) {
                examineAll = 1;
            }
        }
        System.out.println("      > Changing classes to 0, 1 ");
        for (int i = 0; i < this.numberOfExamples; ++i) {
            if (this.classOfExample[i] == -1) {
                this.classOfExample[i] = 0;
            }
        }
        return w;
    }
    /*
    public static void main(final String[] args) {
        System.out.println(" > Starting running the complexity metrics ");
        System.out.println(" > Config File: " + args[0]);
        final ComplexityMetrics cm = new ComplexityMetrics(args[0]);
        cm.run();
    }*/
}
