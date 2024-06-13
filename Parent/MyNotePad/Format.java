package Parent.MyNotePad;

import java.awt.Font;

public class Format {
    private GUI guiObj;

    // void changeFontFamily(int n)
    // void changeFontStyle(int n)
    // void changeFontSize(int n)
    public Format(GUI gui) {
        this.guiObj = gui;
    }

    public void changeFontFamily(int n) {
        Font currentFont = guiObj.getTextArea().getFont();
        Font newFont;
        switch (n) {
            case 1:
                newFont = new Font("Arial", currentFont.getStyle(), currentFont.getSize());
                guiObj.getTextArea().setFont(newFont);
                break;
            case 2:
                newFont = new Font("Calibri", currentFont.getStyle(), currentFont.getSize());
                guiObj.getTextArea().setFont(newFont);
                break;
            case 3:
                newFont = new Font("Consolas", currentFont.getStyle(), currentFont.getSize());
                guiObj.getTextArea().setFont(newFont);
                break;
            case 4:
                newFont = new Font("Georgia", currentFont.getStyle(), currentFont.getSize());
                guiObj.getTextArea().setFont(newFont);
                break;
            case 5:
                newFont = new Font("Times New Roman", currentFont.getStyle(), currentFont.getSize());
                guiObj.getTextArea().setFont(newFont);
                break;
        }
    }

    public void changeFontStyle(int n) {
        Font currentFont = guiObj.getTextArea().getFont();
        Font newFont;
        switch (n) {

            case 1:
                newFont = currentFont.deriveFont(Font.BOLD);
                guiObj.getTextArea().setFont(newFont);
                break;
            case 2:
                newFont = currentFont.deriveFont(Font.ITALIC);
                guiObj.getTextArea().setFont(newFont);
                break;
        }
    }

    public void changeFontSize(int n) {

        Font currentFont = guiObj.getTextArea().getFont();
        Font newFont;
        switch (n) {
            case 1:
                newFont = currentFont.deriveFont(10f);
                guiObj.getTextArea().setFont(newFont);
                break;
            case 2:
                newFont = currentFont.deriveFont(11f);
                guiObj.getTextArea().setFont(newFont);
                break;
            case 3:
                newFont = currentFont.deriveFont(12f);
                guiObj.getTextArea().setFont(newFont);
                break;
            case 4:
                newFont = currentFont.deriveFont(14f);
                guiObj.getTextArea().setFont(newFont);
                break;
            case 5:
                newFont = currentFont.deriveFont(15f);
                guiObj.getTextArea().setFont(newFont);
                break;
            case 6:
                newFont = currentFont.deriveFont(20f);
                guiObj.getTextArea().setFont(newFont);
                break;
        }
    }
}
