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
import net.runelite.api.clan.ClanChannelMember;
import java.util.List;
import java.util.Objects;


@Slf4j
@PluginDescriptor(
		name = "Jacob"
)
public class ExamplePlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ExampleConfig config;

	@Override
	protected void startUp() throws Exception
	{
		System.out.println("Example started!");
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

		if (Objects.equals(sender, "Dzakar")) {
			getClanMembers();
		}
	}

	private void getClanMembers()
	{
		while (true) {
			if (client.getClanChannel() != null) {
				List<ClanChannelMember> members = client.getClanChannel().getMembers();
				int membersOnline = members.size();
				System.out.println("Members online: " + membersOnline);
				for (ClanChannelMember member : members) {
					System.out.println(member.getName());
				}
				break;
			}
		}
	}

	@Provides
	ExampleConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ExampleConfig.class);
	}
}
