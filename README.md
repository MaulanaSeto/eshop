# Modul 1
### Refleksi 1
Saya telah memahami *clean code* dengan lebih baik setelah mengerjakan Latihan 1. Saya menggunakan nama yang representatif untuk setiap variabel. Fungsi yang saya buat memiliki cakupan kerja yang cukup kecil. Saya tidak memberi komentar karena nama variabel dan fungsi yang representatif sudah cukup memberi kejelasan tentang kode.

### Refleksi 2
Pengerjaan Latihan 2 menyadarkan saya akan perlunya pembuatan tes dalam menguji fungsionalitas kode. Tes perlu dibuat minimal satu untuk setiap kasus. Mempunyai cakupan tes 100% tidak menjamin tidak adanya bug pada kode karena tes yang dibuat mungkin tidak mencakup kasus-kasus potensi bug lain.

Setelah saya membuat functional test untuk membuat produk saya rasa saya akan tetap menggunakan cara yang saya gunakan sekarang untuk membuat tes fungsional untuk menghitung jumlah produk pada daftar produk karena saya rasa kode yang saya sekarang sudah cukup efisien dan bisa bekerja dengan baik.

# Modul 2
### Refleksi
1. Terdapat beberapa isu kualitas kode menurut PMD atau SonaqQube
   - Deklarasi Variabel dalam Satu Baris<br>
     Deklarasi variabel `productId` dan `productName` pada `Product.java` berakibat adanya isu pemeliharaan. Variabel tersebut diubah menjadi berada dalam dua baris terpisah.
   - *Jump* Redundan<br>
     Terdapat kode `return` yang tidak berguna pada berkas `CreateProduct.html` karena tidak ada kode lain setelahnya. Kode tersebut telah dihapus.
   - Metode Kosong<br>
     Terdapat metode kosong bernama `setUp` pada berkas `EshopApplicationTests.java` dan `ProductRepositoryTest.java`. Metode tersebut menimbulkan isu pemeliharaan karena tidak ada komentar yang berisi alasan kosongnya isi metode tersebut. Karena metode tersebut tidak mberi efek apa pun, maka kode tersebut dihapus.
   - Variabel `ID`<br>
     Mengubah nama variabel `ID` menjadi `Id` agar sesuai dengan aturan penamaan variabel di Java.
2. Implementasi *workflow* pada modul kali ini telah memenuhi definisi CI/CD. Hal ini terlihat dari proses GitHub Actions. Setiap terdapat *push* ke repositori GitHub, kode akan dites melalui *workflow* yang ada. Kode ini juga telah mengaplikasi *workflow* Sonarcloud dan PMD yang memeriksa kebersihan kode. Kode ini telah menerapkan Continuous Deployment yang bekerja dengan cara melakukan *merge* ke *branch* *main* lalu akan secara otomatis dilakukan *deployment* ke platform Koyeb. Aplikasi ini dapat diakses melalui tautan https://malset-eshop.koyeb.app/.
