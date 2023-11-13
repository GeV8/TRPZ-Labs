package command.SongCommand;

import service.SongService;

public class SongRepeatCommand extends SongCommand{
    private SongService songService;
    @Override
    public void execute(long id) {
        songService.repeatSong(id);
    }
}
