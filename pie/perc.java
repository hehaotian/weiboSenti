/*
 * Created by Haotian He on 05/15/2014.
 */

import java.io.*;
import java.util.*;

public class perc {

    public static void main(String[] args) throws IOException {
        
        BufferedReader res = new BufferedReader(new FileReader("res/" + args[0] + ".res"));
        PrintStream ps = new PrintStream(args[0]);

        int count = 0;
        int pos = 0;
        int neg = 0;

        String resLine = "";
        while ((resLine = res.readLine()) != null) {
            String[] arguments = resLine.split("\t");
            if (arguments[1].equals("POS")) {
                pos ++;
            } else {
                neg ++;
            }
            count ++;
        }
        res.close();

        ps.println("Total:" + "\t" + count);
        ps.println("POS:" + "\t" + pos);
        ps.println("NEG:" + "\t" + neg);
        double posPerc = pos * 1.0 / count;
        ps.println("Percent of Positive View:" + "\t" + posPerc);
        ps.println("Percent of Negative View:" + "\t" + (1.0 - posPerc));

    }
}
