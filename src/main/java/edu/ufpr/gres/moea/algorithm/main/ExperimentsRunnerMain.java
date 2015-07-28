package edu.ufpr.gres.moea.algorithm.main;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.uma.jmetal.core.Algorithm;
import org.uma.jmetal.core.SolutionSet;
import org.uma.jmetal.metaheuristic.multiobjective.ibea.IBEA;
import org.uma.jmetal.metaheuristic.multiobjective.nsgaII.NSGAIITemplate;
import org.uma.jmetal.metaheuristic.multiobjective.spea2.SPEA2;
import org.uma.jmetal.operator.crossover.Crossover;
import org.uma.jmetal.operator.mutation.Mutation;
import org.uma.jmetal.operator.selection.BinaryTournament2;
import org.uma.jmetal.operator.selection.Selection;
import org.uma.jmetal.util.evaluator.SequentialSolutionSetEvaluator;
import org.uma.jmetal.util.evaluator.SolutionSetEvaluator;
import org.uma.jmetal.util.fileoutput.SolutionSetOutput;

import edu.ufpr.gres.moea.operators.ProductMutation;
import edu.ufpr.gres.moea.operators.ProductUniformCrossover;
import edu.ufpr.gres.moea.problem.LPSTestingProblem;
import edu.ufpr.gres.moea.problem.Product;

public class ExperimentsRunnerMain {

    public static void main(String[] args) throws Exception {

        // File logFile = new File(pathname)
        String experimentsPath = "experiments";
        String featureModelPath = experimentsPath + File.separator + "feature-models";

        String resultsPath = experimentsPath + File.separator + "result-test";
        //
        File resultDIR = createDir(resultsPath);
        //
        // PrintStream out = new PrintStream(new FileOutputStream(resultsPath +
        // File.separator + "execution.log"));
        // System.setOut(out);

        String[] algorithms = null;
        if (args[0] != null) {
            String algorithmArg = args[0];
            algorithms = new String[] { algorithmArg };
            System.out.println("User informed the following algorithms: " + algorithmArg);
        } else {
            algorithms = new String[] { "NSGAII", "IBEA", "SPEA2" };
        }

        String[] featureModels = null;
        if (args[1] != null) {
            featureModels = args[1].split(",");
            System.out.println("User informed the following LPS: " + args[1]);
        }

        Double[] crossoverRates = null;
        // CX Rate
        if (args[2] != null) {
            String[] crossRates = args[2].split(",");

            crossoverRates = new Double[crossRates.length];
            for (int i = 0; i < crossRates.length; i++) {
                crossoverRates[i] = Double.valueOf(crossRates[i]);
            }
        } else {
            // SET DEFAULT crossoverRats
        }

        // int executions = 1;
        // if (args[2] != null) {
        // executions = Integer.valueOf(args[2]);
        // }

        Double[] mutationRates = null;
        // Mut Rate
        if (args[3] != null) {
            String[] mutRates = args[3].split(",");

            mutationRates = new Double[mutRates.length];
            for (int i = 0; i < mutRates.length; i++) {
                mutationRates[i] = Double.valueOf(mutRates[i]);
            }
        } else {
            // SET DEFAULT mutation cross
        }

        Integer[] maxEvaluations = null;
        // Max Evaluations
        if (args[4] != null) {
            String[] maxEvals = args[4].split(",");

            maxEvaluations = new Integer[maxEvals.length];
            for (int i = 0; i < maxEvals.length; i++) {
                maxEvaluations[i] = Integer.valueOf(maxEvals[i]);
            }
        } else {
            // SET DEFAULT max evaluations
        }

        Integer[] populationSizes = null;
        if (args[5] != null) {
            String[] popSize = args[5].split(",");
            populationSizes = new Integer[popSize.length];

            for (int i = 0; i < popSize.length; i++) {
                populationSizes[i] = Integer.valueOf(popSize[i]);
            }

        } else {
            // Set Default population size
        }

        int executions = 0;
        if (args[6] != null) {
            executions = Integer.valueOf(args[6]);
        }

        int auxPopulationSize = 50;

        for (String featureModel : featureModels) {
            System.out.println(featureModel);
            String productsPath = featureModelPath + File.separator + featureModel + File.separator + "PROD_";
            String productsMutantsPath =
                featureModelPath + File.separator + featureModel + File.separator + "PROD_MUTANTS_";
            String productsPairsPath =
                featureModelPath + File.separator + featureModel + File.separator + "PROD_PAIRS_";
            LPSTestingProblem problem = new LPSTestingProblem(productsPath, productsMutantsPath, productsPairsPath);
            List<Product> products = ((LPSTestingProblem) problem).getProducts();

            String featureModelResultPath = resultDIR.getPath() + File.separator + featureModel;
            File featureModelResultDIR = createDir(featureModelResultPath);

            for (String algorithmName : algorithms) {
                System.out.println("-" + algorithmName);

                long startTimeByAlgorithm = System.currentTimeMillis();

                String algorithmPath = featureModelResultDIR.getPath() + File.separator + algorithmName;
                File algorithmDIR = createDir(algorithmPath);

                SolutionSetEvaluator evaluator = new SequentialSolutionSetEvaluator();
                Algorithm algorithm = null;

                for (Integer maxEvaluation : maxEvaluations) {
                    for (Integer populationSize : populationSizes) {

                        for (Double crossoverRate : crossoverRates) {
                            Crossover crossover =
                                new ProductUniformCrossover.Builder().setProbability(crossoverRate).build();

                            for (Double mutationRate : mutationRates) {
                                System.out.println("--MaxEvaluation:" + maxEvaluation);
                                System.out.println("--PopulationSize:" + populationSize);
                                System.out.println("--CxRate:" + crossoverRate);
                                System.out.println("--MuRate:" + mutationRate);
                                Mutation mutation =
                                    new ProductMutation.Builder().setProducts(products).setProbability(mutationRate)
                                        .build();

                                Selection selection = new BinaryTournament2.Builder().build();
                                if (algorithmName.equals("NSGAII")) {
                                    NSGAIITemplate.Builder builder = new NSGAIITemplate.Builder(problem, evaluator);
                                    algorithm =
                                        builder.setMaxEvaluations(maxEvaluation).setPopulationSize(populationSize)
                                            .setSelection(selection).setCrossover(crossover).setMutation(mutation)
                                            .build("NSGAII");
                                } else if (algorithmName.equals("SPEA2")) {

                                    algorithm =
                                        new SPEA2.Builder(problem).setCrossover(crossover).setMutation(mutation)
                                            .setSelection(selection).setMaxEvaluations(maxEvaluation)
                                            .setPopulationSize(populationSize).setArchiveSize(auxPopulationSize)
                                            .build();
                                } else if (algorithmName.equals("IBEA")) {
                                    algorithm =
                                        new IBEA.Builder(problem).setCrossover(crossover).setMutation(mutation)
                                            .setSelection(selection).setMaxEvaluations(maxEvaluation)
                                            .setPopulationSize(populationSize).setArchiveSize(auxPopulationSize)
                                            .build();
                                } else {
                                    System.out.println("AlgorithmName: " + algorithmName + " not supported");
                                    throw new Exception("AlgorithmName: " + algorithmName + " not supported");
                                }

                                String configurationPath =
                                    algorithmDIR.getPath() + File.separator + "ME_" + maxEvaluation + "_" + "PS_"
                                        + populationSize + "_" + "CX_" + crossoverRate + "_" + "MU_" + mutationRate;
                                File configurationDIR = createDir(configurationPath);
                                long allExecutionTime = 0;
                                SolutionSet allRuns = new SolutionSet();

                                System.out.println("Starting executions...");
                                for (int i = 0; i < executions; i++) {

                                    // Execute the Algorithm
                                    System.out.println("Execution: " + (i + 1));
                                    long initTime = System.currentTimeMillis();
                                    SolutionSet population = algorithm.execute();
                                    long estimatedTime = System.currentTimeMillis() - initTime;

                                    allExecutionTime += estimatedTime;

                                    String executionDirectory =
                                        configurationDIR.getPath() + File.separator + "EXECUTION_" + i;
                                    createDir(executionDirectory);

                                    problem.removeDominateds(population);
                                    problem.removeDuplicates(population);

                                    population.printObjectives();

                                    SolutionSetOutput.printVariablesToFile(population, executionDirectory
                                        + File.separator + "VAR.txt");
                                    SolutionSetOutput.printObjectivesToFile(population, executionDirectory
                                        + File.separator + "FUN.txt");

                                    allRuns = allRuns.union(population);
                                }

                                System.out.println("End of executions for problem " + problem.getClass().getName()
                                    + ".");
                                System.out.println("Total time (seconds): " + allExecutionTime / 1000);
                                System.out.println("Writing results.");
                                System.out.println("");

                                problem.removeDominateds(allRuns);
                                problem.removeDuplicates(allRuns);

                                SolutionSetOutput.printVariablesToFile(allRuns, configurationDIR.getPath()
                                    + File.separator + "VAR.txt");
                                SolutionSetOutput.printObjectivesToFile(allRuns, configurationDIR.getPath()
                                    + File.separator + "FUN.txt");

                                File logTimeFile =
                                    new File(configurationDIR.getPath() + File.separator + "totalTime.log");
                                try (FileWriter fileWriter = new FileWriter(logTimeFile)) {
                                    fileWriter.write("Total time (seconds) taken by " + executions + " executions: "
                                        + allExecutionTime / 1000);
                                }

                            }
                        }

                    }

                }
                long endTimeByAlgorithm = System.currentTimeMillis();
                System.out.println("Total time (seconds) taken with " + algorithmName + " : "
                    + (endTimeByAlgorithm - startTimeByAlgorithm));
            }

        }

    }

    private static File createDir(String dir) {

        File file = new File(dir);
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }
}
