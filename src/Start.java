import java.io.*;
import java.util.*;


public class Start {

    public static void main(String[] args) {
        
        BufferedReader startFile = new BufferedReader(new FileReader("dataset/start_t.txt"));
        PrintStream ps = new PrintStream("dataset/start_tweet_clean");

        String label = "";
        String content = "";
        String line = "";

        while ((line = startFile.readLine()) != null)) {
            String[] arguments = line.split("\t");
            if (arguments[0].equals("s")) {
                label = "NEU";
            } else if (arguments[0].equals("a")) {
                label = "POS";
            } else if (arguments[0].equals("d")) {
                label = "NEG";
            }
            content = arguments[1];
            content = content.replaceAll("(http://)([A-Za-z/.1-9]+)", "");
            content = content.replaceAll("[/@“]+", "");
            content = content.replaceAll("[【】：]+", " ");
            content = content.replaceAll("[#]+", " ");
            content = content.replaceAll("[。，！？]+", "。");
            content = content.replaceAll("&quot; | &amp; | bull;", "");
            content = content.replaceAll("[~]+", "。");
            content = content.replaceAll("[…]+", " ");
            content = content.replaceAll("[\\s]+", " ");

            ps.println(label + "\t" + content);
        }
    }

}