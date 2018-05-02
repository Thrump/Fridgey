package com.raichu.discord.commands;

import java.awt.Color;

import com.raichu.discord.Command;
import com.wrapper.spotify.Api;
import com.wrapper.spotify.methods.TrackSearchRequest;
import com.wrapper.spotify.models.Page;
import com.wrapper.spotify.models.Track;

import io.magiccraftmaster.util.StringUtils;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Spotify extends Command {
	Api api = Api.DEFAULT_API;
	
	
	public Spotify() {
		super(null, null, "spotify", "Everything that deals with spotify", null, 0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(MessageReceivedEvent event, String[] args) {
		// TODO Auto-generated method stub
		if(args.length < 1) {
			return;
		}
		
		if(args[1].equalsIgnoreCase("tracks")) {
			String search = StringUtils.toString(StringUtils.clip(args, 2, StringUtils.ClipType.LEFT), " ");
			TrackSearchRequest request = api.searchTracks(search).market("US").build();
			try {
				Page<Track> trackSearchResult = request.get();
				send(event.getGuild(), event.getChannel(), "Results:\n" + trackSearchResult.getTotal(), true);
			}catch(Exception e) {
				send(event.getGuild(),event.getChannel(), "ERROR!", true);
			}
		}
	}

}
