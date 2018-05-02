package com.raichu.discord.commands;

import com.raichu.discord.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.Color;

public class Ping extends Command {
	public Ping() {
		super(null, null, "Ping" , "Responds with a pong" , null, 0);
	}

	@Override
	public void run(MessageReceivedEvent event, String[] args) {
		send(event.getGuild(), event.getChannel(), "Pong~ " + event.getJDA().getPing() + "ms", false);
	}
}
