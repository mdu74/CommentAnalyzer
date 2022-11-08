package com.ikhokha.techcheck;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;


public class CommentAnalyzer implements Supplier<Map<String, Integer>>{
	
	private File file;
	
	public CommentAnalyzer(File file) {
		this.file = file;
	}
	
	public Map<String, Integer> analyze() throws InterruptedException {
		
		Map<String, Integer> resultsMap = new HashMap<>();
		ShorterComments shorterComments = new ShorterComments();
		Shaker shacker = new Shaker();
		Mover mover = new Mover();
		Question question = new Question();
		Spam spam = new Spam();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			
			String line = null;
			while ((line = reader.readLine()) != null) {
				shorterComments.setMetricCount(line);
				shacker.setMetricCount(line);
				mover.setMetricCount(line);
				question.setMetricCount(line);
				spam.setMetricCount(line);
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + file.getAbsolutePath());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO Error processing file: " + file.getAbsolutePath());
			e.printStackTrace();
		}

		resultsMap = Map.of(
			"SHORTER_THAN_15", shorterComments.getMetricCount(), 
			"MOVER_MENTIONS", shacker.getMetricCount(), 
			"SHAKER_MENTIONS", mover.getMetricCount(),
			"QUESTIONS", question.getMetricCount(),
			"SPAM", spam.getMetricCount()
			);
			
		return resultsMap;
	}
	
	@Override
	public Map<String, Integer> get() {
		try {
			return analyze();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return Map.of();
	}
}
