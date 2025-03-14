package net.niure.addons.nick.widget;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourceLocation;
import net.niure.addons.nick.MirrorAddon;
import net.niure.addons.nick.storage.NickStorage;

public class NicknameWidget extends TextHudWidget<TextHudWidgetConfig> {

  private TextLine textLine;

  public NicknameWidget(HudWidgetCategory category) {
    super("mirror_ghdnick_nickname");

    this.setIcon(Icon.texture(ResourceLocation.create("ghdnick", "textures/widgets/nickname.png")));
    this.bindCategory(category);
  }

  @Override
  public void load(TextHudWidgetConfig config) {
    super.load(config);

    NickStorage nickStorage = MirrorAddon.getInstance().getNickStorage();

    if (nickStorage.getOwnNickname() == null) {
      this.textLine = super.createLine(
          Component.translatable("ghdnick.hud.nickname.name"),
          Component.translatable("ghdnick.hud.nickname.empty")
      );

      return;
    }

    this.textLine = super.createLine(
        Component.translatable("ghdnick.hud.nickname.name"),
        Component.text(nickStorage.getOwnNickname())
    );
  }

  @Override
  public void onTick(boolean isEditorContext) {
    NickStorage nickStorage = MirrorAddon.getInstance().getNickStorage();

    if (nickStorage.getOwnNickname() == null) {
      textLine.updateAndFlush(
          Component.translatable("ghdnick.hud.nickname.empty")
      );

      return;
    }

    textLine.updateAndFlush(
        Component.text(nickStorage.getOwnNickname())
    );
  }
}
