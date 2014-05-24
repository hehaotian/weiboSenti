import java.io.*;
import java.util.*;


public class getUnsupervisedData {

    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new FileReader("dataset/train.seg"));
        PrintStream ps1 = new PrintStream("dataset/train25.seg");
        PrintStream ps2 = new PrintStream("dataset/train75.seg");

        String line = "";
        int limit = 14883 / 4;
        int count = 1;
        while ((line = br.readLine()) != null) {
            if (count <= limit) {
                ps1.println(line);
            } else {
                ps2.println(line);
            }
            count ++;
        }
    }

}