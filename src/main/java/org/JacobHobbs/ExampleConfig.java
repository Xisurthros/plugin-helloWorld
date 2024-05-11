package org.JacobHobbs;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("example")
public interface ExampleConfig extends Config
{
	@ConfigItem(
			keyName = "changeSkyboxColor",
			name = "Change Skybox color",
			description = "Change the skybox color with channel chat"
	)
	default boolean changeSkyboxColor()
	{
		return true;
	}

	@ConfigItem(
			keyName = "displayItem",
			name = "Displays Item",
			description = "Type in a number and show the item"
	)
	default boolean displayItem()
	{
		return true;
	}

}
