package com.ikhokha.techcheck;

public class ShorterComments implements IMetric {
    private Integer countShortComments = 0;

    @Override
    public void setMetricCount(String line) {
        if (line.length() < 15) {
            this.countShortComments++;
        }
    }

    @Override
    public int getMetricCount() {
        return countShortComments;
    }
}
