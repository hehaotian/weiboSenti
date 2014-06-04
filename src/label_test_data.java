import java.io.*;
import java.util.*;


public class label_test_data {

    private static List<String> negList = new ArrayList<String>();
    private static List<String> posList = new ArrayList<String>();

    public static void main(String[] args) throws IOException {

        BufferedReader testFile = new BufferedReader(new FileReader("dataset/training/label_test"));
        BufferedReader posFile = new BufferedReader(new FileReader("dataset/polarity/pos.seg"));
        BufferedReader negFile = new BufferedReader(new FileReader("dataset/polarity/neg.seg"));

        PrintStream ps1 = new PrintStream("ml/" + args[0] + "/files/label_test.txt");
        
        String posLine = "";
        while ((posLine = posFile.readLine()) != null) {
            posList.add(posLine);
        }
        posFile.close();

        String negLine = "";
        while ((negLine = negFile.readLine()) != null) {
            negList.add(negLine);
        }
        negFile.close();

        String testLine = "";
        int count = 65001;
        while ((testLine = testFile.readLine()) != null) {
            String label = testLine.split(" ")[0];
        	String featureCount1 = ngramGenerator(testLine) + trigramGenerator(testLine) + polarityList(testLine) + emojiGenerator(testLine);
            ps1.println(count + " " + label + " " + featureCount1);
        	count ++;
        }
        testFile.close();
    }

    // adds unigram, bigram, and trigram features to the instances
    public static String ngramGenerator(String query) {
    	query = query.replaceAll("POS_EMO", "");
    	query = query.replaceAll("NEG_EMO", "");
        String ngramCount = "";
        String[] tokens = query.split(" ");
        Map<String, Integer> featureTally = new HashMap<String, Integer>();

        for (int i = 1; i < tokens.length; i ++) {
            if (!featureTally.containsKey(tokens[i])) {
                featureTally.put(tokens[i], 1);
            } else {
                featureTally.put(tokens[i], featureTally.get(tokens[i]) + 1);
            }
            if (i < tokens.length - 1) {
                if (!featureTally.containsKey(tokens[i] + "_" + tokens[i + 1])) {
                    featureTally.put(tokens[i] + "_" + tokens[i + 1], 1);
                } else {
                    featureTally.put(tokens[i] + "_" + tokens[i + 1], featureTally.get(tokens[i] + "_" + tokens[i + 1]) + 1);
                }
            }
        }

        for (String s : featureTally.keySet()) {
            ngramCount += s + ":" + featureTally.get(s) + " ";
            // ngramCount += s + ":1 ";
        }
        return ngramCount;
    }

    public static String trigramGenerator(String query) {
    	query = query.replaceAll("POS_EMO", "");
    	query = query.replaceAll("NEG_EMO", "");
        String trigramCount = "";
        String[] tokens = query.split(" ");
        Map<String, Integer> featureTally = new HashMap<String, Integer>();

        for (int i = 1; i < tokens.length; i ++) {
            if (i < tokens.length - 2) {
               if (!featureTally.containsKey(tokens[i] + "_" + tokens[i + 1] + "_" + tokens[i + 2])) {
                   featureTally.put(tokens[i] + "_" + tokens[i + 1] + "_" + tokens[i + 2], 1);
               } else {
                   featureTally.put(tokens[i] + "_" + tokens[i + 1] + "_" + tokens[i + 2], featureTally.get(tokens[i] + "_" + tokens[i + 1] + "_" + tokens[i + 2]) + 1);
               }
            }
        }

        for (String s : featureTally.keySet()) {
            trigramCount += s + ":" + featureTally.get(s) + " ";
            // trigramCount += s + ":1 ";
        }
        return trigramCount;
    }

    public static String polarityList(String query) {
        String polarityCount = "";
        Map<String, Integer> featureTally = new HashMap<String, Integer>();

        for (int i = 0; i < posList.size(); i ++) {
            if (query.contains(posList.get(i))) {
                String posFeat = posList.get(i).replaceAll("[\\s]+", "") + "_POSITIVE";
                if (!featureTally.containsKey(posFeat)) {
                    featureTally.put(posFeat, 1);
                } else {
                    featureTally.put(posFeat, featureTally.get(posFeat) + 1);
                } 
            }
        }
        for (int i = 0; i < negList.size(); i ++) {
            if (query.contains(negList.get(i))) {
                String negFeat = negList.get(i).replaceAll("[\\s]+", "") + "_NEGATIVE";
                if (!featureTally.containsKey(negFeat)) {
                    featureTally.put(negFeat, 1);
                } else {
                    featureTally.put(negFeat, featureTally.get(negFeat) + 1);
                } 
            }
        }
        
        for (String s : featureTally.keySet()) {
            polarityCount += s + ":" + featureTally.get(s) + " ";
        }

        return polarityCount;
    }

    public static String emojiGenerator(String query) {
        String emojiCount = "";
        String[] tokens = query.split(" ");
        Map<String, Integer> featureTally = new HashMap<String, Integer>();
        for (int i = 1; i < tokens.length; i ++) {
            if (tokens[i].equals("POS_EMO") || tokens[i].equals("NEG_EMO")) {
                if (!featureTally.containsKey(tokens[i])) {
                    featureTally.put(tokens[i], 1);
                } else {
                    featureTally.put(tokens[i], featureTally.get(tokens[i]) + 1);
                }
            }
        }
        for (String s : featureTally.keySet()) {
            emojiCount += s + ":" + featureTally.get(s) + " ";
        }
        return emojiCount;
    }

}