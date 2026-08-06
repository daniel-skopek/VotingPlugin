package com.bencodez.votingplugin.voteshop.shop;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Extra vote shop item.
 */
@Getter
@Setter
public class VoteShopExtraItem {

	private String identifier;

	private ConfigurationSection displaySection;
}
