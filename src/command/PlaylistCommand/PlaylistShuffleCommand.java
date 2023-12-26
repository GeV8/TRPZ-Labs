package command.PlaylistCommand;

import service.PlaylistService;

public class PlaylistShuffleCommand extends PlaylistCommand {
    private Snapshot backup;

    private PlaylistService playlistService;

    public PlaylistShuffleCommand(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Override
    public void execute() {
        playlistService.shufflePlaylist();
    }

    public void createBackup(){
        backup = playlistService.getOpenedPlaylist().createSnapshot();
    }

    public void undo(){
        backup.restore();
    }
}
