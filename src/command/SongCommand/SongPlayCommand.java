package command.SongCommand;

import entity.Song;
import service.SongService;

public class SongPlayCommand extends SongCommand {
    Song song;
    private SongService songService;

    public SongPlayCommand(SongService songService) {
        this.songService = songService;
    }


    @Override
    public void execute() {
        songService.playMusic(song.getId());
    }
}
