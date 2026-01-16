package com.thevelopers.model.appointment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public record Exam(File sourceFile) {

  public void exportTo(File destinationFile) throws IOException {
    if (sourceFile == null || !sourceFile.exists()) {
      throw new IOException("O arquivo original não existe no sistema.");
    }

    Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
  }

  public String getOriginalFileName() {
    return sourceFile.getName();
  }
}