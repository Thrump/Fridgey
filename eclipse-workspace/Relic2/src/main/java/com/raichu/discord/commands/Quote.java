package com.raichu.discord.commands;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.raichu.discord.ArraySaver;
import com.raichu.discord.Command;

import io.magiccraftmaster.util.Calculator;
import io.magiccraftmaster.util.StringUtils;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Quote extends Command {
	private static final List<String> quotes = new ArrayList<>();
	
	public Quote() {
		super(new Color(153,50,204), "*", "quote", "Save quotes" , null, 0);
		try { 
			quotes.addAll(Arrays.asList(ArraySaver.load(new File(System.getProperty("user.home") + "/Desktop/bot_quotes.txt"))));
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	

	@Override
	public void run(MessageReceivedEvent event, String[] args) {
		if(args.length ==  1) {
			send(event.getGuild(),event.getChannel(),quotes.get((int) Math.round(Calculator.randomIn(0,quotes.size() - 1))),true);
		}
		if(args.length > 1) {
			quotes.add(StringUtils.toString(StringUtils.clip(args, 1, StringUtils.ClipType.LEFT), " ")); 
			try {
				ArraySaver.save(new File(System.getProperty("user.home") + "/Desktop/bot_quotes.txt"), quotes);
				send(event.getGuild(),event.getChannel(),"*Saving Quote*",true);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	

}
