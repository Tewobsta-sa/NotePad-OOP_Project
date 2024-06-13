package Parent.MyNotePad;

import java.awt.FileDialog;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

public class File {
    private GUI guiObj;

    public File(GUI gui) {
        this.guiObj = gui;
    }

    public void saveFile() {
        FileDialog fileDialog = new FileDialog(guiObj.getJFrameWindow(), "Save File", FileDialog.SAVE);
        fileDialog.setVisible(true);
        try {
            /*
             * fileDialog.getDirectory() gets the directory chosen by the user.
             * fileDialog.getFile() gets the name of the file chosen by the user.
             * These are concatenated to form the full file path.
             */
            String filePath = fileDialog.getDirectory() + fileDialog.getFile() + ".txt";
            if (filePath != null) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
                String text = guiObj.getTextArea().getText();
                bw.write(text);
                guiObj.getJFrameWindow().setTitle(fileDialog.getFile());
                bw.close();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(guiObj.getJFrameWindow(),
                    "An error occurred while saving the file: " + e.getMessage(), "Save Error",
                    JOptionPane.ERROR_MESSAGE);// handles errors like inaccurate file paths, no enough space to save the
                                               // file etc
        }
    }

    public void openFile() {
        FileDialog fileDialog = new FileDialog(guiObj.getJFrameWindow(), "Open File", FileDialog.LOAD);// fFileDialog.Load
                                                                                                       // sets it to an
                                                                                                       // open dialogue
        fileDialog.setVisible(true);

        try {
            String filePath = fileDialog.getDirectory() + fileDialog.getFile();
            if (filePath != null) {
                BufferedReader br = new BufferedReader(new FileReader(filePath));
                String text;
                guiObj.getTextArea().setText("");
                while ((text = br.readLine()) != null) {
                    guiObj.getTextArea().append(text + "\n");
                }
                br.close();
                guiObj.getJFrameWindow().setTitle(fileDialog.getFile());
            }
        } catch (IOException e) {

        }
    }

    public void newFile() {
        // Show a save confirmation dialog
        int option = JOptionPane.showConfirmDialog(
                guiObj.getJFrameWindow(),
                "The current text has been modified. Do you want to save it?",
                "Save File",
                JOptionPane.YES_NO_CANCEL_OPTION);

        // Handle the user's choice
        switch (option) {
            case JOptionPane.YES_OPTION:
                if (guiObj.getTextArea().getText().trim().isEmpty()) {
                    // If the text area is empty, show a warning dialog
                    JOptionPane.showMessageDialog(
                            guiObj.getJFrameWindow(),
                            "The text field is empty. Please enter some text before saving.",
                            "Save Error",
                            JOptionPane.WARNING_MESSAGE);
                    break;
                } else {
                    // Save the file
                    saveFile();
                    guiObj.getTextArea().setText("");
                    guiObj.getJFrameWindow().setTitle("Untitled");
                    break;
                }
            case JOptionPane.NO_OPTION:
                // Don't save, clear the text area and set the title
                guiObj.getTextArea().setText("");
                guiObj.getJFrameWindow().setTitle("Untitled");
                break;
            case JOptionPane.CANCEL_OPTION:
                // Cancel the operation, do nothing
                break;
        }
    }
}
