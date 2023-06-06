package com.capstone.siapabisa.dummy

data class Jobs(
    val namaPerusahaan: String,
    val jenisLowongan: String,
    val pendidikan: String,
    val pengalaman: String,
    val deskripsi: String,
    val lokasi: String,
    val imageUrl: String,
    val datePosted: String,
)

val jobList = listOf(
    Jobs(
        "PT DataSoft Setiabudi",
        "Software Engineer",
        "Sarjana Teknik Informatika",
        "Minimal 2 tahun",
        "Kami mencari Software Engineer yang berpengalaman untuk bergabung dalam tim pengembangan kami.",
        "Jakarta",
        "https://sttindonesia.ac.id/wp-content/uploads/2023/04/IMG_20230408_112106-1536x1152.jpg",
        "2023-05-30"
    ),
    Jobs(
        "Flopcom",
        "Data Analyst",
        "Sarjana Statistika",
        "Tidak diwajibkan",
        "Kami membutuhkan Data Analyst yang memiliki pemahaman mendalam tentang analisis data.",
        "Surabaya",
        "https://sttindonesia.ac.id/wp-content/uploads/2023/04/1681703972411-scaled.jpg",
        "2023-05-28"
    ),
    Jobs(
        "PT. Karya Anak Bangsa",
        "UI/UX Designer",
        "Sarjana Desain Grafis",
        "Minimal 3 tahun",
        "Kami mencari UI/UX Designer yang kreatif dan berpengalaman dalam merancang antarmuka pengguna yang intuitif.",
        "Bandung",
        "https://sttindonesia.ac.id/wp-content/uploads/2023/05/IMG_20230506_214523-1536x2048.jpg",
        "2023-05-25"
    ),
    Jobs(
        "Company X",
        "Product Manager",
        "Sarjana Manajemen Bisnis",
        "Minimal 5 tahun",
        "We are seeking a skilled Product Manager to lead our product development team and drive product strategy.",
        "Yogyakarta",
        "https://sttindonesia.ac.id/wp-content/uploads/2023/05/IMG_20230506_214523-1536x2048.jpg",
        "2023-05-20"
    ),
    Jobs(
        "Company Y",
        "Marketing Specialist",
        "Sarjana Komunikasi",
        "Minimal 2 tahun",
        "We are looking for a Marketing Specialist to develop and implement marketing strategies to promote our products.",
        "Surabaya",
        "https://sttindonesia.ac.id/wp-content/uploads/2023/05/IMG_20230506_214523-1536x2048.jpg",
        "2023-05-18"
    ),
    Jobs(
        "Company Z",
        "Sales Executive",
        "Diploma Manajemen Penjualan",
        "Minimal 1 tahun",
        "We are hiring a Sales Executive to drive sales and build relationships with new and existing clients.",
        "Jakarta",
        "https://sttindonesia.ac.id/wp-content/uploads/2023/05/IMG_20230506_214523-1536x2048.jpg",
        "2023-05-15"
    ),
    Jobs(
        "Company Z",
        "Sales Executive",
        "Diploma Manajemen Penjualan",
        "Minimal 1 tahun",
        "We are hiring a Sales Executive to drive sales and build relationships with new and existing clients.",
        "Jakarta",
        "https://sttindonesia.ac.id/wp-content/uploads/2023/05/IMG_20230506_214523-1536x2048.jpg",
        "2023-05-15"
    ),
    Jobs(
        "Company Z",
        "Sales Executive",
        "Diploma Manajemen Penjualan",
        "Minimal 1 tahun",
        "We are hiring a Sales Executive to drive sales and build relationships with new and existing clients.",
        "Jakarta",
        "https://sttindonesia.ac.id/wp-content/uploads/2023/05/IMG_20230506_214523-1536x2048.jpg",
        "2023-05-15"
    )
)
