package MyNotePad;

import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Counter {
    private GUI guiObj;
    private JLabel charCountLabel;
    private JLabel wordCountLabel;

    public Counter(GUI gui) {
        this.guiObj = gui;
    }

    public void addCharCounter() {
        charCountLabel = new JLabel("Characters: 0");
        guiObj.getSouthPanel().add(charCountLabel);
        guiObj.getTextArea().getDocument().addDocumentListener(new DocumentListener() {
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

    private void updateCharCount() {
        String text = guiObj.getTextArea().getText();
        charCountLabel.setText("Characters: " + text.length());
    }

    public void addWordCounter() {
        wordCountLabel = new JLabel("Word Count: 0");
        guiObj.getSouthPanel().add(wordCountLabel);
        guiObj.getTextArea().getDocument().addDocumentListener(new DocumentListener() {
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

    private void updateWordCount() {
        String text = guiObj.getTextArea().getText();
        String[] words = text.trim().split("\\s+");
        wordCountLabel.setText("Word Count: " + (text.isEmpty() ? 0 : words.length));
    }
}
