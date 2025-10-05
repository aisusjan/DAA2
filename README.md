# DAA2
Student: Aisultan Nuriman
Pair: 2 — Advanced Sorting Algorithms
Algorithm: Shell Sort (Shell, Knuth, Sedgewick sequences)
Language: Java (Maven project, JDK 17)
 Objective

The purpose of this assignment is to implement and analyze advanced sorting algorithms using theoretical and empirical methods.
This project focuses on Shell Sort, an optimized version of insertion sort, and compares three different gap sequences — Shell’s, Knuth’s, and Sedgewick’s.
The analysis includes runtime measurement, asymptotic complexity estimation, and clean code design principles.
 Algorithm Description

Shell Sort works by comparing and swapping elements far apart using a sequence of gaps that gradually decreases until the final insertion sort pass.

Implemented Gap Sequences:

Shell’s Sequence:
Formula – n/2, n/4, ..., 1

Simple and classical version.

Average complexity: O(n²)

Knuth’s Sequence:
Formula – h = 3*h + 1

Common practical improvement.

Average complexity: O(n^(3/2))

Sedgewick’s Sequence:
Combines arithmetic and geometric progressions for better efficiency.

Theoretical complexity: O(n^(4/3))
