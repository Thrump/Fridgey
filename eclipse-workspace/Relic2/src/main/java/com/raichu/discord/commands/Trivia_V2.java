package com.raichu.discord.commands;

import com.raichu.discord.Command;
import com.raichu.discord.trivia.TriviaHandler;
import com.raichu.discord.trivia.TriviaQuestion;
import com.raichu.discord.trivia.TriviaState;
import io.magiccraftmaster.util.StringUtils;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.IOException;

public class Trivia_V2 extends Command {
	private TriviaHandler handler = new TriviaHandler();
	private TriviaState state = TriviaState.IDLE;
	private TriviaQuestion currentQuestion;
	private String lastCorrectAnswer;

	/**
	 * Creates a trivia command
	 */
	public Trivia_V2() throws IOException {
		super(null, null, "Trivia", "Random trivia", null, 0);
	}
	
	@Override
	public void run(MessageReceivedEvent event, String[] args) {
		if (args.length < 2) send(event.getGuild(), event.getChannel(), "You need to specify a category or answer", true);
		if (args.length < 2) return; // Helps with index-out-of-bounds exceptions

		if (args[1].equalsIgnoreCase("list")) {
			send(event.getGuild(), event.getChannel(), "The current categories are: " + TriviaHandler.Category.getNames(", "), true);
			return;
		}

		if (args[1].equalsIgnoreCase("answer")) {
			switch (state) {
				case IDLE:
					String send = "No question is currently being asked..." + (lastCorrectAnswer != null ? "\nThe last answer was: " + lastCorrectAnswer : "");
					send(event.getGuild(), event.getChannel(), send, true);
					break;

				case WAITING_FOR_ANSWER:
					if (args.length < 3) send(event.getGuild(), event.getChannel(), "You need to specify an answer", true);
					if (args.length < 3) break;
					String userAnswer = StringUtils.toString(StringUtils.clip(args, 2, StringUtils.ClipType.LEFT), " ");
					for (String answer : currentQuestion.getAnswers()) {
						if (userAnswer.equalsIgnoreCase(answer)) {
							lastCorrectAnswer = (event.getMember() != null ? event.getMember().getEffectiveName() : event.getAuthor().getName()) +
									" got the answer: \"" + answer + "\" for the question: \"" + currentQuestion.getQuestion() + "\"";
							send(event.getGuild(), event.getChannel(), "Correct! " + lastCorrectAnswer, true);
							state = TriviaState.IDLE;
							return;
						}
					}
					send(event.getGuild(), event.getChannel(), "Incorrect!", true);
					break;
			}
			return;
		}

		switch (state) {
			case WAITING_FOR_ANSWER:
				for (TriviaHandler.Category category : TriviaHandler.Category.values()) {
					if (args[1].equalsIgnoreCase(category.getName())) {
						send(event.getGuild(), event.getChannel(), "A question is already being asked\n" + currentQuestion.getQuestion(), true);
						state = TriviaState.IDLE;
						return;
					}
				}
				break;

			case IDLE:
				for (TriviaHandler.Category category : TriviaHandler.Category.values()) {
					if (args[1].equalsIgnoreCase(category.getName())) {
						currentQuestion = handler.getQuestion(category);
						send(event.getGuild(), event.getChannel(), currentQuestion.getQuestion(), true);
						state = TriviaState.WAITING_FOR_ANSWER;
						return;
					}
				}
				break;
		}

		send(event.getGuild(), event.getChannel(), "Unknown option", true);
	}
}
