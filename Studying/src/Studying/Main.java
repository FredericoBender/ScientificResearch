package Studying;

import java.io.IOException;
import java.util.Collections; //Usado para ordenar o vetor
import java.util.ArrayList; //Agora pode usar Array, muito top

/* @author Frederico Bender */

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<Double> x = new ArrayList<>(); //cada instancia é uma g(x)
        double r;
        x.add(0.2);      
        x.add(0.4);
        x.add(0.6);
        x.add(0.7);
        //sugenoP teste = new sugenoP();
        //double y = teste.calculateLambda(x);
        //System.out.println(y);
 
        
        //Collections.sort(x);
       /* 
        SugenoLambda sugeno = new SugenoLambda();   
        ArrayList<Double> mA = sugeno.calcula(x);
        System.out.println("Resultados Sugeno: " + mA);
                
        ChoquetIntegral choquet = new ChoquetIntegral();
        System.out.println("Choquet com Cardinalidade: " + choquet.calcula_com_cardinalidade(x));
        System.out.println("Choquet com Lambda: " + choquet.calcula_com_mA(x,mA));
*/
    }
}