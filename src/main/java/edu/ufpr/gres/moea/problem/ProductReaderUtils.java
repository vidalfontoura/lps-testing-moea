package edu.ufpr.gres.moea.problem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProductReaderUtils {

    public static List<Product> readProductsAndMutants(String productsPath, String prodMutPath, String prodPairs)
        throws IOException {

        List<Product> products = ProductReaderUtils.readProducts(productsPath);
        List<String> mutants = ProductReaderUtils.readProductsMutants(prodMutPath);
        List<String> pairs = ProductReaderUtils.readPairs(prodPairs);

        for (int i = 0; i < products.size(); i++) {
            List<String> mutantsList = Arrays.asList(mutants.get(i).split(","));
            products.get(i).setMutants(mutantsList);
            List<String> pairsList = Arrays.asList(pairs.get(i).split(","));
            products.get(i).setPairs(pairsList);
        }

        return products;

    }

    public static List<Product> readProducts(String productsPath) throws IOException {

        Path path = Paths.get(productsPath);
        try (Stream<String> lines = Files.lines(path);) {
            Stream<String> productLines = lines.filter(line -> line.contains("Product"));
            return productLines.map(productLine -> {
                String[] vet = productLine.split(":");
                String[] prods = vet[1].split(",");
                Product product = new Product();
                product.setProductId(vet[0].replace("Product", "").trim());
                product.getFeatures().addAll(Arrays.asList(prods).stream().map(p -> {
                    return p.trim();
                }).collect(Collectors.toList()));

                return product;
            }).collect(Collectors.toList());
        }
    }

    public static List<String> readProductsMutants(String filepath) throws IOException {

        Path path = Paths.get(filepath);
        try (Stream<String> lines = Files.lines(path)) {
            Stream<String> productMutantsLine = lines.filter(line -> line.contains("Product"));
            return productMutantsLine.map(prodMutLine -> {
                String[] split = prodMutLine.split("Mutants:");
                return split[1].replace("[", "").replace("]", "");
            }).collect(Collectors.toList());

        }
    }

    public static List<String> readPairs(String filepath) throws IOException {

        Path path = Paths.get(filepath);
        try (Stream<String> lines = Files.lines(path)) {
            Stream<String> productPairs = lines.filter(line -> line.contains("Product"));
            return productPairs.map(prodPairsLine -> {
                String[] split = prodPairsLine.split("Pairs:");
                return split[1].replace("[", "").replace("]", "");
            }).collect(Collectors.toList());
        }
    }

    public static void main(String[] args) throws IOException {

        String productsPath = "products_mutants/PROD_";
        String productsMutantsPath = "products_mutants/PROD_MUTANTS_";
        String productsPairs = "products_mutants/PROD_PAIRS_";

        List<Product> readProductsFile =
            ProductReaderUtils.readProductsAndMutants(productsPath, productsMutantsPath, productsPairs);
        for (Product product : readProductsFile) {
            System.out.println(product.toString());
        }

    }
}
