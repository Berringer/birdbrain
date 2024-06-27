package net.berringer.birdbrain;

import net.berringer.birdbrain.events.EndTickEvent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BirdbrainModClient implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("birdbrain");

    public static KeyBinding birdbrainKeyAutoMove, birdbrainKeyNorth, birdbrainKeyWest, birdbrainKeySouth, birdbrainKeyEast;

    @Override
    public void onInitializeClient() {

        birdbrainKeyAutoMove = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.birdbrain.automove",
                InputUtil.Type.MOUSE,
                GLFW.GLFW_MOUSE_BUTTON_4,
                "key.category.birdbrain"
        ));

        birdbrainKeyNorth = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.birdbrain.north",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UP,
                "key.category.birdbrain"
        ));

        birdbrainKeyWest = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.birdbrain.west",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT,
                "key.category.birdbrain"
        ));

        birdbrainKeySouth = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.birdbrain.south",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_DOWN,
                "key.category.birdbrain"
        ));

        birdbrainKeyEast = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.birdbrain.east",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT,
                "key.category.birdbrain"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(new EndTickEvent(birdbrainKeyAutoMove, birdbrainKeyNorth, birdbrainKeyWest, birdbrainKeySouth, birdbrainKeyEast));

        LOGGER.info("BirdBrain mod loaded.");
    }
}