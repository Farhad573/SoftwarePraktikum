package model;

import java.util.*;

import static model.FitnessEvaluator.*;

public class PairGenerator {
    private static Random random = new Random();
    private static final int TOURNAMENT_SIZE = 3;
    private final double CROSSOVER_RATE = 0.8;
    private final double MUTATION_RATE = 0.1;
    private static double generatedFitness = 0;



    // make pair list randomly -> initial
    public List<Pair> generateInitialPopulation(List<Participant> participants) {
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

    //*** make pair list -> new Generation ***
    public List<Pair> generateNextGeneration(List<Pair> currentGeneration) {
        List<Pair> nextGeneration = new ArrayList<>();
        while (nextGeneration.size() < currentGeneration.size()) {
            // Tournament selection
            Pair parent1 = tournamentSelection(currentGeneration);
            Pair parent2 = tournamentSelection(currentGeneration);

            // Crossover
            if (random.nextDouble() < CROSSOVER_RATE) {
                Pair offspring = crossover(parent1, parent2);
                generatedFitness += evaluateFitness(offspring);
                nextGeneration.add(offspring);
            }
            // Mutation
            if (random.nextDouble() < MUTATION_RATE) {
                int mutationIndex = random.nextInt(nextGeneration.size());
                mutate(nextGeneration.get(mutationIndex),nextGeneration,currentGeneration,mutationIndex);
            }
        }
        double average = generatedFitness / nextGeneration.size();

        if (average < 4){
            return generateNextGeneration(currentGeneration);
        }else{
            return nextGeneration;
        }
    }


    // make pair list with best fitness
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

    // make pair -> with max fitness
    private static Pair crossover(Pair parent1, Pair parent2) {
        // Perform crossover operation to create offspring pair

        // Get the participants from the parents
        Participant participant1Parent1 = parent1.getPerson1();
        Participant participant2Parent1 = parent1.getPerson2();
        Participant participant1Parent2 = parent2.getPerson1();
        Participant participant2Parent2 = parent2.getPerson2();

        // Perform crossover for food preferences
        Pair pair1 = new Pair(participant1Parent1,participant1Parent2,false);
        Pair pair2 =  new Pair(participant1Parent1,participant2Parent2,false);
        Pair pair3 = new Pair(participant2Parent1,participant1Parent2,false);
        Pair pair4 = new Pair(participant2Parent1,participant2Parent2,false);

        double fitness1 = evaluateFitness(pair1);
        double fitness2 = evaluateFitness(pair2);
        double fitness3 = evaluateFitness(pair3);
        double fitness4 = evaluateFitness(pair4);

        double maxFitness = findMax(fitness1,fitness2,fitness3,fitness4);
        if(maxFitness == fitness1){
            return pair1;
        } else if (maxFitness == fitness2) {
            return pair2;
        } else if (maxFitness == fitness3) {
            return pair3;
        }
        return pair4;
    }

    private static double findMax(double f1, double f2,double f3,double f4){
        return Math.max(f1, Math.max(f2, Math.max(f3, f4)));
    }


    private static void mutate(Pair pair,List<Pair> nextGeneration,List<Pair> currentGeneration,int mutationIndex) {
        Pair currentPair = currentGeneration.get(random.nextInt(currentGeneration.size()));
        Pair mutedPair = crossover(pair,currentPair);
        generatedFitness -= evaluateFitness(pair);
        generatedFitness += evaluateFitness(mutedPair);
        nextGeneration.set(mutationIndex,mutedPair);
    }



    public static double getGeneratedFitness() {
        return generatedFitness;
    }
}
