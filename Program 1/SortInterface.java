/*
* File: SortInterface.java
* Author: John Kucera
* Date: November 8, 2021
* Purpose: This Java interface provides abstract methods used to perform a
* sorting algorithm with a recursive implementation or with an iterative
* implementation. This also provides getter methods for critical operation count
* and run time.
*/

// Interface: SortInterface
public interface SortInterface {
    void recursiveSort(int[] list) throws UnsortedException;
    void iterativeSort(int[] list) throws UnsortedException;
    int getCount();
    long getTime();
} // end of interface
