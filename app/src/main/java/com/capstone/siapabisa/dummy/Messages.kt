package com.capstone.siapabisa.dummy

data class Messages(
    val imageUrl: String,
    val company: String,
    val messages: String,
    val date: String
)

val messagesList = listOf(
    Messages(
        "https://sttindonesia.ac.id/wp-content/uploads/2023/04/IMG_20230408_112106-1536x1152.jpg",
    "Flopcom",
    "Kami telah menerima lamaran anda",
    "2021-05-30"),
    Messages(
        "https://sttindonesia.ac.id/wp-content/uploads/2023/04/1681703972411-scaled.jpg",
        "Flopcom",
        "Kami telah menerima lamaran anda",
        "2021-05-30"),
    Messages(
        "https://sttindonesia.ac.id/wp-content/uploads/2023/05/IMG_20230506_214523-1536x2048.jpg",
        "PT. Datasoft Setiabudi",
        "Kami telah menerima lamaran anda",
        "2021-05-30"),

    )