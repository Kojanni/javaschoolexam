package com.tsystems.javaschool.tasks.subsequence;

import java.util.List;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {
        // TODO: Implement the logic here
        boolean possible = false;
        if (x == null || y == null) {
            throw new IllegalArgumentException();
        } else {
                int xElements = 0;
                int j = 0;
                for (int i = 0; i < x.size(); i++) {
                    while (j < y.size()) {
                        if (y.get(j) == x.get(i)) {
                            xElements++;
                            j++;
                            break;
                        }
                        j++;
                    }
                }
                if (xElements == x.size()) {
                    possible = true;
                }
            return possible;
        }
    }
}
