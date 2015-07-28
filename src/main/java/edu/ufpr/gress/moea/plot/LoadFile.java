package edu.ufpr.gress.moea.plot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LoadFile {

    public static void main(String[] args) throws IOException {

        String instance = "EShop";
        File spea2 =
            new File("experiments" + File.separator + "result-test" + File.separator + instance + File.separator
                + "SPEA2-UniformCrossover" + File.separator + "FUN.txt");

        File nsgaii =
            new File("experiments" + File.separator + "result-test" + File.separator + instance + File.separator
                + "NSGAII-UniformCrossover" + File.separator + "FUN.txt");

        File pf =
            new File("experiments" + File.separator + "result-test" + File.separator + instance + File.separator
                + "PF-UniformCrossover" + File.separator + "FUN.txt");

        File ibea =
            new File("experiments" + File.separator + "result-test" + File.separator + instance + File.separator
                + "IBEA-UniformCrossover" + File.separator + "FUN.txt");

        List<String> pfLines = new ArrayList<String>();
        List<String> spea2Lines = new ArrayList<String>();
        List<String> nsgaiiLines = new ArrayList<String>();
        List<String> ibeaLines = new ArrayList<String>();

        try (BufferedReader br = new BufferedReader(new FileReader(pf))) {
            pfLines = br.lines().collect(Collectors.toList());
        }

        try (BufferedReader br = new BufferedReader(new FileReader(spea2))) {
            spea2Lines = br.lines().collect(Collectors.toList());
        }

        try (BufferedReader br = new BufferedReader(new FileReader(nsgaii))) {
            nsgaiiLines = br.lines().collect(Collectors.toList());
        }

        try (BufferedReader br = new BufferedReader(new FileReader(ibea))) {
            ibeaLines = br.lines().collect(Collectors.toList());
        }

        int speaCount = 0;
        int nsgaIICount = 0;
        int ibeaCount = 0;
        System.out.println(pfLines.size());
        for (String pfline : pfLines) {
            for (String spea2Line : spea2Lines) {
                if (pfline.trim().equals(spea2Line.trim())) {
                    speaCount++;
                    break;
                }
            }
            for (String ibeaLine : ibeaLines) {
                if (pfline.trim().equals(ibeaLine.trim())) {
                    ibeaCount++;
                    break;
                }

            }
            for (String nsgaiiLine : nsgaiiLines) {
                if (pfline.trim().equals(nsgaiiLine.trim())) {
                    nsgaIICount++;
                    break;
                }
            }

        }
        System.out.println(speaCount);
        System.out.println(ibeaCount);
        System.out.println(nsgaIICount);
    }
}
