package com.jamdotjar.webstatus.status;

import java.util.List;

public record StatusUpdate(
        String motd,
        int onlinePlayers,
        List<PlayerInfo> players
) {
    public record PlayerInfo(
            String name,
            Float hp,
            String dimension,
            String gameMode
    ){}
}
