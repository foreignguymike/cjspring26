package com.distraction.cjspring26.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

import java.util.HashMap;
import java.util.Map;

public class AudioHandler {

    private final Map<String, Music> music;
    private final Map<String, Sound> sounds;

    private final Map<String, MusicConfig> playing;

    public AudioHandler() {
        music = new HashMap<>();
        addMusic("main", "music/ontheway.ogg");
        addMusic("beach", "music/beach.mp3");
        sounds = new HashMap<>();
        importSounds();

        playing = new HashMap<>();
    }

    private void importSounds() {
        FileHandle dir = Gdx.files.internal("assets/sfx");
        for (FileHandle file : dir.list()) {
            String ext = file.extension().toLowerCase();
            if (ext.equals("wav")) {
                sounds.put(file.nameWithoutExtension(), Gdx.audio.newSound(file));
            }
        }
    }

    private void addMusic(String key, String fileName) {
        music.put(key, Gdx.audio.newMusic(Gdx.files.internal(fileName)));
    }

    public void playMusic(String key, float volume, boolean looping) {
        Music newMusic = music.get(key);
        if (newMusic == null) {
            throw new IllegalArgumentException("Unknown music: " + key);
        }
        if (playing.containsKey(key)) {
            playing.get(key).play();
        } else {
            MusicConfig musicConfig = new MusicConfig(music.get(key), volume, looping);
            musicConfig.play();
            playing.put(key, musicConfig);
        }
    }

    public void stopMusic() {
        for (Map.Entry<String, MusicConfig> entry : playing.entrySet()) {
            entry.getValue().stop();
        }
    }

    public void stopMusic(String key) {
        if (playing.containsKey(key)) {
            playing.get(key).stop();
        }
    }

    public void playSound(String key) {
        playSound(key, 1, false, 1f);
    }

    public void playSound(String key, float volume) {
        playSound(key, volume, false, 1f);
    }

    public void playSound(String key, float volume, float pitch) {
        playSound(key, volume, false, pitch);
    }

    public void playSoundCut(String key, float volume) {
        playSound(key, volume, true, 1f);
    }

    public void playSound(String key, float volume, boolean cut, float pitch) {
        for (Map.Entry<String, Sound> entry : sounds.entrySet()) {
            if (entry.getKey().equals(key)) {
                if (cut) entry.getValue().stop();
                entry.getValue().play(volume, pitch, 1f);
            }
        }
    }

    public void dispose() {
        for (Music m : music.values()) m.dispose();
        for (Sound s : sounds.values()) s.dispose();
    }

}
