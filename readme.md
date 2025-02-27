# Depo Yönetim Sistemi

Bu proje, bir depo yönetim sistemi uygulamasıdır. Bu uygulama, bir depo yönetim sistemi için temel işlevleri içerir.

## Proje Yapısı

- **Java 21**: Uygulama Java 21 ile geliştirilmiştir.
- **Spring Boot**: Uygulama Spring Boot çerçevesi kullanılarak oluşturulmuştur.
- **Maven**: Proje bağımlılık yönetimi için Maven kullanmaktadır.
- **JPA (Java Persistence API)**: Veritabanı işlemleri için JPA kullanılmıştır.
- **Docker**: Uygulama Docker kullanılarak konteynerize edilmiştir.
- **PostgreSQL**: Veritabanı olarak PostgreSQL kullanılmıştır.
- **Docker Compose**: Uygulama ve veritabanı Docker Compose ile ayağa kaldırılmıştır.
- **Lombok**: Lombok kütüphanesi kullanılarak getter, setter ve constructor gibi metotlar otomatik olarak oluşturulmuştır.

## Kurulum ve Çalıştırma

### Gereksinimler

- Java 21
- Maven
- Docker

### Adımlar

1. Projeyi klonlayın:
    ```sh
    git clone https://github.com/asimcanuz/warehousemanagement.git
    cd warehousemanagement
    ```

2. Maven bağımlılıklarını yükleyin ve projeyi derleyin:
    ```sh
    mvn clean install
    ```

3. Docker imajını oluşturun ve çalıştırın:
    ```sh
    docker-compose up --build
    ```

4. Uygulama artık `http://localhost:8080` adresinde çalışmaktadır.

# To-Do List

## Mevcut Yapılacaklar
- [x] Proje için gerekli konfigurasyonlar ve dosyaların oluşturulması
- [x] Docker Compose ile projeyi ayağa kaldırma
- [x] Projeyi Docker ile konteynerize etme
-
- [x] Ürün ekleme, güncelleme ve silme işlemlerini gerçekleştirme

## Eklenecek Özellikler
### FAZ-1 (15.03.2025)
- [x] Ürünleri aktif/pasif duruma göre filtreleme
- [x] API dökümantasyonu için Swagger entegrasyonu
- [x] Ürünler için kategori ekleme ve listeleme
- [x] Ürünler için stok yönetimi
- [x] Ürün kategorileri ekleme ve yönetme
- [ ] Ürünler için fiyat geçmişi takibi ekleme
- [ ] Ürünler için resim yükleme ve yönetme
- [ ] Ürünler için detaylı arama ve filtreleme özellikleri ekleme
- [ ] Kullanıcı kimlik doğrulama ve yetkilendirme ekleme
- [ ] Ürünler için kampanya ve indirim yönetimi ekleme
- [ ] Ürünler için müşteri yorumları ve değerlendirmeleri ekleme
- [ ] Ürünler için bildirim ve uyarı sistemi ekleme (örneğin, stok azaldığında)
- [ ] Müşteri yönetimi ve müşteri bilgileri ekleme
- [ ] Müşteri sipariş geçmişi takibi
- [ ] Ürün etiketleme ve kategorilendirme
- [ ] Performans iyileştirmeleri ve sorgu optimizasyonları


### FAZ-2
- [ ] Ürünler için toplu veri yükleme (CSV/Excel) desteği ekleme
- [ ] Ürünler için raporlama ve analiz özellikleri ekleme
- [ ] Müşteri sadakat programı ve puan sistemi
- [ ] Müşteri geri bildirim
- [ ] Müşteri hedefleme ve analiz
- [ ] Müşteri için öneri ve kişiselleştirme özellikleri
- [ ] Ürünler için SEO ve arama motoru optimizasyonu
- [ ] Ürünler için sosyal medya entegrasyonu
- [ ] Ürünler için pazaryeri entegrasyonu
- [ ] Ürünler için hedef kitle analizi ve pazarlama stratejileri
- [ ] Ürünler için rakip analizi ve rekabet stratejileri
