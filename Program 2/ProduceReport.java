/*
* File: ProduceReport.java
* Author: John Kucera
* Date: November 9, 2021
* Purpose: This Java program allows the user to select an input file using
* JFileChooser generated from BenchmarkSorts.java using the Insertion Sort
* algorithm. The file is parsed through, creating arrays to hold critical
* operation count data and run time data as well as data set size. These
* are used to calculate averages and coefficients of variance. The calculations
* are displayed on a JFrame using a JTable. ProduceReport extends JFrame to
* create the GUI.
*/

// import necessary Java classes
import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileNotFoundException;
import static java.lang.Math.sqrt;
import java.text.DecimalFormat;
import java.util.Scanner;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

// Class: ProduceReport
public class ProduceReport extends JFrame {
    // Variable Initialization
    private int sizeData;
    private double[] countData = new double[50];
    private double[] timeData = new double[50];
    private final DecimalFormat df = new DecimalFormat("0.00");
    
    // Creating table model to hold data
    private final String[][] dataSkeleton = null;
    private final String[] reportColumns = {"Size", "Avg Count", "Coef Count", "Avg Time", "Coef Time"};
    private final DefaultTableModel reportModel = new DefaultTableModel(dataSkeleton, reportColumns);
    
    // Constructor (extends JFrame)
    public ProduceReport() {
        // Call chooseFileAndAddData to generate data
        chooseFileAndAddData();
        
        // Use JTable to hold data and add it to JFrame
        final JTable reportTbl = new JTable(reportModel);
        final JScrollPane reportScroll = new JScrollPane(reportTbl);
        add(reportScroll);
        
        // Edit JFrame characteristics
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Benchmark Report");
        setLocationRelativeTo(null);
        setResizable(false);
        setSize(500,225);
    } // end of constructor
    
    // Method: chooseFileAndAddData. Uses JFileChooser to allow user to choose
    //         input file. Exit program if not selected. If file selected,
    //         iterate through 10 lines in file and call calculation methods
    //         to calculate averages and coefficients. Then add the results to
    //         JTable.
    private void chooseFileAndAddData() {
        // Allow user to choose file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(new JDialog());
        // If file chosen, call calculations and add results to JTable
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            for (int i = 0; i < 10; i++) {
                parseRow(selectedFile.getAbsolutePath(), i);
                reportModel.addRow(new Object[] {sizeData, df.format(getAverage(countData)), 
                                   getCoeff(countData), df.format(getAverage(timeData)), 
                                   getCoeff(timeData)});
            }
        }
        // If file not chosen, exit program.
        else {
            System.exit(0);
        }
    } // end of method
    
    // Method: parseRow. Scans specified line in input file and reads count
    //         and time data into arrays, as well as data set sizes.
    private void parseRow(String fileName, int lineNumber) {
        try {
            // Initialize file and scanner
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            
            // Go to specified line
            String nextLine;
            for (int i = 0; i < lineNumber; i++) {
                nextLine = scanner.nextLine();
            }
            
            // Traverse through line, storing dataset size, count, and time data
            sizeData = scanner.nextInt();
            for (int i = 0; i < 50; i++) {
                countData[i] = scanner.nextDouble();
                timeData[i] = scanner.nextDouble();
            }
        } 
        catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);
        }
    } // end of method
    
    // Method: getAverage. Returns average of array of doubles.
    private double getAverage(double[] data) {
        double sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += data[i];
        }
        return sum / data.length;
    } // end of method
    
    // Method: getCoeff. Returns coefficient of variance of array of doubles.
    private String getCoeff(double[] data) {
        // Variable Initialization, call getAverage for data
        double average = getAverage(data);
        double stdDev = 0.0;
        double coeff;
        
        // Calculate Standard Deviation
        for (int i = 0; i < data.length; i++) {
            stdDev += Math.pow((data[i]-average), 2);
        }
        stdDev = sqrt(stdDev / (data.length-1));
        
        // Calculate coefficient and return it as a String in percent format
        coeff = stdDev / average;
        return String.valueOf(df.format(coeff*100)) + "%";
    } // end of method
    
    // Method: main. Call constructor to prompt file chooser and create JFrame.
    public static void main(String[] args) {
        ProduceReport gui = new ProduceReport();
    } // end of method
} // end of class
