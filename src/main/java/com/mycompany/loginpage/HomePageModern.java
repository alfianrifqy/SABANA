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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomePageModern extends JFrame {

    private final String userRole;
    private final String username;

    private JLabel locationLabel;

    private static final Color BACKGROUND_COLOR = new Color(245, 247, 250);
    private static final Color HEADER_TEXT_COLOR = new Color(50, 50, 50);
    private static final Color CARD_YELLOW_COLOR = new Color(255, 224, 130);
    private static final Color MAIN_BUTTON_COLOR = new Color(255, 105, 97);
    private static final Color FOOTER_COLOR = Color.WHITE;
    private static final Color ACTIVE_NAV_COLOR = new Color(255, 105, 97);

    public HomePageModern(String userRole, String username) {
        this.userRole = userRole;
        this.username = username;

        setTitle("SABANA - Beranda");
        setUndecorated(true);
        getContentPane().setBackground(BACKGROUND_COLOR);
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(createBodyPanel(), BorderLayout.CENTER);
        mainPanel.add(createFooterPanel(), BorderLayout.SOUTH);
        add(mainPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        updateLocationAutomatically();
    }
    
    private void updateLocationAutomatically() {
        locationLabel.setText("Mendeteksi lokasi...");

        SwingWorker<String, Void> worker = new SwingWorker<>() {
            @Override
            protected String doInBackground() throws Exception {
                return LocationService.detectLocation();
            }

            @Override
            protected void done() {
                try {
                    String detectedLocation = get();
                    locationLabel.setText(detectedLocation);
                } catch (Exception e) {
                    locationLabel.setText("Lokasi tidak diketahui");
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }


    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setBackground(BACKGROUND_COLOR);
        headerPanel.setBorder(new EmptyBorder(20, 15, 10, 15));
        
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        leftPanel.setOpaque(false);
        JLabel logo = new JLabel(loadIcon("/icons/sabana-logo.png", 24, 24));
        leftPanel.add(logo);

        JPanel locationTextPanel = new JPanel();
        locationTextPanel.setLayout(new BoxLayout(locationTextPanel, BoxLayout.Y_AXIS));
        locationTextPanel.setOpaque(false);

        JLabel lokasiAndaLabel = new JLabel("Lokasi anda");
        lokasiAndaLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        lokasiAndaLabel.setForeground(Color.GRAY);
        
        locationLabel = new JLabel("Memuat lokasi..."); 
        locationLabel.setFont(new Font("Arial", Font.BOLD, 13));
        locationLabel.setForeground(HEADER_TEXT_COLOR);
        locationLabel.setIcon(loadIcon("/icons/location-pin.png", 16, 16));

        locationTextPanel.add(lokasiAndaLabel);
        locationTextPanel.add(locationLabel);
        leftPanel.add(locationTextPanel);

        headerPanel.add(leftPanel, BorderLayout.CENTER); 

        return headerPanel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(FOOTER_COLOR);
        footerPanel.setBorder(new EmptyBorder(5, 0, 5, 0));
        footerPanel.setPreferredSize(new Dimension(getWidth(), 70));

        JPanel berandaButton = createNavButton("Beranda", "/icons/nav-beranda.png", true, null);
        JPanel profileButton = createNavButton("Profile", "/icons/nav-profile.png", false, 
            e -> new ProfilePage(username, userRole).setVisible(true)
        );

        if ("Admin".equalsIgnoreCase(userRole)) {
            footerPanel.setLayout(new GridLayout(1, 3));
            JPanel laporanButton = createNavButton("Laporan", "/icons/nav-formulir.png", false, 
                e -> new AdminReportPage().setVisible(true)
            );
            
            footerPanel.add(berandaButton);
            footerPanel.add(laporanButton);
            footerPanel.add(profileButton);

        } else {
            footerPanel.setLayout(new GridLayout(1, 2));
            footerPanel.add(berandaButton);
            footerPanel.add(profileButton);
        }
        return footerPanel;
    }

    private JPanel createNavButton(String text, String iconPath, boolean isActive, ActionListener listener) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        
        JLabel iconLabel = new JLabel(loadIcon(iconPath, 28, 28));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel textLabel = new JLabel(text);
        textLabel.setFont(new Font("Arial", isActive ? Font.BOLD : Font.PLAIN, 12));
        textLabel.setForeground(isActive ? ACTIVE_NAV_COLOR : Color.GRAY);
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(iconLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 4)));
        panel.add(textLabel);
        
        if (listener != null) {
            panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    listener.actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, null));
                }
            });
        }
        return panel;
    }

    private JScrollPane createBodyPanel() {
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
        bodyPanel.setBackground(BACKGROUND_COLOR);
        bodyPanel.setBorder(new EmptyBorder(10, 15, 10, 15));
        bodyPanel.add(createReportCard());
        bodyPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        bodyPanel.add(createMainReportButton());
        bodyPanel.add(Box.createVerticalGlue());
        JScrollPane scrollPane = new JScrollPane(bodyPanel);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scrollPane;
    }

    private JPanel createReportCard() {
        RoundedPanel cardPanel = new RoundedPanel(20, CARD_YELLOW_COLOR);
        cardPanel.setLayout(new BorderLayout(10, 0));
        cardPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        JLabel title = new JLabel("Ayo laporkan sekarang!");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel subtitle = new JLabel("<html>Laporkan logistik yang<br>dibutuhkan di posko yang<br>anda tempatkan saat ini.</html>");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 12));
        textPanel.add(title);
        textPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        textPanel.add(subtitle);
        JLabel illustration = new JLabel(loadIcon("/icons/hand-illustration.png", 80, 80));
        cardPanel.add(textPanel, BorderLayout.CENTER);
        cardPanel.add(illustration, BorderLayout.EAST);
        cardPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 130));
        return cardPanel;
    }
    
    private JPanel createMainReportButton() {
        JPanel panel = new JPanel(null);
        panel.setPreferredSize(new Dimension(200, 200));
        panel.setMaximumSize(new Dimension(200, 200));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel glow = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(230, 230, 230));
                g2d.fillOval(0, 0, 180, 180);
                g2d.dispose();
            }
        };
        glow.setBounds(10, 10, 180, 180);
        JButton reportButton = new JButton("<html><center>Laporkan<br><font size='-2'>Klik untuk melaporkan</font></center></html>") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(MAIN_BUTTON_COLOR);
                g2.fillOval(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
                g2.dispose();
            }
        };
        reportButton.setBounds(25, 25, 150, 150);
        reportButton.setFont(new Font("Arial", Font.BOLD, 20));
        reportButton.setForeground(Color.WHITE);
        reportButton.setContentAreaFilled(false);
        reportButton.setBorderPainted(false);
        reportButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        reportButton.addActionListener(e -> new LaporBantuanPage().setVisible(true));
        panel.add(reportButton);
        panel.add(glow);
        return panel;
    }

    private ImageIcon loadIcon(String path, int width, int height) {
        try {
            return new ImageIcon(new ImageIcon(getClass().getResource(path)).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        } catch (Exception e) {
            System.err.println("Gagal memuat ikon: " + path);
            return new ImageIcon(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
        }
    }

    private static class RoundedPanel extends JPanel {
        private final int cornerRadius;
        private final Color backgroundColor;
        public RoundedPanel(int radius, Color bgColor) {
            super();
            this.cornerRadius = radius;
            this.backgroundColor = bgColor;
            setOpaque(false);
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Dimension arcs = new Dimension(cornerRadius, cornerRadius);
            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setColor(backgroundColor);
            graphics.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcs.width, arcs.height);
        }
    }
}
