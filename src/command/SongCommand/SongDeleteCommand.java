package command.SongCommand;

import entity.Song;
import service.SongService;

public class SongDeleteCommand extends SongCommand{

    Song song;
    private SongService songService;
    @Override
    public void execute() {
        songService.deleteSong(song.getId());
    }
}
