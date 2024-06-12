package NPad;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
    
   

    private JMenu aboutMenu;
    private JMenuItem authorsMI;
    private JMenuItem versionMI;
    private JMenu format;
    private JMenu fontstyle;
    private JMenu fontsize;
    private JMenu fontFamily;

    private JMenuItem fontSize1;
    private JMenuItem fontSize2;
    private JMenuItem fontSize3;
    private JMenuItem fontSize4;
    private JMenuItem fontSize5;
    private JMenuItem fontSize6;
    
    private JMenuItem fontstyle1;
    private JMenuItem fontstyle2;

    private JMenuItem calibri;
    private JMenuItem arial;
    private JMenuItem timesNew;
    private JMenuItem consolas;
    private JMenuItem georgia;

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
        createFormatMenu();
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
    
    void createFormatMenu(){
        format=new JMenu("Format");
        fontsize=new JMenu("Font size");
        fontstyle=new JMenu("Font style");
        fontSize1=new JMenuItem("10");
        fontSize2=new JMenuItem("11");
        fontSize3=new JMenuItem("12");
        fontSize4=new JMenuItem("14");
        fontSize5=new JMenuItem("16");
        fontSize6=new JMenuItem("20");
        fontstyle1=new JMenuItem("Bold");
        fontstyle2=new JMenuItem("Italic");
        fontFamily=new JMenu("Font Family");

        arial=new JMenuItem("Arial");
        calibri=new JMenuItem("Calibri");
        consolas=new JMenuItem("Consolas");
        georgia=new JMenuItem("Georgia");
        timesNew=new JMenuItem("Times New Roman");

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
        
        format.add(fontsize);
        format.add(fontstyle);
        format.add(fontFamily);
        menuBar.add(format);

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
        }else if(e.getSource()==fontSize1){
            changeFontSize(1);
         }else if(e.getSource()==fontSize2){
             changeFontSize(2);
         }else if(e.getSource()==fontSize3){
             changeFontSize(3);
         }else if(e.getSource()==fontSize4){
             changeFontSize(4);
         }else if(e.getSource()==fontSize5){
             changeFontSize(5);
         }else if(e.getSource()==fontSize6){
             changeFontSize(6);
         }else if(e.getSource()==fontstyle1){
             changeFontStyle(1);
         }else if(e.getSource()==fontstyle2){
             changeFontStyle(2);
         }else if(e.getSource()==arial){
             changeFontFamily(1);
         }else if(e.getSource()==calibri){
             changeFontFamily(2);
         }else if(e.getSource()==consolas){
             changeFontFamily(3);
         }else if(e.getSource()==georgia){
             changeFontFamily(4);
         }else if(e.getSource()==timesNew){
             changeFontFamily(5);
         }
    }
    private void changeFontFamily(int n){
        Font currentFont=textArea.getFont();
        Font newFont;
        switch(n){
            
            case 1:
            newFont=new Font("Arial",currentFont.getStyle(),currentFont.getSize());
            textArea.setFont(newFont);
            break;
            case 2:
            newFont=new Font("Calibri",currentFont.getStyle(),currentFont.getSize());
            textArea.setFont(newFont);
            break;
            case 3:
            newFont=new Font("Consolas",currentFont.getStyle(),currentFont.getSize());
            textArea.setFont(newFont);
            break;
            case 4:
            newFont=new Font("Georgia",currentFont.getStyle(),currentFont.getSize());
            textArea.setFont(newFont);
            break;
            case 5:
            newFont=new Font("Times New Roman",currentFont.getStyle(),currentFont.getSize());
            textArea.setFont(newFont);
            break;
        }
    }
    private void changeFontStyle(int n){
        Font currentFont=textArea.getFont();
        Font newFont;
        switch(n){
            
            case 1:
            newFont=currentFont.deriveFont(Font.BOLD);
            textArea.setFont(newFont);
            break;
            case 2:
            newFont=currentFont.deriveFont(Font.ITALIC);
            textArea.setFont(newFont);
            break;
        }
    }
    private void changeFontSize(int n){
        
        Font currentFont=textArea.getFont();
        Font newFont;
         switch(n){
            case 1:
            newFont=currentFont.deriveFont(10f);
            textArea.setFont(newFont);
            break;
            case 2:
            newFont=currentFont.deriveFont(11f);
            textArea.setFont(newFont);
            break;
            case 3:
            newFont=currentFont.deriveFont(12f);
            textArea.setFont(newFont);
            break;
            case 4:
            newFont=currentFont.deriveFont(14f);
            textArea.setFont(newFont);
            break;
            case 5:
            newFont=currentFont.deriveFont(15f);
            textArea.setFont(newFont);
            break;
            case 6:
            newFont=currentFont.deriveFont(20f);
            textArea.setFont(newFont);
            break;
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
   
