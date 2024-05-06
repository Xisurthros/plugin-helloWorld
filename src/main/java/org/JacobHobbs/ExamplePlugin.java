package org.JacobHobbs;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;

import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.api.events.ChatMessage;
import java.util.Objects;


@Slf4j
@PluginDescriptor(
	name = "Jacob"
)
public class ExamplePlugin extends Plugin
{
	private int lastOrientation = -1;

	@Inject
	private Client client;

	@Inject
	private ExampleConfig config;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Example started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Example stopped!");
	}

	@Subscribe
	public void onChatMessage(ChatMessage chatMessage)
	{
		String sender = chatMessage.getName();
		int messageType = chatMessage.getType().ordinal();
		String messageContent = chatMessage.getMessage();
		String messageTimestamp = String.valueOf(chatMessage.getTimestamp());

		if (Objects.equals(sender, "Dzakar")) {
			System.out.println("Sender: " + sender);
			System.out.println("Message Type: " + messageType);
			System.out.println("Message Content: " + messageContent);
			System.out.println("Timestamp: " + messageTimestamp);
		}
	}


	@Provides
	ExampleConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ExampleConfig.class);
	}
}
