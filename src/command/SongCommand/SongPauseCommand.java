package command.SongCommand;

import service.SongService;

public class SongPauseCommand extends SongCommand {

    private SongService songService;

    @Override
    public void execute() {
        songService.pauseMusic();
    }
}
