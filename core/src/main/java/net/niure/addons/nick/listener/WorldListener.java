package net.niure.addons.nick.listener;

import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.world.WorldEnterEvent;
import net.niure.addons.nick.MirrorAddon;
import net.niure.addons.nick.event.PlayerTeleportEvent;
import net.niure.addons.nick.storage.NickStorage;

public class WorldListener {

  @Subscribe
  public void onWorldSwitch(WorldEnterEvent event) {
    this.refetch();
  }

  @Subscribe
  public void onTeleport(PlayerTeleportEvent event) {
    this.refetch();
  }

  private void refetch() {
    if (System.currentTimeMillis() - MirrorAddon.getLastNickRequest() < 1000) {
      return;
    }

    NickStorage nickStorage = MirrorAddon.getInstance().getNickStorage();
    nickStorage.reset();

    ServerData current = MirrorAddon.getInstance().labyAPI().serverController()
        .getCurrentServerData();

    if (current == null) {
      return;
    }

    if (current.address() == null) {
      return;
    }

    if (!current.address().getHost().toLowerCase().contains("gomme")) {
      return;
    }

    MirrorAddon.getInstance().labyAPI().minecraft().chatExecutor().chat("/nicks", false);
    MirrorAddon.setLastNickRequest(System.currentTimeMillis());
  }
}
