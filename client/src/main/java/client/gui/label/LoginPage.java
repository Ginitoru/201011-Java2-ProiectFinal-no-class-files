package client.gui.label;

import client.gui.panel.TransparentPanel;

import javax.swing.*;
import java.awt.*;

public class LoginPage extends JLabel {

    private TransparentPanel transparentPanel;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;


    public LoginPage(int x, int y, int width, int height) {
        this.setBounds(x, y, width, height);
        initTransparentPanel();
        initLoginButton();
        initRegisterButton();
        initPasswordField();
        initUsernameField();
        initUsernameLabel();
        initPasswordLabel();

    }


    private void initTransparentPanel(){
        transparentPanel = new TransparentPanel(400, 100, 400, 600);
        this.add(transparentPanel);
    }

    private void initUsernameLabel(){
        usernameLabel = new JLabel("username / email");
        usernameLabel.setBounds(50,250,300,30);
        transparentPanel.add(usernameLabel);
    }

    private void initUsernameField(){
        usernameField = new JTextField();
        usernameField.setBounds(50,275,300,30);
        transparentPanel.add(usernameField);
    }

    private void initPasswordLabel(){
        passwordLabel = new JLabel("password");
        passwordLabel.setBounds(50,310,300,30);
        transparentPanel.add(passwordLabel);

    }

    private void initPasswordField(){
        passwordField = new JPasswordField();
        passwordField.setBounds(50,335,300,30);
        transparentPanel.add(passwordField);
    }

    private void initLoginButton(){
        loginButton = new JButton("LOGIN");
        loginButton.setBackground(Color.RED);
        loginButton.setBounds(50, 395, 300, 30);
        transparentPanel.add(loginButton);
    }

    private void initRegisterButton(){
        registerButton = new JButton("GO TO REGISTER PAGE!");
        registerButton.setBackground(Color.RED);
        registerButton.setBounds(50, 550, 300, 30);
        transparentPanel.add(registerButton);
    }














}