/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Complexity_Metrics;

import static java.lang.Double.isInfinite;
import java.util.ArrayList;

/**
 *
 * @author Frederico Bender
 */
public class Run {
    /** Default Constructor */
    public Run() {
    }

    public ArrayList<Double> execute(String confFile, String dataSetName, String class1, String class2) { 
       ArrayList<Double> resultados = new ArrayList<>();
       
       ComplexityMetrics measures = new ComplexityMetrics(confFile, dataSetName, class1, class2); //Pode ser chamado somente com os 2 primeiros parâmetros
     
       if("0".equals(class1)){ //Para as Medidas Multiclasse
           //resultados.add(measures.runF3());
           //resultados.add(measures.runN1());
           resultados.add(measures.runN2());
           //resultados.add(measures.runN3());
           //resultados.add(measures.runN4());
           //resultados.add(measures.runT1());
           //resultados.add(measures.runT2());
           
       }
       else{ //Para as Medidas Multiclasse Binárias
           resultados.add(measures.runF1());
           resultados.add(measures.runF2());
           resultados.add(measures.runL1());
           resultados.add(measures.runL2());
           resultados.add(measures.runL3());

       }
       //measures.run();
       return resultados;
    }

    public static void main(String args[]) {    
        ArrayList<Double> resultados = new ArrayList<>();
        String dataSetName = "test";
        String class1 = "0";
        String class2 = "0";
        if(args.length>1){
            dataSetName = args[1]; 
        }
        if(args.length>3){
            class1 = args[2];
            class2 = args[3];
        }
        Run program = new Run();
        CSV csv = new CSV();
        
        resultados = program.execute(args[0],dataSetName, class1, class2);
        //csv.save(dataSetName, class1, class2, resultados);
        
/*        if(!isInfinite(F1 RESULT)){ //Só salva o resultado se ele não for infinito
            csv.save(dataSetName, class1, class2, resultados);
        }
*/
    }
}