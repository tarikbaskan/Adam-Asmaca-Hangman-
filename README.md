🎮 Adam Asmaca (Hangman) Oyun Projesi

Bu proje, \*\*Java Swing\*\* kütüphanesi kullanılarak geliştirilmiş, dosya tabanlı çalışan gelişmiş bir masaüstü Adam Asmaca oyunudur. Program; kullanıcı güvenliği, dinamik görsel yükleme ve canlı skor/günlük (log) takibi gibi özelliklere sahiptir.


\---

-----📁 Gerekli Dosya ve Klasör Yapısı-----

Oyunun bilgisayarınızda sorunsuz çalışabilmesi için `C:\\` dizini altında aşağıdaki klasörlerin ve dosyaların eksiksiz bulunması gerekmektedir:

📂 \*\*C:\\P2Oyun\*\*

┣ 📂 \*\*Resimler\*\* (İçerisinde `1.jpg`'den `11.jpg`'ye kadar 11 adet darağacı görseli olmalı)

┗ 📂 \*\*TXTDosyalar\*\*

┃ ┣ 📄 `sifre.txt` -> Oyuna giriş şifreleri (Alt alta birden fazla şifre yazılabilir)

┃ ┣ 📄 `kelimeler.txt` -> Oyunda sorulacak Türkçe kelimelerin listesi

┃ ┣ 📄 `oyunlar.txt` -> Tamamlanan oyunların skor ve geçmiş kayıtları

┃ ┗ 📄 `log.txt` -> Başarılı/başarısız tüm girişlerin tutulduğu sistem günlüğü

\---

✨ Projenin Öne Çıkan Özellikleri

🔐 Güvenli Giriş Sistemi

\- Program ilk açıldığında ana oyun ekranını tamamen gizler. Şifre doğru girilene kadar oyun açılmaz.

\- Eğer `sifre.txt` dosyası boşsa, sistem otomatik olarak "Yeni Şifre Oluşturma" ekranını açar.

\- Üst üste 3 defa hatalı şifre girilirse, program güvenlik amacıyla kendini tamamen kapatır.


🎯 Dinamik Oyun Alanı \& Kelime Yıldızları

\- Üst menüden \*\*"Oyuna Başla"\*\* denildiğinde, kelimeler dosyasından rastgele bir kelime seçilir.

\- Seçilen kelimenin harf sayısı kadar dinamik çizgi/yıldız ekrana otomatik olarak dizilir.

\- Oyun başladığı an süre sayacı çalışmaya başlar ve geçen saniyeyi canlı gösterir.


🎨 Gelişmiş Tahmin Mekanizması Görseller

\- Oyuncu hem harf hem de kelime tahmini yapabilir. Tahminler hem kutuda `ENTER` tuşuna basarak hem de yanlarındaki \*\*"Tahmin Et"\*\* butonlarına tıklanarak iletilebilir.

\- Her yanlış tahminde can azalır ve darağacı resmi (`1.jpg`, `2.jpg`...) adım adım ekranda güncellenir. 11 hata yapan oyuncu oyunu kaybeder.


📊 Canlı Geçmiş ve Sistem Günlükleri

\- Oyunda sekmeler ("Oyun", "Geçmiş", "Log") arasında geçiş yapıldığı an, tablolar dosyalardan güncel verileri çekerek anında yenilenir.

\- Geçmişi veya Logları temizlemek isteyen kullanıcıdan güvenlik amacıyla tekrar ana şifre istenir. Şifre doğruysa dosyalar ve tablolar sıfırlanır.



\---



🛠️ Kullanılan Teknolojiler



\- Programlama Dili: Java (JDK)

\- Arayüz Tasarımı: Java Swing \& AWT

\- Proje Yönetimi: Maven Mimarisi

\- Geliştirme Ortamı: Apache NetBeans IDE



\---



📸 Ekran Görüntüleri











\---



👤 Geliştirici Bilgileri



\- Geliştirici: Tarık Başkan







