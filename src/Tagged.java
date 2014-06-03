/*
 * Created by Haotian He on 05/15/2014.
 */

import java.io.*;
import java.util.*;

public class Tagged {

    public static void main(String[] args) throws IOException {
        
        String predictedPath = "";
        if (args.length == 1) {
            predictedPath = args[0];
        }

        BufferedReader br = new BufferedReader(new FileReader(predictedPath));

        String line = "";
        while ((line = br.readLine()) != null)  {
            
            String[] tempStr = line.split("\t");
            String qid = tempStr[0];

            Map<Double, String> tagProb = new HashMap<Double, String>();
            List<Double> tagProbs = new ArrayList<Double>();
            tagProbs.add(Double.parseDouble(tempStr[2]));
            tagProbs.add(Double.parseDouble(tempStr[4]));

            tagProb.put(Double.parseDouble(tempStr[2]), tempStr[1]);
            tagProb.put(Double.parseDouble(tempStr[4]), tempStr[3]);
            double max = Collections.max(tagProbs);
            String predictedTag = tagProb.get(max);
            System.out.println(qid + "\t" + predictedTag);
        }

    }
}
