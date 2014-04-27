import org.apache.lucene.queryparser.classic.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Maria Antoniak on 4/13/2014.
 */

// /corpora/LDC/LDC02T31/apw/1999/ /corpora/LDC/LDC02T31/apw/2000/ /corpora/LDC/LDC02T31/nyt/1998/ /corpora/LDC/LDC02T31/nyt/1999/ /corpora/LDC/LDC02T31/nyt/2000/ /corpora/LDC/LDC02T31/xie/1996/ /corpora/LDC/LDC02T31/xie/1997/ /corpora/LDC/LDC02T31/xie/1998/ /corpora/LDC/LDC02T31/xie/1999/ /corpora/LDC/LDC02T31/xie/2000/


// Test Main.
// Creates an index and performs some test queries and functions.
public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        if (args.length == 5) {

            final String RUN_TAG = "dev2";

            String index_fullPath = args[0];
            String index_pPath = args[1];
            String queryFilePath = args[2];
            String stopWordsFilePath = args[3];
            String focusWordsFilePath = args[4];

            ArrayList<String> stopWords = new ArrayList<String>();
            ArrayList<String> focusWords = new ArrayList<String>();
            ArrayList<String> passages = new ArrayList<String>();
            ArrayList<String> qWords = new ArrayList<String>();

            // Open two Lucene indices - one with the full texts, and one with
            // the texts segmented by paragraph.
            Index index_full = new Index(index_fullPath);
            Index index_p = new Index(index_pPath);

            // Perform test queries.
            index_full.queryGetTexts("anymore");
            System.err.println("Total num hits for 'Sox' = " + index_full.getNumDocsContainingTerm("Sox"));
            System.err.println("Total num docs = " + index_full.getTotalDocCount());
            index_p.queryGetTexts("anymore");
            System.err.println("Total num hits for 'Sox' = " + index_p.getNumDocsContainingTerm("Sox"));
            System.err.println("Total num docs = " + index_p.getTotalDocCount());

            // Read stop words and focus words.
            stopWords = getStopWords(stopWordsFilePath);
            focusWords = getFocusWords(stopWordsFilePath);

            // Read the queries file and get a list of Query objects.
            ArrayList<InputQuery> queries = getQueryList(queryFilePath);

            // Process each query and get ranked results.
            String line = "";

            // Loop through all the queries.
            for (InputQuery query : queries)
            {
                // Get the query words.
                String queryString = query.getUpperQuery(stopWords);
                queryString.trim();
//                System.out.println(line);
//                System.out.println(query);

                // Set the query words in a ArrayList.
                qWords.clear();
                qWords.addAll(Arrays.asList(queryString.toLowerCase().split(" ")));

                // Run the Lucene query.
                List<String> textList;
                textList= index_p.queryGetTexts(queryString);
                passages.clear();
                passages.addAll(textList);

                // Rank the answers.
                Ranking myRanking = new Ranking(qWords, passages, stopWords, focusWords, index_full);

                myRanking.doRanking();

                ArrayList<Map.Entry<String, String>> answerDocList = myRanking.getTopNAnswersDocNames(20);

                if (answerDocList.size() == 0)
                {
                    System.out.println(query.getQID() + " " + RUN_TAG + " NIL" + " NIL");
                }
                else
                {
                    // Display results in correct format: qid run-tag docid answer-string
                    for(Map.Entry<String, String> entry : answerDocList)
                    {
                        System.out.println(query.getQID() + " " + RUN_TAG
                                + " " + entry.getValue() + " " + entry.getKey());
                    }
                }
                //System.out.println(myRanking.getTopAnswer());
            }
        }
        else {
            System.err.println("Error: Incorrect number of command line args.");
        }
    }


    // Reads a file and returns a list of stop words.
    public static ArrayList<String> getStopWords(String stopWordsFileName) throws IOException {
        ArrayList<String> stopWords = new ArrayList<String>();
        String line = "";
        BufferedReader bufferedReader;
        bufferedReader = new BufferedReader(new FileReader(stopWordsFileName));
        while ((line = bufferedReader.readLine()) != null) {
            String[] tokens = line.split(", ");
            for (int i=0; i<tokens.length; i++) {
                stopWords.add(tokens[i]);
            }
        }
        bufferedReader.close();
        return(stopWords);
    }


    // Reads a file and returns a list of focus words.
    public static ArrayList<String> getFocusWords(String focusWordsFileName) throws IOException {
        ArrayList<String> focusWords = new ArrayList<String>();
        String line = "";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(focusWordsFileName));
        while ((line = bufferedReader.readLine()) != null) {
            String[] tokens = line.split(", ");
            for (int i=0; i<tokens.length; i++) {
                focusWords.add(tokens[i]);
            }
        }
        bufferedReader.close();
        return(focusWords);
    }

    // Reads a file and returns a list of Query objects
    public static ArrayList<InputQuery> getQueryList(String queryFileName) throws IOException {
        ArrayList<InputQuery> queries = new ArrayList<InputQuery>();
        String line = "";
        double qid = 0;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(queryFileName));
       	String targetStr = "";
        while ((line = bufferedReader.readLine()) != null) {
        	if (line.contains("<target id")) {
                targetStr = line.split("\"")[3];
        	}
            if (line.contains("<q id") && line.contains("FACTOID")) {
                String tempStr = line.split(" ")[3];
                // System.out.println(tempStr);
                qid = Double.parseDouble(tempStr.substring(1, tempStr.length() - 1));
                if ((line = bufferedReader.readLine()) != null) {
                    queries.add(new InputQuery(line+" "+targetStr, qid));
                }
            }
        }
        bufferedReader.close();
        return(queries);
    }
}
