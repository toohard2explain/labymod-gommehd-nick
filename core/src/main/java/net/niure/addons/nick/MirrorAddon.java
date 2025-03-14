package net.niure.addons.nick;

import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.entity.player.tag.PositionType;
import net.labymod.api.client.entity.player.tag.TagRegistry;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.models.addon.annotation.AddonMain;
import net.niure.addons.nick.listener.ChatListener;
import net.niure.addons.nick.listener.WorldListener;
import net.niure.addons.nick.nametag.NicknameNametag;
import net.niure.addons.nick.storage.NickStorage;
import net.niure.addons.nick.widget.NicknameWidget;

@AddonMain
public class MirrorAddon extends LabyAddon<MirrorAddonConfiguration> {

  private static MirrorAddon instance;
  private NickStorage nickStorage;

  private static long lastNickRequest = 0;

  @Override
  protected void enable() {
    instance = this;
    this.nickStorage = new NickStorage();

    HudWidgetCategory category = new HudWidgetCategory("mirror_ghdnick");
    labyAPI().hudWidgetRegistry().categoryRegistry().register(category);
    labyAPI().hudWidgetRegistry().register(new NicknameWidget(category));

    TagRegistry tagRegistry = labyAPI().tagRegistry();
    tagRegistry.register(
        "ghdnick_nickname",
        PositionType.ABOVE_NAME,
        new NicknameNametag()
    );

    this.registerSettingCategory();

    this.registerListener(new ChatListener());
    this.registerListener(new WorldListener());
  }

  @Override
  protected Class<MirrorAddonConfiguration> configurationClass() {
    return MirrorAddonConfiguration.class;
  }

  public static MirrorAddon getInstance() {
    return instance;
  }

  public NickStorage getNickStorage() {
    return nickStorage;
  }

  public static long getLastNickRequest() {
    return lastNickRequest;
  }

  public static void setLastNickRequest(long lastNickRequest) {
    MirrorAddon.lastNickRequest = lastNickRequest;
  }
}
