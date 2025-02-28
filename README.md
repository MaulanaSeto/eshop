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

# Modul 3
### Refleksi
1. Penerapan Prinsip SOLID
   - Single Responsibility Principle<br>
     Kelas `CarController` dipisah dari berkas `ProductController.java` agar berkas tersebut hanya memiliki satu tugas, yaitu menangani permintaan dan repsons HTTP untuk fungsionalitas Produk.
   - Open-Closed Principle<br>
     Kode ini telah menerapkan OCP, karena tidak ada perubahan kode yang sudah ada jika akan menambah fitur atau fungsionalitas baru.
   - Liskov Substitution Principle<br>
     Prinsip ini belum bisa diter
   - Interface Segregation Principle<br>
     Pembuatan dua antarmuka `ProductService` dan `CarService` menerapkan prinsip ini, karena kelas yang mengimplementasinya hanya perlu menggunakan metode yang relevan dengan fungsinya tanpa harus mengimplementasi metode yang tidak dibutuhkan.
   - Dependency Inversion Principle<br>
     Mengganti atribut kelas `CarController` dari bertipe data `CarServiceImpl` menjadi `CarService` agar kelas tersebut bergantung pada antarmuka yang lebih spesifik dan sesuai kebutuhan daripada implementasi konkret.
2. Keuntungan Menggunakan Prinsip Solid
   - Pemeliharaan<br>
     Kode pada proyek ini terdiri dari beberapa modul dengan fungsinya masing-masing. Pemisahan modul membuat proses *debugging* kode menjadi lebih mudah.
   - Keterbacaan<br>
     Prinsip SOLID meningkatkan keterbacaan dan pemahaman kode, sehingga lebih mudah bagi pengembang lain untuk memahami kode.
   - Fleksibilitas dan Skalabilitas<br>
     Kode lebih mudah diubah dan ditingkatkan seiring waktu tanpa mengganggu struktur kode yang sudah ada.
3. Kerugian Tidak Menggunakan Prinsip Solid
   - Pemeliharaan<br>
     Kode cenderung memiliki keterkaitan antar komponen yang tinggi, sehingga setiap perubahan kecil dapat menyebabkan masalah di banyak bagian kode lainnya. Hal ini membuat proses *debugging* menjadi lebih sulit.
   - Keterbacaan<br>
     Kode berpotensi menjadi tidak terstruktur dan sulit dipahami oleh pengembang lain. Hal ini dapat menyulitkan tim dalam menelusuri alur program dan meningkatkan risiko kesalahan dalam pengembangan.
   - Fleksibilitas dan Skalabilitas<br>
     Kode yang tidak dirancang dengan baik akan sulit untuk dikembangkan lebih lanjut. Perubahan atau penambahan fitur baru bisa menyebabkan banyak perubahan pada kode yang sudah ada, meningkatkan risiko *bug* dan menghambat skalabilitas proyek.     
