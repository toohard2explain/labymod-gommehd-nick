package net.niure.addons.nick.storage;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.notification.Notification;
import net.niure.addons.nick.MirrorAddon;
import java.util.HashMap;
import java.util.Map;

public class NickStorage {

  private final Map<String, String> nicknameMap = new HashMap<>();

  public boolean readNicknamesFromChatMessage(String message) {
    String regexList = "\\[NICK\\] - (.+): (.+)";
    String regexSelf = "\\[NICK\\] (.+): (.+)";

    String regexRemoved = "\\[NICK\\] (.+) (removed|entfernt)";

    if (message.matches(regexRemoved)) {
      String originalName = this.getOwnOriginalName();
      this.nicknameMap.remove(originalName);

      MirrorAddon.getInstance().logger().debug("Removed nickname");
      return true;
    } else if (message.matches(regexList)) {
      String originalName = message.replaceAll(regexList, "$1");
      String nickname = message.replaceAll(regexList, "$2");
      this.nicknameMap.put(originalName, nickname);

      MirrorAddon.getInstance().logger().debug("Recognized nickname: " + originalName + " -> " + nickname);
      return true;
    } else if (message.matches(regexSelf)) {
      String originalName = this.getOwnOriginalName();
      String nickname = message.replaceAll(regexSelf, "$2");
      this.nicknameMap.put(originalName, nickname);

      MirrorAddon.getInstance().logger().debug("Recognized own nickname: " + originalName + " -> " + nickname);
      return true;
    }

    return false;
  }

  public boolean isNicked(String originalName) {
    return this.nicknameMap.containsKey(originalName);
  }

  public boolean isNickname(String nickname) {
    return this.nicknameMap.containsValue(nickname);
  }

  public String getNicknameByOriginalName(String originalName) {
    return this.nicknameMap.get(originalName);
  }

  public String getOriginalNameByNickname(String nickname) {
    return this.nicknameMap.entrySet().stream()
        .filter(entry -> entry.getValue().equals(nickname))
        .map(Map.Entry::getKey)
        .findFirst()
        .orElse(null);
  }

  public void setNickname(String originalName, String nickname) {
    this.nicknameMap.put(originalName, nickname);
  }

  public String getOwnOriginalName() {
    ClientPlayer clientPlayer = MirrorAddon.getInstance().labyAPI().minecraft().getClientPlayer();

    if (clientPlayer != null) {
      return clientPlayer.getName();
    }

    return MirrorAddon.getInstance().labyAPI().getName();
  }

  public String getOwnNickname() {
    return this.getNicknameByOriginalName(this.getOwnOriginalName());
  }

  public void reset() {
    this.nicknameMap.clear();
  }
}
