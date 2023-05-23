package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PairGenerator {
    private static Random random = new Random();

    public static List<Pair> generateInitialPopulation(List<Participant> participants) {
        List<Pair> population = new ArrayList<>();

        // Shuffle the participants randomly
        List<Participant> shuffledParticipants = new ArrayList<>(participants);
        Collections.shuffle(shuffledParticipants,new Random());
        // Iterate over the shuffled participants and create pairs
        for (int i = 0; i < shuffledParticipants.size(); i += 2) {
            Participant participant1 = shuffledParticipants.get(i);
            Participant participant2 = shuffledParticipants.get(i + 1);
            Pair pair = new Pair(participant1, participant2,false);
            population.add(pair);
        }

        return population;
    }
}
