private static void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Minecraft mc = Minecraft.getInstance();
        mc.getMusicManager().stopPlaying();

        float master = mc.options.getSoundSourceVolume(SoundSource.MASTER);
        float music = mc.options.getSoundSourceVolume(SoundSource.MUSIC);
        player.setVolume(master * music);

        while (MENU.consumeClick()) {
            if (mc.screen == null) mc.setScreen(new MusicScreen(player));
        }
        while (NEXT.consumeClick()) {
            player.next();
            msg(mc, "\u00a7a\u266a " + player.currentName());
        }
        while (PAUSE.consumeClick()) {
            player.togglePause();
            msg(mc, player.isPaused() ? "\u00a7e\u266a Pause" : "\u00a7a\u266a " + player.currentName());
        }
    }

    private static void msg(Minecraft mc, String s) {
        if (mc.player != null) mc.player.displayClientMessage(Component.literal(s), true);
    }
}
