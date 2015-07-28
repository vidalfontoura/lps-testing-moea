package edu.ufpr.gress.moea.plot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.plot.DataSetPlot;
import com.panayotis.gnuplot.style.NamedPlotColor;
import com.panayotis.gnuplot.style.PlotStyle;
import com.panayotis.gnuplot.style.Style;
import com.panayotis.gnuplot.terminal.ImageTerminal;

public class GeneratePlotMain {

    public static void main(String[] args) throws IOException {

        plotCasSPEA2();
    }

    public static void plotCasSPEA2() throws IOException {

        List<PlotPoint> plotPoints = new ArrayList<>();

        String instance = "CAS";

        // /////////////////////////////////////////////////////////////////////////////////////////////////
        PlotStyle plotStyleSpea2 = new PlotStyle();
        plotStyleSpea2.setStyle(Style.POINTS);
        plotStyleSpea2.setLineWidth(2);
        plotStyleSpea2.setLineType(NamedPlotColor.RED);

        PlotPoint plotPointSpea = new PlotPoint();
        plotPointSpea.setDataPath("experiments" + File.separator + "result-test" + File.separator + instance
            + File.separator + "SPEA2-UniformCrossover" + File.separator + "FUN.txt");
        plotPointSpea.setTitlePoint("SPEA2");
        plotPointSpea.setPlotStyle(plotStyleSpea2);

        plotPoints.add(plotPointSpea);

        // /////////////////////////////////////////////////////////////////////////////////////////////////
        PlotStyle plotStyleIBEA = new PlotStyle();
        plotStyleIBEA.setStyle(Style.POINTS);
        plotStyleIBEA.setLineWidth(2);
        plotStyleIBEA.setLineType(NamedPlotColor.GREEN);

        PlotPoint plotPointIBEA = new PlotPoint();
        plotPointIBEA.setDataPath("experiments" + File.separator + "result-test" + File.separator + instance
            + File.separator + "IBEA-UniformCrossover" + File.separator + "FUN.txt");
        plotPointIBEA.setTitlePoint("IBEA");
        plotPointIBEA.setPlotStyle(plotStyleIBEA);

        plotPoints.add(plotPointIBEA);

        //
        // ///////////////////////////////////////////////////////////////////////////////////////////////
        PlotStyle plotStyleNSGAII = new PlotStyle();
        plotStyleNSGAII.setStyle(Style.POINTS);
        plotStyleNSGAII.setLineType(NamedPlotColor.BLUE);

        PlotPoint plotPointNSGAII = new PlotPoint();
        plotPointNSGAII.setDataPath("experiments" + File.separator + "result-test" + File.separator + instance
            + File.separator + "NSGAII-UniformCrossover" + File.separator + "FUN.txt");
        plotPointNSGAII.setTitlePoint("NSGAII");
        plotPointNSGAII.setPlotStyle(plotStyleNSGAII);

        plotPoints.add(plotPointNSGAII);

        // ///////////////////////////////////////////////////////////////////////////////////////////////
        PlotStyle trueParetoPlotStyle = new PlotStyle();
        trueParetoPlotStyle.setStyle(Style.POINTS);
        trueParetoPlotStyle.setLineType(NamedPlotColor.BLACK);

        PlotPoint plotPointTruePareto = new PlotPoint();
        plotPointTruePareto.setDataPath("experiments" + File.separator + "result-test" + File.separator + instance
            + File.separator + "PF-UniformCrossover" + File.separator + "FUN.txt");
        plotPointTruePareto.setTitlePoint("PFTrue");
        plotPointTruePareto.setPlotStyle(trueParetoPlotStyle);

        plotPoints.add(plotPointTruePareto);

        // /////////////////////////////////////////////////////////////////////////////////////////////////

        plot2lines("Escore de Mutação", "Número de produtos", "Cobertura de pairwise", 0.0, 1.0, 1.0, 0.0, 0.0, 1.0,
            plotPoints, "Fronteira de Pareto", instance + "_pf_un-teste.png", "png");

    }

    private static void
        plot2lines(String labelX, String labelY, String labelZ, double rangeStartX, double rangeEndX,
                   double rangeStartY, double rangeEndY, double rangeStartZ, double rangeEndZ,
                   List<PlotPoint> plotPoints, String titleGraph, String pathSave, String fileExtension)
            throws IOException {

        System.out.println("Salvando o arquivo em: " + System.getProperty("user.dir") + File.separator + pathSave);
        ImageTerminal png = new ImageTerminal();
        File file = new File(System.getProperty("user.dir") + File.separator + pathSave);
        try {
            file.createNewFile();
            png.processOutput(new FileInputStream(file));
        } catch (FileNotFoundException ex) {
            System.err.print(ex);
        } catch (IOException ex) {
            System.err.print(ex);
        }

        JavaPlot p = new JavaPlot(false);
        p.setTerminal(png);
        // p.set("size", "square");

        p.getAxis("x").setLabel(labelX);
        p.getAxis("y").setLabel(labelY);
        p.getAxis("z").setLabel(labelZ);
        p.getAxis("x").setBoundaries(rangeStartX, rangeEndX);
        p.getAxis("y").setBoundaries(rangeStartY, rangeEndY);
        p.getAxis("z").setBoundaries(rangeStartZ, rangeEndZ);

        for (PlotPoint plotPoint : plotPoints) {
            // Read data file A
            InstanceReader reader = new InstanceReader(plotPoint.getDataPath());
            reader.open();
            double[][] values = reader.readDoubleMatrix(" ", true);
            reader.close();

            DataSetPlot dataSet = new DataSetPlot(values);
            dataSet.setPlotStyle(plotPoint.getPlotStyle());
            dataSet.setTitle(plotPoint.getTitlePoint());

            p.addPlot(dataSet);
        }

        p.setTitle(titleGraph);
        // p.newGraph3D();
        p.plot();

        try {
            ImageIO.write(png.getImage(), fileExtension, file);
            System.out.println("Arquivo salvo com sucesso.");
        } catch (IOException ex) {
            System.err.print(ex);
        }
    }
}
