package com.teamjmonkey.sound;

public enum MonkeySound {

        TEST_MUSIC("ahem.ogg", true),
        TEST_SOUND("ahem.ogg", false),
        OCEAN("Environment/Ocean Waves.ogg", true);

        private final String path;
        private final boolean music;

        MonkeySound(String filename, boolean isMusic) {
            path = "Sounds/" + filename;
            music = isMusic;
        }

        public String path() {
            return path;
        }

        public boolean isMusic() {
            return music;
        }
}