import java.io.*;
import java.util.*;


public class tweetProcess {
    public static void main(String[] args) throws IOException {
    	File folder = new File("Data/619763/");
    	File[] listOfFiles = folder.listFiles();
    	PrintStream tweet = new PrintStream("tweets");

    	for (File file : listOfFiles) {
    		if (file.isFile()) {
    			tweet.println(file.getName());
    		}
    	}
    }
}