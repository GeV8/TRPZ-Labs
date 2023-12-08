import Facade.MediaPlayerFacade;
import entity.Playlist;
import entity.Song;
import repository.MySQL.EqualizerRepository;
import repository.MySQL.PlaylistRepository;
import repository.MySQL.SongRepository;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    private static final String url = "jdbc:mysql://localhost:3306/trpz_galagan ";
    private static final String user = "root";
    private static final String password = "159085";
    public static void main(String[] args) {
        try(Connection connection = DriverManager.getConnection(url, user, password);) {
            MediaPlayerFacade mediaPlayer =  new MediaPlayerFacade(connection);
            mediaPlayer.addSong("new SOng", "C:\\Users\\galag\\IdeaProjects\\TRPZ-Labs\\src\\songs\\song1", "genre1");
        }catch (Throwable e){
            e.printStackTrace();
        }
    }
}

