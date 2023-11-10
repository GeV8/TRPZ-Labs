package service;

import entity.Equalizer;
import repository.EqualizerRepository;

public class EqualizerService {
    EqualizerRepository equalizerRepository;
    Equalizer chosenEqualizer;



    public void chooseEqualizer(long id) {
        chosenEqualizer = equalizerRepository.getById(id);
    }

    public void changeVolume(int volume) {
        chosenEqualizer.setVolume(volume);
    }

    public void changeBassBooster(int volume) {
        chosenEqualizer.setBassBooster(volume);
    }

    public void changeSoundBooster(int volume) {
        chosenEqualizer.setSoundBooster(volume);
    }

}
