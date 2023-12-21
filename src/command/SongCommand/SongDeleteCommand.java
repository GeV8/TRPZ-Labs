package command.SongCommand;

import entity.Song;
import service.SongService;

import java.sql.SQLException;

public class SongDeleteCommand extends SongCommand{

    Song song;
    private final SongService songService;

    public SongDeleteCommand(Song song, SongService songService) {
        this.song = song;
        this.songService = songService;
    }

    @Override
    public void execute() throws SQLException {
        songService.deleteSong(song.getId());
    }
}
