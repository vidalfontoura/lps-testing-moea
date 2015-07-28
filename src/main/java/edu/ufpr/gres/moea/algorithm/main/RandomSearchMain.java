package edu.ufpr.gres.moea.algorithm.main;

import java.io.File;

import org.uma.jmetal.core.Algorithm;
import org.uma.jmetal.core.SolutionSet;
import org.uma.jmetal.metaheuristic.multiobjective.randomSearch.RandomSearch;
import org.uma.jmetal.util.fileoutput.SolutionSetOutput;

import edu.ufpr.gres.moea.problem.LPSTestingProblem;

public class RandomSearchMain {

    public static void main(String[] args) throws Exception {

        File file = new File("experiments");
        if (!file.exists()) {
            file.mkdir();
        }

        String path = "experiments/tmp_experiments";
        String algorithms = "Random-3obj-1-";
        int executions = 30;

        String productsPath = "products_mutants/PROD_";
        String mutantsPath = "products_mutants/PROD_MUTANTS_";
        String pairsPath = "products_mutants/PROD_PAIRS_";

        LPSTestingProblem problem = new LPSTestingProblem(productsPath, mutantsPath, pairsPath);

        RandomSearch.Builder builder = new RandomSearch.Builder(problem);

        int maxEvaluations = 25000;
        builder.setMaxEvaluations(maxEvaluations);

        Algorithm algorithm = builder.build();

        File rootDir = createDir(path);
        File algorithmDir = createDir(rootDir.getPath() + File.separator + algorithms + File.separator);
        File objectivesDir = createDir(algorithmDir.getPath() + File.separator);

        String outputDir = objectivesDir.getPath() + File.separator;

        SolutionSet allRuns = new SolutionSet();

        long allExecutionTime = 0;
        System.out.println("Starting executions...");
        for (int i = 0; i < executions; i++) {

            // Execute the Algorithm
            System.out.println("Execution: " + (i + 1));
            long initTime = System.currentTimeMillis();
            SolutionSet population = algorithm.execute();
            long estimatedTime = System.currentTimeMillis() - initTime;

            allExecutionTime += estimatedTime;

            String executionDirectory = outputDir + "EXECUTION_" + i;
            createDir(executionDirectory);

            problem.removeDominateds(population);
            problem.removeDuplicates(population);

            SolutionSetOutput.printVariablesToFile(population, executionDirectory + File.separator + "VAR.txt");
            SolutionSetOutput.printObjectivesToFile(population, executionDirectory + File.separator + "FUN.txt");

            allRuns = allRuns.union(population);
        }

        System.out.println();
        System.out.println("End of execution for problem " + problem.getClass().getName() + ".");
        System.out.println("Total time (seconds): " + allExecutionTime / 1000);
        System.out.println("Writing results.");

        problem.removeDominateds(allRuns);
        problem.removeDuplicates(allRuns);

        SolutionSetOutput.printVariablesToFile(allRuns, outputDir + "VAR.txt");
        SolutionSetOutput.printObjectivesToFile(allRuns, outputDir + "FUN.txt");

    }

    private static File createDir(String dir) {

        File file = new File(dir);
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }
}
