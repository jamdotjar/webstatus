package com.jamdotjar.webstatus.status;

import java.util.List;

public record StatusUpdate(
        String motd,
        int onlinePlayers,
        List<String> players
) {}
