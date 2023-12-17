package io.izzel.arclight.lightcity;

import com.velocitypowered.api.util.GameProfile;
import com.velocitypowered.proxy.config.PlayerInfoForwarding;
import com.velocitypowered.proxy.connection.backend.BackendConnectionPhases;
import com.velocitypowered.proxy.connection.client.ClientConnectionPhases;
import com.velocitypowered.proxy.connection.util.ConnectionTypeImpl;

public class ModernForgeConnectionType extends ConnectionTypeImpl {

    private static final GameProfile.Property IS_FORGE_CLIENT_PROPERTY =
        new GameProfile.Property("forgeClient", "true", "");

    private final String extra;

    public ModernForgeConnectionType(String extra) {
        super(ClientConnectionPhases.VANILLA, BackendConnectionPhases.VANILLA);
        this.extra = extra;
    }

    @Override
    public GameProfile addGameProfileTokensIfRequired(GameProfile original, PlayerInfoForwarding forwardingType) {
        return super.addGameProfileTokensIfRequired(
            original
                .addProperty(IS_FORGE_CLIENT_PROPERTY)
                .addProperty(new GameProfile.Property("extraData", this.extra.replaceAll("\0", "\1"), "")),
            forwardingType);
    }
}
