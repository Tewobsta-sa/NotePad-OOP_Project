package MyNotePad;

import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

public class Edit {
    private GUI guiObj;

    // private void undo()
    // private void redo()
    // public void showFindDialog()
    public Edit(GUI gui) {
        this.guiObj = gui;
    }

    public void undo() {
        if (guiObj.getUndoManager().canUndo()) {
            guiObj.getUndoManager().undo();
        } else {
            JOptionPane.showMessageDialog(guiObj.getJFrameWindow(), "Nothing to undo.");
        }
    }

    public void redo() {
        if (guiObj.getUndoManager().canRedo()) {
            guiObj.getUndoManager().redo();
        } else {
            JOptionPane.showMessageDialog(guiObj.getJFrameWindow(), "Nothing to redo.");
        }
    }

    public void showFindDialog() {

        final String inputValue = JOptionPane.showInputDialog("Find What?");
        if (inputValue == null || inputValue.isEmpty()) {
            return; // Exit if no input provided
        }

        Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.cyan);

        int offset = guiObj.getTextArea().getText().indexOf(inputValue);
        int length = inputValue.length();

        while (offset != -1) {
            try {
                guiObj.getTextArea().getHighlighter().addHighlight(offset, offset + length, painter);
                offset = guiObj.getTextArea().getText().indexOf(inputValue, offset + 1);
            } catch (BadLocationException ble) {
                System.out.println(ble);
            }
        }

    }

    public void showReplaceDialog() {
        String inputValue = JOptionPane.showInputDialog("Find and Replace: ", "Find");
        if (inputValue != null && !inputValue.isEmpty()) {
            String replaceValue = JOptionPane.showInputDialog("Find and Replace: ", "Replace with?");
            if (replaceValue != null) {
                replace(inputValue, replaceValue);
            }
        }
    }

    private void replace(String searchText, String replaceText) {
        String fullText = guiObj.getTextArea().getText();

        if (searchText.isEmpty() || replaceText.isEmpty()) {
            return; // Exit if no search or replace input provided
        }

        String newText = fullText.replace(searchText, replaceText);
        guiObj.getTextArea().setText(newText);

        guiObj.getTextArea().getHighlighter().removeAllHighlights();
    }
}
