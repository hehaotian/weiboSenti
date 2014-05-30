import java.io.*;
import java.util.*;


public class Main {

    public static void main(String[] args) throws IOException {
    	
    	String root = "dataset/619893/";
    	File folder = new File(root);
    	File[] listOfFiles = folder.listFiles();
    	List<String> tweetList = new ArrayList<String>();
        PrintStream ps = new PrintStream("dataset/training/train");
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
        System.out.println("Totally training tweets: " + count);
    }
}