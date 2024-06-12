package NPad;

import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.undo.UndoManager;

public class GUI extends JFrame implements ActionListener {
    private JFrame window;
    
    private JPanel southPanel;
    private JLabel charCountLabel;
    private JLabel wordCountLabel;
    
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
    
    private JMenu formatMenu;
    private JMenuItem fontTypeMI;
    private JMenuItem fontSizeMI;
    private JMenuItem fontColorMI;

    private JMenu aboutMenu;
    private JMenuItem authorsMI;
    private JMenuItem versionMI;

    private UndoManager undoManager;
    
    private JButton themeButton;
    boolean isDarkMode = false;

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
        addCharCounter();
        addWordCounter();
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
    
    void createSouthPanel() {
    	southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 0));
    	window.add(southPanel, BorderLayout.SOUTH);
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
    
    void createFormatMenu() {
    	// JMenu
        formatMenu = new JMenu("Format");
        
        // JMenuItem
        fontTypeMI = new JMenuItem("Font Type");
        fontSizeMI = new JMenuItem("Font Size");
        fontColorMI = new JMenuItem("Font Color");
        
        formatMenu.add(fontTypeMI);
        formatMenu.add(fontSizeMI);
        formatMenu.add(fontColorMI);
        
        fontTypeMI.addActionListener(this);
        fontSizeMI.addActionListener(this);
        fontColorMI.addActionListener(this);
        
        menuBar.add(formatMenu);
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
    	themeButton.setIcon(new ImageIcon("sunYellowCrop.png"));
    	
    	themeButton.addActionListener(this);
    	
    	menuBar.add(Box.createHorizontalGlue());
    	menuBar.add(themeButton);
    }
    
    void createTextArea() {
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setFont(new Font("Calibri", Font.PLAIN, 25));
        window.add(new JScrollPane(textArea), BorderLayout.CENTER);
    }
    
    void addCharCounter() {
        charCountLabel = new JLabel("Characters: 0");
        southPanel.add(charCountLabel);
        textArea.getDocument().addDocumentListener(new DocumentListener() {
        	@Override
            public void insertUpdate(DocumentEvent e) {
                updateCharCount();
            }
        	@Override
            public void removeUpdate(DocumentEvent e) {
                updateCharCount();
            }
        	@Override
            public void changedUpdate(DocumentEvent e) {
                updateCharCount();
            }
        });
    }
    
    void addWordCounter() {
        wordCountLabel = new JLabel("Word Count: 0");
        southPanel.add(wordCountLabel);
        textArea.getDocument().addDocumentListener(new DocumentListener() {
        	@Override
            public void insertUpdate(DocumentEvent e) {
                updateWordCount();
            }
        	@Override
            public void removeUpdate(DocumentEvent e) {
        		updateWordCount();
            }
        	@Override
            public void changedUpdate(DocumentEvent e) {
        		updateWordCount();
            }
        });
    }

    void updateCharCount() {
        charCountLabel.setText("Characters: " + textArea.getText().length());
    }
    
    void updateWordCount() {
    	String text = textArea.getText();
        String[] words = text.split("\\s+");
    	wordCountLabel.setText("Word Count: " + words.length);
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
        	newFile();
        } else if (e.getSource() == authorsMI) {
        	showAuthorsDialog();
        } else if (e.getSource() == redoMI) {
        	redo();
        } else if (e.getSource() == undoMI) {
        	undo();
        } else if (e.getSource() == findMI) {
        	showFindDialog();
        } else if (e.getSource() == themeButton) {
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
    
    private void newFile() {
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
    
    private void changeTheme(){
        ImageIcon light = new ImageIcon("sunYellowCrop.png");
        ImageIcon dark = new ImageIcon("moonBlackCrop.jpg");

        if (isDarkMode) {
            // Switch to light mode
            textArea.setBackground(Color.white);
            textArea.setForeground(Color.black);
            isDarkMode = false;
            themeButton.setIcon(light);
        } else {
            // Switch to dark mode
            textArea.setBackground(Color.darkGray);
            textArea.setForeground(Color.white);
            isDarkMode = true;
            themeButton.setIcon(dark);
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
