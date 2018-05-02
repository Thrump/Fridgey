package com.raichu.discord;

import com.raichu.discord.commands.*;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.IOException;

class Listener extends ListenerAdapter {
	private Command[] commands = {
			new Trivia_V2(),
			new Ping(),
			new Quote(),
			new Info(),
			new Help(),
			new Wikipedia(),
			new Spotify()// Add commands here
	};

	Listener() throws IOException {
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		for (Command command : commands) command.check(event); // This is all ya need to fire the event for every command
	}
}
