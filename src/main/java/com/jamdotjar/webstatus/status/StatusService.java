package com.jamdotjar.webstatus.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minecraft.server.MinecraftServer;

import java.util.List;

public class StatusService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private MinecraftServer server;

    public StatusUpdate update() {
        String motd = server.getMotd();
        int onlinePlayers = server.getPlayerCount();
        List<StatusUpdate.PlayerInfo> players = server.getPlayerList().getPlayers().stream()
                .map(player -> new StatusUpdate.PlayerInfo(
                        player.getName().getString(),
                        player.getHealth(),
                        player.level().dimension().toString(),
                        player.gameMode().getName()
                ))
                .toList();
        return new StatusUpdate(motd, onlinePlayers, players);
    }

    public String updateJson() {
        try {
            return objectMapper.writeValueAsString(update());
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize StatusUpdate", e);
        }
    }

    public void setServer(MinecraftServer server) {
        this.server = server;
    }
}


