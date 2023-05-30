package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HardlyRepeatedRandomNumberGenerator {
    private List<Integer> shuffledNumbers;
    private int currentIndex;
    private Random random;

    public HardlyRepeatedRandomNumberGenerator(int rangeStart, int rangeEnd) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = rangeStart; i <= rangeEnd; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        this.shuffledNumbers = numbers;
        this.currentIndex = 0;
        this.random = new Random();
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int getNextRandomNumber() {
        if (currentIndex >= shuffledNumbers.size()) {
            // All numbers in the range have been generated
            throw new IllegalStateException("All numbers in the range have been generated");
        }

        int randomNumber = shuffledNumbers.get(currentIndex);
        currentIndex++;
        return randomNumber;
    }

}
