package edu.ufpr.gres.moea.problem;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.uma.jmetal.core.Problem;
import org.uma.jmetal.core.Solution;
import org.uma.jmetal.core.SolutionSet;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.random.PseudoRandom;

public class LPSTestingProblem extends Problem {

    private static final long serialVersionUID = -707604624795486334L;

    private List<Product> products;

    private int maxUpperLimit;
    private Set<String> totalActiveMutants;
    private Set<String> totalPairsWise;

    int count = 0;

    public LPSTestingProblem(String productsPath, String mutantsPath, String pairsPath) throws IOException {

        super();
        this.products = ProductReaderUtils.readProductsAndMutants(productsPath, mutantsPath, pairsPath);
        this.totalActiveMutants = new HashSet<String>();
        this.products.stream().forEach(product -> {
            totalActiveMutants.addAll(product.getMutants());
        });

        this.totalPairsWise = new HashSet<String>();
        this.products.stream().forEach(product -> {
            totalPairsWise.addAll(product.getPairs());
        });

        this.numberOfObjectives = 3;
        this.numberOfConstraints = 0;
        this.problemName = "LPSTestingProblem";
        this.numberOfVariables = 1;

        maxUpperLimit = this.products.size();
        upperLimit = new double[numberOfVariables];
        lowerLimit = new double[numberOfVariables];

        for (int i = 0; i < numberOfVariables; i++) {
            lowerLimit[i] = 0;
            upperLimit[i] = maxUpperLimit;
        }

        solutionType = new ProductsPermutationSolutionType(this);

    }

    @Override
    public void evaluate(Solution solution) throws JMetalException {

        ProductsPermutation permutation = (ProductsPermutation) solution.getDecisionVariables()[0];

        int productsSize = permutation.getVector().length;

        double mutationScore = this.getMutationScore(permutation);
        double pairWiseScore = this.getPairsWiseScore(permutation);
        double productsSizeObj = (double) productsSize / products.size();
        //
        // System.out.println("Mutation Score: " + mutationScore);
        // System.out.println("Size: " + productsSizeObj);
        // System.out.println("Distinct features:" + distinctFeatures);
        solution.setObjective(0, -mutationScore);
        solution.setObjective(1, productsSizeObj);
        solution.setObjective(2, -pairWiseScore);

    }

    public double getPairsWiseScore(ProductsPermutation permutation) {

        Set<String> pairsWiseSet = new HashSet<String>();
        int[] vector = permutation.getVector();
        for (int i = 0; i < vector.length; i++) {
            int productId = vector[i];
            Optional<Product> optProduct = products.stream().filter(product -> {
                return product.getProductId().equals(String.valueOf(productId));
            }).findFirst();
            if (optProduct.isPresent()) {
                Product product = optProduct.get();
                pairsWiseSet.addAll(product.getPairs());
            } else {
                // Product didn't find check how to handle this
                System.out.println("Product: " + productId + " does not exists");
            }
        }

        // System.out.println("Pairs Wise Coverage: " + pairsWiseSet.size());

        double pairsWiseCoverage = (double) pairsWiseSet.size() / totalPairsWise.size();

        return pairsWiseCoverage;

    }

    public double getMutationScore(ProductsPermutation permutation) {

        Set<String> mutantsKilled = new HashSet<String>();
        int[] vector = permutation.getVector();
        for (int i = 0; i < vector.length; i++) {
            int productId = vector[i];
            Optional<Product> optProduct = products.stream().filter(product -> {
                return product.getProductId().equals(String.valueOf(productId));
            }).findFirst();
            if (optProduct.isPresent()) {
                Product product = optProduct.get();
                mutantsKilled.addAll(product.getMutants());
            } else {
                // Product didn't find check how to handle this
                System.out.println("Product: " + productId + " does not exists");
            }
        }

        // System.out.println("Mutantes mortos:" + mutantsKilled.size());
        // System.out.println("Total Mutants:" + totalActiveMutants.size());
        double mutationScore = (double) mutantsKilled.size() / totalActiveMutants.size();
        // System.out.println("Mutation score: " + mutationScore);

        // return mutantsKilled.size();
        return mutationScore;
    }

    public SolutionSet removeDominateds(SolutionSet result) {

        boolean dominador, dominado;
        double valor1 = 0;
        double valor2 = 0;

        for (int i = 0; i < (result.size() - 1); i++) {
            for (int j = (i + 1); j < result.size(); j++) {
                dominador = true;
                dominado = true;

                for (int k = 0; k < numberOfObjectives; k++) {
                    valor1 = result.get(i).getObjective(k);
                    valor2 = result.get(j).getObjective(k);

                    if (valor1 > valor2 || dominador == false) {
                        dominador = false;
                    } else if (valor1 <= valor2) {
                        dominador = true;
                    }

                    if (valor2 > valor1 || dominado == false) {
                        dominado = false;
                    } else if (valor2 < valor1) {
                        dominado = true;
                    }
                }

                if (dominador) {
                    result.remove(j);
                    j = j - 1;
                } else if (dominado) {
                    result.remove(i);
                    j = i;
                }
            }
        }

        return result;
    }

    public SolutionSet removeDuplicates(SolutionSet result) {

        String solucao;

        for (int i = 0; i < result.size() - 1; i++) {
            solucao = result.get(i).getDecisionVariables()[0].toString();
            for (int j = i + 1; j < result.size(); j++) {
                if (solucao.equals(result.get(j).getDecisionVariables()[0].toString())) {
                    result.remove(j);
                }
            }
        }

        return result;
    }

    public int getNumberOfProducts() {

        int randInt = PseudoRandom.randInt(0, this.products.size() - 1);
        return randInt;
    }

    public int getMaxUpperLimit() {

        return maxUpperLimit;
    }

    public List<Product> getProducts() {

        return products;
    }

}
