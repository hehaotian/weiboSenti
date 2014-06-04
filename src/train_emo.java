import java.io.*;
import java.util.*;


public class train_emo {

    public static void main(String[] args) throws IOException {

        String path = "";
        if (args[1].equals("unlabel")) {
            path = args[0] + ".txt";
        } else {
            path = args[0] + ".label.txt";
        }

        BufferedReader startFile = new BufferedReader(new FileReader(path));
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

        PrintStream ps = new PrintStream("test_unseg");

        String content = "";

        int count = 0;

        while ((content = startFile.readLine()) != null) {
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
            
            if (content != null) {
                ps.println(content);
                count ++;
            }
        }
        startFile.close();
    }

}