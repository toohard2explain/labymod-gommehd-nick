package net.niure.addons.nick.v1_21_4.mixins;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.TeleportTransition;
import net.niure.addons.nick.MirrorAddon;
import net.niure.addons.nick.event.PlayerTeleportEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class TeleportMixin {

  @Inject(method = "teleport", at = @At("HEAD"))
  private void mixinTeleport(TeleportTransition $$0, CallbackInfoReturnable<Entity> cir) {
    MirrorAddon.getInstance().labyAPI().eventBus().fire(new PlayerTeleportEvent());
  }
}
