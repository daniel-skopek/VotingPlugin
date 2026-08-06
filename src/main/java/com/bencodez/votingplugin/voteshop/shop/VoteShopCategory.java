package com.bencodez.votingplugin.voteshop.shop;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Vote shop category.
 */
@Getter
@Setter
public class VoteShopCategory {

	private String id;

	private String name;

	private boolean backButton = true;

	private final Map<String, VoteShopEntry> entries = new LinkedHashMap<String, VoteShopEntry>();
}
