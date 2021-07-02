package Studying;

import java.io.IOException; //Usado para ordenar o vetor
import java.util.ArrayList; //Agora pode usar Array, muito top

/* @author Frederico Bender */

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<Double> x = new ArrayList<>(); //cada instancia é uma g(x)
        double r;
        /*
        x.add(0.3); 
        x.add(0.9);
        x.add(0.65);
        x.add(0.12);
        */
        x.add(0.1);
        x.add(0.12);
        x.add(0.1);
        x.add(0.7);
        Sugeno teste = new Sugeno();
        double y = teste.lambda(x);
        System.out.println(y);
 
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