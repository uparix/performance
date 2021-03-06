package com.uparix.performance;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.*;

@State(Scope.Thread)
public class MyBenchmark {

    List<Integer> arrayList;
    int[] array;
    Random random;

    @Setup(Level.Trial)
    public void init() {
        random = new Random();
        array = new int[100000];
        arrayList = new ArrayList<Integer>();
        for (int i = 0; i < 25; i++) {
            int randomNumber = random.nextInt();
            array[i] = randomNumber;
            arrayList.add(new Integer(randomNumber));
        }
    }

    @Benchmark
    public void arraysSort() {
        Arrays.sort(array);
    }

    @Benchmark
    public void collectionsSort() {
        Collections.sort(arrayList);
    }

    public static void main(String[] args) throws RunnerException {

        Options options = new OptionsBuilder()
                .include(MyBenchmark.class.getSimpleName()).threads(1)
                .forks(1).shouldFailOnError(true).shouldDoGC(true)
                .jvmArgs("-server").build();
        new Runner(options).run();

    }

}
