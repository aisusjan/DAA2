package org.example;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class ShellSortComparison {

    public enum GapType { SHELL, KNUTH, SEDGEWICK }

    public static void main(String[] args) {
        int[] sizes = {1000, 5000, 10000, 20000};
        GapType[] gapTypes = {GapType.SHELL, GapType.KNUTH, GapType.SEDGEWICK};
        int repetitions = 5;

        System.out.println("Shell Sort Performance Comparison (ms)");
        System.out.printf("%-10s %-12s %-10s %-10s%n", "Size", "GapType", "Avg(ms)", "StdDev");

        for (int size : sizes) {
            for (GapType gapType : gapTypes) {
                long[] times = new long[repetitions];
                for (int i = 0; i < repetitions; i++) {
                    int[] arr = generateArray(size);
                    long t0 = System.nanoTime();
                    shellSort(arr, gapType);
                    long t1 = System.nanoTime();
                    times[i] = (t1 - t0) / 1_000_000;
                    if (!isSorted(arr)) {
                        System.err.println("Error: Array not sorted for " + gapType);
                        return;
                    }
                }
                double avg = Arrays.stream(times).average().orElse(0);
                double std = stddev(times, avg);
                System.out.printf("%-10d %-12s %-10.3f %-10.3f%n", size, gapType, avg, std);
            }
            System.out.println();
        }
    }

    // ---------------- Shell Sort Implementation ----------------
    public static void shellSort(int[] arr, GapType gapType) {
        int[] gaps = generateGaps(arr.length, gapType);

        for (int gap : gaps) {
            // Защита от неправильных gap = 0
            if (gap < 1) continue;

            for (int i = gap; i < arr.length; i++) {
                int temp = arr[i];
                int j = i;
                while (j >= gap && arr[j - gap] > temp) {
                    arr[j] = arr[j - gap];
                    j -= gap;
                }
                arr[j] = temp;
            }
        }
    }

    // ---------------- Gap Generators ----------------
    public static int[] generateGaps(int n, GapType type) {
        switch (type) {
            case KNUTH:
                return knuthGaps(n);
            case SEDGEWICK:
                return sedgewickGaps(n);
            default:
                return shellGaps(n);
        }
    }

    private static int[] shellGaps(int n) {
        List<Integer> gaps = new ArrayList<>();
        for (int gap = n / 2; gap > 0; gap /= 2) {
            gaps.add(gap);
        }
        return gaps.stream().mapToInt(Integer::intValue).toArray();
    }

    private static int[] knuthGaps(int n) {
        List<Integer> gaps = new ArrayList<>();
        int h = 1;
        while (h < n / 3) {
            gaps.add(h);
            h = 3 * h + 1;
        }
        Collections.reverse(gaps);
        if (gaps.isEmpty()) gaps.add(1);
        return gaps.stream().mapToInt(Integer::intValue).toArray();
    }

    private static int[] sedgewickGaps(int n) {
        List<Integer> gaps = new ArrayList<>();
        int k = 0;
        while (true) {
            int gap;
            if (k % 2 == 0) {
                gap = (int) (9 * (Math.pow(2, k / 2.0) - Math.pow(2, k / 4.0)) + 1);
            } else {
                gap = (int) (Math.pow(4, (k + 1) / 2.0) - 3 * Math.pow(2, (k + 1) / 2.0) + 1);
            }
            if (gap >= n) break;
            if (gap > 0) gaps.add(gap);
            k++;
        }
        Collections.reverse(gaps);
        if (gaps.isEmpty() || gaps.get(gaps.size() - 1) != 1) {
            gaps.add(1);
        }
        return gaps.stream().mapToInt(Integer::intValue).toArray();
    }

    // ---------------- Utilities ----------------
    private static int[] generateArray(int n) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = random.nextInt();
        return arr;
    }

    private static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] > arr[i]) return false;
        }
        return true;
    }

    private static double stddev(long[] times, double mean) {
        double sum = 0;
        for (long t : times) sum += (t - mean) * (t - mean);
        return Math.sqrt(sum / times.length);
    }
}
