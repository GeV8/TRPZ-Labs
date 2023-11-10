package command.SongCommand;

import service.SongService;

public class SongDeleteCommand extends SongCommand{
    private SongService songService;
    @Override
    public void execute(long id) {
        songService.deleteSong(id);
    }
}
