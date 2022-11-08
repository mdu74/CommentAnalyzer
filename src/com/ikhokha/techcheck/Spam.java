package com.ikhokha.techcheck;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Spam implements IMetric{
    private Integer countSpam = 0;

    private static final Pattern WEB_URL = Pattern.compile("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");

    @Override
    public void setMetricCount(String line){
        if (containsSpam(line)) {
            countSpam++;
        }
    }
    
    private boolean containsSpam(String line) {
        String textCollection[] = line.split(" ");
        boolean hasSpam = false;

        for (String text : textCollection) {
            String word = text;
            Matcher matcher = WEB_URL.matcher(word);
            if (matcher.find()) {
                hasSpam = true;
                break;
            }
        }

        return hasSpam;
    }

    @Override
    public int getMetricCount(){
        return countSpam;
    }    
}
