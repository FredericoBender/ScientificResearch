package FARCHD;

import flanagan.complex.Complex;
import flanagan.math.Polynomial;
import java.util.ArrayList;
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
        
        
        int contador = 1;        
        Polynomial polinomioFinal = new Polynomial(1,x.get(0));
        while (contador<x.size()){          
            polinomioFinal = polinomioFinal.times(new Polynomial(1,x.get(contador)));//Cria ( 1 + 0.1 * x)                        
            contador++;           
        }
        polinomioFinal = polinomioFinal.minus(new Polynomial(1,1));
        //System.out.println("Polinomio final é: " + polinomioFinal);       
        
        
        Complex[] raizes; //Se o polinomio tem 1 elemento o lambda vale 0 
        if(x.size()>1){ 
            raizes = polinomioFinal.roots(); //Acha as raízes
        }
        else{
            return 0;
        }
        
        for (int i=0; i<raizes.length; i++){  //Procura e corrige as raízes que são 0.
            if(raizes[i].getImag()==0){
                if((raizes[i].getReal()<0.0001) & (raizes[i].getReal()>-0.0001)){
                    raizes[i].setReal(0);
                }
            }
        }
        //System.out.println("Raizes:" + Arrays.toString(raizes));
        
        boolean additiveMeasure=false;
        double lambda = 0; //Pega o elemento positivo. Se encontrar
        for (int i=0; i<raizes.length; i++){      
            if(raizes[i].getImag()==0){
                double d = raizes[i].getReal();
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
        }       
    //System.out.println("LAMBDA: " +lambda);
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