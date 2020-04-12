package FARCHD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Spliterator;

/**
 * <p>Title: Main Class of the Program</p>
 *
 * <p>Description: It reads the configuration file (data-set files and parameters) and launch the algorithm</p>
 *
 * <p>Company: KEEL</p>
 *
 * @author Jes�s Alcal� Fern�ndez
 * @version 1.0
 */
public class Main {

    private parseParameters parameters;

    /** Default Constructor */
    public Main() {
    }

    /**
     * It launches the algorithm
     * @param confFile String it is the filename of the configuration file.
     */
    public ArrayList<Double> execute(String confFile, int crossValidationSyze,  String dataSetName, int dat, int fuzzyMeasure) { 
        parameters = new parseParameters();
        parameters.parseConfigurationFile(confFile,dat,crossValidationSyze,dataSetName);
        Bull method = new Bull(parameters);     
        ArrayList<Double> resultados = method.execute(fuzzyMeasure);  
       
        return resultados;

    }
    /**
     * Main Program
     * @param args It contains the name of the configuration file<br/>
     * Format:<br/>
     * <em>algorith = &lt;algorithm name></em><br/>
     * <em>inputData = "&lt;training file&gt;" "&lt;validation file&gt;" "&lt;test file&gt;"</em> ...<br/>
     * <em>outputData = "&lt;training file&gt;" "&lt;test file&gt;"</em> ...<br/>
     * <br/>
     * <em>seed = value</em> (if used)<br/>
     * <em>&lt;Parameter1&gt; = &lt;value1&gt;</em><br/>
     * <em>&lt;Parameter2&gt; = &lt;value2&gt;</em> ... <br/>
     */

    public static void main(String args[]) {        
        String dataSetName = "ecoli";
        int fuzzyMeasure = 5;
        int dat = 4;        
        int crossValidationSyze = 5;
        if(args.length>1){
            dataSetName = args[1]; //iris, cleveland, automobile, wine, ecoli
            fuzzyMeasure = Integer.parseInt(args[2]); //5=CardGA //50=Sugeno
            dat = Integer.parseInt(args[3]); //parte dos datasets = 1|2|3|4|5
            crossValidationSyze = Integer.parseInt(args[4]);
        }

        Main program = new Main();
        CSV csv = new CSV();

        ArrayList<Double> resultados = program.execute(args[0],crossValidationSyze ,dataSetName, dat, fuzzyMeasure);

        csv.save(dataSetName, fuzzyMeasure, dat, resultados.get(0), resultados.get(1));
    }  
}