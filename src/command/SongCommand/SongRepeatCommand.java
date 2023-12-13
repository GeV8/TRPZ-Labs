package command.SongCommand;

import service.SongService;

public class SongRepeatCommand extends SongCommand{

    private SongService songService;
    @Override
    public void execute() {
        songService.repeatSong();
    }
}
