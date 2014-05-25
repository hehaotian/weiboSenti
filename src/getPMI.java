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
        BufferedReader posList = new BufferedReader(new FileReader("dataset/polarityList/top50Positive.txt"));
        BufferedReader negList = new BufferedReader(new FileReader("dataset/polarityList/top50Negative.txt"));

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
            String[] tokens = trainLine.split(" ");
            Map<String, Integer> posWordMatch = getWordMatch(posWordList, tokens);
            Map<String, Integer> negWordMatch = getWordMatch(negWordList, tokens);
            for (int i = 0; i < tokens.length; i ++) {
                double so_pmi_ir = 0.0;
            }
        }
    }

    public static Map<String, Integer> getWordMatch(List<String> wordlist, String[] tokens) {
        Map<String, Integer> tally = new HashMap<String, Integer>();
        for (int i = 0; i < tokens.length; i ++) {
            for (int j = 0; j < wordlist.size(); j ++) {
                if (tokens[i] == wordlist.get(j)) {
                    if (!tally.containsKey(tokens[i])) {
                        tally.put(tokens[i], 1);
                    } else {
                        tally.put(tokens[i], tally.get(tokens[i]) + 1);
                    }
                }
            }
        }
        return tally;
    }
}