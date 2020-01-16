package com.od.cryptography.validation_module.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ValidationHelper {

    private int[] peselNumberWeights = {1, 3, 7, 9, 1, 3, 7 ,9 ,1 ,3};
    private final Map<Integer, Integer> daysOfEachMonth;

    {
        daysOfEachMonth = new HashMap<>();
        daysOfEachMonth.put(1, 31);
        daysOfEachMonth.put(2, 29);
        daysOfEachMonth.put(3, 31);
        daysOfEachMonth.put(4, 30);
        daysOfEachMonth.put(5, 31);
        daysOfEachMonth.put(6, 30);
        daysOfEachMonth.put(7, 31);
        daysOfEachMonth.put(8, 31);
        daysOfEachMonth.put(9, 30);
        daysOfEachMonth.put(10, 31);
        daysOfEachMonth.put(11, 30);
        daysOfEachMonth.put(12, 31);
    }

    public int[] getPeselNumberWeights() {
        return peselNumberWeights;
    }

    public Map<Integer, Integer> getDaysOfEachMonth() {
        return daysOfEachMonth;
    }
}
