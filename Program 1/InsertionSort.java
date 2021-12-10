/*
* File: InsertionSort.java
* Author: John Kucera (Algorithm implementation sources in References)
* Date: November 8, 2021
* Purpose: This Java program is meant to perform the Insertion Sort algorithm
* on an array of integers. Implementing the SortInterface interface, it contains
* a recursive implementation and an iterative one, as well as getter methods
* for the count and time data. Time in nanoseconds is measured per run and stored
* in a long. The critical operation count is also measured per run and stored
* in an int. Additionally, the notSorted method is used to ensure that each array
* is properly sorted, and UnsortedException is thrown if notSorted returns false.
*
* THE CRITICAL OPERATION BEING COUNTED IS THE ELEMENT SHIFTS.
*
* References:
* 
* Recursive Implementation:
* Chhabra, S. (2021, June 28). Recursive insertion sort. GeeksforGeeks. 
*   Retrieved November 10, 2021, from https://www.geeksforgeeks.org/recursive-insertion-sort/. 
*
* Iterative Implementation:
* GeeksforGeeks. (2021, July 8). Insertion sort. GeeksforGeeks. Retrieved 
*   November 10, 2021, from https://www.geeksforgeeks.org/insertion-sort/. 
*/

// Class: InsertionSort
public class InsertionSort implements SortInterface {
    // Variable Declaration
    private int count; // critical operation count
    private long time; // runtime in nanoseconds
    
    // Method: recursiveSort. Starts recursion and stores run time.
    @Override
    public void recursiveSort(int[] list) throws UnsortedException {
        // Initialize count and time data
        count = 0;
        time = 0;
        long startTime = System.nanoTime();
        
        // Start recursion and store the run time
        list = recursiveSortHelper(list, list.length);
        time = System.nanoTime() - startTime;
        
        // Check is data has been sorted
        if (notSorted(list)) {
            throw new UnsortedException();
        }
    } // end of method
    
    // Method: recursiveSortHelper. Performs recursive implementation of
    //         Insertion Sort algorithm. Check comments at top of file for source.
    private int[] recursiveSortHelper(int[] list, int n) {
        // Base case
        if (n <= 1) {
            return list;
        }
        
        // Sort first n-1 elements
        recursiveSortHelper(list, n-1);
        
        // Insert last element at its correct position in sorted array
        int last = list[n-1];
        int j = n - 2;
        
        // Move elements of arr[0..i-1], that are greater than key, to one
        // position ahead of their current position.
        while (j >= 0 && list[j] > last) {
            list[j+1] = list[j]; // CRITICAL OPERATION: element shift
            j--;
            count++; // 1 critical operation has been performed, increment count
        }
        list[j+1] = last;
        return list;
    } // end of method
    
    // Method: iterativeSort. Performs iterative implementation of Insertion
    //         Sort algorithm. Check comments at top of file for source. Keeps
    //         track of critical operation count and run time.
    @Override
    public void iterativeSort(int[] list) throws UnsortedException {
        // Initialize count and time data
        count = 0;
        time = 0;
        long startTime = System.nanoTime();
        
        // Iterating through list, initializing current element and predecessor
        for (int i = 1; i < list.length; i++) {
            int key = list[i];
            int j = i - 1;
            
            // Move elements of arr[0..i-1, that are greater than key, to one
            // one position ahead of their current position.
            while (j >= 0 && list[j] > key) {
                list[j+1] = list[j]; // CRITICAL OPERATION: element shift
                j--;
                count++; // 1 critical operation has been performed, increment count
            }
            list[j+1] = key;
        }
        time = System.nanoTime() - startTime; // store run time
        
        // Check is data has been sorted
        if (notSorted(list)) {
            throw new UnsortedException();
        }
    } // end of method
    
    // Method: notSorted. Checks if array has been sorted in ascending order.
    //         Returns false is sorted, returns true if not sorted.
    private boolean notSorted (int[] list) {
        for (int i = 0; i < list.length - 1; i++) {
            if (list[i] < list[i+1]) {
                return false;
            }
        }
        return true;
    } // end of method
    
    // Method: Getter methods. getCount returns critical operation count.
    //         getTime returns run time for a single sort.
    @Override
    public int getCount() {
        return count;
    } // end of method
    
    @Override
    public long getTime() {
        return time;
    } // end of method
} // end of class
