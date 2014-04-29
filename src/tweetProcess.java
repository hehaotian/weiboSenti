import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class tweetProcess {
    public static void main(String[] args) throws IOException {
    	File folder = new File("/Data/619763/");
    	File[] listOfFiles = folder.listFiles();

    	for (File file : listOfFiles) {
    		if (file.isFile()) {
    			System.out.println(file.getName());
    		}
    	}
    }
}