package thevelopers.devsoftware.utility;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public final class SwingUtility {

    /**
     * Método estático para criar um botão seguindo a API do Swing
     * @param container Container onde o botão será adicionado
     * @param name Nome do botão
     * @param x Posição X do botão
     * @param y Posição Y do botão
     * @param action Ação do botão
     */
    public static void createButton(Container container, String name, int x, int y, ActionListener action) {
        JButton button = new JButton(name);
        button.setOpaque(true);
        button.setBounds(x, y, 140, 30);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(new Color(22, 139, 242));
        button.setBackground(ClassUtility.BACKGROUND_WHITE_COLOR);

        if (action != null) button.addActionListener(action);

        button.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_ENTER) {
                    button.doClick();
                }
            }
        });

        container.add(button);
    }

    /**
     * Método estático para criar uma área de texto seguindo a API do Swing
     * @param container Container onde será adicionado
     * @param name Nome do texto
     * @param x Posição X do texto
     * @param y Posição Y do texto
     * @param width Largura do texto
     * @param height Altura do texto
     */
    public static JTextArea createTextArea(Container container, String name, int x, int y, int width, int height) {
        JTextArea textArea = new JTextArea(name);
        textArea.setBounds(x, y, width, height);
        textArea.setFont(new Font("Arial", Font.BOLD, 12));
        textArea.setForeground(ClassUtility.BACKGROUND_BLACK_COLOR);
        textArea.setBackground(ClassUtility.BACKGROUND_ALMOST_WHITE_COLOR);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createEmptyBorder());
        container.add(textArea);

        return textArea;
    }

    /**
     * Método estático para criar uma Label seguindo a API do Swing
     * @param container Container onde  será adicionado
     * @param name Nome da label
     * @param x Posição X da label
     * @param y Posição Y da label
     * @param width Largura da label
     * @param height Altura da label
     * @param fontSize Tamanho da fonte da label
     * @param adapter Ação da label
     */
    public static JLabel createLabel(Container container, String name, int x, int y, int width, int height, int fontSize, MouseAdapter adapter) {
        JLabel label = new JLabel(name);
        label.setBounds(x, y, width, height);
        label.setFont(new Font("Arial", Font.BOLD, fontSize));
        label.setForeground(ClassUtility.BACKGROUND_BLUE_COLOR);
        label.setBackground(ClassUtility.BACKGROUND_WHITE_COLOR);

        if (adapter != null) label.addMouseListener(adapter);
        container.add(label);

        return label;
    }

    /**
     * Método estático para criar um campo de texto seguindo a API do Swing
     * @param container Container onde o campo de texto será adicionado
     * @param name Nome do campo de texto
     * @param x Posição X do campo de texto
     * @param y Posição Y do campo de texto
     * @param width Largura do campo de texto
     * @param height Altura do campo de texto
     * @return O campo de texto criado
     */
    public static JTextField createTextField(Container container, String name, int x, int y, int width, int height) {
        JTextField textField = new JTextField(name);
        textField.setBounds(x, y, width, height);
        textField.setFont(new Font("Arial", Font.BOLD, 14));
        textField.setForeground(ClassUtility.BACKGROUND_BLACK_COLOR);
        textField.setBackground(ClassUtility.BACKGROUND_WHITE_COLOR);


        textField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_ENTER) {
                    textField.transferFocus();
                }
            }
        });

        container.add(textField);

        return textField;
    }

    /**
     * Método estático para criar um campo para senha de acordo com a API do Swing
     * @param container Container onde o campo de senha será adicionado
     * @param name Nome do campo de senha
     * @param x Posição X do campo de senha
     * @param y Posição Y do campo de senha
     * @param width Largura do campo de senha
     * @param height Altura do campo de senha
     * @return O campo de senha criado
     */
    public static JPasswordField createPasswordField(Container container, String name, int x, int y, int width, int height) {
        JPasswordField passwordField = new JPasswordField(name);
        passwordField.setBounds(x, y, width, height);
        passwordField.setFont(new Font("Arial", Font.BOLD, 14));
        passwordField.setForeground(ClassUtility.BACKGROUND_BLACK_COLOR);
        passwordField.setBackground(ClassUtility.BACKGROUND_WHITE_COLOR);

        passwordField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_ENTER) {
                    passwordField.transferFocus();
                }
            }
        });

        container.add(passwordField);

        return passwordField;
    }

    /**
     * Método estático para criar um checkbox de acordo com a API do Swing
     * @param container Container onde o checkbox será adicionado
     * @param name Nome do checkbox
     * @param x Posição X do checkbox
     * @param y Posição Y do checkbox
     * @param width Largura do checkbox
     * @param height Altura do checkbox
     * @return O checkbox criado
     */
    public static JCheckBox createCheckBox(Container container, String name, int x, int y, int width, int height) {
        JCheckBox checkBox = new JCheckBox(name);
        checkBox.setBounds(x, y, width, height);
        checkBox.setFont(new Font("Arial", Font.BOLD, 14));
        checkBox.setForeground(ClassUtility.BACKGROUND_BLUE_COLOR);

        container.add(checkBox);

        return checkBox;
    }

    /**
     * Método estático para criar uma Tabela de acordo com a API do Swing
     * @param container Container onde a tabela será adicionada
     * @param x Posição X da tabela
     * @param y Posição Y da tabela
     * @param width Largura da tabela
     * @param height Altura da tabela
     * @return A tabela criada
     */
    public static JTable createTable(Container container, String[] columnNames, int x, int y, int width, int height, boolean manySelection) {
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        //Estilo da tabela
        JTable table = new JTable(model);
        table.setBounds(x, y, width, height);
        table.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        table.setForeground(ClassUtility.BACKGROUND_BLACK_COLOR);
        table.setBackground(ClassUtility.BACKGROUND_WHITE_COLOR);
        table.setRowHeight(30);
        table.setShowGrid(false);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);
        table.getTableHeader().setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        table.getTableHeader().setForeground(ClassUtility.BACKGROUND_BLACK_COLOR);
        table.getTableHeader().setBackground(ClassUtility.BACKGROUND_WHITE_COLOR);
        table.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(true);

        if (!manySelection) table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        container.add(table);

        return table;
    }

    /**
     * Método estático para criar um painel de acordo com a API do Swing
     * @param container Container onde o painel será adicionado
     * @param x Posição X do painel
     * @param y Posição Y do painel
     * @param width Largura do painel
     * @param height Altura do painel
     * @param backgroundColor Cor de fundo do painel
     * @return O painel criado
     */
    public static JPanel createPanel(Container container, int x, int y, int width, int height, Color backgroundColor) {
        JPanel panel = new JPanel();
        panel.setBounds(x, y, width, height);
        panel.setBackground(backgroundColor);

        container.add(panel);

        return panel;
    }

    /* Método estático para criar um ComboBox de acordo com a API Swing
        * @param container Container onde o ComboBox será adicionado
        * @param names Nomes dos itens do ComboBox
        * @param x Posição X do ComboBox
        * @param y Posição Y do ComboBox
        * @param width Largura do ComboBox
        * @param height Altura do ComboBox
        * @param action Ação do ComboBox
        * @return O ComboBox criado
    * */
    public static JComboBox<String> createComboBox(Container container, String[] names, int x, int y, int width, int height, ActionListener action) {
        JComboBox<String> comboBox = new JComboBox<>();

        for (String name : names) {
            comboBox.addItem(name);
        }

        comboBox.setBounds(x, y, width, height);
        comboBox.setFont(new Font("Arial", Font.BOLD, 14));
        comboBox.setForeground(ClassUtility.BACKGROUND_BLUE_COLOR);
        comboBox.setBackground(ClassUtility.BACKGROUND_WHITE_COLOR);
        comboBox.addActionListener(action);

        container.add(comboBox);

        return comboBox;
    }

    /** Método estático para criar uma imagem de acordo com a API do Swing
     * @param container Container onde a imagem será adicionada
     * @param fileName Nome do arquivo da imagem
     * @param x Posição X da imagem
     * @param y Posição Y da imagem
     * @param newWidth Largura da imagem
     * @param newHeight Altura da imagem
     * @throws IOException Exceção de entrada e saída
     */
    public static void createImage(Container container, String fileName, int x, int y, int newWidth, int newHeight) {
        try {
            BufferedImage image = ImageIO.read(new File(Paths.get(fileName).toString()));

            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

            Graphics2D graphics = resizedImage.createGraphics();
            graphics.drawImage(image, 0, 0, newWidth, newHeight, null);
            graphics.dispose();

            JLabel label = new JLabel(new ImageIcon(resizedImage));
            label.setBounds(x, y, newWidth, newHeight);

            container.add(label);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
