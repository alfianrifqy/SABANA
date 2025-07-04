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
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class LaporBantuanPage extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    private JTextField txtNama, txtLokasi, txtAlamat;
    private JComboBox<String> cmbKategori, cmbJenisBantuan;
    private JTextArea txtKeterangan1, txtKeterangan2;
    private JLabel lblFotoTerunggah;
    private File selectedFile;

    private static final Color BACKGROUND_COLOR = new Color(245, 247, 250);
    private static final Color FIELD_BACKGROUND_COLOR = Color.WHITE;
    private static final Color BUTTON_COLOR = new Color(52, 152, 219);

    public LaporBantuanPage() {
        setTitle("Formulir Laporan Bantuan");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 800);
        setLocationRelativeTo(null);
        setUndecorated(true);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(BACKGROUND_COLOR);

        cardPanel.add(createPanel1(), "PANEL_1");
        cardPanel.add(createPanel2(), "PANEL_2");

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(BACKGROUND_COLOR);
        contentPane.add(createHeader("Isilah Formulir Ini"), BorderLayout.NORTH);
        contentPane.add(cardPanel, BorderLayout.CENTER);

        cardLayout.show(cardPanel, "PANEL_1");

        autoDetectLocation();
    }

    // --- METODE HEADER YANG TELAH DIPERBARUI ---
    private JPanel createHeader(String title) {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);
        headerPanel.setBorder(new EmptyBorder(20, 15, 10, 15));

        // Tombol Kembali di kiri (WEST)
        JButton backButton = new JButton("< Kembali");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backButton.setForeground(Color.DARK_GRAY);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> this.dispose()); // Aksi: tutup jendela ini
        
        // Judul di tengah (CENTER)
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        headerPanel.add(backButton, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        return headerPanel;
    }

    private void autoDetectLocation() {
        txtLokasi.setText("Mendeteksi lokasi Anda...");
        txtLokasi.setEnabled(false);

        SwingWorker<String, Void> worker = new SwingWorker<>() {
            @Override
            protected String doInBackground() throws Exception {
                return LocationService.detectLocation();
            }

            @Override
            protected void done() {
                try {
                    String location = get();
                    txtLokasi.setText(location);
                } catch (Exception e) {
                    txtLokasi.setText("Gagal mendeteksi lokasi.");
                    e.printStackTrace();
                } finally {
                    txtLokasi.setEnabled(true);
                }
            }
        };
        worker.execute();
    }
    
    // --- KODE DI BAWAH INI TIDAK ADA PERUBAHAN ---
    
    private JPanel createPanel1() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(BACKGROUND_COLOR);

        txtNama = new JTextField(15);
        txtLokasi = new JTextField(15);
        cmbKategori = new JComboBox<>(new String[]{"Bencana Alam", "Kemanusiaan", "Infrastruktur"});
        txtKeterangan1 = new JTextArea(5, 15);
        lblFotoTerunggah = new JLabel(" ");

        panel.add(createInputPanel("Nama", "/icons/form-user.png", txtNama));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(createInputPanel("Lokasi", "/icons/form-location.png", txtLokasi));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(createInputPanel("Kategori Laporan", "/icons/form-category.png", cmbKategori));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(createInputPanel("Keterangan", "/icons/form-description.png", new JScrollPane(txtKeterangan1)));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(createUploadPanel());
        panel.add(Box.createVerticalGlue());

        JButton btnNext = new JButton("Berikutnya");
        styleButton(btnNext);
        btnNext.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(btnNext);
        
        btnNext.addActionListener(e -> cardLayout.show(cardPanel, "PANEL_2"));
        return panel;
    }

    private JPanel createPanel2() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(BACKGROUND_COLOR);

        txtAlamat = new JTextField(15);
        cmbJenisBantuan = new JComboBox<>(new String[]{"Makanan & Minuman", "Pakaian & Selimut", "Obat-obatan & P3K"});
        txtKeterangan2 = new JTextArea(5, 15);
        JLabel lblNamaValue = new JLabel();
        
        panel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                lblNamaValue.setText(txtNama.getText());
            }
        });

        panel.add(createInputPanel("Nama", "/icons/form-user.png", lblNamaValue));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(createInputPanel("Alamat", "/icons/form-address.png", txtAlamat));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(createInputPanel("Jenis Bantuan", "/icons/form-aid.png", cmbJenisBantuan));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(createInputPanel("Keterangan", "/icons/form-description.png", new JScrollPane(txtKeterangan2)));
        panel.add(Box.createVerticalGlue());

        JButton btnSend = new JButton("Kirim");
        styleButton(btnSend);
        btnSend.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(btnSend);
        
        btnSend.addActionListener(e -> handleSubmit());
        return panel;
    }
    
    private JPanel createInputPanel(String labelText, String iconPath, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout(15, 0));
        panel.setBackground(FIELD_BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 220, 220)),
            new EmptyBorder(10, 10, 10, 10)
        ));
        panel.setMaximumSize(new Dimension(Short.MAX_VALUE, component instanceof JScrollPane ? 120 : 50));

        JLabel iconLabel = new JLabel(loadIcon(iconPath, 24, 24));
        panel.add(iconLabel, BorderLayout.WEST);

        JPanel textAndFieldPanel = new JPanel(new BorderLayout(10,0));
        textAndFieldPanel.setOpaque(false);
        
        JLabel textLabel = new JLabel(labelText + " :");
        textLabel.setFont(new Font("Arial", Font.BOLD, 14));
        textLabel.setPreferredSize(new Dimension(130, 30));
        textAndFieldPanel.add(textLabel, BorderLayout.WEST);

        if (component instanceof JLabel) {
             component.setFont(new Font("Arial", Font.PLAIN, 14));
        } else {
            component.setFont(new Font("Arial", Font.PLAIN, 14));
            if(component instanceof JScrollPane){
                ((JScrollPane) component).getViewport().getView().setFont(new Font("Arial", Font.PLAIN, 14));
            }
        }
        
        textAndFieldPanel.add(component, BorderLayout.CENTER); 
        
        panel.add(textAndFieldPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createUploadPanel(){
        JPanel panel = new JPanel(new BorderLayout(15, 0));
        panel.setBackground(FIELD_BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 220, 220)),
            new EmptyBorder(10, 10, 10, 10)
        ));
        panel.setMaximumSize(new Dimension(Short.MAX_VALUE, 50));
        
        JLabel iconLabel = new JLabel(loadIcon("/icons/form-upload.png", 24, 24));
        panel.add(iconLabel, BorderLayout.WEST);

        JPanel textAndFieldPanel = new JPanel(new BorderLayout(10, 0));
        textAndFieldPanel.setOpaque(false);
        
        JLabel textLabel = new JLabel("Upload Foto :");
        textLabel.setFont(new Font("Arial", Font.BOLD, 14));
        textLabel.setPreferredSize(new Dimension(130, 30));
        textAndFieldPanel.add(textLabel, BorderLayout.WEST);

        JButton uploadButton = new JButton("Pilih...");
        uploadButton.addActionListener(e -> handleUpload());
        
        JPanel buttonAndStatusPanel = new JPanel(new BorderLayout(5,0));
        buttonAndStatusPanel.setOpaque(false);
        buttonAndStatusPanel.add(uploadButton, BorderLayout.WEST);
        buttonAndStatusPanel.add(lblFotoTerunggah, BorderLayout.CENTER);
        
        textAndFieldPanel.add(buttonAndStatusPanel, BorderLayout.CENTER);
        panel.add(textAndFieldPanel, BorderLayout.CENTER);
        return panel;
    }
    
    private void styleButton(JButton button) {
        button.setBackground(BUTTON_COLOR);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(12, 50, 12, 50));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    private void handleUpload() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Pilih Foto Dokumentasi");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Gambar (JPG, PNG)", "jpg", "png"));
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            lblFotoTerunggah.setText(selectedFile.getName());
        }
    }

    private void handleSubmit() {
        if (txtNama.getText().trim().isEmpty() || txtLokasi.getText().trim().isEmpty() || txtLokasi.getText().contains("Mendeteksi")) {
            JOptionPane.showMessageDialog(this, "Nama dan Lokasi harus diisi!", "Input Kurang", JOptionPane.WARNING_MESSAGE);
            cardLayout.show(cardPanel, "PANEL_1");
            return;
        }
        
        String nama = txtNama.getText().trim();
        String lokasi = txtLokasi.getText().trim();
        String kategori = cmbKategori.getSelectedItem().toString();
        String ket1 = txtKeterangan1.getText().trim();
        String alamat = txtAlamat.getText().trim();
        String jenisBantuan = cmbJenisBantuan.getSelectedItem().toString();
        String ket2 = txtKeterangan2.getText().trim();
        String fotoPath = null;
        
        if (selectedFile != null) {
            try {
                File uploadsDir = new File("uploads");
                if (!uploadsDir.exists()) {
                    uploadsDir.mkdirs();
                }
                String newFileName = System.currentTimeMillis() + "_" + selectedFile.getName();
                File destFile = new File(uploadsDir, newFileName);
                Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                fotoPath = destFile.getPath();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Gagal menyimpan foto!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        DatabaseHelper.insertReport(nama, lokasi, kategori, ket1, fotoPath, alamat, jenisBantuan, ket2);
        
        JOptionPane.showMessageDialog(this, "Laporan Anda telah berhasil dikirim dan disimpan. Terima kasih!", "Laporan Terkirim", JOptionPane.INFORMATION_MESSAGE);
        
        this.dispose();
    }

    private ImageIcon loadIcon(String path, int width, int height) {
        try {
            return new ImageIcon(new ImageIcon(getClass().getResource(path)).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        } catch (Exception e) {
            System.err.println("Gagal memuat ikon: " + path);
            return new ImageIcon(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
        }
    }
}