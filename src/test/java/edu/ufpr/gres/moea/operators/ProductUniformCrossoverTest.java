package edu.ufpr.gres.moea.operators;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.uma.jmetal.core.Solution;
import org.uma.jmetal.core.Variable;
import org.uma.jmetal.util.random.PseudoRandom;

import edu.ufpr.gres.moea.problem.ProductsPermutation;
import edu.ufpr.gres.moea.problem.ProductsPermutationSolutionType;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PseudoRandom.class)
public class ProductUniformCrossoverTest {

    private ProductUniformCrossover productUniformCrossover;

    @Before
    public void setup() {

        PowerMockito.mockStatic(PseudoRandom.class);

        productUniformCrossover = new ProductUniformCrossover.Builder().setProbability(1).build();

        PowerMockito.when(PseudoRandom.randDouble(0, 1)).thenAnswer(new Answer<Double>() {

            int count = 0;

            @Override
            public Double answer(InvocationOnMock invocation) throws Throwable {

                switch (count) {
                    case 0:
                        count++;
                        return 0.2;
                    case 1:
                        count++;
                        return 0.6;
                    case 2:
                        count++;
                        return 0.3;
                    case 3:
                        count++;
                        return 0.7;
                    case 4:
                        count++;
                        return 0.2;
                    case 5:
                        count++;
                        return 0.8;
                    default:
                        return null;
                }
            }
        });
    }

    /**
     * Tests the following scenario
     * 
     * <pre>
     *  parent1: 2, 3, 4, 1
     *  parent2: 8, 9, 10, 2
     *  
     *  expected
     *  child1: 2, 9, 4, 
     *  child2: 8, 3, 10, 1
     *         
     *  expected to remove the duplicate 2
     * 
     * </pre>
     * 
     * 
     */

    @Test
    public void testEvenParentsPMXCrossover() {

        Solution[] parents = new Solution[2];
        Solution parent1 = new Solution();
        Solution parent2 = new Solution();

        Variable variable1 = new ProductsPermutation(new int[] { 2, 3, 4, 1 });
        Variable variable2 = new ProductsPermutation(new int[] { 8, 9, 10, 2 });

        parent1.setDecisionVariables(new Variable[] { variable1 });
        parent2.setDecisionVariables(new Variable[] { variable2 });

        parent1.setType(new ProductsPermutationSolutionType());
        parent2.setType(new ProductsPermutationSolutionType());

        parents[0] = parent1;
        parents[1] = parent2;

        Solution[] offSpring = (Solution[]) productUniformCrossover.execute(parents);

        ProductsPermutation child1 = (ProductsPermutation) offSpring[0].getDecisionVariables()[0];

        ProductsPermutation child2 = (ProductsPermutation) offSpring[1].getDecisionVariables()[0];

        Assert.assertArrayEquals(new int[] { 2, 4, 9, }, child1.getVector());
        System.out.println(child1.toString());
        System.out.println(child2.toString());
        Assert.assertArrayEquals(new int[] { 1, 3, 8, 10 }, child2.getVector());
    }

    /**
     * Tests the following scenario
     * 
     * <pre>
     *  parent1: 2, 3, 4, 1, 7
     *  parent2: 8, 9, 10, 11
     *  
     *  expected
     *  child1: 
     *  child2: 
     *  
     *  ordered child 2:
     * 
     * </pre>
     * 
     * 
     */

    @Test
    public void testOddParent1EvenParent2PMXCrossover() {

        Solution[] parents = new Solution[2];
        Solution parent1 = new Solution();
        Solution parent2 = new Solution();

        Variable variable1 = new ProductsPermutation(new int[] { 2, 3, 4, 1, 7 });
        Variable variable2 = new ProductsPermutation(new int[] { 8, 9, 10, 11 });

        parent1.setDecisionVariables(new Variable[] { variable1 });
        parent2.setDecisionVariables(new Variable[] { variable2 });

        parent1.setType(new ProductsPermutationSolutionType());
        parent2.setType(new ProductsPermutationSolutionType());

        parents[0] = parent1;
        parents[1] = parent2;

        Solution[] offSpring = (Solution[]) productUniformCrossover.execute(parents);

        ProductsPermutation child1 = (ProductsPermutation) offSpring[0].getDecisionVariables()[0];

        ProductsPermutation child2 = (ProductsPermutation) offSpring[1].getDecisionVariables()[0];

        System.out.println(child1.toString());
        Assert.assertArrayEquals(new int[] { 2, 4, 7, 9, 11 }, child1.getVector());
        System.out.println(child2.toString());
        Assert.assertArrayEquals(new int[] { 0, 1, 3, 8, 10, }, child2.getVector());
    }

    /**
     * Tests the following scenario
     * 
     * <pre>
     *  parent1: 2, 3, 4, 1
     *  parent2: 8, 9, 10, 11, 5, 6
     *  
     *  expected
     *  child1: 2, 9, 4  11, 5
     *  child2: 9, 3, 10 1   6
     *  
     *  ordered child 2:
     * 
     * </pre>
     * 
     * 
     */

    @Test
    public void testParent2BiggerThanParent1Crossover() {

        Solution[] parents = new Solution[2];
        Solution parent1 = new Solution();
        Solution parent2 = new Solution();

        Variable variable1 = new ProductsPermutation(new int[] { 2, 3, 4, 1 });
        Variable variable2 = new ProductsPermutation(new int[] { 8, 9, 10, 11, 5, 6 });

        parent1.setDecisionVariables(new Variable[] { variable1 });
        parent2.setDecisionVariables(new Variable[] { variable2 });

        parent1.setType(new ProductsPermutationSolutionType());
        parent2.setType(new ProductsPermutationSolutionType());

        parents[0] = parent1;
        parents[1] = parent2;

        Solution[] offSpring = (Solution[]) productUniformCrossover.execute(parents);

        ProductsPermutation child1 = (ProductsPermutation) offSpring[0].getDecisionVariables()[0];

        ProductsPermutation child2 = (ProductsPermutation) offSpring[1].getDecisionVariables()[0];

        System.out.println(child1.toString());
        Assert.assertArrayEquals(new int[] { 0, 2, 4, 6, 9, 11 }, child1.getVector());
        System.out.println(child2.toString());
        Assert.assertArrayEquals(new int[] { 1, 3, 5, 6, 8, 10 }, child2.getVector());
    }
}
