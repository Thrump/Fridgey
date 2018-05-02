package com.raichu.discord.trivia;

import io.magiccraftmaster.util.Calculator;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class TriviaHandler {
	private static final File FILE = new File(System.getProperty("user.home") + "/Desktop/trivia.json");

	public enum Category {
		NATURE("Nature"),
		SCIENCE("Science", "nerdy"),
		POKEMON("Pokemon", "poke", "pkmn"),
		MATH("Math");

		private final String name, shorthands[];

		Category(String name, String... shorthands) {
			this.name = name;
			this.shorthands = shorthands;
		}

		public String getName() {
			return name;
		}

		public String[] getShorthands() {
			return shorthands;
		}

		public static String getNames(String sep) {
			StringBuilder stringBuilder = new StringBuilder();
			for (Category category : Category.values()) stringBuilder.append(sep).append(category.getName());
			return stringBuilder.substring(sep.length());
		}
	}

	private TriviaQuestion[] triviaQuestions;

	public TriviaHandler() throws IOException {
		JSONArray jsonArray = new JSONArray(new String(Files.readAllBytes(FILE.toPath())));
		List<TriviaQuestion> triviaQuestionList = new ArrayList<>();
		for (Object object : jsonArray) {
			//System.out.println("[TriviaHandler] Reading: " + object);
			if (!(object instanceof JSONObject)) continue;
			triviaQuestionList.add(TriviaQuestion.of((JSONObject) object));
		}
		if (triviaQuestionList.isEmpty()) {
			System.err.println("[TriviaHandler] Failed to load questions");
			System.exit(0);
		}
		triviaQuestions = triviaQuestionList.toArray(new TriviaQuestion[triviaQuestionList.size()]);
		//triviaQuestionList.forEach(System.out::println);
	}

	public TriviaQuestion[] getTriviaQuestions() {
		return triviaQuestions;
	}

	public TriviaQuestion getQuestion(TriviaHandler.Category category) {
		List<TriviaQuestion> triviaQuestionList = new ArrayList<>();
		for (TriviaQuestion triviaQuestion : getTriviaQuestions()) if (triviaQuestion.getCategory() == category) triviaQuestionList.add(triviaQuestion);
		return triviaQuestionList.get((int) Math.round(Calculator.randomIn(0,triviaQuestionList.size() - 1)));
	}
}
