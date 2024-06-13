package Parent.MyNotePad;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.undo.UndoManager;

public class GUI extends JFrame implements ActionListener, KeyListener {
    private JFrame window;

    private JPanel southPanel;

    private JLabel charCountLabel, wordCountLabel;

    private JTextArea textArea;
    private JMenuBar menuBar;

    private JMenu fileMenu;
    private JMenuItem newMI, saveMI, openMI, exitMI;

    private JMenu editMenu;
    private JMenuItem findMI, redoMI, undoMI, replaceMI;

    private JMenu formatMenu, fontstyle, fontsize, fontFamily;
    private JMenuItem fontSize1, fontSize2, fontSize3, fontSize4, fontSize5, fontSize6;

    private JMenuItem fontstyle1, fontstyle2;

    private JMenuItem calibri, georgia, consolas, timesNew, arial;

    private JMenu aboutMenu;
    private JMenuItem authorsMI, versionMI;

    private UndoManager undoManager;

    private JButton themeButton;
    private boolean isDarkMode = false;

    // Reference of the classes present in the project
    File file;
    Edit edit;
    Format format;
    About about;
    Counter counter;
    Theme theme;

    // Constructor
    public GUI() {
        createFrame();
        createFileMenu();
        createEditMenu();
        createFormatMenu();
        createAboutMenu();
        createTextArea();
        createThemeButton();
        createSouthPanel();
        undoManager = new UndoManager();
        textArea.getDocument().addUndoableEditListener(undoManager);

        file = new File(this);
        edit = new Edit(this);
        format = new Format(this);
        about = new About(this);
        counter = new Counter(this);
        theme = new Theme(this);

        counter.addCharCounter();
        counter.addWordCounter();
    }

    void createFrame() {
        window = new JFrame();
        ImageIcon appIcon = new ImageIcon("Parent\\Icons\\NotePad-icon.png");
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
        newMI = new JMenuItem("New (ctrl+N)");
        openMI = new JMenuItem("Open (ctrl+O)");
        saveMI = new JMenuItem("Save (ctrl+S)");
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

    void createSouthPanel() {
        southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 0));
        window.add(southPanel, BorderLayout.SOUTH);
    }

    void createEditMenu() {
        // JMenu
        editMenu = new JMenu("Edit");

        // JMenuItem
        findMI = new JMenuItem("Find (ctrl+F)");
        redoMI = new JMenuItem("Redo (ctrl+Y)");
        undoMI = new JMenuItem("Undo (ctrl+Z)");
        replaceMI = new JMenuItem("Replace");

        findMI.addActionListener(this);
        redoMI.addActionListener(this);
        undoMI.addActionListener(this);
        replaceMI.addActionListener(this);

        editMenu.add(findMI);
        editMenu.add(replaceMI);
        editMenu.add(redoMI);
        editMenu.add(undoMI);

        menuBar.add(editMenu);
    }

    void createFormatMenu() {
        formatMenu = new JMenu("Format");
        fontsize = new JMenu("Font size");
        fontstyle = new JMenu("Font style");
        fontSize1 = new JMenuItem("10");
        fontSize2 = new JMenuItem("11");
        fontSize3 = new JMenuItem("12");
        fontSize4 = new JMenuItem("14");
        fontSize5 = new JMenuItem("16");
        fontSize6 = new JMenuItem("20");
        fontstyle1 = new JMenuItem("Bold");
        fontstyle2 = new JMenuItem("Italic");
        fontFamily = new JMenu("Font Family");

        arial = new JMenuItem("Arial");
        calibri = new JMenuItem("Calibri");
        consolas = new JMenuItem("Consolas");
        georgia = new JMenuItem("Georgia");
        timesNew = new JMenuItem("Times New Roman");

        fontsize.add(fontSize1);
        fontsize.add(fontSize2);
        fontsize.add(fontSize3);
        fontsize.add(fontSize4);
        fontsize.add(fontSize5);
        fontsize.add(fontSize6);

        fontstyle.add(fontstyle1);
        fontstyle.add(fontstyle2);

        fontFamily.add(arial);
        fontFamily.add(calibri);
        fontFamily.add(consolas);
        fontFamily.add(georgia);
        fontFamily.add(timesNew);

        formatMenu.add(fontsize);
        formatMenu.add(fontstyle);
        formatMenu.add(fontFamily);
        menuBar.add(formatMenu);

        fontSize1.addActionListener(this);

        fontSize2.addActionListener(this);
        fontSize3.addActionListener(this);
        fontSize4.addActionListener(this);
        fontSize5.addActionListener(this);
        fontSize6.addActionListener(this);

        fontstyle1.addActionListener(this);
        fontstyle2.addActionListener(this);

        arial.addActionListener(this);
        calibri.addActionListener(this);
        consolas.addActionListener(this);
        georgia.addActionListener(this);
        timesNew.addActionListener(this);
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

    void createThemeButton() {
        themeButton = new JButton("Change Theme");
        themeButton.setFocusable(false);
        themeButton.setIcon(new ImageIcon("Parent\\Icons\\sunYellowCrop.png"));
        themeButton.setBorderPainted(false);
        themeButton.setContentAreaFilled(false);

        themeButton.addActionListener(this);

        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(themeButton);
    }

    void createTextArea() {
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Calibri", Font.PLAIN, 16));
        textArea.addKeyListener(this);
        // Add a mouse listener to the text area to remove highlights on mouse click
        textArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                textArea.getHighlighter().removeAllHighlights();
            }
        });
        window.add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    // 4 Counter Methods Taken

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveMI) {
            file.saveFile();
        } else if (e.getSource() == openMI) {
            file.openFile();
        } else if (e.getSource() == exitMI) {
            System.exit(0);
        } else if (e.getSource() == versionMI) {
            about.showVersionDialog();
        } else if (e.getSource() == newMI) {
            file.newFile();
        } else if (e.getSource() == authorsMI) {
            about.showAuthorsDialog();
        } else if (e.getSource() == redoMI) {
            edit.redo();
        } else if (e.getSource() == undoMI) {
            edit.undo();
        } else if (e.getSource() == findMI) {
            edit.showFindDialog();
        } else if (e.getSource() == themeButton) {
            theme.changeTheme();
        } else if (e.getSource() == fontSize1) {
            format.changeFontSize(1);
        } else if (e.getSource() == fontSize2) {
            format.changeFontSize(2);
        } else if (e.getSource() == fontSize3) {
            format.changeFontSize(3);
        } else if (e.getSource() == fontSize4) {
            format.changeFontSize(4);
        } else if (e.getSource() == fontSize5) {
            format.changeFontSize(5);
        } else if (e.getSource() == fontSize6) {
            format.changeFontSize(6);
        } else if (e.getSource() == fontstyle1) {
            format.changeFontStyle(1);
        } else if (e.getSource() == fontstyle2) {
            format.changeFontStyle(2);
        } else if (e.getSource() == arial) {
            format.changeFontFamily(1);
        } else if (e.getSource() == calibri) {
            format.changeFontFamily(2);
        } else if (e.getSource() == consolas) {
            format.changeFontFamily(3);
        } else if (e.getSource() == georgia) {
            format.changeFontFamily(4);
        } else if (e.getSource() == timesNew) {
            format.changeFontFamily(5);
        } else if (e.getSource() == replaceMI) {
            edit.showReplaceDialog();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_N) {
            file.newFile();
        }
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_S) {
            file.saveFile();
        }
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_O) {
            file.openFile();
        }
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z) {
            edit.undo();
        }
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Y) {
            edit.redo();
        }
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F) {
            edit.showFindDialog();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    // UndoManager Obj getter
    public UndoManager getUndoManager() {
        return undoManager;
    }

    // JFrame window getter
    public JFrame getJFrameWindow() {
        return window;
    }

    // TextArea getter
    public JTextArea getTextArea() {
        return textArea;
    }

    // South JPanel getter
    public JPanel getSouthPanel() {
        return southPanel;
    }

    // wordCountLabel getter
    public JLabel getWordCountLabel() {
        return wordCountLabel;
    }

    // charCountLabel getter
    public JLabel getCharCountLabel() {
        return charCountLabel;
    }

    // isDarkMode getter
    public boolean getThemeMode() {
        return isDarkMode;
    }

    // isDarkMode setter
    public void setThemeMode(boolean dark) {
        this.isDarkMode = dark;
    }

    // themeButton getter
    public JButton getThemeButton() {
        return themeButton;
    }
}