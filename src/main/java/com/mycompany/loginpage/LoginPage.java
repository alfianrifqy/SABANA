/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.loginpage;

/**
 *
 * @author LENOVO
 */
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class LoginPage extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbRole;

    // Palet Warna Modern
    private static final Color BACKGROUND_COLOR = new Color(244, 247, 252);
    private static final Color PRIMARY_COLOR = new Color(13, 71, 161);    // Dark Blue
    private static final Color TEXT_COLOR = new Color(80, 80, 80);
    private static final Font MAIN_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 28);

    public LoginPage() {
        setTitle("SABANA - Selamat Datang");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(400, 550));
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_COLOR);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        GridBagConstraints gbc = new GridBagConstraints();

        // 1. Logo Aplikasi
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        
        // --- PERBAIKAN UTAMA DI SINI ---
        ImageIcon logoIcon = loadIcon("/icons/sabana-logo.png", 100, 100);
        JLabel logoLabel;
        if (logoIcon != null) {
            // Gunakan konstruktor JLabel(Icon) jika gambar ada
            logoLabel = new JLabel(logoIcon);
        } else {
            // Gunakan konstruktor JLabel(String) jika gambar tidak ada
            logoLabel = new JLabel("SABANA");
            logoLabel.setFont(TITLE_FONT);
            logoLabel.setForeground(PRIMARY_COLOR);
        }
        mainPanel.add(logoLabel, gbc);
        // --- AKHIR DARI PERBAIKAN ---

        // 2. Judul
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 20, 0);
        JLabel titleLabel = new JLabel("Silakan Login");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(TEXT_COLOR);
        mainPanel.add(titleLabel, gbc);

        // Reset gbc untuk field input
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 5, 0);

        // 3. Field Username
        gbc.gridy++;
        mainPanel.add(new JLabel("Username:"), gbc);
        gbc.gridy++;
        txtUsername = new JTextField(20);
        txtUsername.setFont(MAIN_FONT);
        mainPanel.add(txtUsername, gbc);

        // 4. Field Password
        gbc.gridy++;
        mainPanel.add(new JLabel("Password:"), gbc);
        gbc.gridy++;
        txtPassword = new JPasswordField(20);
        txtPassword.setFont(MAIN_FONT);
        mainPanel.add(txtPassword, gbc);

        // 5. Field Role
        gbc.gridy++;
        mainPanel.add(new JLabel("Login Sebagai:"), gbc);
        gbc.gridy++;
        String[] roles = {"User", "Admin"};
        cmbRole = new JComboBox<>(roles);
        cmbRole.setFont(MAIN_FONT);
        mainPanel.add(cmbRole, gbc);

        // 6. Tombol Login
        gbc.gridy++;
        gbc.insets = new Insets(20, 0, 0, 0);
        gbc.ipady = 10;
        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBackground(PRIMARY_COLOR);
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setBorder(new EmptyBorder(12, 0, 12, 0));
        mainPanel.add(btnLogin, gbc);

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);

        getRootPane().setDefaultButton(btnLogin);
        btnLogin.addActionListener(e -> handleLogin());
    }

    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        String selectedRole = (String) cmbRole.getSelectedItem();

        boolean loginSuccess = false;
        if ("Admin".equals(selectedRole)) {
            if (username.equalsIgnoreCase("alfian") && password.equals("15279")) {
                loginSuccess = true;
            }
        } else if ("User".equals(selectedRole)) {
            if (username.equalsIgnoreCase("deanova") && password.equals("15278")) {
                loginSuccess = true;
            }
        }

        if (loginSuccess) {
            JOptionPane.showMessageDialog(this, "Login berhasil sebagai " + selectedRole, "Sukses", JOptionPane.INFORMATION_MESSAGE);
            SwingUtilities.invokeLater(() -> new HomePageModern(selectedRole, username).setVisible(true));
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Username atau Password salah!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }

    private ImageIcon loadIcon(String path, int width, int height) {
        try {
            URL imgURL = getClass().getResource(path);
            if (imgURL != null) {
                return new ImageIcon(new ImageIcon(imgURL).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
            } else {
                System.err.println("File ikon tidak ditemukan di path: " + path);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Gagal memuat ikon: " + path);
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        DatabaseHelper.createNewTable();
        SwingUtilities.invokeLater(() -> {
            try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception e) {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            new LoginPage().setVisible(true);
        });
    }
}