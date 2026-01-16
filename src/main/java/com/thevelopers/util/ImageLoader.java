package com.thevelopers.util;

import lombok.experimental.UtilityClass;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@UtilityClass
public class ImageLoader {

  private static final Map<String, BufferedImage> cache = new ConcurrentHashMap<>();

  public static BufferedImage getImage(String path) {
    String finalPath = path.startsWith("/") ? path : "/" + path;

    return cache.computeIfAbsent(finalPath, key -> {
      try {
        URL url = ImageLoader.class.getResource(finalPath);
        if (url == null) {
          System.err.println("❌ Imagem não encontrada: " + key);
          return null;
        }

        return ImageIO.read(url);
      } catch (IOException exception) {
        exception.printStackTrace();
        return null;
      }
    });
  }
}
