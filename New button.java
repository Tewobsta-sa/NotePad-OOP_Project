public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveMI) {
            saveFile();
        } else if (e.getSource() == openMI) {
            openFile();
        } else if (e.getSource() == newMI) {
            newFile();
        }else if (e.getSource()==exitMI) {
            exitProgram();
        }
    }

    private void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(window);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            // Save the file
            // ...
        }
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(window);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            // Open the file
            // ...
        }
    }
    private void exitProgram(){
        int option = JOptionPane.showOptionDialog(
                window,
                "Are you sure u want to exit?",
                "Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                0);
        if (option == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }

    private void newFile() {
        if (!textArea.getText().equals(currentText)) {
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
                        setTitle("Untitled");
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
        } else {
            // No modifications, clear the text area and set the title
            textArea.setText("");
            setTitle("Untitled");
        }
    }
}
