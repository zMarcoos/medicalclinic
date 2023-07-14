package thevelopers.devsoftware.utility;

import thevelopers.devsoftware.MedicalClinic;

import java.awt.*;

public class ClassUtility {
    //Atributos constantes para o sistema
    public static final String MAIN_PATH = MedicalClinic.class.getPackage().getName().replace(".", "/");

    public static final Color BACKGROUND_BLUE_COLOR = new Color(23, 139, 241);
    public static final Color BACKGROUND_ALMOST_WHITE_COLOR = new Color(242, 237, 228);
    public static final Color BACKGROUND_WHITE_COLOR = new Color(255, 255, 255);
    public static final Color BACKGROUND_BLACK_COLOR = new Color(0, 0, 0);
}