package model;

import java.util.*;

import static model.FitnessEvaluator.*;

public class PairGenerator {
    private static Random random = new Random();
    private static final int TOURNAMENT_SIZE = 3;
    private final double CROSSOVER_RATE = 0.8;
    private final double MUTATION_RATE = 0.1;
    private static double generatedFitness = 0;
    private  static Set<Pair> hashSet = new HashSet<>();
    private List<Pair> initialPopulation = new ArrayList<>();
    private static List<Pair> pairsToCheckLater = new ArrayList<>();
    //private  HardlyRepeatedRandomNumberGenerator numberGenerator = new HardlyRepeatedRandomNumberGenerator(0,this.initialPopulation.size() - 1);


    /**
    Generates the initial population of pairs from a list of participants.
    @param participants The list of participants from which to generate the initial population.
     @return The list of pairs representing the initial population.
     */
    public List<Pair> generateInitialPopulation(List<Participant> participants) {
    // make pair list randomly -> initial
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
        this.initialPopulation = population;
        return population;
    }

    //*** make pair list -> new Generation ***
    public List<Pair> generateNextGeneration(List<Pair> currentGeneration) {
        List<Pair> nextGeneration = new ArrayList<>();
        HardlyRepeatedRandomNumberGenerator numberGenerator = new HardlyRepeatedRandomNumberGenerator(0,currentGeneration.size() - 1);
        int numberOfNewGeneration = 0;
        if(currentGeneration.size() % 2 == 0){
            numberOfNewGeneration = currentGeneration.size();
        }else {
            numberOfNewGeneration = currentGeneration.size() - 1;
        }
        while (nextGeneration.size() < numberOfNewGeneration) {
            // Tournament selection
//            Pair parent1 = tournamentSelection(currentGeneration);
//            Pair parent2 = tournamentSelection(currentGeneration);
            Pair parent1 = currentGeneration.get(numberGenerator.getNextRandomNumber());
            Pair parent2 = currentGeneration.get(numberGenerator.getNextRandomNumber());

            // Crossover
            //if (random.nextDouble() < CROSSOVER_RATE) {

                Pair[] offsprings = crossover(parent1, parent2);
                if(offsprings == null){
                    continue;
                }
                generatedFitness += evaluateFitness(offsprings[0]);
                generatedFitness += evaluateFitness(offsprings[1]);
                nextGeneration.add(offsprings[0]);
                nextGeneration.add(offsprings[1]);
            //}
            // Mutation
//            if (random.nextDouble() < MUTATION_RATE) {
//                int mutationIndex = random.nextInt(nextGeneration.size());
//                mutate(nextGeneration.get(mutationIndex),nextGeneration,currentGeneration,mutationIndex);
//            }
        }
        double average = generatedFitness / nextGeneration.size();

        if (average < 4){
            return generateNextGeneration(currentGeneration);
        }else{
            return nextGeneration;
        }

    }


    // make pair list with best fitness
    private  Pair tournamentSelection(List<Pair> currentGeneration) {
        List<Pair> tournamentPool = new ArrayList<>();
        // Randomly select pairs for the tournament pool
        while (tournamentPool.size() < TOURNAMENT_SIZE) {
            int index = random.nextInt(currentGeneration.size());
            Pair selectedPair = currentGeneration.get(index);

            // Check if the selected pair is already in the tournament pool
            if (!hashSet.contains(selectedPair)) {
                tournamentPool.add(selectedPair);
                hashSet.add(selectedPair);
//                hashSet.add(selectedPair.getPerson1());
//                hashSet.add(selectedPair.getPerson2());
                System.out.println("added");

            }

        }

        // Find the pair with the highest fitness score in the tournament pool
        Pair bestPair = tournamentPool.stream()
                .max(Comparator.comparingDouble(FitnessEvaluator::evaluateFitness))
                .orElse(null);

        return bestPair;
    }

    // make pair -> with max fitness
    private static Pair[] crossover(Pair parent1, Pair parent2) {
        // Perform crossover operation to create offspring pair

        // Get the participants from the parents
        Participant participant1Parent1 = parent1.getPerson1();
        Participant participant2Parent1 = parent1.getPerson2();
        Participant participant1Parent2 = parent2.getPerson1();
        Participant participant2Parent2 = parent2.getPerson2();


        // Perform crossover for food preferences

        Pair pair1 = new Pair(participant1Parent1,participant1Parent2,false);
        Pair pair4 = new Pair(participant2Parent1,participant2Parent2,false);
        Pair pair2 =  new Pair(participant1Parent1,participant2Parent2,false);
        Pair pair3 = new Pair(participant2Parent1,participant1Parent2,false);
        List<Pair> checklist = new ArrayList<>();
        List<Pair> validPairs = new ArrayList<>();
        checklist.add(pair1);
        checklist.add(pair2);
        checklist.add(pair3);
        checklist.add(pair4);
        for(Pair pair : checklist){
            if(FitnessEvaluator.checkFoodPreferenceFitness(pair) && FitnessEvaluator.checkKitchenFitness(pair)){
                validPairs.add(pair);
            }else {
                pairsToCheckLater.add(pair);
            }
        }
        if(validPairs.size() <= 1){
            return null;
        }

        Pair[] pairToreturn1 = {pair1,pair4};
        double fitness1 = Arrays.stream(pairToreturn1)
                .mapToDouble(FitnessEvaluator::evaluateFitness)
                .sum();

        Pair[] pairToreturn2 = {pair2,pair3};
        double fitness2 = Arrays.stream(pairToreturn2)
                .mapToDouble(FitnessEvaluator::evaluateFitness)
                .sum();
        List<Pair[]> list = new ArrayList<>();
        list.add(pairToreturn1);
        list.add(pairToreturn2);
        if(fitness1 == fitness2){
            return list.get(random.nextInt(2));
        } else if (fitness1 > fitness2) {
            return pairToreturn1;
        }else
            return pairToreturn2;


//        double fitness1 = evaluateFitness(pair1);
//        double fitness2 = evaluateFitness(pair2);
//        double fitness3 = evaluateFitness(pair3);
//        double fitness4 = evaluateFitness(pair4);
//
//        double maxFitness = findMax(fitness1,fitness2,fitness3,fitness4);
//        if(maxFitness == fitness1){
//            return pair1;
//        } else if (maxFitness == fitness2) {
//            return pair2;
//        } else if (maxFitness == fitness3) {
//            return pair3;
//        }
//        return pair4;
    }

    private static double findMax(double f1, double f2,double f3,double f4){
        return Math.max(f1, Math.max(f2, Math.max(f3, f4)));
    }


    private static void mutate(Pair pair, List<Pair> nextGeneration, List<Pair> currentGeneration, int mutationIndex) {
        Pair currentPair;
        do {
            int randomNum = random.nextInt(currentGeneration.size());
            currentPair = currentGeneration.get(randomNum);
        } while (!hashSet.contains(currentPair));

        //Pair mutatedPair = crossover(pair, currentPair);
//        generatedFitness -= evaluateFitness(nextGeneration.get(mutationIndex));
//        generatedFitness += evaluateFitness(mutatedPair);
//        nextGeneration.set(mutationIndex, mutatedPair);
    }


    public List<Pair> getInitialPopulation() {
        return initialPopulation;
    }

    public static double getGeneratedFitness() {
        return generatedFitness;
    }
}
