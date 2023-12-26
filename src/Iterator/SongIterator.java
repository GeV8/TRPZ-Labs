package Iterator;

import entity.Song;

import java.util.ArrayList;
import java.util.LinkedList;

public class SongIterator implements ISongIterator {
    LinkedList<Song> songList;
    private int currentPosition;

    public SongIterator(LinkedList<Song> songList) {
        this.songList = songList;
        this.currentPosition = 0;
    }

    @Override
    public boolean hasNext() {
        return currentPosition < songList.size();
    }

    @Override
    public Song getNext() {
        if (!hasNext()) {
            return null;
        }
        Song songIteration=songList.get(currentPosition);
        currentPosition++;
        return songIteration ;

    }

    @Override
    public void reset() {
        currentPosition = 0;
    }
}
