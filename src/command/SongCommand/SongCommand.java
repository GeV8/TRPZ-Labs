package command.SongCommand;

import service.SongService;

import java.sql.SQLException;

public abstract class SongCommand {
    public abstract void execute() throws SQLException;
}
