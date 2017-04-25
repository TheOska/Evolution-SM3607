package kaihcheng7_kmlee65.scm.evolution.modeltrainning.utils;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;


import kaihcheng7_kmlee65.scm.evolution.R;

import static android.content.Context.AUDIO_SERVICE;

/**
 * Created by TheOska on 11/4/2016.
 */

public class SoundManger{

    private MediaPlayer bgMusicPlayer;
    private Context context;
    private SoundPool soundPool;
    private AudioManager audioManager;
    private static final int MAX_STREAMS = 5;
    private boolean loaded;
    private int idClear, idClick, idDragBack,
                idDragOut, idHint, idReset,
                idSynTheFail, idSynTheNew, idSynTheSucceed;

    public static final int SOUND_CLEAR = 0;
    public static final int SOUND_CLICK = 1;
    public static final int SOUND_DRAG_BACK = 2;
    public static final int SOUND_DRAG_OUT = 3;
    public static final int SOUND_SYN_THE_FAIL = 4;
    public static final int SOUND_SYN_THE_NEW = 5;
    public static final int SOUND_SYN_THE_SUCCEED = 6;


    public SoundManger(Context context){
        this.context = context;
        audioManager = (AudioManager) context.getSystemService(AUDIO_SERVICE);
        if (Build.VERSION.SDK_INT >= 21 ) {
            AudioAttributes audioAttr = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            SoundPool.Builder builder= new SoundPool.Builder();
            builder.setAudioAttributes(audioAttr).setMaxStreams(MAX_STREAMS);

            this.soundPool = builder.build();
        }
        else {
            this.soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        }

        // When Sound Pool load complete.
        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;
            }
        });

        // Load sound file into SoundPool.
        idClear = soundPool.load(context, R.raw.clear,1);
        idClick = soundPool.load(context, R.raw.click,1);
        idDragBack= soundPool.load(context, R.raw.dragback,1);
        idDragOut = soundPool.load(context, R.raw.dragout,1);
        idHint = soundPool.load(context, R.raw.hint,1);
        idReset = soundPool.load(context, R.raw.reset,1);
        idSynTheFail = soundPool.load(context, R.raw.synthefail,1);
        idSynTheSucceed = soundPool.load(context, R.raw.synthesucceed,1);
        idSynTheNew = soundPool.load(context, R.raw.synthenew,1);



    }

    public void startBgMusic(){
        bgMusicPlayer = MediaPlayer.create(context, R.raw.background01);
        bgMusicPlayer.setLooping(true);
        bgMusicPlayer.setVolume(100,100);
        bgMusicPlayer.start();
    }
    public void stopBgMusic(){
        bgMusicPlayer.release();
    }


    public void playSound(int soundType){
        switch (soundType){
            case SOUND_CLEAR:
                soundPool.play(this.idClear,100, 100, 1, 0, 1f);
                break;
            case SOUND_CLICK:
                soundPool.play(this.idClick,100, 100, 1, 0, 1f);
                break;

            case SOUND_DRAG_BACK:
                soundPool.play(this.idDragBack,100, 100, 1, 0, 1f);
                break;

            case SOUND_DRAG_OUT:
                soundPool.play(this.idDragOut,100, 100, 1, 0, 1f);
                break;

            case SOUND_SYN_THE_FAIL:
                soundPool.play(this.idSynTheFail,100, 100, 1, 0, 1f);
                break;

            case SOUND_SYN_THE_NEW:
                soundPool.play(this.idSynTheNew,100, 100, 1, 0, 1f);
                break;

            case SOUND_SYN_THE_SUCCEED:
                soundPool.play(this.idSynTheSucceed,100, 100, 1, 0, 1f);
                break;
        }
    }


}
