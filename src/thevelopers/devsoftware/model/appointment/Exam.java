package thevelopers.devsoftware.model.appointment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Exam {

    // Atributos da classe Exame (Arquivo)
    private final File file;

    /**
     * Construtor da classe Exame
     * @param file Arquivo do exame
     */
    public Exam(File file) {
        this.file = file;
    }

    /**
     * Método para pegar o arquivo do exame
     * @return Arquivo do exame
     */
    public File getFile() {
        return file;
    }

    /**
     * Método para baixar o arquivo do exame
     * @param newFile Arquivo do exame
     */
    public void download(File newFile) {
        try {
            Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exception) {
            System.out.println("Erro ao baixar o arquivo: " + exception.getMessage());
        }
    }
}