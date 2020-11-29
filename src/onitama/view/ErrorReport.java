package onitama.view;

import javax.swing.*;
import java.util.Arrays;

public class ErrorReport {

    private final Exception e;

    public ErrorReport(Exception e) {
        super();
        this.e = e;
        initialize();
    }

    private void initialize() {
        JFrame f = new JFrame();
        String message = e.getMessage() + "\n"
                + Arrays.toString(e.getStackTrace()) + "\n";
        JOptionPane.showMessageDialog(f, message, "An error occurred", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }
}
