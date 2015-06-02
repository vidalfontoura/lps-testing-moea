package edu.ufpr.gres.moea.operators;

import java.util.List;

import org.uma.jmetal.core.Solution;
import org.uma.jmetal.core.Variable;
import org.uma.jmetal.operator.mutation.Mutation;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.random.PseudoRandom;

import edu.ufpr.gres.moea.problem.Product;
import edu.ufpr.gres.moea.problem.ProductsPermutation;
import edu.ufpr.gres.moea.problem.ProductsPermutationSolutionType;

public class ProductMutation extends Mutation {

    private double mutationProbability = 0.0;
    private List<Product> products;

    /** Constructor */
    private ProductMutation(Builder builder) {

        addValidSolutionType(ProductsPermutationSolutionType.class);
        mutationProbability = builder.mutationProbability;
        products = builder.products;
    }

    public double getMutationProbability() {

        return mutationProbability;
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
            throw new JMetalException("ProductMutation.execute: the solution " + "type " + solution.getType()
                + " is not allowed with this operator");
        }

        doMutation(mutationProbability, solution);
        return solution;
    }

    /** Perform the mutation operation */
    public void doMutation(double probability, Solution solution) throws JMetalException {

        if (PseudoRandom.randDouble() <= probability) {

            ProductsPermutation permutation = (ProductsPermutation) solution.getDecisionVariables()[0];
            // int randInt = PseudoRandom.randInt(0, 2);
            int randInt = 0;
            int[] newVector = null;
            switch (randInt) {
                case 0: {
                    // Add one product
                    int[] vector = permutation.getVector();
                    
                    break;
                }
                case 1: {
                    // remove one
                    // int[] vector = permutation.getVector();
                    // int removeProductIndex = PseudoRandom.randInt(0,
                    // vector.length - 1);
                    // if (removeProductIndex > 0 && removeProductIndex <
                    // vector.length) {
                    // int[] firstHalf = Arrays.copyOfRange(vector, 0,
                    // removeProductIndex);
                    // int[] secondHalf = Arrays.copyOfRange(vector,
                    // removeProductIndex + 1, vector.length);
                    // newVector = ArrayUtils.addAll(firstHalf, secondHalf);
                    // } else if (removeProductIndex == 0) {
                    // newVector = Arrays.copyOfRange(vector, 1, vector.length);
                    // } else if (removeProductIndex == vector.length - 1) {
                    // newVector = Arrays.copyOfRange(vector, 0, vector.length -
                    // 1);
                    // }
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

        private double mutationProbability;
        private List<Product> products;

        public Builder() {

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
