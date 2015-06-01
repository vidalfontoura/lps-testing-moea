package edu.ufpr.gres.moea.operators;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.uma.jmetal.core.Solution;
import org.uma.jmetal.core.Variable;

import edu.ufpr.gres.moea.problem.ProductsPermutation;
import edu.ufpr.gres.moea.problem.ProductsPermutationSolutionType;

public class ProductPMXCrossoverTest {

    private ProductPMXCrossover productPMXCrossover;

    @Before
    public void setup() {

        productPMXCrossover = new ProductPMXCrossover.Builder().setProbability(1).build();

    }

    /**
     * Tests the following scenario
     * 
     * <pre>
     *  parent1: 2, 3, 4, 1
     *  parent2: 8, 9, 10, 2
     *  
     *  expected
     *  child1: 2, 3, 10, 2
     *  child2: 8, 9, 10, 2
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

        Solution[] offSpring = (Solution[]) productPMXCrossover.execute(parents);

        ProductsPermutation child1 = (ProductsPermutation) offSpring[0].getDecisionVariables()[0];

        ProductsPermutation child2 = (ProductsPermutation) offSpring[1].getDecisionVariables()[0];

        Assert.assertArrayEquals(new int[] { 2, 3, 10, 2 }, child1.getVector());
        Assert.assertArrayEquals(new int[] { 8, 9, 4, 1 }, child2.getVector());
    }

    /**
     * Tests the following scenario
     * 
     * <pre>
     *  parent1: 2, 3, 4, 1, 7
     *  parent2: 8, 9, 10, 11
     *  
     *  expected
     *  child1: 2, 3, 10, 11
     *  child2: 8, 9, 4, 1, 7
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

        Solution[] offSpring = (Solution[]) productPMXCrossover.execute(parents);

        ProductsPermutation child1 = (ProductsPermutation) offSpring[0].getDecisionVariables()[0];

        ProductsPermutation child2 = (ProductsPermutation) offSpring[1].getDecisionVariables()[0];

        Assert.assertArrayEquals(new int[] { 2, 3, 10, 11 }, child1.getVector());
        Assert.assertArrayEquals(new int[] { 8, 9, 4, 1, 7 }, child2.getVector());
    }

    /**
     * Tests the following scenario
     * 
     * <pre>
     *  parent1: 2
     *  parent2: 8
     *  
     *  expected
     *  child1: 2
     *  child2: 8
     * 
     * </pre>
     * 
     * 
     */

    @Test
    public void testParent1Size1Parent2Size1PMXCrossover() {

        Solution[] parents = new Solution[2];
        Solution parent1 = new Solution();
        Solution parent2 = new Solution();

        Variable variable1 = new ProductsPermutation(new int[] { 2 });
        Variable variable2 = new ProductsPermutation(new int[] { 8 });

        parent1.setDecisionVariables(new Variable[] { variable1 });
        parent2.setDecisionVariables(new Variable[] { variable2 });

        parent1.setType(new ProductsPermutationSolutionType());
        parent2.setType(new ProductsPermutationSolutionType());

        parents[0] = parent1;
        parents[1] = parent2;

        Solution[] offSpring = (Solution[]) productPMXCrossover.execute(parents);

        ProductsPermutation child1 = (ProductsPermutation) offSpring[0].getDecisionVariables()[0];

        ProductsPermutation child2 = (ProductsPermutation) offSpring[1].getDecisionVariables()[0];

        Assert.assertArrayEquals(new int[] { 2 }, child1.getVector());
        Assert.assertArrayEquals(new int[] { 8 }, child2.getVector());
    }

    /**
     * Tests the following scenario
     * 
     * <pre>
     *  parent1: 2
     *  parent2: 8,4,5,2
     *  
     *  expected
     *  child1: 2,8,4
     *  child2: 2,5,2
     * 
     * </pre>
     * 
     * 
     */

    @Test
    public void testParent1Size1Parent2Size4PMXCrossover() {

        Solution[] parents = new Solution[2];
        Solution parent1 = new Solution();
        Solution parent2 = new Solution();

        Variable variable1 = new ProductsPermutation(new int[] { 2 });
        Variable variable2 = new ProductsPermutation(new int[] { 8, 4, 5, 2 });

        parent1.setDecisionVariables(new Variable[] { variable1 });
        parent2.setDecisionVariables(new Variable[] { variable2 });

        parent1.setType(new ProductsPermutationSolutionType());
        parent2.setType(new ProductsPermutationSolutionType());

        parents[0] = parent1;
        parents[1] = parent2;

        Solution[] offSpring = (Solution[]) productPMXCrossover.execute(parents);

        ProductsPermutation child1 = (ProductsPermutation) offSpring[0].getDecisionVariables()[0];

        ProductsPermutation child2 = (ProductsPermutation) offSpring[1].getDecisionVariables()[0];

        Assert.assertArrayEquals(new int[] { 2, 8, 4 }, child1.getVector());
        Assert.assertArrayEquals(new int[] { 2, 5, 2 }, child2.getVector());
    }

    /**
     * Tests the following scenario
     * 
     * <pre>
     *  parent1: 8,4,5,2 
     *  parent2: 2
     *  
     *  expected
     *  child1:  2,8,4
     *  child2:  2,5,2
     * 
     * </pre>
     * 
     * 
     */

    @Test
    public void testParent1Size4Parent2Size1PMXCrossover() {

        Solution[] parents = new Solution[2];
        Solution parent1 = new Solution();
        Solution parent2 = new Solution();

        Variable variable1 = new ProductsPermutation(new int[] { 8, 4, 5, 2 });
        Variable variable2 = new ProductsPermutation(new int[] { 2 });

        parent1.setDecisionVariables(new Variable[] { variable1 });
        parent2.setDecisionVariables(new Variable[] { variable2 });

        parent1.setType(new ProductsPermutationSolutionType());
        parent2.setType(new ProductsPermutationSolutionType());

        parents[0] = parent1;
        parents[1] = parent2;

        Solution[] offSpring = (Solution[]) productPMXCrossover.execute(parents);

        ProductsPermutation child1 = (ProductsPermutation) offSpring[0].getDecisionVariables()[0];

        ProductsPermutation child2 = (ProductsPermutation) offSpring[1].getDecisionVariables()[0];

        Assert.assertArrayEquals(new int[] { 2, 8, 4 }, child1.getVector());
        Assert.assertArrayEquals(new int[] { 2, 5, 2 }, child2.getVector());
    }

    /**
     * Tests the following scenario
     * 
     * <pre>
     *  parent1: 8,4,5,2,3
     *  parent2: 2
     *  
     *  expected
     *  child1: 2,8,4
     *  child2:  2,5,2,3
     * 
     * </pre>
     * 
     * 
     */

    @Test
    public void testParent1Size5Parent2Size1PMXCrossover() {

        Solution[] parents = new Solution[2];
        Solution parent1 = new Solution();
        Solution parent2 = new Solution();

        Variable variable1 = new ProductsPermutation(new int[] { 8, 4, 5, 2, 3 });
        Variable variable2 = new ProductsPermutation(new int[] { 2 });

        parent1.setDecisionVariables(new Variable[] { variable1 });
        parent2.setDecisionVariables(new Variable[] { variable2 });

        parent1.setType(new ProductsPermutationSolutionType());
        parent2.setType(new ProductsPermutationSolutionType());

        parents[0] = parent1;
        parents[1] = parent2;

        Solution[] offSpring = (Solution[]) productPMXCrossover.execute(parents);

        ProductsPermutation child1 = (ProductsPermutation) offSpring[0].getDecisionVariables()[0];

        ProductsPermutation child2 = (ProductsPermutation) offSpring[1].getDecisionVariables()[0];

        Assert.assertArrayEquals(new int[] { 2, 8, 4 }, child1.getVector());
        Assert.assertArrayEquals(new int[] { 2, 5, 2, 3 }, child2.getVector());
    }

    /**
     * Tests the following scenario
     * 
     * <pre>
     *  parent1: 2 
     *  parent2: 8,4,5,2,3
     *  
     *  expected
     *  child1: 2,8,4
     *  child2:  2,5,2,3
     * 
     * </pre>
     * 
     * 
     */

    @Test
    public void testParent1Size1Parent2Size5PMXCrossover() {

        Solution[] parents = new Solution[2];
        Solution parent1 = new Solution();
        Solution parent2 = new Solution();

        Variable variable1 = new ProductsPermutation(new int[] { 8, 4, 5, 2, 3 });
        Variable variable2 = new ProductsPermutation(new int[] { 2 });

        parent1.setDecisionVariables(new Variable[] { variable1 });
        parent2.setDecisionVariables(new Variable[] { variable2 });

        parent1.setType(new ProductsPermutationSolutionType());
        parent2.setType(new ProductsPermutationSolutionType());

        parents[0] = parent1;
        parents[1] = parent2;

        Solution[] offSpring = (Solution[]) productPMXCrossover.execute(parents);

        ProductsPermutation child1 = (ProductsPermutation) offSpring[0].getDecisionVariables()[0];

        ProductsPermutation child2 = (ProductsPermutation) offSpring[1].getDecisionVariables()[0];

        Assert.assertArrayEquals(new int[] { 2, 8, 4 }, child1.getVector());
        Assert.assertArrayEquals(new int[] { 2, 5, 2, 3 }, child2.getVector());
    }
}
