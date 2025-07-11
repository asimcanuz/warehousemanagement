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
- **AWS S3**: Ürün resimlerini depolamak için AWS S3 entegrasyonu bulunmaktadır.
- 
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


## Eklenecek Özellikler

### FAZ-1 
- [x] Ürünleri aktif/pasif duruma göre filtreleme
- [x] API dökümantasyonu için Swagger entegrasyonu
- [x] Ürünler için kategori ekleme ve listeleme
- [x] Ürünler için stok yönetimi
- [x] Ürün kategorileri ekleme ve yönetme
- [ ] Ürünler için resim yükleme ve yönetme (in progress)
- [ ] logging ve hata yönetimi
- [X] Ürünler için detaylı arama ve filtreleme özellikleri ekleme
- [ ] Müşteri entegrasyonu
- [ ] Kullanıcı kimlik doğrulama ve yetkilendirme ekleme
- [ ] Ürünler için kampanya ve indirim yönetimi ekleme
- [ ] Müşteri sipariş geçmişi takibi 
- [ ] Ürünler için envanter yönetimi ve takip sistemi ekleme
- [ ] Ürün etiketleme ve kategorilendirme
- [ ] Performans iyileştirmeleri ve sorgu optimizasyonları

### FAZ-2

- [ ] Çoklu dil desteği ekleme
- [ ] Çoklu para birimi desteği ekleme
- [ ] Çoklu fiyatlandırma stratejileri ekleme
- [ ] Farkli depo desteği ekleme
- [ ] Fiyat geçmişi takibi ekleme 
- [ ] Toplu veri yükleme (CSV/Excel) desteği ekleme
- [ ] Raporlama ve analiz özellikleri ekleme
- [ ] Müşteri hedefleme ve analiz
- [ ] Hedef kitle analizi ve pazarlama stratejileri Veri?
- [ ] Rakip analizi ve rekabet stratejileri Veri?
### FAZ-3 
- [ ] Müşteri sadakat programı ve puan sistemi
- [ ] Bildirimler ve uyarılar için e-posta ve SMS entegrasyonu
- [ ] Bildirimler ve uyarılar için konfigürasyon paneli
- [ ] Bildirimler ve uyarılar için kullanıcı tercihleri
  Entegrasyonlar ve API'ler...
- [ ] E-ticaret entegrasyonu (örneğin, Shopify, WooCommerce, Magento)
- [ ] Pazaryeri entegrasyonu (örneğin, Amazon, Trendyol, Hepsiburada)
- [ ] Sosyal medya entegrasyonu (örneğin, Instagram, Facebook, Twitter)
- [ ] entegrasyonlardan müşteri geri bildirimleri ve yorumları toplama
- [ ] Bildirim ve uyarı sistemi ekleme (örneğin, stok azaldığında, müşteri yorumu gelince, Pazaryerinden sipariş geldiğinde, ... vb.)
