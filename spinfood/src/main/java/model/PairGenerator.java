package model;

import java.util.*;

public class PairGenerator {
    private static Random random = new Random();
    private static final int TOURNAMENT_SIZE = 3;
    private static final double CROSSOVER_RATE = 0.8;
    private static final double MUTATION_RATE = 0.1;


    public static List<Pair> generateNextGeneration(List<Pair> currentGeneration) {
        List<Pair> nextGeneration = new ArrayList<>();

        while (nextGeneration.size() < currentGeneration.size()) {
            // Tournament selection
            Pair parent1 = tournamentSelection(currentGeneration);
            Pair parent2 = tournamentSelection(currentGeneration);

            // Crossover
            if (random.nextDouble() < CROSSOVER_RATE) {
                Pair offspring = crossover(parent1, parent2);
                nextGeneration.add(offspring);
            }

            // Mutation
            if (random.nextDouble() < MUTATION_RATE) {
                int mutationIndex = random.nextInt(nextGeneration.size());
                mutate(nextGeneration.get(mutationIndex));
            }
        }

        return nextGeneration;
    }

    private static Pair tournamentSelection(List<Pair> currentGeneration) {
        List<Pair> tournamentPool = new ArrayList<>();

        // Randomly select pairs for the tournament pool
        for (int i = 0; i < TOURNAMENT_SIZE; i++) {
            int index = random.nextInt(currentGeneration.size());
            tournamentPool.add(currentGeneration.get(index));
        }

        // Find the pair with the highest fitness score in the tournament pool
        Pair bestPair = tournamentPool.stream()
                .max(Comparator.comparingDouble(FitnessEvaluator::evaluateFitness))
                .orElse(null);

        return bestPair;
    }

    private static Pair crossover(Pair parent1, Pair parent2) {
        // Perform crossover operation to create offspring pair

        // Get the participants from the parents
        Participant participant1Parent1 = parent1.getPerson1();
        Participant participant2Parent1 = parent1.getPerson2();
        Participant participant1Parent2 = parent2.getPerson1();
        Participant participant2Parent2 = parent2.getPerson2();

        // Perform crossover for food preferences
        FoodPreference pref1 = crossoverFoodPreference(participant1Parent1.getFoodPreference(), participant1Parent2.getFoodPreference());
        FoodPreference pref2 = crossoverFoodPreference(participant2Parent1.getFoodPreference(), participant2Parent2.getFoodPreference());

        // Perform crossover for other fields
        // Adjust the crossover logic based on your specific requirements
        double age1 = (participant1Parent1.getAge() + participant1Parent2.getAge()) / 2.0;
        double age2 = (participant2Parent1.getAge() + participant2Parent2.getAge()) / 2.0;

        // Create the offspring pair with the crossed-over participants
        //Participant offspringParticipant1 = new Participant(participant1Parent1.getId(), participant1Parent1.getName(), age1, pref1, participant1Parent1.getSex(), participant1Parent1.getKitchenCount());
        //Participant offspringParticipant2 = new Participant(participant2Parent2.getId(), participant2Parent2.getName(), age2, pref2, participant2Parent2.getSex(), participant2Parent2.getKitchenCount());
        //Pair offspringPair = new Pair(offspringParticipant1, offspringParticipant2,false);

        //return offspringPair;
        return null;
    }
    private static FoodPreference crossoverFoodPreference(FoodPreference pref1, FoodPreference pref2) {
        // Perform crossover for food preferences based on the given criteria

        if (pref1 == pref2) {
            // Same food preferences, no crossover needed
            return pref1;
        } else if ((pref1 == FoodPreference.meat && pref2 == FoodPreference.none) ||
                (pref1 == FoodPreference.none && pref2 == FoodPreference.meat)) {
            // Fleischi and Egali, choose Fleischi as crossover
            return FoodPreference.meat;
        } else if ((pref1 == FoodPreference.vegan && pref2 == FoodPreference.veggie) ||
                (pref1 == FoodPreference.veggie && pref2 == FoodPreference.vegan)) {
            // Veganer and Veggie, choose Veganer as crossover
            return FoodPreference.vegan;
        } else {
            // Other combinations, no crossover needed, choose one of the preferences randomly
            return random.nextBoolean() ? pref1 : pref2;
        }
    }

    private static void mutate(Pair pair) {
        // Perform mutation operation on the pair

        // Implement your mutation logic here
    }

    public static List<Pair> generateInitialPopulation(List<Participant> participants) {
        List<Pair> population = new ArrayList<>();

        // Shuffle the participants randomly
        List<Participant> shuffledParticipants = new ArrayList<>(participants);
        Collections.shuffle(shuffledParticipants, new Random());
        // Iterate over the shuffled participants and create pairs
        if (shuffledParticipants.size() % 2 == 0) {
            for (int i = 0; i < shuffledParticipants.size(); i += 2) {
                Participant participant1 = shuffledParticipants.get(i);
                Participant participant2 = shuffledParticipants.get(i + 1);
                Pair pair = new Pair(participant1, participant2, false);
                population.add(pair);
            }
        } else {
            for (int i = 0; i < shuffledParticipants.size() - 1; i += 2) {
                Participant participant1 = shuffledParticipants.get(i);
                Participant participant2 = shuffledParticipants.get(i + 1);
                Pair pair = new Pair(participant1, participant2, false);
                population.add(pair);
            }

        }
        return population;
    }
}
