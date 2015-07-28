package edu.ufpr.gres.moea.reader.tests;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import edu.ufpr.gres.moea.problem.Product;
import edu.ufpr.gres.moea.problem.ProductReaderUtils;

public class ReaderTests {

    public static void main(String[] args) throws IOException {

        String productsPath = "products_mutants/PROD_";
        String mutantsPath = "products_mutants/PROD_MUTANTS_";
        String pairsWisePath = "products_mutants/PROD_PAIRS_";

        List<Product> products = ProductReaderUtils.readProductsAndMutants(productsPath, mutantsPath, pairsWisePath);

        Optional<Product> max = products.stream().max(new Comparator<Product>() {

            @Override
            public int compare(Product o1, Product o2) {

                if (o1.getFeatures().size() > o2.getFeatures().size()) {
                    return 1;
                } else if (o1.getFeatures().size() < o2.getFeatures().size()) {
                    return -1;
                } else {
                    return 0;
                }

            }

        });

        List<Integer> productsSelected = Lists.newArrayList(83, 426);
        List<Product> filtered =
            products.stream().filter(p -> productsSelected.contains(Integer.valueOf(p.getProductId())))
                .collect(Collectors.toList());
        Set<String> distinctMutants = new HashSet<String>();
        for (Product p : filtered) {
            distinctMutants.addAll(p.getMutants());
        }
        System.out.println(distinctMutants.size());

    }
}
