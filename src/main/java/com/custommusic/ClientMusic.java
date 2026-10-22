package com.custommusic;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.loading.FMLPaths;
import org.lwjgl.glfw.GLFW;

import java.nio.file.Files;
import java.nio.file.Path;

/** Tout ce qui touche au client (touches, menu, lecture). */
public class ClientMusic {
    private static final KeyMapping NEXT = new KeyMapping("key.custommusic.next", GLFW.GLFW_KEY_N, "key.categories.custommusic");
    private static final KeyMapping PAUSE = new KeyMapping("key.custommusic.pause", GLFW.GLFW_KEY_M, "key.categories.custommusic");
    private static final KeyMapping MENU = new KeyMapping("key.custommusic.menu", GLFW.GLFW_KEY_K, "key.categories.custommusic");
    private static MusicPlayer player;

    public static void init(IEventBus modBus) {
        Path dir = FMLPaths.GAMEDIR.get().resolve("custom_music");
        try { Files.createDirectories(dir); } catch (Exception ignored) {}
        player = new MusicPlayer(dir);

        modBus.addListener((RegisterKeyMappingsEvent e) -> {
            e.register(NEXT);
            e.register(PAUSE);
            e.register(MENU);
        });
        MinecraftForge.EVENT_BUS.addListener(ClientMusic::onTick);

        Runtime.getRuntime().addShutdownHook(new Thread(player::shutdown));
        player.start();
    }
