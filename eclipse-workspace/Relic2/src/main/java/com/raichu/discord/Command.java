/*
 * Copyright (c) 2017. MagicCraftMaster
 */
package com.raichu.discord;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

public abstract class Command {
	private final Color embedColor;
	private final String prefix, name, description;
	private final Permission requiredPermission;
	private final long requiredID;

	/**
	 * Creates a generic command
	 * @param embedColor the embed color for decorated messages
	 * @param prefix the prefix
	 * @param name the command name
	 * @param description the command description
	 * @param requiredPermission the permission required (null makes no permissions required)
	 * @param requiredID if this is set only that user ID will be allowed (0 is unset)
	 */
	public Command(Color embedColor, String prefix, String name, String description, Permission requiredPermission, long requiredID) {
		this.embedColor = embedColor == null ? Relic.COLOR : embedColor;
		this.prefix = prefix == null ? Relic.PREFIX : prefix;
		this.name = name;
		this.description = description;
		this.requiredPermission = requiredPermission;
		this.requiredID = requiredID;
	}

	/**
	 * Returns the command name
	 * @return a name string
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the command description
	 * @return a description string
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Returns the required Discord permission for this command
	 * @return a {@link Permission} or null
	 */
	public Permission getRequiredPermission() {
		return requiredPermission;
	}

	/**
	 * Embeds a message nicely for you
	 * @param jda the JDA instance
	 * @param message the message to embed
	 * @return a {@link MessageEmbed}
	 */
	private MessageEmbed embed(JDA jda, String message) {
		return new EmbedBuilder().setColor(embedColor)
				.setAuthor(jda.getSelfUser().getName(), null, jda.getSelfUser().getEffectiveAvatarUrl())
				.setDescription(message).build();
	}

	/**
	 * Sends a message to the given channel
	 * Won't send an embed the the bot doesn't have permission to use embeds
	 * @param channel the channel to send to
	 * @param message the message to send
	 * @param decorate if the message should be an embed
	 */
	protected void send(Guild guild, MessageChannel channel, String message, boolean decorate) {
		System.out.println(("Sending \"" + message + "\" - " + (decorate && (guild == null || guild.getSelfMember().hasPermission(Permission.MESSAGE_EMBED_LINKS)))).replace("\n", "\\n").replace("\t", "\\t"));
		if (decorate && (guild == null || guild.getSelfMember().hasPermission(Permission.MESSAGE_EMBED_LINKS))) {
			channel.sendMessage(embed(channel.getJDA(), message)).queue();
		} else {
			channel.sendMessage(message).queue();
		}
	}

	/**
	 * Checks an event for validity and runs the command if it passes
	 * @param event the event to check
	 */
	public void check(MessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split(" +");
		if (args[0].startsWith(prefix)) args[0] = args[0].substring(prefix.length()); else return;
		if ((requiredID == 0 || event.getAuthor().getIdLong() == requiredID) && requiredPermission != null && event.isFromType(ChannelType.TEXT) && !event.getMember().hasPermission(requiredPermission)) {
			send(event.getGuild(), event.getChannel(), "You don't have permission for that", false);
			return;
		}
		if (args[0].equalsIgnoreCase(name)) run(event, args);
	}

	/**
	 * This is the actual code to run when the command fires
	 * @param event the event that passed
	 * @param args the args
	 */
	public abstract void run(MessageReceivedEvent event, String[] args);
}
