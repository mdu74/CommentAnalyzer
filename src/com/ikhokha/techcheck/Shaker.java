package com.ikhokha.techcheck;

public class Shaker implements IMetric {
    private Integer countShaker = 0;

    @Override
    public void setMetricCount(String line) {
        String[] text = line.split(" ");
        for (String word : text) {
			if (word.toLowerCase().contains("shaker")) {
                 countShaker++;
            }
		}
    }

    @Override
    public int getMetricCount() {
        return countShaker;
    }    
}
