package FARCHD;

import polynom.Polynom;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/* @author Frederico Bender */

public class Sugeno {
     /**
     * Retorna o resultado de lambda
     * @param x para determinar lambda ex: X = {x1, x2, xn}
     * @return valor de lambda
     */
    public double lambda(ArrayList<Double> x){

        for (int i=0;i<x.size();i++){ //Correção necessária para o funcionamento de qualquer Array
            if(x.get(i)<0.001){
                x.set(i, 0.001);
            }
        }
        
        //System.out.println("X = " + x);    
        ArrayList<Double> vetorFinal = new ArrayList<>(); // utilizado no retorno
        
        
        
        int contador = 0;        
        Polynom polinomioFinal = new Polynom("1");
        while (contador<x.size()){
            String termo = "1 +" + x.get(contador) + "* x"; //Cria ( 1 + 0.1 * x)
            Polynom polinomioNovo = new Polynom(termo);           
            polinomioFinal = polinomioFinal.multiply(polinomioNovo); //Multiplica pela nova iteração                      
            contador++;
        }
        polinomioFinal = polinomioFinal.subtract(new Polynom("1+x"));
        //System.out.println("Polinomio final é: " + polinomioFinal);
        
       // double[] raizes = polinomioFinal.solve(); //Acha as raízes   
       
       double[] raizes ={1.27,-123}; 
       
       for (int i=0; i<raizes.length; i++){  //Procura e corrige as raízes que são 0.
            if((raizes[i]<0.0001) & (raizes[i]>-0.0001)){
                raizes[i]=0;
            }
        }
        //System.out.println("Raizes:" + Arrays.toString(raizes));
        
        boolean additiveMeasure=false;
        double lambda = 0; //Pega o elemento positivo. Se encontrar
        for (int i=0; i<raizes.length; i++){      
            double d = raizes[i];
            if(d>-1){              
                if(d==0){
                    additiveMeasure = true;             
                }
                else{
                    lambda=d; 
                    additiveMeasure = false;
                    break;
                }
            }            
        }
        /**
        if(additiveMeasure){
            System.out.println("Additive Measure!");
        }
        else{
            System.out.println("Lambda: " + lambda + "\n"); 
        }**/
    return lambda;
    }
    
    /**
    * Retorna a agregação entre os valores
    * @param lambda
    * @param x1
    * @param x2
    * @return g(x1,x2)
    */
    public double calcula(double lambda,double x1, double x2){
        double resposta;
        resposta = x1 + x2 + lambda*x1*x2;
        return resposta;
    }
}