
import java.awt.event.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.*;
import javax.swing.*;

public class GUI extends JFrame implements ActionListener {
    private JFrame window;

    private JTextArea textArea;
    private JMenuBar menuBar;

    private JMenu fileMenu;
    private JMenuItem newMI;
    private JMenuItem openMI;
    private JMenuItem exitMI;
    private JMenuItem saveMI;

    private JMenu editMenu;
    private JMenuItem findMI;
    private JMenuItem redoMI;
    private JMenuItem undoMI;

    private JMenu aboutMenu;

    private JButton aboutButton;

    // Constructor
    public GUI() {
        createFrame();
        createFileMenu();
        createEditMenu();
        createTextArea();
    }

    void createFrame() {
        window = new JFrame();
        ImageIcon appIcon = new ImageIcon("NotePad-icon.png");
        window.setIconImage(appIcon.getImage());

        window.setTitle("NotePad");
        window.setSize(600, 600);
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    void createFileMenu() {
        // JMenuBar
        menuBar = new JMenuBar();

        // JMenu
        fileMenu = new JMenu("File");

        // JMenuItems
        newMI = new JMenuItem("New");
        openMI = new JMenuItem("Open");
        saveMI = new JMenuItem("Save");
        exitMI = new JMenuItem("Exit");

        newMI.addActionListener(this);
        openMI.addActionListener(this);
        saveMI.addActionListener(this);
        exitMI.addActionListener(this);

        fileMenu.add(newMI);
        fileMenu.add(openMI);
        fileMenu.add(saveMI);
        fileMenu.add(exitMI);

        menuBar.add(fileMenu);

        window.setJMenuBar(menuBar);
    }

    void createEditMenu() {
        // JMenu
        editMenu = new JMenu("Edit");
        aboutMenu = new JMenu("About");

        // JMenuItem
        findMI = new JMenuItem("Find");
        redoMI = new JMenuItem("Redo");
        undoMI = new JMenuItem("Undo");

        aboutMenu.addActionListener(this);

        findMI.addActionListener(this);
        redoMI.addActionListener(this);
        undoMI.addActionListener(this);

        editMenu.add(findMI);
        editMenu.add(redoMI);
        editMenu.add(undoMI);

        menuBar.add(editMenu);
        menuBar.add(aboutMenu);
    }

    void createTextArea() {
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setFont(new Font("Calibri", Font.PLAIN, 25));
        window.add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveMI) {
            FileDialog fileDialog = new FileDialog(window, "Save File", FileDialog.SAVE);// the mode FileDialog.SAVE  sets it to a save dialog.
            fileDialog.setVisible(true);
            String filePath = fileDialog.getDirectory() + fileDialog.getFile();
            /*fileDialog.getDirectory() gets the directory chosen by the user.
fileDialog.getFile() gets the name of the file chosen by the user.
These are concatenated to form the full file path. */
            if (filePath != null) {// if the dialogue is not canceled
                saveFile(filePath);
            }
        } else if (e.getSource() == openMI) {
            FileDialog fileDialog = new FileDialog(window, "Open File", FileDialog.LOAD);//fFileDialog.Load sets it to an open dialogue
            fileDialog.setVisible(true);
            String filePath = fileDialog.getDirectory() + fileDialog.getFile();
            if (filePath != null) {
                openFile(filePath);
            }
        } else if (e.getSource() == exitMI) {
            System.exit(0);
        }
    }

    private void saveFile(String filePath)  {
        try (FileWriter writer = new FileWriter(filePath)) {
            textArea.write(writer);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(window, "An error occurred while saving the file: " + e.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);// handles errors like inaccurate file paths, no enough space to save the file etc
        }
    }

    private void openFile(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            textArea.read(reader, null);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(window, "An error occurred while opening the file: " + e.getMessage(), "Open Error", JOptionPane.ERROR_MESSAGE);// to handle errors when user tries to open a file that does not exist
        }
        }
    }
