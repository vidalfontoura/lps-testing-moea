package edu.ufpr.gres.moea.problem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.uma.jmetal.core.Variable;
import org.uma.jmetal.encoding.variable.Permutation;
import org.uma.jmetal.util.random.PseudoRandom;

public class ProductsPermutation extends Permutation {

    private static final long serialVersionUID = 2657470069340722041L;

    private int[] vector;

    private int size;

    public ProductsPermutation() {

        size = 0;
        vector = null;
    }

    public ProductsPermutation(int[] vector) {

        this.vector = vector;
        this.size = vector.length;
    }

    public ProductsPermutation(int size, int upperBound) {

        this.size = size;
        vector = new int[this.size];

        Set<Integer> randomSequence = new HashSet<>();

        while (randomSequence.size() != this.size) {
            int randInt = PseudoRandom.randInt(1, upperBound);
            randomSequence.add(randInt);
        }

        Iterator<Integer> iterator = randomSequence.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            vector[count] = iterator.next();
            count++;
        }

    }

    public ProductsPermutation(ProductsPermutation permutation) {

        size = permutation.size;
        vector = new int[size];

        System.arraycopy(permutation.vector, 0, vector, 0, size);
    }

    public int[] getVector() {

        return vector;
    }

    public int getSize() {

        return size;
    }

    public Variable copy() {

        return new ProductsPermutation(this);
    }

    public int getLength() {

        return size;
    }

    public String toString() {

        String string;

        string = "";
        for (int i = 0; i < size; i++) {
            string += vector[i] + " ";
        }

        return string;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + size;
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
        if (size != other.size) {
            return false;
        }
        if (!Arrays.equals(vector, other.vector)) {
            return false;
        }
        return true;
    }

}
