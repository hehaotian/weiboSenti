import java.io.*;
import java.util.*;


public class Tweet {

    private String content;
    private String originalQuery;

    public Tweet(String line) {
        this.originalQuery = line;
        content = line.replaceAll("(.*content\":\")(.*)(\",\"createTime.*)", "$2");
    }

    public String getContent() {
        return(this.content);
    }
}