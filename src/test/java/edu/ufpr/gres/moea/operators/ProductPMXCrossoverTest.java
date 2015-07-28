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
     *  child1: 2, 3, 10
     *  child2: 8, 9, 4, 1
     *  
     *  ordered child2: 1, 4, 8, 9 
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

        Solution[] offSpring = (Solution[]) productPMXCrossover.execute(parents);

        ProductsPermutation child1 = (ProductsPermutation) offSpring[0].getDecisionVariables()[0];

        ProductsPermutation child2 = (ProductsPermutation) offSpring[1].getDecisionVariables()[0];

        Assert.assertArrayEquals(new int[] { 2, 3, 10 }, child1.getVector());
        System.out.println(child1.toString());
        System.out.println(child2.toString());
        Assert.assertArrayEquals(new int[] { 1, 4, 8, 9 }, child2.getVector());
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
     *  ordered child 2: 1, 4, 7, 8, 9
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
        System.out.println(child2.toString());
        Assert.assertArrayEquals(new int[] { 1, 4, 7, 8, 9 }, child2.getVector());
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
     *  child2: 2,5
     *  
     *  ordered child1: 2,4,8
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

        Assert.assertArrayEquals(new int[] { 2, 4, 8 }, child1.getVector());
        Assert.assertArrayEquals(new int[] { 2, 5 }, child2.getVector());
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
     *  child2:  2,5
     *  
     *  ordered child1: 2,4,8
     * 
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

        Assert.assertArrayEquals(new int[] { 2, 4, 8 }, child1.getVector());
        Assert.assertArrayEquals(new int[] { 2, 5 }, child2.getVector());
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
     *  child2:  2,5,3
     *  
     *  ordered child1: 2,4,8
     *  ordered child2: 2,3,5
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

        Assert.assertArrayEquals(new int[] { 2, 4, 8 }, child1.getVector());
        Assert.assertArrayEquals(new int[] { 2, 3, 5 }, child2.getVector());
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
     *  child2:  2,5,3
     * 
     * 
     * ordered child 1: 2,4,8
     * ordered child 2: 2,3,5
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

        Assert.assertArrayEquals(new int[] { 2, 4, 8 }, child1.getVector());
        Assert.assertArrayEquals(new int[] { 2, 3, 5 }, child2.getVector());
    }

    /**
     * Tests the following scenario
     * 
     * <pre>
     *  parent1: 2 
     *  parent2: 8,4,5,1,3
     *  
     *  expected
     *  child1: 2,8,4
     *  child2:  2,5,1,3
     * 
     * 
     * ordered child 1: 2,4,8
     * ordered child 2: 1,2,3,5
     * </pre>
     * 
     * 
     */

    @Test
    public void testParent1Size1Parent2Size5UnikeValuesPMXCrossover() {

        Solution[] parents = new Solution[2];
        Solution parent1 = new Solution();
        Solution parent2 = new Solution();

        Variable variable1 = new ProductsPermutation(new int[] { 8, 4, 5, 1, 3 });
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

        Assert.assertArrayEquals(new int[] { 2, 4, 8 }, child1.getVector());
        System.out.println(child2.toString());
        Assert.assertArrayEquals(new int[] { 1, 2, 3, 5 }, child2.getVector());
    }

    /**
     * Tests the following scenario
     * 
     * <pre>
     *  parent1: 2,3,4,9 
     *  parent2: 12,13,1,8
     *  
     *  expected
     *  child1: 2,3,1,8
     *  child2: 12,13,4,9
     * 
     * 
     * ordered child 1: 1,2,3,8
     * ordered child 2: 4,9,12,13
     * </pre>
     * 
     * 
     */

    @Test
    public void testOddParentsUnikeValuesPMXCrossover() {

        Solution[] parents = new Solution[2];
        Solution parent1 = new Solution();
        Solution parent2 = new Solution();

        Variable variable1 = new ProductsPermutation(new int[] { 2, 3, 4, 9 });
        Variable variable2 = new ProductsPermutation(new int[] { 12, 13, 1, 8 });

        parent1.setDecisionVariables(new Variable[] { variable1 });
        parent2.setDecisionVariables(new Variable[] { variable2 });

        parent1.setType(new ProductsPermutationSolutionType());
        parent2.setType(new ProductsPermutationSolutionType());

        parents[0] = parent1;
        parents[1] = parent2;

        Solution[] offSpring = (Solution[]) productPMXCrossover.execute(parents);

        ProductsPermutation child1 = (ProductsPermutation) offSpring[0].getDecisionVariables()[0];

        ProductsPermutation child2 = (ProductsPermutation) offSpring[1].getDecisionVariables()[0];

        Assert.assertArrayEquals(new int[] { 1, 2, 3, 8 }, child1.getVector());
        Assert.assertArrayEquals(new int[] { 4, 9, 12, 13 }, child2.getVector());
    }
}
