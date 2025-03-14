package net.niure.addons.nick;

import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;

@ConfigName("settings")
public class MirrorAddonConfiguration extends AddonConfig {

  @SwitchSetting
  private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

  @SwitchSetting
  private final ConfigProperty<Boolean> replaceInChatOnSend = new ConfigProperty<>(true);

  @SwitchSetting
  private final ConfigProperty<Boolean> showNicknameOverHead = new ConfigProperty<>(true);

  @SwitchSetting
  private final ConfigProperty<Boolean> replaceInChatOnReceive = new ConfigProperty<>(false);

  @Override
  public ConfigProperty<Boolean> enabled() {
    return this.enabled;
  }

  public ConfigProperty<Boolean> replaceInChatOnSend() {
    return this.replaceInChatOnSend;
  }

  public ConfigProperty<Boolean> showNicknameOverHead() {
    return showNicknameOverHead;
  }

  public ConfigProperty<Boolean> replaceInChatOnReceive() {
    return this.replaceInChatOnReceive;
  }
}
