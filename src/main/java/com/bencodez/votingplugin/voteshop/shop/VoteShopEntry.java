package com.bencodez.votingplugin.voteshop.shop;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Base vote shop entry.
 */
@Getter
@Setter
public abstract class VoteShopEntry {

	private String identifier;

	private ConfigurationSection displaySection;
}
