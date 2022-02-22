package Complexity_Metrics;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
/**
 *
 * @author Frederico Bender
 */
public class CSV {
    public void save(String dataSetName, String class1, String class2, ArrayList<Double> results){
        String save="resultsOneVsOne.csv";    
        if("0".equals(class1)){
            save="resultsMultiClass.csv";
        }
        if("all".equals(class2)){
            save="resultsOneVsAll.csv";
        }
        
        try{    
            PrintWriter pw = new PrintWriter(new FileWriter(save, true));
            StringBuilder sb = new StringBuilder();

            if("0".equals(class1)){ //Para as Medidas Multiclasse
                sb.append(dataSetName);
                for(int i=0;i<7;i++){
                    sb.append(",");
                    sb.append(results.get(i).toString());
                }  
            }
            else{ //Para as Medidas Binárias
                sb.append(dataSetName);
                sb.append(",");
                sb.append(class1);
                sb.append(",");
                sb.append(class2);
                for(int i=0;i<5;i++){
                    sb.append(",");
                    sb.append(results.get(i).toString());
                }
            }
            sb.append("\n");
            pw.write(sb.toString());
            pw.close();
        }catch (Exception e){
        //Handle exception
        }
    }
}