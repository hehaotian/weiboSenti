import java.io.*;
import java.util.*;


public class add_co_train {

    public static void main(String[] args) throws IOException {

        BufferedReader test1File = new BufferedReader(new FileReader("dataset/kloop/k3/test.v1.txt"));
        BufferedReader test2File = new BufferedReader(new FileReader("dataset/kloop/k3/test.v2.txt"));
        BufferedReader editFile = new BufferedReader(new FileReader("dataset/kloop/k3/k3.ma.res"));

        PrintStream ps1 = new PrintStream("dataset/kloop/k3/test3.v1.toadd.txt");
        PrintStream ps2 = new PrintStream("dataset/kloop/k3/test3.v2.toadd.txt");

        Map<String, String> tags = new HashMap<String, String>();

        String editLine = "";
        while ((editLine = editFile.readLine()) != null) {
            String[] arguments = editLine.split("\t");
            tags.put(arguments[0], arguments[1]);
        }
        editFile.close();

        String test1Line = "";
        while ((test1Line = test1File.readLine()) != null) {
            String newLine = "";
            String[] arguments = test1Line.split(" ");
            newLine = arguments[0] + " " + tags.get(arguments[0]) + " ";
            for (int i = 1; i < arguments.length; i ++) {
                newLine += arguments[i] + " ";
            }
            ps1.println(newLine);
        }
        test1File.close();

        String test2Line = "";
        while ((test2Line = test2File.readLine()) != null) {
            String newLine = "";
            String[] arguments = test2Line.split(" ");
            newLine = arguments[0] + " " + tags.get(arguments[0]) + " ";
            for (int i = 1; i < arguments.length; i ++) {
                newLine += arguments[i] + " ";
            }
            ps2.println(newLine);
        }
        test2File.close();
    }
}