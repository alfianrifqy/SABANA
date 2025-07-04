/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loginpage;

/**
 *
 * @author LENOVO
 */
import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LocationService {

    // Kita akan menggunakan layanan gratis dari ip-api.com
    private static final String API_URL = "http://ip-api.com/json";

    public static String detectLocation() {
        try {
            // Menggunakan HttpClient modern dari Java 11+
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .build();

            // Mengirim request dan mendapatkan response sebagai String
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Parsing response JSON
                JSONObject jsonObject = new JSONObject(response.body());
                
                // Cek status, jika gagal, kembalikan pesan error
                if ("fail".equals(jsonObject.optString("status"))) {
                    return "Gagal mendeteksi: " + jsonObject.optString("message");
                }

                // Ambil data lokasi dan gabungkan
                String city = jsonObject.optString("city", "Tidak diketahui");
                String region = jsonObject.optString("regionName", "Tidak diketahui");
                String country = jsonObject.optString("country", "Tidak diketahui");

                return city + ", " + region + ", " + country;
            } else {
                return "Gagal terhubung ke layanan lokasi.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Tidak ada koneksi internet.";
        }
    }
}