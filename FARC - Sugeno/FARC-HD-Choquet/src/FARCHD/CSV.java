package FARCHD;

import java.io.FileWriter;
import java.io.PrintWriter;
/**
 *
 * @author Frederico Bender
 */
public class CSV {
    public void save(String dataSetName, int fuzzyMeasure, int dat, double training, double testing){
    try{
        PrintWriter pw = new PrintWriter(new FileWriter("resultados.csv", true));
        StringBuilder sb = new StringBuilder();
        
        sb.append(dataSetName);
        sb.append(",");
        sb.append(fuzzyMeasure);
        sb.append(",");
        sb.append(dat);
        sb.append(",");
        sb.append(training);
        sb.append(",");
        sb.append(testing);
        sb.append("\n");

        pw.write(sb.toString());
        pw.close();
        //System.out.println("data saved");
    }catch (Exception e){
    //Handle exception
    }
    }
}
