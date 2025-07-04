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
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminReportPage extends JFrame {

    private JTable reportTable;
    private DefaultTableModel tableModel;
    private JTextArea detailArea;
    private JLabel imageLabel;

    public AdminReportPage() {
        setTitle("Admin - Daftar Laporan Masuk");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        String[] columnNames = {"ID", "Tanggal", "Nama Pelapor", "Lokasi", "Jenis Bantuan"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        reportTable = new JTable(tableModel);
        loadReportData();
        
        reportTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && reportTable.getSelectedRow() != -1) {
                displayReportDetails(reportTable.getSelectedRow());
            }
        });
        
        tablePanel.add(new JScrollPane(reportTable), BorderLayout.CENTER);
        
        JPanel detailPanel = new JPanel(new BorderLayout(10,10));
        detailPanel.setBorder(BorderFactory.createTitledBorder("Detail Laporan Terpilih"));
        
        detailArea = new JTextArea(10, 30);
        detailArea.setEditable(false);
        detailArea.setLineWrap(true);
        detailArea.setWrapStyleWord(true);
        
        imageLabel = new JLabel("Pilih baris untuk melihat foto", SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(300, 300));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        detailPanel.add(new JScrollPane(detailArea), BorderLayout.CENTER);
        detailPanel.add(imageLabel, BorderLayout.EAST);

        add(tablePanel, BorderLayout.CENTER);
        add(detailPanel, BorderLayout.SOUTH);
    }
    
    private void loadReportData() {
        List<Object[]> reports = DatabaseHelper.getAllReports();
        tableModel.setRowCount(0);
        for (Object[] report : reports) {
            tableModel.addRow(new Object[]{report[0], report[1], report[2], report[3], report[4]});
        }
    }

    private void displayReportDetails(int selectedRow) {
        List<Object[]> allData = DatabaseHelper.getAllReports();
        Object[] fullReportData = allData.get(selectedRow);

        String fotoPath = (String) fullReportData[5];
        String ket1 = (String) fullReportData[6];
        String ket2 = (String) fullReportData[7];
        String alamat = (String) fullReportData[8];
        String kategori = (String) fullReportData[9];
        
        String details = "ID Laporan: " + fullReportData[0] + "\n" +
                         "Tanggal: " + fullReportData[1] + "\n\n" +
                         "Pelapor: " + fullReportData[2] + "\n" +
                         "Lokasi: " + fullReportData[3] + "\n" +
                         "Alamat Detail: " + alamat + "\n\n" +
                         "Kategori: " + kategori + "\n" +
                         "Jenis Bantuan: " + fullReportData[4] + "\n\n" +
                         "Keterangan Awal:\n" + ket1 + "\n\n" +
                         "Keterangan Tambahan:\n" + ket2;
        
        detailArea.setText(details);
        detailArea.setCaretPosition(0);
        
        if (fotoPath != null && !fotoPath.isEmpty()) {
            ImageIcon icon = new ImageIcon(fotoPath);
            Image image = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(image));
            imageLabel.setText(null);
        } else {
            imageLabel.setIcon(null);
            imageLabel.setText("Tidak ada foto");
        }
    }
}