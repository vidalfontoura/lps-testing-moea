package edu.ufpr.gress.moea.plot;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Prado Lima
 */
public class InstanceReader {

    protected FileReader reader;

    protected BufferedReader buffer;

    protected String filename;

    public InstanceReader(String filename) {

        this.filename = filename;
    }

    /**
     * Open file to read
     */
    public void open() {

        try {
            this.reader = new FileReader(filename);
            this.buffer = new BufferedReader(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close file after read it
     */
    public void close() {

        try {
            if (buffer != null) {
                buffer.close();
            }
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read a line
     *
     * @return A line read
     */
    public String readLine() {

        try {
            return buffer.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int readInt() {

        return Integer.valueOf(readLine());
    }

    public double[][] readDoubleMatrix(String separator, boolean isAbsValues) throws IOException {

        int rows;
        int columns;
        try (BufferedReader bufferAuxReader = new BufferedReader(new FileReader(filename))) {
            rows = 0;
            columns = 0;
            // pre-read in the number of rows/columns
            // Loop over lines in the file and print them.
            while (true) {
                ++rows;
                String line = bufferAuxReader.readLine();
                if (line == null) {
                    --rows;
                    break;
                }

                columns = line.split(separator).length;
            }
        }

        return isAbsValues ? readAbsDoubleMatrix(rows, rows, separator) : readDoubleMatrix(rows, columns, separator);
    }

    public double[][] readDoubleMatrix(int i, int j) {

        return readDoubleMatrix(i, j, ",");
    }

    public double[][] readDoubleMatrix(int i, int j, String separator) {

        double[][] result = new double[i][j];

        for (int k = 0; k < i; k++) {
            String[] split = readLine().split(separator);
            result[k] = Convert.toDoubleArray(split);
        }

        return result;
    }

    public double[][] readAbsDoubleMatrix(int i, int j, String separator) {

        double[][] result = new double[i][j];

        for (int k = 0; k < i; k++) {
            String[] split = readLine().split(separator);
            result[k] = Convert.toAbsDoubleArray(split);
        }

        return result;
    }

    public int[] readIntVector(String separator) {

        String[] split = readLine().split(separator);

        int[] result = new int[split.length];

        for (int k = 0; k < split.length; k++) {
            result[k] = Integer.valueOf(split[k]);
        }

        return result;
    }

    public int[] readIntVector(int size, String separator) {

        return readIntVector(",");
    }

    public int[][] readIntMatrix(int i, int j) {

        return readIntMatrix(i, j, ",");
    }

    public int[][] readIntMatrix(int i, int j, String separator) {

        int[][] result = new int[i][j];

        for (int k = 0; k < i; k++) {
            String[] split = readLine().split(separator);
            result[k] = Convert.toIntArray(split);
        }

        return result;
    }
}
