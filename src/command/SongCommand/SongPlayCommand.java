package command.SongCommand;

import entity.Song;
import service.SongService;

import java.sql.SQLException;

public class SongPlayCommand extends SongCommand {
    Song song;
    private SongService songService;

    public SongPlayCommand(Song song, SongService songService) {
        this.song = song;
        this.songService = songService;
    }

    @Override
    public void execute() throws SQLException {
        songService.playMusic(song.getId());
    }
}
