package edu.ufpr.gres.moea.problem;

import java.util.ArrayList;
import java.util.List;

public class Product {

    private String productId;
    private List<String> features;
    private List<String> mutants;
    private List<String> pairs;

    public List<String> getPairs() {

        if (pairs == null) {
            pairs = new ArrayList<String>();
        }

        return pairs;
    }

    public void setPairs(List<String> pairs) {

        this.pairs = pairs;
    }

    public List<String> getFeatures() {

        if (features == null) {
            features = new ArrayList<String>();
        }
        return features;
    }

    public void setFeatures(List<String> features) {

        this.features = features;
    }

    @Override
    public String toString() {

        return "Product " + productId + ", features=" + features + ", mutants=" + mutants;
    }

    public String getProductId() {

        return productId;
    }

    public void setProductId(String productId) {

        this.productId = productId;
    }

    public List<String> getMutants() {

        if (mutants == null) {
            mutants = new ArrayList<String>();
        }
        return mutants;
    }

    public void setMutants(List<String> mutants) {

        this.mutants = mutants;
    }

}
