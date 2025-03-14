package net.niure.addons.nick;

import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.models.addon.annotation.AddonMain;
import net.niure.addons.nick.listener.ChatListener;
import net.niure.addons.nick.listener.ServerListener;
import net.niure.addons.nick.storage.NickStorage;
import net.niure.addons.nick.widget.NicknameWidget;

@AddonMain
public class MirrorAddon extends LabyAddon<MirrorAddonConfiguration> {

  private static MirrorAddon instance;
  private NickStorage nickStorage;

  @Override
  protected void enable() {
    instance = this;
    this.nickStorage = new NickStorage();

    HudWidgetCategory category = new HudWidgetCategory("mirror_ghdnick");
    labyAPI().hudWidgetRegistry().categoryRegistry().register(category);
    labyAPI().hudWidgetRegistry().register(new NicknameWidget(category));

    this.registerSettingCategory();

    this.registerListener(new ChatListener());
    this.registerListener(new ServerListener());
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
}
