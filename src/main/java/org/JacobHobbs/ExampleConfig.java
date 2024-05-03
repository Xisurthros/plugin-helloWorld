package org.JacobHobbs;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("example")
public interface ExampleConfig extends Config
{
	@ConfigItem(
		keyName = "greeting",
		name = "Welcome Greeting",
		description = "The message to show to the user when they login"
	)
	default String greeting()
	{
		return "Hello";
	}

	@ConfigItem(
			keyName = "toggleFeature",
			name = "Toggle Feature",
			description = "Enable or disable a feature"
	)
	default boolean toggleFeature()
	{
		return false; // Default value
	}
}
