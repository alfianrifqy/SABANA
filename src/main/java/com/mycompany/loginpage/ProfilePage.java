/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginpage;

/**
 *
 * @author LENOVO
 */
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ProfilePage extends JFrame {

    private final String username;
    private final String userRole;

    // Palet Warna
    private static final Color BACKGROUND_COLOR = new Color(244, 247, 252);
    private static final Color PRIMARY_TEXT_COLOR = new Color(50, 50, 50);
    private static final Color SECONDARY_TEXT_COLOR = new Color(120, 120, 120);
    private static final Color LOGOUT_BUTTON_COLOR = new Color(220, 53, 69); // Merah

    public ProfilePage(String username, String userRole) {
        this.username = username;
        this.userRole = userRole;

        setTitle("Profil Pengguna - " + username);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Hanya tutup jendela ini, bukan aplikasi
        setSize(400, 500);
        setLocationRelativeTo(null);
        setUndecorated(true); // Tampilan modern tanpa border default

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            new EmptyBorder(20, 20, 20, 20)
        ));

        // Panel Atas untuk Tombol Kembali
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);
        JButton backButton = new JButton("< Kembali");
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> this.dispose());
        topPanel.add(backButton);
        
        // Panel Tengah untuk Konten Profil
        JPanel profileContentPanel = new JPanel();
        profileContentPanel.setLayout(new BoxLayout(profileContentPanel, BoxLayout.Y_AXIS));
        profileContentPanel.setOpaque(false);

        // Avatar
        JLabel avatarLabel = new JLabel(loadIcon("/icons/profile-avatar.png", 100, 100));
        avatarLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Nama Pengguna
        JLabel usernameLabel = new JLabel(username);
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        usernameLabel.setForeground(PRIMARY_TEXT_COLOR);
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Role Pengguna
        JLabel roleLabel = new JLabel(userRole);
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        roleLabel.setForeground(SECONDARY_TEXT_COLOR);
        roleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Tombol Logout
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        logoutButton.setBackground(LOGOUT_BUTTON_COLOR);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.addActionListener(e -> handleLogout());

        profileContentPanel.add(avatarLabel);
        profileContentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        profileContentPanel.add(usernameLabel);
        profileContentPanel.add(roleLabel);
        profileContentPanel.add(Box.createVerticalGlue()); // Pendorong ke bawah
        profileContentPanel.add(logoutButton);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(profileContentPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void handleLogout() {
        int response = JOptionPane.showConfirmDialog(this, 
                "Apakah Anda yakin ingin logout?", 
                "Konfirmasi Logout", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE);
        
        if (response == JOptionPane.YES_OPTION) {
            // Tutup semua jendela yang sedang terbuka
            for (Window window : Window.getWindows()) {
                window.dispose();
            }
            // Buka kembali halaman login
            SwingUtilities.invokeLater(() -> new LoginPage().setVisible(true));
        }
    }

    private ImageIcon loadIcon(String path, int width, int height) {
        try {
            return new ImageIcon(new ImageIcon(getClass().getResource(path)).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        } catch (Exception e) {
            System.err.println("Gagal memuat ikon: " + path);
            return null;
        }
    }
}