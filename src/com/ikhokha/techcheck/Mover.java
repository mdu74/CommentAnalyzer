package com.ikhokha.techcheck;

public class Mover implements IMetric {
    private Integer countMovers = 0;

    @Override
    public void setMetricCount(String line) {
        String[] text = line.split(" ");
        for (String word : text) {
			if (word.toLowerCase().contains("mover")) {
                countMovers++;
            }
		}
    }

    @Override
    public int getMetricCount() {
        return countMovers;
    }
}
