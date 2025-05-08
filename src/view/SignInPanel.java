package view;

import controller.IController;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class SignInPanel extends JPanel {
    public SignInPanel(SignInFrom signInFrom) {
        setLayout(new BorderLayout());
        add(signInFrom,BorderLayout.CENTER);
    }
}
