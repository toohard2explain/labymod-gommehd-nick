package net.niure.addons.nick.listener;

import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.SubServerSwitchEvent;
import net.niure.addons.nick.MirrorAddon;
import net.niure.addons.nick.storage.NickStorage;

public class ServerListener {

  @Subscribe
  public void onSwitch(SubServerSwitchEvent event) {
    NickStorage nickStorage = MirrorAddon.getInstance().getNickStorage();
    nickStorage.reset();

    if(!event.serverData().address().getHost().toLowerCase().contains("gomme")) {
      return;
    }

    MirrorAddon.getInstance().labyAPI().minecraft().chatExecutor().chat("/nicks");
  }
}
