package NPad;

import java.awt.event.*;
import java.io.BufferedWriter;
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
    private JMenuItem authorsMI;
    private JMenuItem versionMI;

    // Constructor
    public GUI() {
        createFrame();
        createFileMenu();
        createEditMenu();
        createAboutMenu();
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

        // JMenuItem
        findMI = new JMenuItem("Find");
        redoMI = new JMenuItem("Redo");
        undoMI = new JMenuItem("Undo");

        findMI.addActionListener(this);
        redoMI.addActionListener(this);
        undoMI.addActionListener(this);

        editMenu.add(findMI);
        editMenu.add(redoMI);
        editMenu.add(undoMI);

        menuBar.add(editMenu);
    }

    void createAboutMenu() {
        // JMenu
        aboutMenu = new JMenu("About");

        // JMenuItem
        authorsMI = new JMenuItem("Authors");
        versionMI = new JMenuItem("Version");

        authorsMI.addActionListener(this);
        versionMI.addActionListener(this);

        aboutMenu.add(authorsMI);
        aboutMenu.add(versionMI);

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
            saveFile();
        } else if (e.getSource() == openMI) {
            FileDialog fileDialog = new FileDialog(window, "Open File", FileDialog.LOAD);// fFileDialog.Load sets it to
                                                                                         // an open dialogue
            fileDialog.setVisible(true);
            String filePath = fileDialog.getDirectory() + fileDialog.getFile();
            if (filePath != null) {
                openFile(filePath);
            }
        } else if (e.getSource() == exitMI) {
            System.exit(0);
        } else if (e.getSource() == versionMI) {
            showAboutDialog();
        }
    }

    private void saveFile() {
        FileDialog fileDialog = new FileDialog(window, "Save File", FileDialog.SAVE);// the mode FileDialog.SAVE sets it
                                                                                     // to a save dialog.
        fileDialog.setVisible(true);
        try {
            /*
             * fileDialog.getDirectory() gets the directory chosen by the user.
             * fileDialog.getFile() gets the name of the file chosen by the user.
             * These are concatenated to form the full file path.
             */
            String filePath = fileDialog.getDirectory() + fileDialog.getFile();
            if (filePath != null) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
                String text = textArea.getText();
                bw.write(text);
                bw.close();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(window, "An error occurred while saving the file: " + e.getMessage(),
                    "Save Error", JOptionPane.ERROR_MESSAGE);// handles errors like inaccurate file paths, no enough
                                                             // space to save the file etc
        }
    }

    private void openFile(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            textArea.read(reader, null);
        } catch (IOException e) {

        }
    }

    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this, "NotePad v1.0\nA simple notepad application\nCreated by us!", "About",
                JOptionPane.INFORMATION_MESSAGE);
    }

}