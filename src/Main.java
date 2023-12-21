import Facade.MediaPlayerFacade;
import command.PlaylistCommand.PlaylistCommand;
import command.PlaylistCommand.PlaylistShuffleCommand;
import entity.Playlist;
import entity.Song;
import repository.MySQL.EqualizerRepository;
import repository.MySQL.PlaylistRepository;
import repository.MySQL.SongRepository;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

public class Main {
    private static final String url = "jdbc:mysql://localhost:3306/trpz_galagan ";
    private static final String user = "root";
    private static final String password = "159085";

    static int port = 5003;

    public static void main(String[] args) {

        try(Connection connection = DriverManager.getConnection(url,user,password)) {
            ServerSocket serverSocket = new ServerSocket(12345);
            Socket clientSocket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());

            MediaPlayerFacade mediaPlayerFacade = new MediaPlayerFacade(connection);
            System.out.println("Сервер запущено. Очікування клієнта...");
            System.out.println("Клієнт підключений.");
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                System.out.println("Клієнт відправив: " + inputLine);
                // Обробка введених значень клієнта
                switch (inputLine) {
                    case "1":
                        String nestedInput1;
                        do{
                            LinkedList<Playlist>playlistsToWrite = mediaPlayerFacade.getAllPlaylist();
                            objectOutputStream.writeObject(playlistsToWrite);
                            nestedInput1 = in.readLine();
                            System.out.println(nestedInput1);
                            // Вкладений switch-case
                            switch (nestedInput1) {
                                case "exit":
                                    break;
                                default:
                                    System.out.println(nestedInput1);
                                    Playlist playlist = mediaPlayerFacade.getChoosenPlaylist(Long.parseLong(nestedInput1));
                                    objectOutputStream.writeObject(playlist);
                                    String nestedInput2;
                                    do{
                                        nestedInput2=in.readLine();
                                        switch (nestedInput2){
                                            case "shuffle":
                                                break;
                                            case "exit":
                                                break;

                                            default:
                                                mediaPlayerFacade.playSong(Long.parseLong(nestedInput2));
                                                String nestedInput3;
                                                do{
                                                    nestedInput3=in.readLine();
                                                    switch (nestedInput3){
                                                        case "pause":
                                                            mediaPlayerFacade.pauseSong();
                                                        case "exit":
                                                            break;
                                                        default:

                                                    }


                                                }while (nestedInput3.equals("exit"));
                                        }
                                    }while (nestedInput2.equals("exit"));

                            }
                            break;
                        }while (!nestedInput1.equals("exit"));

                    case "2":
                        out.println("Введено 1. Введіть 1 або 2:");
                        String nestedInput2 = in.readLine();

                        // Вкладений switch-case
                        switch (nestedInput2) {
                            case "1":
                                out.println("Введено 1 вкладений. Обробка Case1");
                                break;
                            case "2":
                                out.println("Введено 2 вкладений. Обробка Case2");
                                break;
                            default:
                                out.println("Введено невірне значення для вкладеного випадку");
                        }
                        break;
                    case "3":
                        out.println("Введено 1. Введіть 1 або 2:");
                        String nestedInput3 = in.readLine();

                        // Вкладений switch-case
                        switch (nestedInput3) {
                            case "1":
                                out.println("Введено 1 вкладений. Обробка Case1");
                                break;
                            case "2":
                                out.println("Введено 2 вкладений. Обробка Case2");
                                break;
                            default:
                                out.println("Введено невірне значення для вкладеного випадку");
                        }
                        break;
                    default:
                        out.println("Невідоме значення");
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}

