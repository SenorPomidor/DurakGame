package mirea.sipi.durak.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.io.File;
import java.util.Map;

public class FileUtils {
    public static void walk(Map<String, Texture> textures) {
        walk("./", textures);
    }

    private static void walk(String path, Map<String, Texture> textures) {
        File root = new File(path);
        File[] list = root.listFiles();

        String fullRoot = root.getAbsolutePath();
        if (list == null) return;

        for (File f : list) {
            if (f.isDirectory()) {
                walk(f.getAbsolutePath(), textures);
            }
            else {
                textures.put(f.getName(), new Texture(Gdx.files.internal(f.getAbsolutePath())));
            }
        }
    }
}
