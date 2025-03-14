package net.niure.addons.nick.nametag;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.tag.tags.NameTag;
import net.labymod.api.client.render.font.RenderableComponent;
import net.niure.addons.nick.MirrorAddon;
import net.niure.addons.nick.storage.NickStorage;
import org.jetbrains.annotations.Nullable;

public class NicknameNametag extends NameTag {

  @Override
  public float getScale() {
    return 0.6f;
  }

  @Override
  protected @Nullable RenderableComponent getRenderableComponent() {
    if (!MirrorAddon.getInstance().configuration().showNicknameOverHead().get()) {
      return null;
    }

    if(entity == null || !(entity instanceof Player player)) return null;
    NickStorage nickStorage = MirrorAddon.getInstance().getNickStorage();

    String nickname = nickStorage.getNicknameByOriginalName(player.getName());

    if (nickname == null) return null;

    return RenderableComponent.of(Component.text("§6" + nickname));
  }

  @Override
  public boolean isVisible() {
    return !this.entity.isCrouching() && super.isVisible();
  }
}