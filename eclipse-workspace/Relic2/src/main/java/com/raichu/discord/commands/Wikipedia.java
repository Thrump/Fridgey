package com.raichu.discord.commands;

import java.awt.Color;

import com.raichu.discord.Command;

import fastily.jwiki.core.Wiki;
import io.magiccraftmaster.util.StringUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Wikipedia extends Command {

	public Wikipedia() {
		super(null, null, "wiki", "all commands falling under wiki", null, 0);
		// TODO Auto-generated constructor stub
	}
	private static final Color wikiColor = new Color(150,50,204);
	@Override
	public void run(MessageReceivedEvent event, String[] args) {
		
		if(args.length < 1) {
			send(event.getGuild(), event.getChannel(), "ERROR: need title page", true);
			return;
		}
		Wiki search = new Wiki("en.wikipedia.org");
		if(args[1].equalsIgnoreCase("lookup")) {
			String split  = StringUtils.toString(StringUtils.clip(args, 2, StringUtils.ClipType.LEFT), " ");
			String upper = split.substring(0, 1).toUpperCase() + split.substring(1);
			if(search.exists(upper) == true) {
				EmbedBuilder wiki = new EmbedBuilder();
				String split2 =  search.getTextExtract(upper);
				String text;
				if(split2.length() > 500) {
					 text = split2.substring(0, 500);
				}else {
					text = split2;
				}
				
				wiki.setTitle(upper);
				wiki.setColor(wikiColor);
				wiki.setDescription(text + "...");
				wiki.setFooter("This is powered by Jwiki Java Library", null);
				event.getChannel().sendMessage(wiki.build()).queue();
			}else {
				send(event.getGuild(), event.getChannel(), "ERROR: Page does not exist", true);
			}
		}
		
		
	}

}
