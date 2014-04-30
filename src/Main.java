import java.io.*;
import java.util.*;


public class Main {

    public static void main(String[] args) throws IOException {
    	
    	String root = "Data/619763/";
    	File folder = new File(root);
    	File[] listOfFiles = folder.listFiles();
    	ArrayList<String> tweetList = new ArrayList<String>();

    	for (File file : listOfFiles) {
    		if (file.isFile()) {
    			String fileName = file.getName();
    			if (fileName.contains(".json")) {
    				BufferedReader br = new BufferedReader(new FileReader(root + fileName));
    				String line = "";
    				while ((line = br.readLine()) != null) {
    					Tweet t = new Tweet(line);
    					tweetList.add(t.getContent());
    				}
    				br.close();
    			}
    		}
    	}
    }
}