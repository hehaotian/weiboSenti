import java.io.*;
import java.util.*;


public class start_training_data {

    public static void main(String[] args) throws IOException {

        BufferedReader labFile = new BufferedReader(new FileReader("dataset/start_training/start_tweet_clean"));
        BufferedReader segFile = new BufferedReader(new FileReader("dataset/start_training/no_label_tweet.seg"));

        List<String> segTweets = new ArrayList<String>();

        String segLine = "";
        while ((segLine = segFile.readLine()) != null) {
            segTweets.add(segLine);
        }

        

}