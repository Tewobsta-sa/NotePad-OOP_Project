package Parent.MyNotePad;

import javax.swing.JOptionPane;

public class About {
    private GUI guiObj;

    public About(GUI gui) {
        this.guiObj = gui;
    }

    public void showVersionDialog() {
        JOptionPane.showMessageDialog(guiObj.getJFrameWindow(),
                "NotePad v1.0\nA simple notepad application\nCreated by us!", "About",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void showAuthorsDialog() {
        JOptionPane.showMessageDialog(guiObj.getJFrameWindow(),
                "Authors\n"
                        + "- Lydia Yoseph\r\n"
                        + "- Nathanael Cheramlak\r\n"
                        + "- Salem Gebru\r\n"
                        + "- Tewobsta Seyoum\r\n"
                        + "- Tsion Teklay\r\n"
                        + "- Umer Ahmed\r\n"
                        + "- Yohannes Alemu",
                "Author", JOptionPane.INFORMATION_MESSAGE);
    }
}
