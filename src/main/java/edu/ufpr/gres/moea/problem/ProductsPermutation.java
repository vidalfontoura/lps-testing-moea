package edu.ufpr.gres.moea.problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.uma.jmetal.core.Variable;
import org.uma.jmetal.encoding.variable.Permutation;
import org.uma.jmetal.util.random.PseudoRandom;

public class ProductsPermutation extends Permutation {

    private static final long serialVersionUID = 2657470069340722041L;

    private int[] vector;
    private List<Integer> possibleValues;

    private int numberOfRemovedProducts;

    public ProductsPermutation() {

        numberOfRemovedProducts = 0;
        vector = null;
    }

    public ProductsPermutation(String[] vectorStr) {

        this.vector = new int[vectorStr.length];
        for (int i = 0; i < vectorStr.length; i++) {
            this.vector[i] = Integer.valueOf(vectorStr[i]);
        }
    }

    public ProductsPermutation(int[] vector) {

        this.vector = vector;
        this.numberOfRemovedProducts = vector.length;
    }

    public ProductsPermutation(int size, int upperBound) {

        this.numberOfRemovedProducts = size;
        this.possibleValues = getPossibleValues(upperBound);

        List<Integer> products = new ArrayList<>(this.possibleValues);

        for (int i = 0; i < this.numberOfRemovedProducts; i++) {
            int randInt = PseudoRandom.randInt(0, products.size() - 1);
            products.remove(randInt);
        }

        vector = new int[products.size()];
        for (int i = 0; i < products.size(); i++) {
            vector[i] = products.get(i);
        }

    }

    public ProductsPermutation(ProductsPermutation permutation) {

        numberOfRemovedProducts = permutation.numberOfRemovedProducts;
        vector = new int[permutation.getVector().length];

        System.arraycopy(permutation.vector, 0, vector, 0, permutation.getVector().length);
    }

    public List<Integer> getPossibleValues(int bounds) {

        if (possibleValues == null) {
            possibleValues = new ArrayList<Integer>();
            for (int i = 0; i < bounds; i++) {
                possibleValues.add(i);
            }
        }
        return possibleValues;
    }

    public void shuffleArray(int[] ar) {

        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    public int[] getVector() {

        return vector;
    }

    public int getSize() {

        return numberOfRemovedProducts;
    }

    public Variable copy() {

        return new ProductsPermutation(this);
    }

    public int getLength() {

        return numberOfRemovedProducts;
    }

    public String toString() {

        String string;

        string = "";
        for (int i = 0; i < vector.length; i++) {
            string += vector[i] + " ";
        }

        return string;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + numberOfRemovedProducts;
        result = prime * result + Arrays.hashCode(vector);
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ProductsPermutation)) {
            return false;
        }
        ProductsPermutation other = (ProductsPermutation) obj;
        if (numberOfRemovedProducts != other.numberOfRemovedProducts) {
            return false;
        }
        if (!Arrays.equals(vector, other.vector)) {
            return false;
        }
        return true;
    }

}
