package org.JacobHobbs;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;

import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.MenuOpened;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.api.events.ChatMessage;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@PluginDescriptor(
		name = "Jacob"
)
public class ExamplePlugin extends Plugin
{
	WorldPoint currentWorldPoint;
	private RuneLiteObject currentItem = null;

	@Inject
	private Client client;

	@Inject
	private ExampleConfig config;

	@Override
	protected void startUp() throws Exception
	{
		System.out.println("Example started!");
		client.setUsername("MagicJake548");
		client.setPassword("QNBFhdfmeXdtgFRqPSyy");
	}

	@Override
	protected void shutDown() throws Exception
	{
		System.out.println("Example stopped!");
	}

	@Subscribe
	public void onChatMessage(ChatMessage chatMessage)
	{

		String sender = chatMessage.getName();
		String messageSender = client.getLocalPlayer().getName();
		int messageType = chatMessage.getType().ordinal();
		String messageContent = chatMessage.getMessage();

		if (config.changeSkyboxColor()) {
			if (Objects.equals(messageType, 8) && Objects.equals(sender, "Dzakar")) {
				changeSkybox(messageContent);
				log.info("Client user logged in is: {}", messageSender);
				chatMessage.setMessage("Testing Hello");
				List<NPC> npcs = client.getNpcs();
				npcs.forEach(npc -> {
					if (npc.getId() == 2897)
					{
						npc.setOverheadText("I'm a Banker!");
						npc.setOverheadCycle(600);
					} else {
						npc.setOverheadText("Hello, World!");
						npc.setOverheadCycle(600);
						log.info(String.valueOf(npc.getId()));
					}
				});
			}
		}
	}

	@Subscribe
	public void onGameTick(GameTick e)
	{
		if(client.getLocalPlayer() == null) return;

		if (config.displayItem())
		{
			currentWorldPoint = client.getLocalPlayer().getWorldLocation();
			displayItem(client.getLocalPlayer().getWorldLocation());
		}
		if (!config.displayItem())
		{
			clearAnimation();
			return;
		}
	}

	@Subscribe
	public void onMenuOpened(MenuOpened menu)
	{
		String menu_data = Arrays.toString(menu.getMenuEntries());
		log.info("Menu data: {}", menu_data);
	}

	private void displayItem(WorldPoint point)
	{
		if (currentItem != null && !currentItem.getLocation().equals(point)) {
			// Hide the current item
			currentItem.setActive(false);
		}

		Model model = client.loadModel(9941);

		if (currentItem == null) {
			// If currentItem is null, create a new item
			currentItem = client.createRuneLiteObject();
			currentItem.setModel(model);
		}

		// Update the location of the current item
		currentItem.setLocation(LocalPoint.fromWorld(client, point), point.getPlane());
		currentItem.setActive(true);
	}

	private void clearAnimation()
	{
		if (currentItem != null) {
			currentItem.setActive(false);
			currentItem = null;
		}
	}

	private void changeSkybox(String color_input)
	{
		Map<String, Integer> colors = new HashMap<>();
		colors.put("Red", 0xFF0000); // Red
		colors.put("Green", 0x00FF00); // Green
		colors.put("Blue", 0x0000FF); // Blue

		client.setSkyboxColor(colors.get(color_input));

		DoSomethingElse something = new DoSomethingElse();
		String data = something.sayHi();
		log.info(data);
	}

	@Provides
	ExampleConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ExampleConfig.class);
	}
}

class DoSomethingElse
{
	public String sayHi()
	{
		return "Hello, World!";
	}
}