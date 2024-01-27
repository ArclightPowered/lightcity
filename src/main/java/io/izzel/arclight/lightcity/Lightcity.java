package io.izzel.arclight.lightcity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.ConnectionHandshakeEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.proxy.config.PlayerInfoForwarding;
import com.velocitypowered.proxy.config.VelocityConfiguration;
import com.velocitypowered.proxy.connection.client.InitialInboundConnection;
import com.velocitypowered.proxy.connection.client.LoginInboundConnection;
import com.velocitypowered.proxy.protocol.packet.HandshakePacket;
import org.slf4j.Logger;

import java.util.Locale;

@Plugin(
    id = "lightcity",
    name = "lightcity",
    authors = "IzzelAliz",
    url = "https://github.com/ArclightPowered/lightcity",
    version = BuildConstants.VERSION
)
public class Lightcity {

    private static final String MODERN_FORGE_TOKEN = "\0FORGE";

    @Inject private Logger logger;
    @Inject private ProxyServer server;

    @Subscribe
    public void onInit(ProxyInitializeEvent event) {
        var forwarding = ((VelocityConfiguration) server.getConfiguration()).getPlayerInfoForwardingMode();
        if (forwarding == PlayerInfoForwarding.NONE) {
            logger.warn("Velocity config player-info-forwarding-mode is configured as " + forwarding.toString().toLowerCase(Locale.ROOT));
            logger.warn("You should set it as legacy to enable Arclight support.");
        }
    }

    @Subscribe
    public void onConnect(ConnectionHandshakeEvent event) throws Exception {
        var connection = (LoginInboundConnection) event.getConnection();
        var delegateField = LoginInboundConnection.class.getDeclaredField("delegate");
        delegateField.setAccessible(true);
        var initConn = (InitialInboundConnection) delegateField.get(connection);
        var field = InitialInboundConnection.class.getDeclaredField("handshake");
        field.setAccessible(true);
        var handshake = (HandshakePacket) field.get(initConn);
        var host = handshake.getServerAddress();
        if (host.contains("\0")) {
            var split = host.split("\0", 2);
            var extra = "\0" + split[1];
            if (extra.contains(MODERN_FORGE_TOKEN)) {
                initConn.getConnection().setType(new ModernForgeConnectionType(extra));
            }
        }
    }
}
