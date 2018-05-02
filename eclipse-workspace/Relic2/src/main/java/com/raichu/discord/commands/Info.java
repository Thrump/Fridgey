package com.raichu.discord.commands;

import java.awt.Color;
import java.time.*;

import com.raichu.discord.Command;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Info extends Command {

	public Info() {
		super(null, null, "Info", "Quick bio of the bot", null, 0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(MessageReceivedEvent event, String[] args) {
		// TODO Auto-generated method stub
		LocalDateTime clock = LocalDateTime.now();
		LocalDate date = clock.toLocalDate();
		int hours = clock.getHour();
		String minutes = ""+clock.getMinute();
		String seconds =""+  clock.getSecond();
		if (Integer.parseInt(minutes) < 9) {
			minutes = "0" + minutes;
		}
		if(Integer.parseInt(seconds) < 9) {
			seconds  = "0" + seconds;
		}
		
		EmbedBuilder info = new EmbedBuilder();
		info.setTitle("Bot Information");
		info.addField("Version:", "0.15", true);
		info.addField("Date Created:", "12/07/17", true);
		info.addField("Created By:", "Thrump",  true);
		info.addField("Today's Date: ", "" +date, true);
		info.addField("Current Time: ", hours + ":" + minutes + ":" + seconds + " EST", true);
		info.setDescription("This bot was created as a test dummy for me.");
		info.setColor(new Color(255, 36, 0));
		info.setFooter("type *help for commands of bot.","https://image.flaticon.com/icons/png/512/224/224641.png");
		event.getChannel().sendMessage(info.build()).queue();
		
	}

}
