package Parent.MyNotePad;

import java.awt.Color;

import javax.swing.ImageIcon;

public class Theme {
    private GUI guiObj;

    // void changeTheme()
    public Theme(GUI gui) {
        this.guiObj = gui;
    }

    public void changeTheme() {
        ImageIcon light = new ImageIcon("Parent\\Icons\\sunYellowCrop.png");
        ImageIcon dark = new ImageIcon("Parent\\Icons\\moonBlackCrop.jpg");
        boolean isDarkMode = guiObj.getThemeMode();

        if (isDarkMode) {
            // Switch to light mode
            guiObj.getTextArea().setBackground(Color.white);
            guiObj.getTextArea().setForeground(Color.black);
            guiObj.setThemeMode(false);
            guiObj.getThemeButton().setIcon(light);
        } else {
            // Switch to dark mode
            guiObj.getTextArea().setBackground(Color.darkGray);
            guiObj.getTextArea().setForeground(Color.white);
            guiObj.setThemeMode(true);
            guiObj.getThemeButton().setIcon(dark);
        }
    }
}
