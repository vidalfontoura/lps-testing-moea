package edu.ufpr.gres.moea.algorithm.main;

import java.io.File;
import java.util.List;

import org.uma.jmetal.core.Algorithm;
import org.uma.jmetal.core.Operator;
import org.uma.jmetal.core.SolutionSet;
import org.uma.jmetal.metaheuristic.multiobjective.spea2.SPEA2;
import org.uma.jmetal.operator.selection.BinaryTournament2;
import org.uma.jmetal.qualityindicator.QualityIndicatorGetter;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.fileoutput.DefaultFileOutputContext;
import org.uma.jmetal.util.fileoutput.SolutionSetOutput;

import edu.ufpr.gres.moea.operators.ProductMutation;
import edu.ufpr.gres.moea.operators.ProductPMXCrossover;
import edu.ufpr.gres.moea.problem.LPSTestingProblem;
import edu.ufpr.gres.moea.problem.Product;

public class SPEA2Main {

    public static void main(String[] args) throws Exception {

        File file = new File("experiments");
        if (!file.exists()) {
            file.mkdir();
        }

        String path = "experiments/tmp_experiments";
        String algorithms = "SPEA2";
        int executions = 30;

        LPSTestingProblem problem;
        Algorithm algorithm;
        Operator crossover;
        Operator mutation;
        Operator selection;

        QualityIndicatorGetter indicators;

        indicators = null;

        String productsPath = "products_mutants/PROD_";
        String mutantsPath = "products_mutants/PROD_MUTANTS_";

        problem = new LPSTestingProblem(productsPath, mutantsPath);
        List<Product> products = ((LPSTestingProblem) problem).getProducts();

        // crossover = new
        // SBXCrossover.Builder().setDistributionIndex(20.0).setProbability(0.9).build();
        crossover = new ProductPMXCrossover.Builder().setProbability(0.9).build();
        mutation =
            new ProductMutation.Builder().setProducts(products).setProbability(1.0 / problem.getNumberOfVariables())
                .build();

        selection = new BinaryTournament2.Builder().build();

        File rootDir = createDir(path);
        File algorithmDir = createDir(rootDir.getPath() + File.separator + algorithms + File.separator);
        File objectivesDir = createDir(algorithmDir.getPath() + File.separator);

        String outputDir = objectivesDir.getPath() + File.separator;

        SolutionSet allRuns = new SolutionSet();

        algorithm =
            new SPEA2.Builder(problem).setCrossover(crossover).setMutation(mutation).setSelection(selection)
                .setMaxEvaluations(25000).setPopulationSize(100).setArchiveSize(100).build();

        for (int i = 0; i < executions; i++) {

            // Execute the Algorithm
            System.out.println("Execution: " + (i + 1));
            AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();

            SolutionSet population = algorithmRunner.getSolutionSet();
            long computingTime = algorithmRunner.getComputingTime();

            String executionDirectory = outputDir + "EXECUTION_" + i;
            createDir(executionDirectory);

            problem.removeDominateds(population);
            problem.removeDuplicates(population);

            new SolutionSetOutput.Printer(population).setSeparator("\t")
                .setVarFileOutputContext(new DefaultFileOutputContext(executionDirectory + File.separator + "VAR.txt"))
                .setFunFileOutputContext(new DefaultFileOutputContext(executionDirectory + File.separator + "FUN.txt"))
                .print();

            allRuns = allRuns.union(population);

            JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
            JMetalLogger.logger.info("Objectives values have been written to file FUN.tsv");
            JMetalLogger.logger.info("Variables values have been written to file VAR.tsv");

            if (indicators != null) {
                JMetalLogger.logger.info("Quality indicators");
                JMetalLogger.logger.info("Hypervolume: " + indicators.getHypervolume(population));
                JMetalLogger.logger.info("GD         : " + indicators.getGD(population));
                JMetalLogger.logger.info("IGD        : " + indicators.getIGD(population));
                JMetalLogger.logger.info("Spread     : " + indicators.getSpread(population));
                JMetalLogger.logger.info("Epsilon    : " + indicators.getEpsilon(population));
            }
        }

        problem.removeDominateds(allRuns);
        problem.removeDuplicates(allRuns);

        new SolutionSetOutput.Printer(allRuns).setSeparator("\t")
            .setVarFileOutputContext(new DefaultFileOutputContext(outputDir + "VAR.txt"))
            .setFunFileOutputContext(new DefaultFileOutputContext(outputDir + "FUN.txt")).print();
    }

    private static File createDir(String dir) {

        File file = new File(dir);
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }
}
