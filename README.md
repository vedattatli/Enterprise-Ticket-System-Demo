# Enterprise Ticket System — Demo

Kurumsal destek talebi (ticket) yönetimi için full-stack demo uygulaması. JWT tabanlı kimlik doğrulama, rol bazlı yetkilendirme ve talep yaşam döngüsü yönetimi içerir.

## Ne yapıyor

Kullanıcılar destek talebi açar, durumunu izler; yöneticiler talepleri listeler, personele atar, durum günceller ve özet istatistikleri görür.

## Mimari

**Backend** — Java 21, Spring Boot, REST API
- JWT ile giriş (JJWT), rol bazlı yetki: `ADMIN` / `USER`
- Ticket açma, görüntüleme, listeleme, durum güncelleme, atama
- Yönetici istatistikleri
- PostgreSQL + JPA / Hibernate
- Swagger UI ile API dokümantasyonu
- Açılışta `DataSeeder` demo rolleri, kullanıcıları ve örnek talebi oluşturur

**Frontend** — React 19 + Vite
- Giriş ve kontrol paneli sayfaları
- axios interceptor ile Bearer token yönetimi

## Çalıştırma

**1. Veritabanı**
```bash
docker compose up -d
```

**2. Backend**

```bash
./mvnw spring-boot:run
```

API `http://localhost:8080` adresinde. Swagger arayüzü: `/swagger-ui.html`

**3. Frontend**

```bash
cd ticket-frontend
npm install
npm run dev
```

Arayüz `http://localhost:5173` adresinde. CORS yalnızca bu adrese izin verir.

## Test

Spring Boot bağlam testi mevcut (Testcontainers kullanır, Docker gerektirir).

## ⚠️ Güvenlik notu

Bu bir **demo** uygulamasıdır. `application.properties` içindeki JWT imza anahtarı ve veritabanı kimlik bilgileri örnek değerlerdir ve depoda açık olarak durur.

Gerçek bir ortamda kullanılacaksa:
- JWT imza anahtarını ortam değişkenine taşıyın ve yenisiyle değiştirin
- Veritabanı kimlik bilgilerini ortam değişkeninden okuyun
- `DataSeeder` ile oluşturulan demo parolaları kaldırın

## Ölçek

Yaklaşık 1.560 satır — 914 Java, 266 JSX/JS, kalanı yapılandırma ve stil.
