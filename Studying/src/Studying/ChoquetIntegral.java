package agrega;

import java.util.ArrayList;

/* @author Frederico Bender */

public class ChoquetIntegral {
    
    public double calcula_com_cardinalidade(ArrayList<Double> x){ 
        double choq = 0; 
        double size = x.size(); //pega tamanho do vetor (n) em double
        double numerador=size; //cria variável que atuará como o numerador da card.
        choq = (x.get(0)-0) * 1 + choq;
        for (int i = 1; i < (size); i++) {
            numerador--; //decrementa numerador em cada iteração (-1)
            choq = ((x.get(i)-x.get(i-1)) * (numerador)/size) + choq;       
        }
        return choq;
    }
    
    public double calcula_com_mA(ArrayList<Double> x, ArrayList<Double> mA){ //Utilizando as medidas da Sugeno
        double choq = 0; 
        double size = x.size(); //pega tamanho do vetor (n) em double
        double numerador=size; //cria variável que atuará como o numerador da card.
        choq = (x.get(0)-0) * 1 + choq;
        for (int i = 1; i < (size); i++) {
            numerador--; //decrementa numerador em cada iteração (-1)
            choq = ((x.get(i)-x.get(i-1)) * (mA.get(i-1)))+ choq;       
        }
        return choq;
    }
}