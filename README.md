# LP7DPBO2024C1
# Janji
Saya Mohammad Raihan Aulia Kamil (2205449) mengerjakan soal Latihan Praktikum 7 C1 dalam mata kuliah Desain Pemrograman Berorientasi Objek untuk keberkahan-Nya maka saya tidak melakukan kecurangan. Aamiin.

# Desain Program
1. Kelas FlappyBird:  
   Kelas utama yang mewarisi JPanel dan mengimplementasikan ActionListener dan KeyListener. Ini adalah tempat utama di mana permainan dan logika permainan dikelola. Kelas ini mengendalikan penggambaran elemen-elemen permainan dan interaksi dengan pemain melalui keyboard.
2. Kelas Player:  
   Mewakili objek pemain dalam permainan. Memiliki atribut seperti posisi, lebar, tinggi, dan gambar pemain. Kelas ini juga mengatur perilaku pemain seperti pergerakan vertikal.
3. Kelas Pipe:  
   Mewakili pipa dalam permainan. Memiliki atribut seperti posisi, lebar, tinggi, dan gambar pipa. Kelas ini mengatur perilaku pipa seperti pergerakan horizontal dan deteksi tabrakan dengan pemain.
4. Kelas App:  
   Kelas yang berfungsi sebagai titik masuk program. Ini membuat dan menampilkan JFrame untuk permainan Flappy Bird.

# Alur Program
1. Permainan Dimulai:  
   Ketika tombol "Start Game" ditekan, MainForm ditutup dan JFrame permainan Flappy Bird (FlappyBird) ditampilkan. Permainan dimulai, dan pemain harus mengendalikan burung untuk melewati pipa dengan menekan tombol spasi untuk melompat.
2. Pergerakan dan Kollision Detection:  
   Burung bergerak secara otomatis ke bawah karena gravitasi. Pemain dapat mengendalikan burung dengan menekan tombol spasi untuk melompat. Ketika burung menyentuh pipa atau batas atas atau bawah layar, permainan berakhir.
4. Penambahan Skor:  
   Setiap kali burung berhasil melewati pipa tanpa menabrak, skor bertambah satu. Skor ditampilkan di panel skor.
6. Game Over:  
   Ketika permainan berakhir karena burung menabrak pipa atau batas atas atau bawah layar, teks "Game Over" ditampilkan di layar bersama dengan instruksi untuk menekan tombol "r" untuk mencoba lagi.
8. Restart Game:  
   Jika pemain menekan tombol "r", permainan diulang dari awal, skor direset, dan burung kembali ke posisi awalnya.

# Dokumentasi

