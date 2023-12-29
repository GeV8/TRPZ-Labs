import Facade.IMediaPlayerFacade;
import Facade.MediaPlayerFacade;
import command.PlaylistCommand.PlaylistShuffleCommand;
import entity.Playlist;
import entity.Song;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.*;
import java.awt.*;

public class Main {
    private static final String url = "jdbc:mysql://localhost:3306/trpz_galagan ";
    private static final String user = "root";
    private static final String password = "159085";

    private static JList<Playlist> playlistJList;
    private static JList<Song> songJList;
    private static DefaultListModel<Song> songListModel;
    private static DefaultListModel<Playlist> playlistModel;
    private static PlaylistShuffleCommand playlistShuffleCommand;


    private static JDialog addSongDialog;
    private static JDialog deleteSongDialog;
    private static JTextField songNameTextField;
    private static JTextField songPathTextField;
    private static JTextField playlistNameField;
    private static JComboBox<Song> existingSongsComboBox;
    private static boolean sliderChangedByUser = true;
    private static JSlider seekSlider;
    private static JDialog addPlaylistDialog;
    private static IMediaPlayerFacade mediaPlayerFacade;


    public static void main(String[] args) {

        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            mediaPlayerFacade = new MediaPlayerFacade(connection);


            JFrame frame = new JFrame("Медіа програвач");
            frame.setSize(1000, 500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panel = new JPanel();

            LinkedList<Playlist> playlists = mediaPlayerFacade.getAllPlaylist();
            playlistModel = new DefaultListModel<>();
            for (Playlist playlist : playlists) {
                playlistModel.addElement(playlist);
            }
            playlistJList = new JList<>(playlistModel);
            playlistJList.setPreferredSize(new Dimension(300, 400));


            playlistJList.addListSelectionListener(e -> updateSongList(mediaPlayerFacade));


            songListModel = new DefaultListModel<>();
            songJList = new JList<>(songListModel);
            songJList.setPreferredSize(new Dimension(300, 400));

            JButton playButton = new JButton("Відтворити");
            JButton pauseButton = new JButton("Пауза");
            JButton resumeButton = new JButton("Продовжити");
            JButton restartButton = new JButton("Перезапустити");
            JButton repeatButton = new JButton("Повтор");
            JButton stopButton = new JButton("Вимкнути");
            JButton addSongButton = new JButton("Додати пісню");
            JButton deleteButton = new JButton("Видалити пісню");
            JButton deletePlaylist = new JButton("Видалити плейліст");
            JButton createPlaylist = new JButton("Створити плейліст");
            JButton deleteSong = new JButton("Видалити пісню з додатку");

            createPlaylist.addActionListener(e -> openAddPlaylistDialog(mediaPlayerFacade));
            deletePlaylist.addActionListener(e -> deleteSelectedPlaylist(mediaPlayerFacade));
            deleteButton.addActionListener(e -> deleteSelectedSong(mediaPlayerFacade));
            addSongButton.addActionListener(e -> openAddSongDialog(mediaPlayerFacade));
            deleteSong.addActionListener(e -> openDeleteSongDialog(mediaPlayerFacade));
            JButton playPlaylistButton = new JButton("Програти плейліст");
            JButton shuffleButton = new JButton("Перемішати");
            JButton undoButton = new JButton("Відмінити");

            playPlaylistButton.addActionListener(e -> {
                        if (mediaPlayerFacade.getChoosenPlaylist() != null) {
                            mediaPlayerFacade.playPlaylist(mediaPlayerFacade.getChoosenPlaylist());
                        }
                    }
            );

            shuffleButton.addActionListener(e -> {
                if (playlistJList.getSelectedValue() != null) {
                    playlistShuffleCommand = mediaPlayerFacade.generateShuffleCommand();
                    playlistShuffleCommand.createBackup();
                    playlistShuffleCommand.execute();
                    updateShuffle(mediaPlayerFacade);
                }
            });

            undoButton.addActionListener(e -> {
                if (playlistShuffleCommand != null) {
                    playlistShuffleCommand.undo();
                    updateSongList(mediaPlayerFacade);
                }
            });

            playButton.addActionListener(e -> {
                Song selectedSong = songJList.getSelectedValue();
                if (selectedSong != null) {
                    mediaPlayerFacade.playSong(selectedSong.getId());
                }
            });

            seekSlider = new JSlider(JSlider.HORIZONTAL);
            seekSlider.setMajorTickSpacing(10);
            seekSlider.setMinorTickSpacing(1);
            seekSlider.setPaintTicks(true);
            seekSlider.setPaintLabels(true);
            seekSlider.addChangeListener(e -> {
                if (sliderChangedByUser) {
                    adjustPosition(seekSlider.getValue(), mediaPlayerFacade);
                }
            });

            JSlider volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
            volumeSlider.setMajorTickSpacing(20);
            volumeSlider.setMinorTickSpacing(5);
            volumeSlider.setPaintTicks(true);
            volumeSlider.setPaintLabels(true);


            volumeSlider.addChangeListener(e -> adjustVolume(volumeSlider.getValue(), mediaPlayerFacade));
            // Оновлення кожну секунду
            Timer timer = new Timer(1000, e -> updateSliderPosition(mediaPlayerFacade));
            timer.start();


            pauseButton.addActionListener(e -> {
                if (mediaPlayerFacade.isMusicIsPlaying()) {
                    mediaPlayerFacade.pauseSong();
                }
            });

            resumeButton.addActionListener(e -> {
                if (!mediaPlayerFacade.isMusicIsPlaying()) {
                    mediaPlayerFacade.resumeSong();
                }

            });

            restartButton.addActionListener(e -> {
                if (mediaPlayerFacade.isMusicIsPlaying()) {
                    mediaPlayerFacade.restartSong();
                }

            });

            repeatButton.addActionListener(e -> {
                if (mediaPlayerFacade.isMusicIsPlaying()) {
                    mediaPlayerFacade.loopSong();
                }

            });

            stopButton.addActionListener(e -> {
                mediaPlayerFacade.stopSong();
                try {
                    connection.close();
                    System.exit(0);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            });


            JScrollPane playlistScrollPane = new JScrollPane(playlistJList);
            JScrollPane songScrollPane = new JScrollPane(songJList);
            JPanel controlPanel = new JPanel();
            controlPanel.add(playPlaylistButton);
            controlPanel.add(shuffleButton);
            controlPanel.add(undoButton);
            controlPanel.add(playButton);
            controlPanel.add(pauseButton);
            controlPanel.add(resumeButton);
            controlPanel.add(restartButton);
            controlPanel.add(repeatButton);
            controlPanel.add(stopButton);

            panel.add(createPlaylist, BorderLayout.NORTH);
            panel.add(deleteSong, BorderLayout.NORTH);
            panel.add(deletePlaylist, BorderLayout.NORTH);
            panel.add(addSongButton, BorderLayout.NORTH);
            panel.add(deleteButton, BorderLayout.NORTH);
            panel.add(playlistScrollPane, BorderLayout.WEST);
            panel.add(songScrollPane, BorderLayout.CENTER);
            panel.add(controlPanel, BorderLayout.SOUTH);
            panel.add(seekSlider, BorderLayout.NORTH);
            panel.add(volumeSlider, BorderLayout.NORTH);


            frame.getContentPane().add(panel);

            frame.setVisible(true);


        } catch (Throwable e) {
            e.printStackTrace();
        }

    }


    private static void updateSliderPosition(IMediaPlayerFacade mediaPlayerFacade) {
        if (mediaPlayerFacade.isMusicIsPlaying()) {
            long length = mediaPlayerFacade.getSeconds();
            long position = mediaPlayerFacade.getPosition();
            int sliderPosition = (int) ((position * 100) / length);
            sliderChangedByUser = false;
            seekSlider.setValue(sliderPosition);
            sliderChangedByUser = true;
        }
    }

    private static void adjustPosition(int value, IMediaPlayerFacade songPlaylistFacade) {
        if (songPlaylistFacade.isMusicIsPlaying()) {
            songPlaylistFacade.setSeconds(value);
        }
    }

    private static void adjustVolume(int value, IMediaPlayerFacade mediaPlayerFacade) {
        if (mediaPlayerFacade.isMusicIsPlaying()) {
            mediaPlayerFacade.changeVolume(value);
        }
    }

    private static void deleteSelectedSong(IMediaPlayerFacade songPlaylistFacade) {
        Song selectedSong = songJList.getSelectedValue();
        Playlist playlist = playlistJList.getSelectedValue();

        if (selectedSong != null && playlist != null) {
            // Видалити пісню з плейлиста
            songPlaylistFacade.removeSongFromPlaylist(selectedSong.getId(), playlist.getId());
            updateSongList(songPlaylistFacade);
        }
    }

    private static void deleteSelectedPlaylist(IMediaPlayerFacade songPlaylistFacade) {

        Playlist playlistToDelete = songPlaylistFacade.getChoosenPlaylist();

        if (playlistToDelete != null) {
            for (Song song : playlistToDelete.getSongs()) {
                songPlaylistFacade.removeSongFromPlaylist(song.getId(), playlistToDelete.getId());
            }
            songPlaylistFacade.deletePlaylist();
            updatePlaylist(songPlaylistFacade);
        }


    }

    private static void updateSongList(IMediaPlayerFacade songPlaylistFacade) {
        songListModel.clear();
        Playlist selectedPlaylist = playlistJList.getSelectedValue();
        if (selectedPlaylist != null) {
            songPlaylistFacade.openPlaylist(selectedPlaylist.getId());
            for (Song song : songPlaylistFacade.getChoosenPlaylist().getSongs()) {
                songListModel.addElement(song);
            }
        }
    }

    private static void updateShuffle(IMediaPlayerFacade songPlaylistFacade) {
        songListModel.clear();
        for (Song song : songPlaylistFacade.getChoosenPlaylist().getSongs()) {
            songListModel.addElement(song);
        }
    }

    private static void openAddSongDialog(IMediaPlayerFacade songPlaylistFacade) {
        if (addSongDialog == null) {
            // Якщо діалогове вікно ще не створене, створити його
            createAddSongDialog(songPlaylistFacade);
        }

        // Очистити текстові поля для імені та шляху пісні при кожному відкритті вікна
        songNameTextField.setText("");
        songPathTextField.setText("");
        updateExistingSongsComboBox(songPlaylistFacade);

        addSongDialog.setVisible(true);
    }

    private static void createAddSongDialog(IMediaPlayerFacade songPlaylistFacade) {
        addSongDialog = new JDialog();
        addSongDialog.setTitle("Додати пісню");
        addSongDialog.setSize(600, 300);
        addSongDialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

        JPanel dialogPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel nameLabel = new JLabel("Назва пісні:");
        nameLabel.setPreferredSize(new Dimension(100, 25));
        gbc.gridx = 0;
        gbc.gridy = 0;
        dialogPanel.add(nameLabel, gbc);

        songNameTextField = new JTextField();
        songNameTextField.setPreferredSize(new Dimension(200, 25));
        gbc.gridx = 1;
        gbc.gridy = 0;
        dialogPanel.add(songNameTextField, gbc);

        JLabel pathLabel = new JLabel("Шлях пісні:");
        pathLabel.setPreferredSize(new Dimension(100, 25));
        gbc.gridx = 0;
        gbc.gridy = 1;
        dialogPanel.add(pathLabel, gbc);

        songPathTextField = new JTextField();
        songPathTextField.setPreferredSize(new Dimension(200, 25));
        gbc.gridx = 1;
        gbc.gridy = 1;
        dialogPanel.add(songPathTextField, gbc);

        JButton browseButton = new JButton("Обрати...");
        browseButton.addActionListener(e -> browsePath());
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        dialogPanel.add(browseButton, gbc);

        JButton createButton = new JButton("Створити нову пісню");
        createButton.addActionListener(e -> addSong(songPlaylistFacade));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        dialogPanel.add(createButton, gbc);

        JLabel orLabel = new JLabel("або");
        gbc.gridx = 2;
        gbc.gridy = 2;
        dialogPanel.add(orLabel, gbc);

        JLabel existingSongsLabel = new JLabel("Вибрати існуючу пісню:");
        existingSongsLabel.setPreferredSize(new Dimension(150, 25));
        gbc.gridx = 0;
        gbc.gridy = 3;
        dialogPanel.add(existingSongsLabel, gbc);

        existingSongsComboBox = new JComboBox<>();
        existingSongsComboBox.setPreferredSize(new Dimension(200, 25));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        dialogPanel.add(existingSongsComboBox, gbc);

        JButton addButton = new JButton("Додати вибрану пісню");
        addButton.addActionListener(e -> addExistingSong(songPlaylistFacade));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        dialogPanel.add(addButton, gbc);

        addSongDialog.getContentPane().add(dialogPanel);


    }

    private static void openAddPlaylistDialog(IMediaPlayerFacade songPlaylistFacade) {
        if (addPlaylistDialog == null) {
            createPlaylistDialog(songPlaylistFacade);
        }
        playlistNameField.setText("");
        addPlaylistDialog.setVisible(true);
    }

    private static void createPlaylistDialog(IMediaPlayerFacade songPlaylistFacade) {
        addPlaylistDialog = new JDialog();
        addPlaylistDialog.setTitle("Додати плейліст");
        addPlaylistDialog.setSize(600, 300);
        addPlaylistDialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

        JPanel dialogPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel nameLabel = new JLabel("Назва плейліста:");
        nameLabel.setPreferredSize(new Dimension(100, 25));
        gbc.gridx = 0;
        gbc.gridy = 0;
        dialogPanel.add(nameLabel, gbc);

        playlistNameField = new JTextField();
        playlistNameField.setPreferredSize(new Dimension(200, 25));
        gbc.gridx = 1;
        gbc.gridy = 0;
        dialogPanel.add(playlistNameField, gbc);

        JButton addButton = new JButton("Додати плейліст");
        addButton.addActionListener(e -> addPlaylist(songPlaylistFacade));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        dialogPanel.add(addButton, gbc);


        addPlaylistDialog.getContentPane().add(dialogPanel);

    }

    private static void addPlaylist(IMediaPlayerFacade songPlaylistFacade) {
        if (!playlistNameField.getText().isEmpty()) {
            songPlaylistFacade.createPlaylist(playlistNameField.getText());
            addPlaylistDialog.setVisible(false);
            updatePlaylist(songPlaylistFacade);
        }
    }

    private static void addExistingSong(IMediaPlayerFacade songPlaylistFacade) {
        Song selectedSong = (Song) existingSongsComboBox.getSelectedItem();
        if (selectedSong != null) {
            songPlaylistFacade.addSongToPlaylist(selectedSong.getId(), songPlaylistFacade.getChoosenPlaylist().getId());
        }
        // Оновити список пісень
        updateSongList(songPlaylistFacade);
        // Сховати діалогове вікно
        addSongDialog.setVisible(false);
    }

    private static void browsePath() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            String selectedPath = fileChooser.getSelectedFile().getAbsolutePath();
            songPathTextField.setText(selectedPath);
        }
    }

    private static void addSong(IMediaPlayerFacade songPlaylistFacade) {
        // Отримати ім'я та шлях пісні з текстових полей
        String songName = songNameTextField.getText();
        String songPath = songPathTextField.getText();


        if (!songName.isEmpty() && !songPath.isEmpty()) {
            Song song = songPlaylistFacade.addSong(songName, songPath, "genre");
            if (songPlaylistFacade.getChoosenPlaylist() != null) {
                songPlaylistFacade.addSongToPlaylist(song.getId(), songPlaylistFacade.getChoosenPlaylist().getId());
            }
        }
        addSongDialog.setVisible(false);
    }

    private static void openDeleteSongDialog(IMediaPlayerFacade mediaPlayerFacade) {
        if (deleteSongDialog == null) {
            createDeleteSongDialog(mediaPlayerFacade);
        }
        updateExistingSongsComboBox(mediaPlayerFacade);
        deleteSongDialog.setVisible(true);

    }

    private static void createDeleteSongDialog(IMediaPlayerFacade mediaPlayerFacade) {
        deleteSongDialog = new JDialog();
        deleteSongDialog.setTitle("Видалити пісню");
        deleteSongDialog.setSize(600, 300);
        deleteSongDialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

        JPanel dialogPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel deleteSongsLabel = new JLabel("Обрати пісню:");
        deleteSongsLabel.setPreferredSize(new Dimension(150, 25));
        gbc.gridx = 0;
        gbc.gridy = 3;
        dialogPanel.add(deleteSongsLabel, gbc);

        existingSongsComboBox = new JComboBox<>();
        existingSongsComboBox.setPreferredSize(new Dimension(200, 25));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        dialogPanel.add(existingSongsComboBox, gbc);

        JButton deleteButton = new JButton("Видалити пісню");
        deleteButton.addActionListener(e -> deleteSong(mediaPlayerFacade));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        dialogPanel.add(deleteButton, gbc);

        deleteSongDialog.getContentPane().add(dialogPanel);
    }

    private static void deleteSong(IMediaPlayerFacade mediaPlayerFacade) {
        Song songToDelete = (Song) existingSongsComboBox.getSelectedItem();
        if (songToDelete != null) {
            mediaPlayerFacade.deleteSong(songToDelete);
            updateSongList(mediaPlayerFacade);
            deleteSongDialog.setVisible(false);
        }

    }

    private static void updateExistingSongsComboBox(IMediaPlayerFacade songPlaylistFacade) {
        // Оновити випадаючий список існуючих пісень
        LinkedList<Song> songs = songPlaylistFacade.getAllSongs();
        DefaultComboBoxModel<Song> songComboBoxModel = new DefaultComboBoxModel<>();

        for (Song song : songs) {
            songComboBoxModel.addElement(song);
        }
        existingSongsComboBox.setModel(songComboBoxModel);
    }

    private static void updatePlaylist(IMediaPlayerFacade songPlaylistFacade) {
        playlistModel.clear();
        for (Playlist playlist : songPlaylistFacade.getAllPlaylist()) {
            playlistModel.addElement(playlist);
        }

    }
}

