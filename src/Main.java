import java.io.*;
import java.util.*;


public class Main {

    public static void main(String[] args) throws IOException {
    	
    	String root = "Data/619763/";
    	File folder = new File(root);
    	File[] listOfFiles = folder.listFiles();
    	List<String> tweetList = new ArrayList<String>();
        PrintStream ps = new PrintStream("tweet.chn.utf8");
        int count = 0;

    	for (File file : listOfFiles) {
    		if (file.isFile()) {
    			String fileName = file.getName();
    			if (fileName.contains(".json")) {
    				BufferedReader br = new BufferedReader(new FileReader(root + fileName));
    				String line = "";
    				while ((line = br.readLine()) != null) {
    					Tweet t = new Tweet(line);
                        String content = t.getContent();
                        if (!tweetList.contains(content)) {
                            count ++;
                            tweetList.add(content);
                            ps.println(content);
                        }
    				}
    				br.close();
    			}
    		}
    	}
        System.out.println("Totally tweets: " + count);
        String trainPath = args[0];
        String testPath = args[1];
        PrintStream train = new PrintStream(trainPath);
        PrintStream test = new PrintStream(testPath);
        int limit = count / 5 * 4;
        System.out.println("Training tweets: " + limit);
        System.out.println("Testing tweets: " + (count - limit));
        int trainCount = 0;
        BufferedReader br = new BufferedReader(new FileReader("tweet.chn.utf8"));
        String line = "";
        while ((line = br.readLine()) != null) {
            trainCount ++;
            if (trainCount <= limit) {
                train.println(line);
            } else {
                test.println(line);
            }
        }
    }
}