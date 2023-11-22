import command.PlaylistCommand.Snapshot;
import entity.Playlist;
import entity.Song;

public class Main {
    public static void main(String[] args) {
        Playlist playlist = new Playlist("Name");
        playlist.getSongs().add(new Song("name", "author", "genre", 132, 123 ));
        Snapshot snapshot = new Snapshot(playlist, playlist.getSongs());
        System.out.println(playlist.getSongs().get(0));
        System.out.println(snapshot.getSongs().get(0));
        snapshot.getSongs().remove(0);
        System.out.println();
    }
}
