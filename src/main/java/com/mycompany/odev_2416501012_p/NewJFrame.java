/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.odev_2416501012_p;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Tarık
 */
public class NewJFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(NewJFrame.class.getName());
        public static String sifreY="C:\\P2Oyun\\TXTDosyalar\\sifre.txt";
        public static String kelimelerY="C:\\P2Oyun\\TXTDosyalar\\kelimeler.txt";
        public static String logY="C:\\P2Oyun\\TXTDosyalar\\log.txt";
        public static String oyunlarY="C:\\P2Oyun\\TXTDosyalar\\oyunlar.txt";
        public static String resimY= "C:\\P2Oyun\\Resimler";
        
        private String secilenKelime;                 
        private javax.swing.JLabel[] harfEtiketleri;  
        private int yanlisTahminSayisi = 0;          
        private int gecenSüreSaniye = 0;              
        private javax.swing.Timer oyunTimer;          
        
        
        public int hataliSifre=0;
    /**
     * Creates new form NewJFrame
     */
        
       public static void logYaz(String etiket) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(logY, true))) {
            LocalDateTime simdi = LocalDateTime.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            pw.println("[" + simdi.format(format) + "] - " + etiket);
        } catch (IOException e) {
            System.err.println("Log yazma hatası: " + e.getMessage());
        }
    }
        
public void sifreKontrolSisteminiBaslat() {
    // Ekran ilk açıldığında arkadaki koca oyun formunu gizliyoruz
    this.setVisible(false); 

    File sifreDosyasi = new File(sifreY);

    // ---- MADDE 3: Eğer önceden şifre belirlenmediyse veya dosya boşsa ----
    if (!sifreDosyasi.exists() || sifreDosyasi.length() == 0) {
        logYaz("GİRİŞ: Şifre bulunamadı, yeni şifre oluşturma ekranı tetiklendi.");
        
        String yeniSifre = JOptionPane.showInputDialog(this, 
            "Sistemde kayıtlı bir şifre bulunamadı.\nLütfen yeni bir şifre belirleyin:", 
            "Şifre Belirleme", JOptionPane.INFORMATION_MESSAGE);

        if (yeniSifre == null || yeniSifre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Şifre girmediniz. Program sonlandırılıyor.");
            System.exit(0);
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(sifreDosyasi))) {
            pw.print(yeniSifre.trim());
            logYaz("GİRİŞ: Yeni şifre başarıyla 'sifre.txt' dosyasına kaydedildi.");
            JOptionPane.showMessageDialog(this, "Şifreniz başarıyla oluşturuldu! Şimdi giriş yapabilirsiniz.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Şifre dosyası yazılırken hata oluştu: " + e.getMessage());
            System.exit(0);
        }
    }

    // ---- MADDE 4: Şifre zaten varsa Giriş Kontrol Döngüsü Başlar ----
    int hataliGirisSayisi = 0;
    while (hataliGirisSayisi < 3) {
        String girilenSifre = JOptionPane.showInputDialog(this, "Lütfen oyun giriş şifresini yazınız:", "Giriş Kontrolü", JOptionPane.QUESTION_MESSAGE);
        
        if (girilenSifre == null) { 
            logYaz("GİRİŞ: Kullanıcı giriş işlemini iptal etti.");
            System.exit(0);
        }
        
        boolean sifreDogruMu = false;
        
        // İŞTE ÇÖZÜMÜMÜZ: Dosyadaki TÜM satırları satır satır okuyup kontrol ediyoruz
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(sifreDosyasi))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                if (satir.trim().equals(girilenSifre.trim())) {
                    sifreDogruMu = true;
                    break; // Eşleşen şifre bulundu, dosya okuma döngüsünden çık
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Şifre dosyası okunurken hata oluştu: " + ex.getMessage());
            System.exit(0);
        }
        
        // Doğrulama kontrolü
        if (sifreDogruMu) {
            logYaz("GİRİŞ: Başarılı kullanıcı girişi yapıldı.");
            this.setVisible(true); 
            return; // Şifre doğru, metodu bitir ve oyunu aç!
        } else {
            hataliGirisSayisi++;
            logYaz("GİRİŞ DENEMESİ: Hatalı şifre girildi. Deneme sayısı: " + hataliGirisSayisi);
            
            if (hataliGirisSayisi >= 3) {
                logYaz("KİLİTLEME: 3 defa hatalı girildiği için program sonlandırıldı.");
                JOptionPane.showMessageDialog(this, "3 defa hatalı şifre girdiniz! Oyun açılmayacaktır.");
                System.exit(0); 
            } else {
                JOptionPane.showMessageDialog(this, "Hatalı şifre! Kalan hakkınız: " + (3 - hataliGirisSayisi));
            }
        }
    }
}
        
        
        public NewJFrame() {
          
        initComponents();
       // jTabbedPane1.addTab("log", JPanel);
       sifreKontrolSisteminiBaslat();
    
    jTabbedPane3.addChangeListener(new javax.swing.event.ChangeListener() {
        @Override
        public void stateChanged(javax.swing.event.ChangeEvent e) {
            int seciliSekmeIndex = jTabbedPane3.getSelectedIndex();
            if (seciliSekmeIndex == 1) {      
                skorTablosunuGuncelle();
            } else if (seciliSekmeIndex == 2) { 
                logTablosunuGuncelle();
            }
        }
    });
       
    }
        

        
private void oyunuBaslat() {
    // 1. Değişkenleri sıfırla
    this.yanlisTahminSayisi = 0;
    this.gecenSüreSaniye = 0;
    lblSure.setText("Süre: 00 sn");
    txtHarfTahmin.setText("");
    txtKelimeTahmin.setText("");
    
    // Darağacını ilk adımına getir
    resimGuncelle(1);

    // 2. Kelimeyi doğrudan sınıf değişkenine atıyoruz
    this.secilenKelime = rastgeleKelimeSec();
    
    // Eğer hâlâ bir şekilde null kalırsa diye en katı koruma duvarı:
    if (this.secilenKelime == null || this.secilenKelime.trim().isEmpty()) {
        this.secilenKelime = "yazilim";
    }
    
    // Kelimenin boşluklarını temizle
    this.secilenKelime = this.secilenKelime.trim();

   
    panelKelimeler.removeAll();
    panelKelimeler.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 15, 10));


    int harfSayisi = this.secilenKelime.length();
    harfEtiketleri = new javax.swing.JLabel[harfSayisi];
    
    for (int i = 0; i < harfSayisi; i++) {
        harfEtiketleri[i] = new javax.swing.JLabel("*");
        harfEtiketleri[i].setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
        harfEtiketleri[i].setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, java.awt.Color.BLACK));
        panelKelimeler.add(harfEtiketleri[i]);
    }

   
    panelKelimeler.revalidate();
    panelKelimeler.repaint();
    
   
    javax.swing.SwingUtilities.invokeLater(() -> {
        this.getContentPane().validate();
        this.getContentPane().repaint();
    });


    if (oyunTimer != null) {
        oyunTimer.stop();
    }
    oyunTimer = new javax.swing.Timer(1000, new java.awt.event.ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            gecenSüreSaniye++;
            lblSure.setText("Süre: " + gecenSüreSaniye + " sn");
        }
    });
    oyunTimer.start();
}
private String rastgeleKelimeSec() {
    java.util.List<String> kelimeListesi = new java.util.ArrayList<>();
    java.io.File dosya = new java.io.File(kelimelerY);
    
    if (!dosya.exists()) {
        System.err.println("Dosya bulunamadı, varsayılan kelime atanıyor.");
        return "yazilim";
    }

    try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(dosya))) {
        String hat;
        while ((hat = br.readLine()) != null) {
            if (!hat.trim().isEmpty()) {
                // Kelimeleri listeye eklerken Türkçe karakter güvenliğini sağlıyoruz
                kelimeListesi.add(hat.trim().toLowerCase(new java.util.Locale("tr", "TR")));
            }
        }
    } catch (java.io.IOException e) {
        System.err.println("Dosya okunurken hata: " + e.getMessage());
    }

    if (kelimeListesi.isEmpty()) {
        return "yazilim"; // Liste boşsa koruma kelimesi
    }

    java.util.Random rnd = new java.util.Random();
    return kelimeListesi.get(rnd.nextInt(kelimeListesi.size()));
}

// Resmi C:\P2Oyun\Resimler dizininden çeken yardımcı fonksiyon
private void resimGuncelle(int adim) {
    String resimYolu = resimY + "\\" + adim + ".jpg";
    java.io.File resimDosyasi = new java.io.File(resimYolu);
    if (resimDosyasi.exists()) {
        // Resmi etiketin boyutuna göre ölçekle
        javax.swing.ImageIcon icon = new javax.swing.ImageIcon(resimYolu);
        java.awt.Image img = icon.getImage().getScaledInstance(lblResim.getWidth() > 0 ? lblResim.getWidth() : 250, 
                                                              lblResim.getHeight() > 0 ? lblResim.getHeight() : 300, 
                                                              java.awt.Image.SCALE_SMOOTH);
        lblResim.setIcon(new javax.swing.ImageIcon(img));
    } else {
        lblResim.setText("Resim bulunamadı: " + adim + ".jpg");
    }
}

private void harfTahminiYap() {
    String tahmin = txtHarfTahmin.getText().trim().toLowerCase(new java.util.Locale("tr", "TR"));
    txtHarfTahmin.setText(""); 
    
    if (tahmin.isEmpty() || tahmin.length() > 1) {
        javax.swing.JOptionPane.showMessageDialog(this, "Lütfen sadece tek bir harf giriniz!");
        return;
    }

    char harf = tahmin.charAt(0);
    boolean harfBulundu = false;

    for (int i = 0; i < secilenKelime.length(); i++) {
        if (secilenKelime.charAt(i) == harf) {
            harfEtiketleri[i].setText(String.valueOf(harf));
            harfBulundu = true;
        }
    }

    if (!harfBulundu) {
        yanlisTahminSayisi++;
        resimGuncelle(yanlisTahminSayisi + 1); 
        
        if (yanlisTahminSayisi >= 11) {
            oyunuBitir(false); 
        }
    } else {
        boolean oyunKazanildi = true;
        for (javax.swing.JLabel lbl : harfEtiketleri) {
            if (lbl.getText().equals("*")) {
                oyunKazanildi = false;
                break;
            }
        }
        if (oyunKazanildi) {
            oyunuBitir(true);
        }
    }
}

private void kelimeTahminiYap() {
    String tahmin = txtKelimeTahmin.getText().trim().toLowerCase(new java.util.Locale("tr", "TR"));
    txtKelimeTahmin.setText("");

    if (tahmin.isEmpty()) return;

    if (tahmin.equals(secilenKelime)) {
        for (int i = 0; i < secilenKelime.length(); i++) {
            harfEtiketleri[i].setText(String.valueOf(secilenKelime.charAt(i)));
        }
        oyunuBitir(true);
    } else {
        yanlisTahminSayisi++;
        resimGuncelle(yanlisTahminSayisi + 1);
        
        if (yanlisTahminSayisi >= 11) {
            oyunuBitir(false);
        }
    }
}

private void oyunuBitir(boolean kazandiMi) {
    if (oyunTimer != null) {
        oyunTimer.stop();
    }

    String sonuc = kazandiMi ? "KAZANDI" : "KAYBETTİ";
    javax.swing.JOptionPane.showMessageDialog(this, "Oyun Bitti! Sonuç: " + sonuc + "\nKelime: " + secilenKelime);

    try (java.io.PrintWriter pw = new java.io.PrintWriter(new java.io.FileWriter(oyunlarY, true))) {
        java.time.LocalDateTime simdi = java.time.LocalDateTime.now();
        java.time.format.DateTimeFormatter format = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        pw.println("[" + simdi.format(format) + "] - Süre: " + gecenSüreSaniye + " sn - Sonuç: " + sonuc + " - Kelime: " + secilenKelime);
    } catch (java.io.IOException e) {
        System.err.println("Skor kaydedilemedi: " + e.getMessage());
    }
}
// Madde 7.b.i: oyunlar.txt dosyasını okuyup gecmisTable'a (Geçmiş) yükler [cite: 18, 20]
private void skorTablosunuGuncelle() {
    String[] sutunlar = {"Oyun Kaydı Bilgileri"};
    javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(sutunlar, 0);
    
    java.io.File dosya = new java.io.File(oyunlarY);
    if (dosya.exists()) {
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(dosya))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                if (!satir.trim().isEmpty()) {
                    model.addRow(new Object[]{satir}); 
                }
            }
        } catch (java.io.IOException e) {
            System.err.println("Skor tablosu okunurken hata: " + e.getMessage());
        }
    }
    gecmisTable.setModel(model); // Yeni isim uygulandı [cite: 20]
}

// Madde 7.c.i: log.txt dosyasını okuyup logTable'a (Log) yükler [cite: 7, 23]
private void logTablosunuGuncelle() {
    String[] sutunlar = {"Sistem Log Kayıtları"};
    javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(sutunlar, 0);
    
    java.io.File dosya = new java.io.File(logY);
    if (dosya.exists()) {
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(dosya))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                if (!satir.trim().isEmpty()) {
                    model.addRow(new Object[]{satir}); 
                }
            }
        } catch (java.io.IOException e) {
            System.err.println("Log tablosu okunurken hata: " + e.getMessage());
        }
    }
    logTable.setModel(model); // Yeni isim uygulandı [cite: 23]
}




    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        lblResim = new javax.swing.JLabel();
        txtKelimeTahmin = new javax.swing.JTextField();
        txtHarfTahmin = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblSure = new javax.swing.JLabel();
        panelKelimeler = new javax.swing.JPanel();
        btnKelimeTahmin = new javax.swing.JButton();
        btnHarfTahmin = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        gecmisTable = new javax.swing.JTable();
        gecmisBtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        logTable = new javax.swing.JTable();
        logBtn = new javax.swing.JButton();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu5 = new javax.swing.JMenu();
        itemBasla = new javax.swing.JMenuItem();
        itemYenidenBasla = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtKelimeTahmin.setText("Kelime");
        txtKelimeTahmin.addActionListener(this::txtKelimeTahminActionPerformed);

        txtHarfTahmin.setText("Harf");
        txtHarfTahmin.addActionListener(this::txtHarfTahminActionPerformed);

        jLabel3.setText("Kelime Tahmin Ediniz!");

        jLabel4.setText("Harf Tahmin Ediniz!");

        lblSure.setText("Süre : ");

        javax.swing.GroupLayout panelKelimelerLayout = new javax.swing.GroupLayout(panelKelimeler);
        panelKelimeler.setLayout(panelKelimelerLayout);
        panelKelimelerLayout.setHorizontalGroup(
            panelKelimelerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 324, Short.MAX_VALUE)
        );
        panelKelimelerLayout.setVerticalGroup(
            panelKelimelerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 63, Short.MAX_VALUE)
        );

        btnKelimeTahmin.setText("Kelime Tahmin Et");
        btnKelimeTahmin.addActionListener(this::btnKelimeTahminActionPerformed);

        btnHarfTahmin.setText("Harf Tahmin Et");
        btnHarfTahmin.addActionListener(this::btnHarfTahminActionPerformed);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(114, 114, 114)
                .addComponent(lblResim, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtKelimeTahmin, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSure))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtHarfTahmin, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel4)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnKelimeTahmin)
                                .addGap(43, 43, 43)
                                .addComponent(btnHarfTahmin))
                            .addComponent(panelKelimeler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 254, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelKelimeler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblResim, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(29, 29, 29)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKelimeTahmin, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHarfTahmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnKelimeTahmin)
                    .addComponent(btnHarfTahmin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblSure)
                .addGap(91, 91, 91))
        );

        jTabbedPane3.addTab("Oyun", jPanel3);

        gecmisTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(gecmisTable);

        gecmisBtn.setText("Temizle");
        gecmisBtn.addActionListener(this::gecmisBtnActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(gecmisBtn)))
                .addContainerGap(156, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(gecmisBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Geçmiş", jPanel1);

        logTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        jScrollPane2.setViewportView(logTable);

        logBtn.setText("Temizle");
        logBtn.addActionListener(this::logBtnActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(logBtn)))
                .addContainerGap(156, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(logBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Log", jPanel2);

        jMenu5.setText("Oyun İşlemleri");

        itemBasla.setText("Oyuna Başla");
        itemBasla.addActionListener(this::itemBaslaActionPerformed);
        jMenu5.add(itemBasla);

        itemYenidenBasla.setText("Yeniden Başla");
        itemYenidenBasla.addActionListener(this::itemYenidenBaslaActionPerformed);
        jMenu5.add(itemYenidenBasla);

        jMenuBar2.add(jMenu5);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 614, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(71, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 579, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(74, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtHarfTahminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHarfTahminActionPerformed
 harfTahminiYap();
// TODO add your handling code here:
    }//GEN-LAST:event_txtHarfTahminActionPerformed

    private void txtKelimeTahminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKelimeTahminActionPerformed
kelimeTahminiYap();
// TODO add your handling code here:
    }//GEN-LAST:event_txtKelimeTahminActionPerformed

    private void gecmisBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gecmisBtnActionPerformed
      String girilenSifre = javax.swing.JOptionPane.showInputDialog(this, "Geçmişi temizlemek için şifreyi giriniz:", "Güvenlik Kontrolü", javax.swing.JOptionPane.WARNING_MESSAGE);
    
    try {
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(sifreY));
        String gercekSifre = br.readLine();
        br.close();
        
        if (gercekSifre != null && girilenSifre != null && girilenSifre.equals(gercekSifre.trim())) {
            java.io.PrintWriter pw = new java.io.PrintWriter(new java.io.FileWriter(oyunlarY));
            pw.print(""); // İçeriği sıfırla [cite: 21]
            pw.close();
            
            skorTablosunuGuncelle(); // Tabloyu yenile [cite: 20]
            javax.swing.JOptionPane.showMessageDialog(this, "Skor geçmişi başarıyla temizlendi!");
        } else if (girilenSifre != null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Hatalı şifre! Dosya silinmedi.");
        }
    } catch (java.io.IOException ex) {
        javax.swing.JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
    }
        
// TODO add your handling code here:
    }//GEN-LAST:event_gecmisBtnActionPerformed

    private void logBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logBtnActionPerformed
      String girilenSifre = javax.swing.JOptionPane.showInputDialog(this, "Logları temizlemek için şifreyi giriniz:", "Güvenlik Kontrolü", javax.swing.JOptionPane.WARNING_MESSAGE);
    
    try {
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(sifreY));
        String gercekSifre = br.readLine();
        br.close();
        
        if (gercekSifre != null && girilenSifre != null && girilenSifre.equals(gercekSifre.trim())) {
            java.io.PrintWriter pw = new java.io.PrintWriter(new java.io.FileWriter(logY));
            pw.print(""); // İçeriği sıfırla [cite: 24]
            pw.close();
            
            logTablosunuGuncelle(); // Tabloyu yenile [cite: 23]
            javax.swing.JOptionPane.showMessageDialog(this, "Sistem logları başarıyla temizlendi!");
        } else if (girilenSifre != null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Hatalı şifre! Loglar silinmedi.");
        }
    } catch (java.io.IOException ex) {
        javax.swing.JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
    } 
        // TODO add your handling code here:
    }//GEN-LAST:event_logBtnActionPerformed

    private void itemBaslaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemBaslaActionPerformed
oyunuBaslat();
        // TODO add your handling code here:
    }//GEN-LAST:event_itemBaslaActionPerformed

    private void itemYenidenBaslaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemYenidenBaslaActionPerformed
oyunuBaslat();
// TODO add your handling code here:
    }//GEN-LAST:event_itemYenidenBaslaActionPerformed

    private void btnHarfTahminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHarfTahminActionPerformed
        harfTahminiYap();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHarfTahminActionPerformed

    private void btnKelimeTahminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKelimeTahminActionPerformed
       kelimeTahminiYap();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnKelimeTahminActionPerformed

    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
    } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
        logger.log(java.util.logging.Level.SEVERE, null, ex);
    }

    /* Formu görünür kılan asıl çalışma noktası */
    java.awt.EventQueue.invokeLater(() -> {
        NewJFrame frame = new NewJFrame();
        frame.setLocationRelativeTo(null); // Ekranın tam ortasında açılması için
        // frame.setVisible(true); <-- BURAYI YORUM SATIRI YAPTIK YA DA SİL. 
        // Çünkü şifre doğruysa yukarıdaki metot kendi içinde true yapacak.
    });
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        //java.awt.EventQueue.invokeLater(() -> new NewJFrame().setVisible(true));
    }
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHarfTahmin;
    private javax.swing.JButton btnKelimeTahmin;
    private javax.swing.JButton gecmisBtn;
    private javax.swing.JTable gecmisTable;
    private javax.swing.JMenuItem itemBasla;
    private javax.swing.JMenuItem itemYenidenBasla;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JLabel lblResim;
    private javax.swing.JLabel lblSure;
    private javax.swing.JButton logBtn;
    private javax.swing.JTable logTable;
    private javax.swing.JPanel panelKelimeler;
    private javax.swing.JTextField txtHarfTahmin;
    private javax.swing.JTextField txtKelimeTahmin;
    // End of variables declaration//GEN-END:variables
}
