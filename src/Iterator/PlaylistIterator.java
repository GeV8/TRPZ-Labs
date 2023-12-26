package Iterator;

import entity.Playlist;

import java.util.ArrayList;
import java.util.LinkedList;

public class PlaylistIterator implements IPlaylistIterator {
    LinkedList<Playlist> playlistList;
    private int currentPosition;

    public PlaylistIterator(LinkedList<Playlist> playlistList) {
        this.playlistList = playlistList;
        this.currentPosition = 0;
    }

    @Override
    public boolean hasNext() {
        return currentPosition < playlistList.size();
    }

    @Override
    public Playlist getNext() {
        if (!hasNext()) {
            return null;
        }
        Playlist songIteration=playlistList.get(currentPosition);
        currentPosition++;
        return songIteration ;

    }

    @Override
    public void reset() {
        currentPosition = 0;
    }
}
