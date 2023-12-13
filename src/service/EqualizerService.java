package service;

import entity.Equalizer;
import repository.IEqualizerRepository;

import java.sql.SQLException;

public class EqualizerService {
    IEqualizerRepository equalizerRepository;
    Equalizer chosenEqualizer;

    public EqualizerService(IEqualizerRepository equalizerRepository) {
        this.equalizerRepository = equalizerRepository;
    }

    public void chooseEqualizer(long id) throws SQLException {
        chosenEqualizer = equalizerRepository.getById(id);
    }

    public void changeVolume(int volume) {
        chosenEqualizer.setVolume(volume);
    }

    public void changeBassBooster(int volume) {
        chosenEqualizer.setBassBooster(volume);
    }

    public void addNewEqualizer(Equalizer equalizer) throws SQLException {
        equalizerRepository.add(equalizer);
    }

    public Equalizer getChosenEqualizer() {
        return chosenEqualizer;
    }
}
