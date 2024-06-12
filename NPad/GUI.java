package NPad;

import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.*;
import javax.swing.*;
import javax.swing.undo.UndoManager;

public class GUI extends JFrame implements ActionListener , KeyListener{
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

    private UndoManager undoManager;
    ImageIcon light = new ImageIcon("sunlight.png");
    ImageIcon dark = new ImageIcon("moon.png");


    Image lightImage = light.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
    // Resize the dark image to 32x32 pixels
    Image darkImage = dark.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
    boolean isDarkMode = false;

    // Constructor
    public GUI() {
        createFrame();
        createFileMenu();
        createEditMenu();
        createAboutMenu();
        createTextArea();
        undoManager = new UndoManager();
        textArea.getDocument().addUndoableEditListener(undoManager);
    }

    void createFrame() {
        window = new JFrame();
        ImageIcon appIcon = new ImageIcon("NotePad-icon.png");
        window.setIconImage(appIcon.getImage());

        window.setTitle("NotePad");
	window.setIconImage(new ImageIcon("notepad.png").getImage());
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

    void createEditMenu() {
        // JMenu
        editMenu = new JMenu("Edit");

        // JMenuItem
        findMI = new JMenuItem("Find (ctrl+F)");
        redoMI = new JMenuItem("Redo (ctrl+Y)");
        undoMI = new JMenuItem("Undo (ctrl+Z)");

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

    void createThemeButton() {
        theme = new JButton();


        theme.setIcon(new ImageIcon(darkImage));
        theme.addActionListener(this);

        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(theme);

    }
    void createTextArea() {
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setFont(new Font("Calibri", Font.PLAIN, 25));
        window.add(new JScrollPane(textArea), BorderLayout.CENTER);
	textArea.addKeyListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveMI) {
        	saveFile();
        } else if (e.getSource() == openMI) {
        	openFile();
        } else if (e.getSource() == exitMI) {
            System.exit(0);
        } else if (e.getSource() == versionMI) {
        	showVersionDialog();
        } else if (e.getSource() == newMI) {
        	newFileT();
        } else if (e.getSource() == authorsMI) {
        	showAuthorsDialog();
        } else if (e.getSource() == redoMI) {
        	redo();
        } else if (e.getSource() == undoMI) {
        	undo();
        } else if (e.getSource() == findMI) {
        	showFindDialog();
        } else if (e.getSource() == theme) {
            changeTheme();
        }
    }

	private void saveFile()  {
        FileDialog fileDialog = new FileDialog(window, "Save File", FileDialog.SAVE);// the mode FileDialog.SAVE  sets it to a save dialog.
        fileDialog.setVisible(true);
        try {
            /*  fileDialog.getDirectory() gets the directory chosen by the user.
				fileDialog.getFile() gets the name of the file chosen by the user.
				These are concatenated to form the full file path. */
            String filePath = fileDialog.getDirectory() + fileDialog.getFile() + ".txt";
            if (filePath != null) {
    			BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
    			String text = textArea.getText();
    			bw.write(text);
    			window.setTitle(fileDialog.getFile());
    			bw.close();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(window, "An error occurred while saving the file: " + e.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);// handles errors like inaccurate file paths, no enough space to save the file etc
        }
    }

    private void openFile() {
        FileDialog fileDialog = new FileDialog(window, "Open File", FileDialog.LOAD);//fFileDialog.Load sets it to an open dialogue
        fileDialog.setVisible(true);
        
        try {
        	String filePath = fileDialog.getDirectory() + fileDialog.getFile();
            if (filePath != null) {	
	        	FileReader reader = new FileReader(filePath);
	            textArea.read(reader, null);
	            window.setTitle(fileDialog.getFile());
            }
        } catch (IOException e) {
        	
        }
     }
    
    private void newFileT() {
            // Show a save confirmation dialog
            int option = JOptionPane.showConfirmDialog(
                    window,
                    "The current text has been modified. Do you want to save it?",
                    "Save File",
                    JOptionPane.YES_NO_CANCEL_OPTION
            );

            // Handle the user's choice
            switch (option) {
                case JOptionPane.YES_OPTION:
                    if (textArea.getText().trim().isEmpty()) {
                        // If the text area is empty, show a warning dialog
                        JOptionPane.showMessageDialog(
                                window,
                                "The text field is empty. Please enter some text before saving.",
                                "Save Error",
                                JOptionPane.WARNING_MESSAGE
                        );
                        break;
                    } else {
                        // Save the file
                        saveFile();
                        textArea.setText("");
                        window.setTitle("Untitled");
                        break;
                    }
                case JOptionPane.NO_OPTION:
                    // Don't save, clear the text area and set the title
                    textArea.setText("");
                    setTitle("Untitled");
                    break;
                case JOptionPane.CANCEL_OPTION:
                    // Cancel the operation, do nothing
                    break;
            }
            }
    
    private void undo() {
        if (undoManager.canUndo()) {
            undoManager.undo();
        } else {
            JOptionPane.showMessageDialog(window, "Nothing to undo.");
        }
    }

    private void redo() {
        if (undoManager.canRedo()) {
            undoManager.redo();
        } else {
            JOptionPane.showMessageDialog(window, "Nothing to redo.");
        }
    }
    
    public void showFindDialog() {
    	final String inputValue = JOptionPane.showInputDialog("Find What?");
    	final int l1 = textArea.getText().indexOf(inputValue);
    	final int l2 = inputValue.length();

    	if (l1 == -1) {
    	    JOptionPane.showMessageDialog(null, "Search Value Not Found");
    	} else {
    	    textArea.select(l1, l2+l1);
    	}
    }
    
    private void changeTheme(){

        if (isDarkMode) {
            // Switch to light mode
            theme.setIcon(new ImageIcon(lightImage));
            textArea.setBackground(Color.white);
            textArea.setForeground(Color.black);
            isDarkMode = false;

        } else {
            // Switch to dark mode
            theme.setIcon(new ImageIcon(darkImage));
            textArea.setBackground(Color.darkGray);
            textArea.setForeground(Color.white);
            isDarkMode = true;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_S){
            saveFile();
        }
        if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_O){
            openFile();
        }
        if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z){
            undo();
        }
        if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Y){
            redo();
        }
        if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F){
            showFindDialog();
        }
        if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_N){
            newFileT();
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
     private void showVersionDialog() {
        JOptionPane.showMessageDialog(this, "NotePad v1.0\nA simple notepad application\nCreated by us!", "About", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showAuthorsDialog() {
        JOptionPane.showMessageDialog(this, 
        		"Authors\n"
        		+ "- Lydia Yoseph\r\n"
        		+ "- Nathanael Cheramlak\r\n"
        		+ "- Salem Gebru\r\n"
        		+ "- Tewobsta Seyoum\r\n"
        		+ "- Tsion Teklay\r\n"
        		+ "- Umer Ahmed\r\n"
        		+ "- Yohannes Alemu", "Author", JOptionPane.INFORMATION_MESSAGE);
	}
    
    }
