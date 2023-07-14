package thevelopers.devsoftware.view;

import thevelopers.devsoftware.observer.Observer;
import thevelopers.devsoftware.utility.ClassUtility;

import javax.swing.*;
import java.util.Objects;

public abstract class View extends JFrame implements Observer {

    private JFrame previousFrame; // Frame anterior ao atual

    /**
     * Construtor da classe View
     */
    public View() {
        setTitle("Clínica Médica");
        setIconImage(new ImageIcon(
                Objects.requireNonNull(getClass().getResource("/" + ClassUtility.MAIN_PATH + "/assets/doctor_icon.png"))
        ).getImage());
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(ClassUtility.BACKGROUND_ALMOST_WHITE_COLOR);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Método abstrato para construir a view
     */
    public abstract void build();

    /**
     * Método para pegar o frame anterior ao atual
     *
     * @return Frame anterior ao atual (JFrame)
     */
    public JFrame getPreviousFrame() {
        return previousFrame;
    }

    /**
     * Método para setar o frame anterior ao atual
     * @param previousFrame Frame anterior ao atual (JFrame)
     * @return View atual (View)
     */
    public View setPreviousFrame(JFrame previousFrame) {
        this.previousFrame = previousFrame;
        return this;
    }
}