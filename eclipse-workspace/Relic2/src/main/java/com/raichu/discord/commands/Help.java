package com.raichu.discord.commands;

import java.awt.Color;

import com.raichu.discord.Command;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Help extends Command {

	public Help() {
		super(null, null, "Help", "Gets a list of commands", null, 0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(MessageReceivedEvent event, String[] args) {
		// TODO Auto-generated method stub
		EmbedBuilder help  = new EmbedBuilder();
		help.setTitle("Command Functions");
		help.setColor( new Color(150,50,204));
		help.setDescription("*ping - Returns a pong back~\n*shutdown - Shutdowns the bot."
				+ "\n*roll (l, h, or 6) - mini roll game. \n*quote (your quote) - Adds a quote. \n*quote - Pulls up a quote from the saved quotes. "
				+ "quotes.\n*info - Gives information about bot\n*trivia (category) - Gives a random trivia questions. \n"
				+ "*wiki (page) - gives a small summary of the wikipedia page.");
		help.setFooter("Made by Thrump", "https://image.flaticon.com/icons/png/512/224/224641.png");
		help.setThumbnail("https://www.shareicon.net/data/256x256/2016/12/30/866941_textbook_512x512.png");
		event.getChannel().sendMessage(help.build()).queue();;
	}

}
