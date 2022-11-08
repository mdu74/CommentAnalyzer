package com.ikhokha.techcheck;

public class Question implements IMetric {
    private Integer countQuestion = 0;

    @Override
    public void setMetricCount(String line) {
        String[] text = line.split(" ");
        for (String word : text) {
			if (word.toLowerCase().contains("?")) {
                countQuestion++;
            }
		}
    }

    @Override
    public int getMetricCount() {
        return countQuestion;
    }
}
