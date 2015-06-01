package edu.ufpr.gres.moea.problem;

import org.uma.jmetal.core.Problem;
import org.uma.jmetal.core.SolutionType;
import org.uma.jmetal.core.Variable;

public class ProductsPermutationSolutionType implements SolutionType {

    private Problem problem;

    /** Constructor */
    public ProductsPermutationSolutionType(Problem problem) {

        this.problem = problem;
    }

    public ProductsPermutationSolutionType() {

    }

    /** Creates the variables of the solution type */
    public Variable[] createVariables() {

        Variable[] variables = new Variable[problem.getNumberOfVariables()];

        LPSTestingProblem lpsProblem = (LPSTestingProblem) problem;
        for (int var = 0; var < problem.getNumberOfVariables(); var++) {
            variables[var] = new ProductsPermutation(lpsProblem.getNumberOfProducts(), lpsProblem.getMaxUpperLimit());
        }

        return variables;
    }
}
