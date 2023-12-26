package service;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class EqualizerService {


    public void changeVolume(Clip clip, float sliderValue) {
        FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float dB = (float) (Math.log(sliderValue) / Math.log(10.0) * 20.0);
        volumeControl.setValue(dB);
    }

}
