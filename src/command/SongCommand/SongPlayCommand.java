package command.SongCommand;

import service.SongService;

public class SongPlayCommand extends SongCommand {
    private SongService songService;

    public SongPlayCommand(SongService songService) {
        this.songService = songService;
    }


    @Override
    public void execute(long id) {
        songService.playMusic(id);
    }
}
