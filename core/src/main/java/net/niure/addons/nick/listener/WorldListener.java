package net.niure.addons.nick.listener;

import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.world.WorldEnterEvent;
import net.niure.addons.nick.MirrorAddon;

public class WorldListener {

  @Subscribe
  public void onWorldSwitch(WorldEnterEvent event) {
    ServerData current = MirrorAddon.getInstance().labyAPI().serverController().getCurrentServerData();

    if (current == null) {
      return;
    }

    if (current.address() == null) {
      return;
    }

    if (!current.address().getHost().toLowerCase().contains("gomme")) {
      return;
    }

    MirrorAddon.getInstance().labyAPI().minecraft().chatExecutor().chat("/nicks");
  }

}
