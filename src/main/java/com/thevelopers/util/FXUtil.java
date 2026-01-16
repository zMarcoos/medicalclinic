package com.thevelopers.util;

import javafx.scene.control.Alert;
import lombok.experimental.UtilityClass;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

@UtilityClass
public class FXUtil {

  public static ImageIcon getIcon(String path, int width, int height) {
    BufferedImage originalImage = ImageLoader.getImage(path);
    if (originalImage == null) return null;

    Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    return new ImageIcon(resizedImage);
  }

  public static void addImage(Container parent, String path, int x, int y, int width, int height) {
    ImageIcon icon = getIcon(path, width, height);

    JLabel label = new JLabel();
    if (icon == null) {
      label.setText("IMAGEM 404");
      label.setHorizontalAlignment(SwingConstants.CENTER);
    } else {
      label.setIcon(icon);
    }

    label.setBounds(x, y, width, height);
    parent.add(label);
  }

  public void showAlert(String title, String message, Alert.AlertType type) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
