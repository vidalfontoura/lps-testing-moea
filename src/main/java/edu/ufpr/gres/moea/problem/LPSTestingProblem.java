package edu.ufpr.gres.moea.problem;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.uma.jmetal.core.Problem;
import org.uma.jmetal.core.Solution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.random.PseudoRandom;

public class LPSTestingProblem extends Problem {

    private static final long serialVersionUID = -707604624795486334L;

    private List<Product> products;

    private int maxUpperLimit;

    public LPSTestingProblem(String productsPath, String mutantsPath) throws IOException {

        super();
        this.products = ProductReaderUtils.readProductsAndMutants(productsPath, mutantsPath);
        this.numberOfObjectives = 2;
        this.numberOfConstraints = 0;
        this.problemName = "LPSTestingProblem";
        this.numberOfVariables = 1;

        maxUpperLimit = 449;
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

        int mutationScore = this.calculateMutationScore(solution);

        ProductsPermutation permutation = (ProductsPermutation) solution.getDecisionVariables()[0];

        int productsSize = permutation.getSize();
        solution.setObjective(0, -mutationScore);
        solution.setObjective(1, productsSize);

    }

    public int calculateMutationScore(Solution solutionProducts) {

        int mutantsKilled = 0;
        ProductsPermutation permutation = (ProductsPermutation) solutionProducts.getDecisionVariables()[0];

        int[] vector = permutation.getVector();
        // for (int i = 0; i < vector.length; i++) {
        // System.out.print(vector[i] + ",");
        // }
        // System.out.println();

        for (int i = 0; i < vector.length; i++) {
            int productId = vector[i];
            Optional<Product> optProduct = products.stream().filter(product -> {
                return product.getProductId().equals(String.valueOf(productId));
            }).findFirst();
            if (optProduct.isPresent()) {
                Product product = optProduct.get();
                int size = product.getMutants().size();
                mutantsKilled += size;
            } else {
                // Product didn't find check how to handle this
                System.out.println("Product does not exists");
                // Repare or penalize
            }
        }
        return mutantsKilled;
    }

    public int getNumberOfProducts() {

        int randInt = PseudoRandom.randInt(1, maxUpperLimit);
        return randInt;
    }

    public int getMaxUpperLimit() {

        return maxUpperLimit;
    }

    public List<Product> getProducts() {

        return products;
    }

}
