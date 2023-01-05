import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress game1 = new GameProgress(12, 100, 4, 102.8);
        GameProgress game2 = new GameProgress(25, 400, 7, 302.64);
        GameProgress game3 = new GameProgress(90, 700, 10, 848.59);

        String[] filePaths = {
                "/Users/Games/savegames/game1.dat",
                "/Users/Games/savegames/game2.dat",
                "/Users/Games/savegames/game3.dat"};

        GameProgress[] games = {game1, game2, game3};
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(filePaths));

        for (int i = 0; i < filePaths.length; i++) {
            saveGame(filePaths[i], games[i]);
        }
        zipFiles("/Users/Games/savegames/zip.zip", arrayList);

        for (String filePath : filePaths) {
            File file = new File(filePath);
            if (file.delete()) {
                System.out.println("Файл " + filePath + " удален");
            }
        }
    }

    private static void saveGame(String path, GameProgress game) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(game);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void zipFiles(String path, List<String> arrayList) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))) {
            for (String arr : arrayList) {
                try (FileInputStream fis = new FileInputStream(arr)) {
                    ZipEntry entry = new ZipEntry(arr);
                    zout.putNextEntry(entry);
                    while (fis.available() > 0) {
                        zout.write(fis.read());
                    }
                    zout.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}