package com.raichu.discord.trivia;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TriviaQuestion {
	private final TriviaHandler.Category category;
	private final String question, answers[];

	TriviaQuestion(TriviaHandler.Category category, String question, String[] answers) {
		this.category = category;
		this.question = question;
		this.answers = answers;
	}

	public TriviaHandler.Category getCategory() {
		return category;
	}

	public String getQuestion() {
		return question;
	}

	public String[] getAnswers() {
		return answers;
	}

	public String getAnswer() {
		return getAnswers()[0];
	}

	static TriviaQuestion of(JSONObject jsonObject) {
		if (!jsonObject.has("category") || !jsonObject.has("question") || !jsonObject.has("answers")) throw new NullPointerException("Invalid data");
		TriviaHandler.Category category = TriviaHandler.Category.valueOf(jsonObject.getString("category"));
		String question = jsonObject.getString("question");
		List<String> strings = new ArrayList<>();
		jsonObject.getJSONArray("answers").forEach(string -> strings.add(String.valueOf(string)));
		return new TriviaQuestion(category, question, strings.toArray(new String[strings.size()]));
	}
}
