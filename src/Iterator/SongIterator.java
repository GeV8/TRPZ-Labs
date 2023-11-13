package Iterator;

import entity.Song;

import java.util.ArrayList;

public class SongIterator implements ISongIterator {
    ArrayList<Song> songList;
    private int currentPosition;

    public SongIterator(ArrayList<Song> songList) {
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
