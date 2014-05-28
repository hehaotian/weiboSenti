import java.io.*;
import java.util.*;


public class getPMI {

    public static void main(String[] args) throws IOException {

        Map<String, String[]> data = new HashMap<String, String[]>();
        Map<String, String[]> grams1 = new HashMap<String, String[]>();
        Map<String, String[]> grams2 = new HashMap<String, String[]>();
        Map<String, String[]> grams3 = new HashMap<String, String[]>();

        Scanner lm = new Scanner(new File("dataset/lm/lm_train25"));
        String line = "";
        String keyname = "";

        while (lm.hasNextLine()) {
            if (lm.hasNextLine()) {
                line = lm.nextLine();

                if (line.equals("\\data\\")) {
                    String gr1 = lm.nextLine();
                    String gr2 = lm.nextLine();
                    String gr3 = lm.nextLine();
                    data.put("gr1", gr1.split(" "));
                    data.put("gr2", gr2.split(" "));
                    data.put("gr3", gr3.split(" "));
                } else if (line.split(" ").length == 4) {
                    keyname = line.split(" ")[3];
                    String[] value = {line.split(" ")[0], line.split(" ")[1], line.split(" ")[2]};
                    grams1.put(keyname, value);
                } else if (line.split(" ").length == 5) {
                    keyname = line.split(" ")[3] + " " + line.split(" ")[4];
                    String[] value = {line.split(" ")[0], line.split(" ")[1], line.split(" ")[2]};
                    grams2.put(keyname, value);
                } else if (line.split(" ").length == 6) {
                    keyname = line.split(" ")[3] + " " + line.split(" ")[4] + " " + line.split(" ")[5];
                    String[] value = {line.split(" ")[0], line.split(" ")[1], line.split(" ")[2]};
                    grams3.put(keyname, value);
                }
            }
        }

        BufferedReader trainFile = new BufferedReader(new FileReader("dataset/train25.seg"));
        BufferedReader posList = new BufferedReader(new FileReader("dataset/polarityList/pos.seg"));
        BufferedReader negList = new BufferedReader(new FileReader("dataset/polarityList/neg.seg"));

        PrintStream res = new PrintStream("dataset/rough_percent_res.txt");

        List<String> posWordList = new ArrayList<String>();
        List<String> negWordList = new ArrayList<String>();

        String posLine = "";
        String negLine = "";

        while ((posLine = posList.readLine()) != null) {
            posWordList.add(posLine);
        }
        posList.close();

        while ((negLine = negList.readLine()) != null) {
            negWordList.add(negLine);
        }
        negList.close();

        String trainLine = "";
        while ((trainLine = trainFile.readLine()) != null) {
            Map<String, Integer> posWordMatch = getWordMatch(posWordList, trainLine);
            Map<String, Integer> negWordMatch = getWordMatch(negWordList, trainLine);
            double perc = posWordMatch.size() * 1.0 / negWordMatch.size();
            String label = "";
            if (perc > 1.0) {
                label = "POS";
            } else {
                label = "NEG";
            }
            res.println(perc + " " + label + " " + trainLine);
        }
    }

    public static Map<String, Integer> getWordMatch(List<String> wordlist, String trainLine) {
        Map<String, Integer> tally = new HashMap<String, Integer>();        
        for (int j = 0; j < wordlist.size(); j ++) {
            if (trainLine.contains(wordlist.get(j))) {
                if (!tally.containsKey(wordlist.get(j))) {
                    tally.put(wordlist.get(j), 1);
                } else {
                    tally.put(wordlist.get(j), tally.get(wordlist.get(j)) + 1);
                }
            }
        }
        return tally;
    }
}