package com.tsystems.javaschool.tasks.pyramid;

import java.util.Collections;
import java.util.List;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {
        // TODO : Implement your solution here
        inputNumbers.forEach(n -> {
            if (n == null) {
                throw new CannotBuildPyramidException();
            }
        });
        int i = 0;
        do {
          for (int x = i + 1; x < inputNumbers.size(); x++) {
              if (inputNumbers.get(i) == inputNumbers.get(x)) {
                  throw new CannotBuildPyramidException();
              }
          }
          i++;
        } while (i < inputNumbers.size());
            Collections.sort(inputNumbers);
            int amountNumbers = inputNumbers.size();
            int amountLines = 0;
            do {
                amountLines++;
                amountNumbers -= amountLines;
            } while (amountNumbers > 0);
            if (amountNumbers < 0) {
                throw new CannotBuildPyramidException();
            } else {
                int elementInLine = inputNumbers.size();
                for (int e = amountLines - 1; e >= 0; e--) {
                    elementInLine += (e * 2 + (amountLines - e - 1));
                }
                elementInLine = elementInLine / amountLines;
                int[][] pyramid = new int[amountLines][elementInLine];
                int amountZero = 0;
                amountNumbers = inputNumbers.size();
                while (amountLines > 0) {
                    int withoutZero = amountLines;
                    int column = elementInLine - 1;
                    boolean putZero = false;
                    for (int z = 0; z < amountZero; z++) {
                        pyramid[amountLines - 1][column] = 0;
                        column--;
                    }
                    while (withoutZero != 0) {
                        if (putZero) {
                            pyramid[amountLines - 1][column] = 0;
                            putZero = false;
                            column--;
                        }
                        pyramid[amountLines - 1][column] = inputNumbers.get(amountNumbers - 1);
                        putZero = true;
                        withoutZero--;
                        amountNumbers--;
                        column--;
                    }
                    for (int z = 0; z < amountZero; z++) {
                        pyramid[amountLines - 1][column] = 0;
                        column--;
                    }
                    amountZero++;
                    amountLines--;
                }
                return pyramid;
            }
    }
}



