package edu.ufpr.gres.moea.operators;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.uma.jmetal.core.Solution;
import org.uma.jmetal.core.Variable;
import org.uma.jmetal.encoding.solutiontype.PermutationSolutionType;
import org.uma.jmetal.operator.crossover.Crossover;
import org.uma.jmetal.util.JMetalException;

import edu.ufpr.gres.moea.problem.ProductsPermutation;
import edu.ufpr.gres.moea.problem.ProductsPermutationSolutionType;

public class ProductPMXCrossover extends Crossover {

    private static final long serialVersionUID = -4059314233890056350L;
    private static final double DEFAULT_PROBABILITY = 0.9;

    private double crossoverProbability;

    /** Constructor */
    private ProductPMXCrossover(Builder builder) {

        addValidSolutionType(PermutationSolutionType.class);
        addValidSolutionType(ProductsPermutationSolutionType.class);

        crossoverProbability = builder.crossoverProbability;
    }

    /* Getter */
    public double getCrossoverProbability() {

        return crossoverProbability;
    }

    /** Builder class */
    public static class Builder {

        private double crossoverProbability;

        public Builder() {

            crossoverProbability = DEFAULT_PROBABILITY;
        }

        public Builder setProbability(double probability) {

            if ((probability < 0) || (probability > 1.0)) {
                throw new JMetalException("Probability value invalid: " + probability);
            } else {
                crossoverProbability = probability;
            }

            return this;
        }

        public ProductPMXCrossover build() {

            return new ProductPMXCrossover(this);
        }
    }

    /** Execute() method */
    public Object execute(Object object) throws JMetalException {

        if (null == object) {
            throw new JMetalException("Null parameter");
        } else if (!(object instanceof Solution[])) {
            throw new JMetalException("Invalid parameter class");
        }

        Solution[] parents = (Solution[]) object;

        if (parents.length != 2) {
            throw new JMetalException("PMXCrossover.execute: operator needs two " + "parents");
        }

        if (!solutionTypeIsValid(parents)) {
            throw new JMetalException("PMXCrossover.execute: the solutions " + "type " + parents[0].getType()
                + " is not allowed with this operator");
        }

        Solution[] offspring = doCrossover(crossoverProbability, parents[0], parents[1]);

        return offspring;
    }

    /**
     * Perform the crossover operation
     *
     * @param probability Crossover setProbability
     * @param parent1 The first parent
     * @param parent2 The second parent
     * @return An array containig the two offsprings
     * @throws org.uma.jmetal.util.JMetalException
     */
    public Solution[] doCrossover(double probability, Solution parent1, Solution parent2) {

        Solution[] offspring = new Solution[2];

        offspring[0] = new Solution(parent1);
        offspring[1] = new Solution(parent2);

        ProductsPermutation permutation1 = (ProductsPermutation) parent1.getDecisionVariables()[0];
        ProductsPermutation permutation2 = (ProductsPermutation) parent2.getDecisionVariables()[0];

        int size1 = permutation1.getVector().length;
        int size2 = permutation2.getVector().length;

        int[] parentVector1 = permutation1.getVector();
        int[] parentVector2 = permutation2.getVector();

        int[] childVector1 = Arrays.copyOf(parentVector1, parentVector1.length);
        int[] childVector2 = Arrays.copyOf(parentVector2, parentVector2.length);
        if (size1 == 1 && size2 == 1) {
            return offspring;
        }
        if (size1 > 1 && size2 > 1) {
            int[] firstHalfVector1;
            int[] secondHalfVector1;

            int[] firstHalfVector2;
            int[] secondHalfVector2;
            if ((size1 % 2) == 0) {

                firstHalfVector1 = Arrays.copyOfRange(parentVector1, 0, size1 / 2);
                secondHalfVector1 = Arrays.copyOfRange(parentVector1, size1 / 2, size1);

            } else {
                int endFirstPart = size1 / 2;
                firstHalfVector1 = Arrays.copyOfRange(parentVector1, 0, endFirstPart);
                secondHalfVector1 = Arrays.copyOfRange(parentVector1, endFirstPart, size1);
            }

            if ((size2 % 2) == 0) {

                firstHalfVector2 = Arrays.copyOfRange(parentVector2, 0, size2 / 2);
                secondHalfVector2 = Arrays.copyOfRange(parentVector2, size2 / 2, size2);

            } else {
                int endFirstPart = size2 / 2;
                firstHalfVector2 = Arrays.copyOfRange(parentVector2, 0, endFirstPart);
                secondHalfVector2 = Arrays.copyOfRange(parentVector2, endFirstPart, size2);
            }

            Set<Integer> childSet1 = new HashSet<Integer>();
            for (int i = 0; i < firstHalfVector1.length; i++) {
                childSet1.add(firstHalfVector1[i]);
            }
            for (int i = 0; i < secondHalfVector2.length; i++) {
                childSet1.add(secondHalfVector2[i]);
            }

            Set<Integer> childSet2 = new HashSet<Integer>();
            for (int i = 0; i < firstHalfVector2.length; i++) {
                childSet2.add(firstHalfVector2[i]);
            }
            for (int i = 0; i < secondHalfVector1.length; i++) {
                childSet2.add(secondHalfVector1[i]);
            }

            childVector1 = new int[childSet1.size()];
            Iterator<Integer> iteratorChildSet1 = childSet1.iterator();
            int count = 0;
            while (iteratorChildSet1.hasNext()) {
                childVector1[count] = iteratorChildSet1.next();
                count++;
            }

            childVector2 = new int[childSet2.size()];
            Iterator<Integer> iteratorChildSet2 = childSet2.iterator();
            count = 0;
            while (iteratorChildSet2.hasNext()) {
                childVector2[count] = iteratorChildSet2.next();
                count++;
            }

        }
        if (size1 == 1 && size2 != 1) {
            int[] firstHalfVector2;
            int[] secondHalfVector2;
            if ((size2 % 2) == 0) {

                firstHalfVector2 = Arrays.copyOfRange(parentVector2, 0, size2 / 2);
                secondHalfVector2 = Arrays.copyOfRange(parentVector2, size2 / 2, size2);

            } else {
                int endFirstPart = size2 / 2;
                firstHalfVector2 = Arrays.copyOfRange(parentVector2, 0, endFirstPart);
                secondHalfVector2 = Arrays.copyOfRange(parentVector2, endFirstPart, size2);
            }

            Set<Integer> childSet1 = new HashSet<Integer>();
            Set<Integer> childSet2 = new HashSet<Integer>();
            for (int i = 0; i < childVector1.length; i++) {
                childSet1.add(childVector1[i]);
                childSet2.add(childVector1[i]);
            }

            for (int i = 0; i < firstHalfVector2.length; i++) {
                childSet1.add(firstHalfVector2[i]);
            }

            for (int i = 0; i < secondHalfVector2.length; i++) {
                childSet2.add(secondHalfVector2[i]);
            }

            childVector1 = new int[childSet1.size()];
            Iterator<Integer> iteratorChildSet1 = childSet1.iterator();
            int count = 0;
            while (iteratorChildSet1.hasNext()) {
                childVector1[count] = iteratorChildSet1.next();
                count++;
            }

            childVector2 = new int[childSet2.size()];
            Iterator<Integer> iteratorChildSet2 = childSet2.iterator();
            count = 0;
            while (iteratorChildSet2.hasNext()) {
                childVector2[count] = iteratorChildSet2.next();
                count++;
            }

        }

        if (size1 != 1 && size2 == 1) {
            int[] firstHalfVector1;
            int[] secondHalfVector1;
            if ((size1 % 2) == 0) {

                firstHalfVector1 = Arrays.copyOfRange(parentVector1, 0, size1 / 2);
                secondHalfVector1 = Arrays.copyOfRange(parentVector1, size1 / 2, size1);

            } else {
                int endFirstPart = size1 / 2;
                firstHalfVector1 = Arrays.copyOfRange(parentVector1, 0, endFirstPart);
                secondHalfVector1 = Arrays.copyOfRange(parentVector1, endFirstPart, size1);
            }

            Set<Integer> childSet1 = new HashSet<Integer>();
            Set<Integer> childSet2 = new HashSet<Integer>();
            for (int i = 0; i < childVector2.length; i++) {
                childSet1.add(childVector2[i]);
                childSet2.add(childVector2[i]);
            }

            for (int i = 0; i < firstHalfVector1.length; i++) {
                childSet1.add(firstHalfVector1[i]);
            }

            for (int i = 0; i < secondHalfVector1.length; i++) {
                childSet2.add(secondHalfVector1[i]);
            }

            childVector1 = new int[childSet1.size()];
            Iterator<Integer> iteratorChildSet1 = childSet1.iterator();
            int count = 0;
            while (iteratorChildSet1.hasNext()) {
                childVector1[count] = iteratorChildSet1.next();
                count++;
            }

            childVector2 = new int[childSet2.size()];
            Iterator<Integer> iteratorChildSet2 = childSet2.iterator();
            count = 0;
            while (iteratorChildSet2.hasNext()) {
                childVector2[count] = iteratorChildSet2.next();
                count++;
            }

        }

        Variable variable1 = new ProductsPermutation(childVector1);
        Variable variable2 = new ProductsPermutation(childVector2);
        offspring[0].setDecisionVariables(new Variable[] { variable1 });
        offspring[1].setDecisionVariables(new Variable[] { variable2 });

        return offspring;
    }
}
