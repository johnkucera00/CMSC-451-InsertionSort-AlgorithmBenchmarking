/*
* File: BenchmarkSorts.java
* Author: John Kucera
* Date: November 8, 2021
* Purpose: This Java program is meant to benchmark the behavior of a Java
* implementation of the Insertion Sort algorithm. In this Java class, 50 data
* sets are created containing randomly generated integers, with sizes of 500 to 
* 9500. InsertionSort.java is called perform the insertion sort algorithm on the
* datasets, then produces the critical operation count and run time. In 
* BenchmarkSorts, the count and time data is written to two files, one for
* iterative implementation and one for recursive implementation. Additionally,
* there are 10 warm-up runs performed before the main run to warm up the JVM
* so the time measurements will be accurate.
*/

// import necessary Java classes
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;

// Class: BenchmarkSorts
public class BenchmarkSorts {
    // Variable Initialization
    private final InsertionSort insertionSort = new InsertionSort();
    private final Random random = new Random();
    private final int[] dataSizes = {500, 1500, 2500, 3500, 4500, 5500, 6500, 7500, 8500, 9500};
    private final double[] recursiveCounts = new double[50];
    private final double[] recursiveTimes = new double[50];
    private final double[] iterativeCounts = new double[50];
    private final double[] iterativeTimes = new double[50];
    
    // Constructor
    BenchmarkSorts() {

    }
    
    // Method: runBenchmark. Generate data sets, call insertion sort function,
    //         call function to write data to output file.
    private void runBenchmark() throws UnsortedException {
        // Create or overwrite existing text files for output
        try {
            Files.newBufferedWriter(Paths.get("recursiveData.txt"));
            Files.newBufferedWriter(Paths.get("iterativeData.txt"));
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }

        // For each data set size
        for (int n : dataSizes) {
            // Array Initialization
            int[] recursiveDataset = new int[n];
            int[] iterativeDataset;
            
            // Generate data and Run algorithm 50 times
            for (int i = 0; i < 50; i++) {
                // Generate random integers and put into arrays
                for (int j = 0; j < n; j++) {
                    recursiveDataset[j] = random.nextInt(n);
                }
                iterativeDataset = Arrays.copyOf(recursiveDataset, n);
                
                // Call recursive implementation, put count and time data into arrays
                insertionSort.recursiveSort(recursiveDataset);
                recursiveCounts[i] = insertionSort.getCount();
                recursiveTimes[i] = insertionSort.getTime();
                
                // Call iterative implementation, put count and time data into arrays
                insertionSort.iterativeSort(iterativeDataset);
                iterativeCounts[i] = insertionSort.getCount();
                iterativeTimes[i] = insertionSort.getTime();
            }
            // Write the count and time data to files after all 50 runs
            writeLine("recursiveData.txt", n, recursiveCounts, recursiveTimes);
            writeLine("iterativeData.txt", n, iterativeCounts, iterativeTimes);
        } // end of for
    } // end of method
    
    // Method: writeLine. Uses FileWriter class to write count and time data to
    //         text files. Each line contains the dataset size, followed by 50
    //         pairs of values. All values are separated by a single space.            
    private void writeLine(String fileName, int n, double[] countData, double[] timeData) {
        try {
            FileWriter filewriter = new FileWriter(fileName, true);
            filewriter.append(String.valueOf(n)).append(" ");
            for (int i = 0; i < countData.length; i++) {
                filewriter.append(String.valueOf(countData[i])).append(" ").append(String.valueOf(timeData[i])).append(" ");
            }
            filewriter.append("\n"); // Go to next line for next n
            filewriter.close();
        }
        catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    } // end of method
    
    // Method: main. Performs 10 warmup runs to warm up JVM so that the time
    //         measurements are accurate, printing out the runtimes in the
    //         console for confirmation. Then performs main benchmark run.
    public static void main(String[] args) throws UnsortedException {
        // 10 warmup runs, print runtimes for each
        for (int i = 0; i < 10; i++) {
            long startTime = System.nanoTime();
            BenchmarkSorts jvmWarmup = new BenchmarkSorts();
            jvmWarmup.runBenchmark();
            System.out.println("Time for JVM Warm-up Run " + (i+1) + ":\t" + (System.nanoTime()-startTime) + " ns");
        }
        // Main run
        BenchmarkSorts benchmark = new BenchmarkSorts();
        benchmark.runBenchmark();
        System.out.println("Main Run completed.");
    } // end of method
} // end of class
