# Depo Yönetim Sistemi

Bu proje, bir depo yönetim sistemi uygulamasıdır. Bu uygulama, bir depo yönetim sistemi için temel işlevleri içerir. Bu uygulama, ürün ekleme, güncelleme ve silme işlemlerini gerçekleştirebilir. Uygulama, bir veritabanı kullanarak verileri saklar ve bu verilere erişim sağlar. Uygulama, bir web API olarak sunulur ve istemciler, bu API'yi kullanarak uygulamaya erişebilir.

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
- [ ] Ürünleri aktif/pasif duruma göre filtreleme
- [ ] Ürünler için kategori ekleme ve listeleme
- [ ] Ürünler için stok yönetimi
- [ ] Kullanıcı kimlik doğrulama ve yetkilendirme ekleme
- [ ] Ürün kategorileri ekleme ve yönetme
- [ ] Ürünler için fiyat geçmişi takibi ekleme
- [ ] Ürünler için resim yükleme ve yönetme
- [ ] Ürünler için detaylı arama ve filtreleme özellikleri ekleme
- [ ] Ürünler için toplu veri yükleme (CSV/Excel) desteği ekleme
- [ ] API dökümantasyonu için Swagger entegrasyonu
- [ ] Performans iyileştirmeleri ve sorgu optimizasyonları
- [ ] Ürünler için müşteri yorumları ve değerlendirmeleri ekleme
- [ ] Ürünler için kampanya ve indirim yönetimi ekleme
- [ ] Ürünler için bildirim ve uyarı sistemi ekleme (örneğin, stok azaldığında)
- [ ] Ürünler için raporlama ve analiz özellikleri ekleme
- [ ] Müşteri yönetimi ve müşteri bilgileri ekleme
- [ ] Müşteri sipariş geçmişi takibi
- [ ] Müşteri sadakat programı ve puan sistemi
- [ ] Müşteri geri bildirim
- [ ] Müşteri hedefleme ve analiz
- [ ] Müşteri için öneri ve kişiselleştirme özellikleri
- [ ] Ürün etiketleme ve kategorilendirme
- [ ] Ürünler için SEO ve arama motoru optimizasyonu
- [ ] Ürünler için sosyal medya entegrasyonu
- [ ] Ürünler için pazaryeri entegrasyonu
- [ ] Ürünler için hedef kitle analizi ve pazarlama stratejileri
- [ ] Ürünler için rakip analizi ve rekabet stratejileri
