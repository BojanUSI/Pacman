package AudioEngine;

import Media.EAudio;
import Media.Media;

import javax.sound.sampled.LineEvent;import javax.sound.sampled.Clip;

import javax.sound.sampled.LineListener;

import java.util.ArrayList;
import java.util.EnumMap;

public class AudioEngine {
    
    private static final ArrayList<AudioEntity> entities = new ArrayList<AudioEntity>();
    
    /**
     * Initializes the AudioEngine class.
     */
    public static void initAudioEngine() {
        try {
            entities.add(new AudioEntity(EAudio.ghost_moving, PlaybackMode.loop));
        
        } catch (Exception e) {
            System.out.println("[-] Couldn't set up the AudioEntities correctly.");
            e.printStackTrace();
        }
    }
    
    
    /**
     * Creates ad plays an AudioEntity on demand, without keeping track of it.
     * @param audio the audio to play
     * @param callback the callback function to call after the AudioEntity has finished playing
     */
    public static void play(EAudio audio, PlaybackMode mode, FunctionCallback callback) {
        
        try {
            
            AudioEntity a = new AudioEntity(audio, mode);
            entities.add(a);
            
            Clip c = a.getClip();
            
            c.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event){
                    if (event.getType() == LineEvent.Type.STOP && a.status != PlaybackStatus.paused) {
                        c.removeLineListener(this);
                        c.close();
                        entities.remove(a);
                        if (callback != null) {
                            callback.callback();
                        }
                    }
                }
            });
            
            a.play();
        }catch (Exception e) {
            System.out.println("[-] Couldn't set up the AudioEntity correctly.");
            e.printStackTrace();
        }
    }
    
    /**
     * Plays an audio only if its not playing already.
     * @param audio the audio to play
     * @param callback the callback function to call after the AudioEntity has finished playing
     */
    public static void playIfNotAlready(EAudio audio, PlaybackMode mode, FunctionCallback callback) {
        if (!isPlaying(audio))
            play(audio,mode, callback);
    }
    
    public static void restartOrPlay(EAudio audio, PlaybackMode mode, FunctionCallback callback) {
        for (AudioEntity e : entities) {
            if(e.getAudio() == audio) {
                e.restart();
                return;
            }
        }
        play(audio, mode, callback);
    }
    
    /**
     * Pauses all AudioEntities.
     */
    public static void pauseAll() {
        for(AudioEntity e : entities) {
            e.pause();
        }
    }
    
    /**
     * Resumes all AudioEntities.
     */
    public static void resumeALl() {
        for(AudioEntity e : entities) {
            e.resumeAudio();
        }
    }
    
    public static boolean isPlaying(EAudio audio) {
        for(AudioEntity a : entities) {
            if(a.isPlaying()) {
                return true;
            }
        }
        return false;
    }
}
