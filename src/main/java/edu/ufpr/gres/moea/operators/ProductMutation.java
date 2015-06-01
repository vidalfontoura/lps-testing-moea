package edu.ufpr.gres.moea.operators;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.uma.jmetal.core.Solution;
import org.uma.jmetal.core.Variable;
import org.uma.jmetal.operator.mutation.Mutation;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.random.PseudoRandom;

import edu.ufpr.gres.moea.problem.Product;
import edu.ufpr.gres.moea.problem.ProductsPermutation;
import edu.ufpr.gres.moea.problem.ProductsPermutationSolutionType;

public class ProductMutation extends Mutation {

    private static final double ETA_M_DEFAULT = 20.0;
    private double distributionIndex = ETA_M_DEFAULT;

    private double mutationProbability = 0.0;
    private List<Product> products;

    /** Constructor */
    private ProductMutation(Builder builder) {

        addValidSolutionType(ProductsPermutationSolutionType.class);

        mutationProbability = builder.mutationProbability;
        distributionIndex = builder.distributionIndex;
        products = builder.products;
    }

    public double getMutationProbability() {

        return mutationProbability;
    }

    public double getDistributionIndex() {

        return distributionIndex;
    }

    /** run() method */
    public Object execute(Object object) throws JMetalException {

        if (null == object) {
            throw new JMetalException("Null parameter");
        } else if (!(object instanceof Solution)) {
            throw new JMetalException("Invalid parameter class");
        }

        Solution solution = (Solution) object;

        if (!solutionTypeIsValid(solution)) {
            throw new JMetalException("PolynomialMutation.execute: the solution " + "type " + solution.getType()
                + " is not allowed with this operator");
        }

        doMutation(mutationProbability, solution);
        return solution;
    }

    /** Perform the mutation operation */
    public void doMutation(double probability, Solution solution) throws JMetalException {

        if (PseudoRandom.randDouble() <= probability) {

            ProductsPermutation permutation = (ProductsPermutation) solution.getDecisionVariables()[0];
            int randInt = PseudoRandom.randInt(0, 2);
            int[] newVector = null;
            switch (randInt) {
                case 0: {
                    // Add one product
                    int[] vector = permutation.getVector();
                    boolean foundNewProduct = false;
                    while (foundNewProduct == false) {
                        int newProductIndex = PseudoRandom.randInt(0, products.size() - 1);
                        Product product = products.get(newProductIndex);
                        for (int i = 0; i < vector.length; i++) {
                            if (vector[i] != Integer.valueOf(product.getProductId())) {
                                newVector = new int[vector.length + 1];
                                newVector = ArrayUtils.addAll(vector, Integer.valueOf(product.getProductId()));
                                break;
                            }
                        }
                    }
                    break;
                }
                case 1: {
                    // remove one
                    int[] vector = permutation.getVector();
                    int removeProductIndex = PseudoRandom.randInt(0, vector.length - 1);
                    if (removeProductIndex > 0 && removeProductIndex < vector.length) {
                        int[] firstHalf = Arrays.copyOfRange(vector, 0, removeProductIndex);
                        int[] secondHalf = Arrays.copyOfRange(vector, removeProductIndex + 1, vector.length);
                        newVector = ArrayUtils.addAll(firstHalf, secondHalf);
                    } else if (removeProductIndex == 0) {
                        newVector = Arrays.copyOfRange(vector, 1, vector.length);
                    } else if (removeProductIndex == vector.length - 1) {
                        newVector = Arrays.copyOfRange(vector, 0, vector.length - 1);
                    }
                    break;
                }
                case 2: {
                    // do nothing for now
                    break;
                }
                default:
                    break;
            }
            if (newVector != null) {
                ProductsPermutation productsPermutation = new ProductsPermutation(newVector);
                solution.setDecisionVariables(new Variable[] { productsPermutation });
            }
        }

    }

    /** Builder class */
    public static class Builder {

        private double distributionIndex;
        private double mutationProbability;
        private List<Product> products;

        public Builder() {

            distributionIndex = ETA_M_DEFAULT;
        }

        public Builder setDistributionIndex(double distributionIndex) {

            this.distributionIndex = distributionIndex;

            return this;
        }

        public Builder setProbability(double probability) {

            mutationProbability = probability;

            return this;
        }

        public Builder setProducts(List<Product> products) {

            this.products = products;
            return this;
        }

        public ProductMutation build() {

            return new ProductMutation(this);
        }
    }
}
