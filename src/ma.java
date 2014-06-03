/*
 * Created by Haotian He on 05/15/2014.
 */

import java.io.*;
import java.util.*;

public class ma {

    public static void main(String[] args) throws IOException {
        
        BufferedReader res1 = new BufferedReader(new FileReader("ml/" + args[0] + "/files/res/" + args[0] + ".v1.res"));
        BufferedReader res2 = new BufferedReader(new FileReader("ml/" + args[0] + "/files/res/" + args[0] + ".v2.res"));
        PrintStream ps = new PrintStream("ml/" + args[0] + "/files/res/" + args[0] + ".ma.res");

        String res1Line = "";
        String res2Line = "";

        Map<String, String> res1Map = new HashMap<String, String>();

        while ((res1Line = res1.readLine()) != null) {
            String[] arguments = res1Line.split("\t");
            res1Map.put(arguments[0], arguments[1]);
        }
        res1.close();

        while ((res2Line = res2.readLine()) != null) {
            String[] arguments = res2Line.split("\t");
            if (arguments[1].equals(res1Map.get(arguments[0]))) {
                ps.println(arguments[0] + "\t" + arguments[1]);
            } else {
                ps.println(arguments[0] + "\t" + arguments[1] + "\t" + res1Map.get(arguments[0]));
            }
        }
        res2.close();

    }
}
