package net.berringer.birdbrain.events;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import org.apache.commons.lang3.ObjectUtils;

public class EndTickEvent implements ClientTickEvents.EndTick {
    private KeyBinding birdbrainKeyAutoMove, birdbrainKeyNorth, birdbrainKeyWest, birdbrainKeySouth, birdbrainKeyEast;
    private int autoJumpDefault;
    private boolean autoMoveActive = false;
    private boolean hasTurned = false;
    private boolean hasToggled = false;

    public EndTickEvent (KeyBinding autoMove, KeyBinding north, KeyBinding west,KeyBinding south,KeyBinding east) {
        birdbrainKeyAutoMove = autoMove;
        birdbrainKeyNorth = north;
        birdbrainKeyWest = west;
        birdbrainKeySouth = south;
        birdbrainKeyEast = east;
        autoJumpDefault = -1;
    }
    @Override
    public void onEndTick(MinecraftClient client) {
        if (!hasTurned) {
            if (birdbrainKeyNorth.isPressed() && !birdbrainKeyWest.isPressed() && !birdbrainKeySouth.isPressed() && !birdbrainKeyEast.isPressed()) {
                if (setYaw(client.player, -180.0f)) {
                    hasTurned = true;
                    client.player.sendMessage(Text.literal("Turning North"), true);
                }
            }
            if (birdbrainKeyWest.isPressed() && !birdbrainKeyNorth.isPressed() && !birdbrainKeySouth.isPressed() && !birdbrainKeyEast.isPressed()) {
                if (setYaw(client.player, 90.0f)) {
                    hasTurned = true;
                    client.player.sendMessage(Text.literal("Turning West"), true);
                }
            }
            if (birdbrainKeySouth.isPressed() && !birdbrainKeyNorth.isPressed() && !birdbrainKeyWest.isPressed() && !birdbrainKeyEast.isPressed()) {
                if (setYaw(client.player, 0.0f)) {
                    hasTurned = true;
                    client.player.sendMessage(Text.literal("Turning South"), true);
                }
            }
            if (birdbrainKeyEast.isPressed() && !birdbrainKeyNorth.isPressed() && !birdbrainKeyWest.isPressed() && !birdbrainKeySouth.isPressed()) {
                if (setYaw(client.player, -90.0f)) {
                    hasTurned = true;
                    client.player.sendMessage(Text.literal("Turning East"), true);
                }
            }
        } else if (!birdbrainKeyNorth.isPressed() && !birdbrainKeyWest.isPressed() && !birdbrainKeySouth.isPressed() && !birdbrainKeyEast.isPressed()) {
            hasTurned = false;
        }

        if (!hasToggled && birdbrainKeyAutoMove.isPressed()) {
            hasToggled = true;
            if (!autoMoveActive) {
                autoMoveActive = true;
                client.options.forwardKey.setPressed(true);
                client.player.sendMessage(Text.literal("Auto Move Enabled"), true);
            } else {
                autoMoveActive = false;
                client.options.forwardKey.setPressed(false);
                client.player.sendMessage(Text.literal("Auto Move Disabled"), true);
            }
        }

        if (hasToggled && !birdbrainKeyAutoMove.isPressed()) {
            hasToggled = false;
        }

        if (autoMoveActive) {
            if (!client.options.forwardKey.isPressed()) {
                autoMoveActive = false;
                client.options.forwardKey.setPressed(false);
                client.player.sendMessage(Text.literal("Auto Move Disabled"), true);
            }
            if (client.options.backKey.isPressed()) {
                autoMoveActive = false;
                client.options.forwardKey.setPressed(false);
                client.player.sendMessage(Text.literal("Auto Move Disabled"), true);
            }
        }

        if (autoMoveActive && client.player.isRiding() && client.options.sneakKey.isPressed()) {
            autoMoveActive = false;
            client.options.forwardKey.setPressed(false);
            client.player.sendMessage(Text.literal("Auto Move Disabled"), true);
        }

        if (autoJumpDefault < 0) {
            autoJumpDefault = (client.options.getAutoJump().getValue()) ? 1 : 0;
        }
        else if (autoJumpDefault == 0) {
            client.options.getAutoJump().setValue(autoMoveActive);
        }
    }

    private boolean setYaw(ClientPlayerEntity player, float yaw) {

        boolean valueChanged = false;

        if (player.isRiding() && player.getVehicle().getYaw() != yaw) {
            player.getVehicle().setYaw(yaw);
            player.setYaw(yaw);
            player.setBodyYaw(yaw);
            player.setHeadYaw(yaw);
            valueChanged = true;
        }

        if (!player.isRiding() && player.getYaw() != yaw) {
            player.setYaw(yaw);
            player.setBodyYaw(yaw);
            player.setHeadYaw(yaw);
            valueChanged = true;
        }

        return valueChanged;
    }
}
