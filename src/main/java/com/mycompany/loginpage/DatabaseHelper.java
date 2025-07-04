/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginpage;

/**
 *
 * @author LENOVO
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "sabana_db";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    private static final String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME 
                                       + "?useSSL=false&serverTimezone=UTC";

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    public static void createNewTable() {
        String sql = "CREATE TABLE IF NOT EXISTS `laporan` (\n"
                + "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
                + "  `nama_pelapor` varchar(255) NOT NULL,\n"
                + "  `lokasi_kejadian` varchar(255) NOT NULL,\n"
                + "  `kategori` varchar(100) NOT NULL,\n"
                + "  `keterangan_awal` text DEFAULT NULL,\n"
                + "  `foto_path` varchar(255) DEFAULT NULL,\n"
                + "  `alamat_detail` text DEFAULT NULL,\n"
                + "  `jenis_bantuan` varchar(255) NOT NULL,\n"
                + "  `keterangan_tambahan` text DEFAULT NULL,\n"
                + "  `tanggal_laporan` timestamp NOT NULL DEFAULT current_timestamp(),\n"
                + "  PRIMARY KEY (`id`)\n"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Gagal membuat tabel: " + e.getMessage());
        }
    }

    public static void insertReport(String nama, String lokasi, String kategori, String ket1, String fotoPath, String alamat, String jenisBantuan, String ket2) {
        String sql = "INSERT INTO laporan(nama_pelapor, lokasi_kejadian, kategori, keterangan_awal, foto_path, alamat_detail, jenis_bantuan, keterangan_tambahan) VALUES(?,?,?,?,?,?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nama);
            pstmt.setString(2, lokasi);
            pstmt.setString(3, kategori);
            pstmt.setString(4, ket1);
            pstmt.setString(5, fotoPath);
            pstmt.setString(6, alamat);
            pstmt.setString(7, jenisBantuan);
            pstmt.setString(8, ket2);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal memasukkan laporan: " + e.getMessage());
        }
    }

    public static List<Object[]> getAllReports() {
        String sql = "SELECT id, tanggal_laporan, nama_pelapor, lokasi_kejadian, jenis_bantuan, foto_path, keterangan_awal, keterangan_tambahan, alamat_detail, kategori FROM laporan ORDER BY id DESC";
        List<Object[]> reports = new ArrayList<>();

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Object[] row = new Object[]{
                    rs.getInt("id"),
                    rs.getString("tanggal_laporan"),
                    rs.getString("nama_pelapor"),
                    rs.getString("lokasi_kejadian"),
                    rs.getString("jenis_bantuan"),
                    rs.getString("foto_path"),
                    rs.getString("keterangan_awal"),
                    rs.getString("keterangan_tambahan"),
                    rs.getString("alamat_detail"),
                    rs.getString("kategori")
                };
                reports.add(row);
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil laporan: " + e.getMessage());
        }
        return reports;
    }
}