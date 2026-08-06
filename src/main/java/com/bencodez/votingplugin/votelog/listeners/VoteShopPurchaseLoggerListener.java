package com.bencodez.votingplugin.votelog.listeners;

import com.bencodez.votingplugin.VotingPluginMain;
import com.bencodez.votingplugin.events.VoteShopPurchaseEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class VoteShopPurchaseLoggerListener implements Listener {

	private final VotingPluginMain plugin;

	public VoteShopPurchaseLoggerListener(VotingPluginMain plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onVoteShopPurchase(VoteShopPurchaseEvent event) {
		if (!plugin.isEnabled()) {
			return;
		}

		if (!plugin.getConfigFile().isVoteLoggingEnabled()) {
			return;
		}

		plugin.getVoteLogMysqlTable().logVoteShopPurchase(event.getPlayerUuid().toString(), event.getPlayerName(),
				LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(), event.getIdentifier(),
				event.getCost());
	}
}
