package edu.ufpr.gres.moea.algorithm.main;

import java.io.File;
import java.util.List;

import org.uma.jmetal.core.Algorithm;
import org.uma.jmetal.core.SolutionSet;
import org.uma.jmetal.metaheuristic.multiobjective.ibea.IBEA;
import org.uma.jmetal.operator.crossover.Crossover;
import org.uma.jmetal.operator.mutation.Mutation;
import org.uma.jmetal.operator.selection.BinaryTournament;
import org.uma.jmetal.util.comparator.FitnessComparator;
import org.uma.jmetal.util.fileoutput.SolutionSetOutput;

import edu.ufpr.gres.moea.operators.ProductMutation;
import edu.ufpr.gres.moea.operators.ProductPMXCrossover;
import edu.ufpr.gres.moea.problem.LPSTestingProblem;
import edu.ufpr.gres.moea.problem.Product;

public class IBEAMain {

    public static void main(String[] args) throws Exception {

        File file = new File("experiments");
        if (!file.exists()) {
            file.mkdir();
        }

        String path = "experiments/tmp_experiments";
        String algorithms = "IBEA";
        int executions = 30;

        LPSTestingProblem problem;
        Algorithm algorithm; // The algorithm to use
        Crossover crossover;
        Mutation mutation;

        String productsPath = "products_mutants/PROD_";
        String mutantsPath = "products_mutants/PROD_MUTANTS_";

        problem = new LPSTestingProblem(productsPath, mutantsPath);
        List<Product> products = ((LPSTestingProblem) problem).getProducts();

        IBEA.Builder builder = new IBEA.Builder(problem);

        int populationSize = 100;
        builder.setPopulationSize(populationSize);

        int maxEvaluations = 25000;
        builder.setMaxEvaluations(maxEvaluations);

        int archiveSize = 100;
        builder.setArchiveSize(archiveSize);

        double crossoverProbability = 0.9;
        crossover = new ProductPMXCrossover.Builder().setProbability(crossoverProbability).build();
        builder.setCrossover(crossover);

        double mutationProbability = 1.0 / problem.getNumberOfVariables();

        mutation = new ProductMutation.Builder().setProducts(products).setProbability(mutationProbability).build();
        builder.setMutation(mutation);

        builder.setSelection(new BinaryTournament.Builder().setComparator(new FitnessComparator()).build());

        algorithm = builder.build();

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
