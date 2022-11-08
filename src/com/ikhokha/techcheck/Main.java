package com.ikhokha.techcheck;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Main {

	public static void main(String[] args) {
		
		Map<String, Integer> totalResults = new HashMap<>();
		List<CompletableFuture<Map<String, Integer>>> resultList = new ArrayList<>();
					
		File docPath = new File("docs");
		File[] commentFiles = docPath.listFiles((d, n) -> n.endsWith(".txt"));
		ExecutorService executor = Executors.newFixedThreadPool(4);
		
		for (File commentFile : commentFiles) {
			CompletableFuture<Map<String, Integer>> commentCalculations = CompletableFuture
					.supplyAsync(new CommentAnalyzer(commentFile), executor);
			resultList.add(commentCalculations);	
		}

		getReportFromCompletableFuture(totalResults, resultList);

		executor.shutdown();
		System.out.println("RESULTS\n=======");
		totalResults.forEach((k,v) -> System.out.println(k + " : " + v));
	}
	
	/**
	 * This method adds the result counts from a source map to the target map 
	 * @param totalResults the total result dictionaty of type string and integer
	 * @param resultList the result list with Completable Future map of type string and integer
	 */
	private static void getReportFromCompletableFuture(
		Map<String, Integer> totalResults, List<CompletableFuture<Map<String, Integer>>> resultList
		) {
		resultList.forEach(withCounter((i, result) -> {
			try {
				Map<String, Integer> report = resultList.get(i).get();
				addReportResults(report, totalResults);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}));
	}
	
	/**
	 * This method adds the result counts from a source map to the target map 
	 * @param source the source map
	 * @param target the target map
	 */
	private static void addReportResults(Map<String, Integer> source, Map<String, Integer> target) {
		for (Map.Entry<String, Integer> entry : source.entrySet()) {
			if (sourceMapHasKey(target, entry)) {
				Integer currentValue = entry.getValue() + target.get(entry.getKey());
			 	target.put(entry.getKey(), currentValue);
			} else {
				target.put(entry.getKey(), entry.getValue());
			}
		}
	}

	private static boolean sourceMapHasKey(Map<String, Integer> target, Entry<String, Integer> entry) {
		return target.containsKey(entry.getKey()) == true;
	}

	/**
	 * This method keep track of the counter and pass the item onto a BiConsumer:
	 * @param reportConsumer the BiConsumer<Integer, T>
	 */
	public static <T> Consumer<T> withCounter(BiConsumer<Integer, T> reportConsumer) {
		AtomicInteger counter = new AtomicInteger(0);
		return item -> reportConsumer.accept(counter.getAndIncrement(), item);
	}
}
