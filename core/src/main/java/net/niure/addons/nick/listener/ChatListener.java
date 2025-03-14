package net.niure.addons.nick.listener;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;
import net.labymod.api.event.client.chat.ChatReceiveEvent;
import net.niure.addons.nick.MirrorAddon;
import net.niure.addons.nick.storage.NickStorage;
import java.util.Arrays;

public class ChatListener {

  @Subscribe
  public void onChat(ChatReceiveEvent event) {
    String plainText = event.chatMessage().getPlainText();
    NickStorage nickStorage = MirrorAddon.getInstance().getNickStorage();

    MirrorAddon.getInstance().logger().debug("Received chat message: " + plainText);

    nickStorage.readNicknamesFromChatMessage(plainText);

    if (plainText.startsWith("[NICK]") || plainText.contains("/help")) {
      long distance = MirrorAddon.getLastNickRequest() - System.currentTimeMillis();

      if (distance < 500) {
        event.setCancelled(true);
        return;
      }

      return;
    }

    if (!MirrorAddon.getInstance().configuration().replaceInChatOnReceive().get()) {
      return;
    }

    Component[] args = event.chatMessage().component().getChildren().toArray(new Component[0]);

    for (int i = 0; i < args.length; i++) {
      if (!(args[i] instanceof TextComponent textComponent)) {
        continue;
      }

      if (nickStorage.isNickname(textComponent.getText())) {
        args[i] = textComponent.text(nickStorage.getOriginalNameByNickname(textComponent.getText())).decorate(
            TextDecoration.ITALIC);
      }
    }

    event.chatMessage().component().setChildren(Arrays.asList(args));
  }

  @Subscribe
  public void onChatSend(ChatMessageSendEvent event) {
    if (!MirrorAddon.getInstance().configuration().replaceInChatOnSend().get()) {
      return;
    }

    if (event.getMessage().contains("/nicks")) {
      NickStorage nickStorage = MirrorAddon.getInstance().getNickStorage();
      nickStorage.reset();
      return;
    }

    String[] args = event.getMessage().split(" ");

    if (args.length == 0) {
      return;
    }

    NickStorage nickStorage = MirrorAddon.getInstance().getNickStorage();

    for (int i = 0; i < args.length; i++) {
      String originalName = args[i];

      if (nickStorage.isNicked(originalName)) {
        args[i] = nickStorage.getNicknameByOriginalName(originalName);
      }
    }

    event.changeMessage(String.join(" ", args));
  }
}
