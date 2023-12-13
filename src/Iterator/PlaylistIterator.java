package Iterator;

import entity.Playlist;

import java.util.ArrayList;

public class PlaylistIterator implements IPlaylistIterator {
    ArrayList<Playlist> playlistList;
    private int currentPosition;

    public PlaylistIterator(ArrayList<Playlist> songList) {
        this.playlistList = songList;
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
