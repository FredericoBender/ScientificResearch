/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agrega;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author Frederico Bender
 */
public class SugenoP {
	public double calculateLambda(ArrayList<Double> x) throws IOException{
		String pythonScriptPath = "D:\\Documentos\\NetBeansProjects\\Studying\\sugeno.py";
                String r;
		String[] cmd = new String[2+x.size()];
		cmd[0] = "python"; 
		cmd[1] = pythonScriptPath;
                for (int i=0;i<x.size();i++){
                    cmd[i+2] = x.get(i).toString();
                }
 
		Runtime rt = Runtime.getRuntime();
		Process pr = rt.exec(cmd);
     
               
		BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
                String line = bfr.readLine();
                line = bfr.readLine();
   
                return Double.parseDouble(line);
        }
        public double calculateFM(double lambdaMeasure, double x1, double x2){
               return x1 + x2 + (x1*x2*lambdaMeasure);
        }
}