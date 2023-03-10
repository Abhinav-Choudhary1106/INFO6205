package edu.neu.coe.info6205.sort.linearithmic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.InstrumentedHelper;
import edu.neu.coe.info6205.sort.SortWithHelper;
import edu.neu.coe.info6205.sort.elementary.HeapSort;
import edu.neu.coe.info6205.util.Config;
import edu.neu.coe.info6205.util.SortBenchmark;
import edu.neu.coe.info6205.util.TimeLogger;

public class SortingBenchmarks {
	
	static Random random = new Random();

	public static void main(String[] args) throws IOException {
		int size = 10000;
		int runs = 100;
		
		while (size >= 10000 && size <= 256000) {
			switch(size) {
			case 10000:
			case 20000: runs = 100;
						break;
			case 40000: runs = 20;
						break;
			case 80000:
			case 1600000: runs = 1;
							break;
			default: runs = 1;
					break;
			}
			System.out.println("Size: " + size);
			System.out.println("Runs: " + runs);
			
			Integer[] randomArray = getRandomArray(size);
			
			//Instrumented
			System.out.println("Instrumented");
			
			//Merge sort
			final Config configMerge = Config.setupConfig("true", "0", "1", "1", "");
			final BaseHelper<Integer> helperMerge = new InstrumentedHelper<>("merge sort",size, configMerge);
			final SortWithHelper<Integer> sorterMerge = new MergeSortBasic<>(helperMerge);
			SortBenchmark.runIntegerSortBenchmark(randomArray.clone(), size, runs, sorterMerge, sorterMerge::preProcess, timeLoggers);
			System.out.println(helperMerge.showStats());
			
			//Quick sort dual pivot
			final Config configQuick = Config.setupConfig("true", "0", "1", "1", "");
			final BaseHelper<Integer> helperQuick = new InstrumentedHelper<>("quick sort dual pivot",size, configQuick);
			final SortWithHelper<Integer> sorterQuick = new QuickSort_DualPivot<>(helperQuick);
			SortBenchmark.runIntegerSortBenchmark(randomArray.clone(), size, runs, sorterQuick, sorterQuick::preProcess, timeLoggers);
			System.out.println(helperQuick.showStats());
			
			//Heap sort
			final Config configHeap = Config.setupConfig("true", "0", "1", "1", "");
			final BaseHelper<Integer> helperHeap = new InstrumentedHelper<>("heap sort",size, configHeap);
			final SortWithHelper<Integer> sorterHeap = new HeapSort<>(helperHeap);
			SortBenchmark.runIntegerSortBenchmark(randomArray.clone(), size, runs, sorterHeap, sorterHeap::preProcess, timeLoggers);
			System.out.println(helperHeap.showStats());
			
			//Non-Instrumented
			System.out.println("Non-Instrumented");
			//Merge sort
			final SortWithHelper<Integer> sorterMergeBasic = new MergeSortBasic<Integer>(BaseHelper.getHelper(MergeSortBasic.class));
			SortBenchmark.runIntegerSortBenchmark(randomArray.clone(), size, runs, sorterMergeBasic, sorterMergeBasic::preProcess, timeLoggers);
					
			//Quick sort dual pivot
			final SortWithHelper<Integer> sorterQuickBasic = new QuickSort_DualPivot<Integer>(BaseHelper.getHelper(QuickSort_DualPivot.class));
			SortBenchmark.runIntegerSortBenchmark(randomArray.clone(), size, runs, sorterQuickBasic, sorterQuickBasic::preProcess, timeLoggers);
					
			//Heap sort
			final SortWithHelper<Integer> sorterHeapBasic = new HeapSort<Integer>(BaseHelper.getHelper(HeapSort.class));
			SortBenchmark.runIntegerSortBenchmark(randomArray.clone(), size, runs, sorterHeapBasic, sorterHeapBasic::preProcess, timeLoggers);
			
			size = size*2;
		}
	}
	
	private final static TimeLogger[] timeLoggers = {
            new TimeLogger("Raw time per run (mSec): ", (time, n) -> time)
    };
	
	public static Integer[] getRandomArray(int n) {
		ArrayList<Integer> arr = new ArrayList<>();
		for (int i=0; i<n; i++) {
			arr.add(random.nextInt(n));
		}
		Integer[] result = arr.toArray(new Integer[arr.size()]);
		return result;
	}
}