package com.raichu.discord;

import net.dv8tion.jda.bot.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.bot.sharding.ShardManager;
import net.dv8tion.jda.core.ShardedRateLimiter;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.io.IOException;

public class Relic {
	@SuppressWarnings("SpellCheckingInspection")
	private static final String TOKEN = "Mjc4NjI2NDA4NTI2MzgxMDU2.DRhopw.DieQnOktKWLWr-yxVr2zoizjX4A";

	// This is now the default color for commands
	public static final Color COLOR = new Color(153,50,204);

	// This is now the default prefix for commands
	public static final String PREFIX = "*";

	public static void main(String[] args) throws LoginException, IOException {
		/*ShardManager SHARD_MANAGER = */new DefaultShardManagerBuilder()
				.setToken(TOKEN)
				.setShardsTotal(1)
				.addEventListeners(new Listener())
				.setBulkDeleteSplittingEnabled(false)
				.setShardedRateLimiter(new ShardedRateLimiter())
				.build();
	}
}
