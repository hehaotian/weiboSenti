import java.io.*;
import java.util.*;


public class getPolarityList {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("dataset/yuanbo/pl.txt"));
        Map<String, Double> positiveList = new HashMap<String, Double>();
        Map<String, Double> negativeList = new HashMap<String, Double>();

        int topNumber = 100;

        String line = "";
        while ((line = br.readLine()) != null) {
            String[] arguments = line.split("\t");
            if (arguments.length == 2) {
                String word = arguments[0];
                double value = Double.parseDouble(arguments[1]);
                if (value > 0) {
                    positiveList.put(word, value);
                } else if (value < 0) {
                    negativeList.put(word, value);
                }
            }
        }
        br.close();

        getTopPositive(positiveList, 3000);
        getTopNegative(negativeList, 3000);
        getTopPositive(positiveList, 5000);
        getTopNegative(negativeList, 5000);
    }

    public static void getTopPositive(Map<String, Double> list, int topNum) throws IOException {
        
        PrintStream top = new PrintStream("dataset/yuanbo/top" + topNum + "Positive.txt");

        List<Map.Entry<String, Double>> entryList = new ArrayList<Map.Entry<String, Double>>();
        entryList.addAll(list.entrySet());
      
        Collections.sort(entryList, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> a, Map.Entry<String, Double> b) {
                double temp = b.getValue() * 100000000 - a.getValue() * 100000000;
                return (int)temp;
            }
        }
        );

        int count = 1;
        for (Map.Entry<String, Double> a : entryList) {
            if (count <= topNum) {
                top.println(a.getKey());
            }
            count ++;
        }
    }

    public static void getTopNegative(Map<String, Double> list, int topNum) throws IOException {
        
        PrintStream top = new PrintStream("dataset/yuanbo/top" + topNum + "Negative.txt");

        List<Map.Entry<String, Double>> entryList = new ArrayList<Map.Entry<String, Double>>();
        entryList.addAll(list.entrySet());
      
        Collections.sort(entryList, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> a, Map.Entry<String, Double> b) {
                double temp = a.getValue() * 100000000 - b.getValue() * 100000000;
                return (int)temp;
            }
        }
        );

        int count = 1;
        for (Map.Entry<String, Double> a : entryList) {
            if (count <= topNum) {
                top.println(a.getKey());
            }
            count ++;
        }
    }

}