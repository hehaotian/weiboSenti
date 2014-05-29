import java.io.*;
import java.util.*;


public class Start {

    public static void main(String[] args) throws IOException {

        BufferedReader startFile = new BufferedReader(new FileReader("dataset/start_training/start_t.txt"));
        BufferedReader emojiFile = new BufferedReader(new FileReader("dataset/emoji.txt"));

        Map<String, String> emoji = new HashMap<String, String>();

        String emojiLine = "";
        while ((emojiLine = emojiFile.readLine()) != null) {
            String[] arguments = emojiLine.split("\t");
            String emo = arguments[1];
            String pol = "";
            if (arguments[3].equals("正面")) {
                pol = "POS";
            } else if (arguments[3].equals("负面")) {
                pol = "NEG";
            } else if (arguments[3].equals("中性")) {
                pol = "NEU";
            }
            emoji.put(emo, pol);
        }
        emojiFile.close();

        PrintStream ps1 = new PrintStream("dataset/start_training/start_tweet_clean");
        PrintStream ps2 = new PrintStream("dataset/start_training/no_label_tweet");

        String label = "";
        String content = "";
        String line = "";

        int count = 0;

        while ((line = startFile.readLine()) != null) {
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
            content = content.replaceAll("[]+", "");
            content = content.replaceAll("[]", "");
            content = content.replaceAll("[…]+", " ");
            content = content.replaceAll("[\\s]+", " ");
            content = content.replaceAll("[(≧▽≦)]", " POS_EMO ");

            for (String key : emoji.keySet()) {
                if (content.contains(key)) {
                    String value = " " + emoji.get(key) + "_EMO ";
                    content = content.replace(key, value);
                }
            }
            
            if (line != null) {
                ps1.println(count + "\t" + label + "\t" + content);
                ps2.println(content);
                count ++;
            }
        }
        startFile.close();
    }

}