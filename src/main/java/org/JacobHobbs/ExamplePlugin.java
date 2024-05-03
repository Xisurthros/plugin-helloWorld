package org.JacobHobbs;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
		name = "Example Plugin",
		description = "Prints player angle when orientation changes",
		tags = {"example", "plugin"}
)
public class ExamplePlugin extends Plugin
{
	@Inject
	private Client client;

	private int lastOrientation = -1;

	@Override
	protected void startUp() throws Exception
	{
		if (client.getGameState() == GameState.LOGGED_IN)
		{
			lastOrientation = client.getLocalPlayer().getOrientation();
			printAngle();
		}
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		if (event.getGameState() == GameState.LOGGED_IN)
		{
			lastOrientation = client.getLocalPlayer().getOrientation();
			printAngle();
		}
	}

	@Subscribe
	public void onGameTick(GameTick event)
	{
		if (client.getLocalPlayer() != null && client.getLocalPlayer().getOrientation() != lastOrientation)
		{
			lastOrientation = client.getLocalPlayer().getOrientation();
			printAngle();
		}
	}

	private void printAngle()
	{
		if (client.getLocalPlayer() == null)
		{
			log.warn("Player is null");
			return;
		}

		int orientation = client.getLocalPlayer().getOrientation();
		log.info("Player orientation: {}", orientation);
	}

	@Provides
	ExampleConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ExampleConfig.class);
	}
}
