package com.capstone.siapabisa.util

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import com.capstone.siapabisa.data.remote.model.BiodataEdit
import com.capstone.siapabisa.data.remote.model.BiodataSubmit
import com.capstone.siapabisa.data.remote.model.Birthday
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

private const val DATETIME_FORMAT = "dd-MMM-yyyy"

val timeStamp: String = SimpleDateFormat(
        DATETIME_FORMAT,
        Locale.US
    ).format(System.currentTimeMillis())

class BiodataSubmitSerializer : JsonSerializer<BiodataSubmit> {
    override fun serialize(
        src: BiodataSubmit?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        val jsonObject = JsonObject()
        val dataObject = JsonObject()

        dataObject.addProperty("nama", src?.data?.nama)
        dataObject.add("birthday", context?.serialize(src?.data?.birthday))
        dataObject.addProperty("alamat", src?.data?.alamat)
        dataObject.addProperty("deskripsiDiri", src?.data?.deskripsiDiri)
        dataObject.addProperty("pendidikan", src?.data?.pendidikan)
        dataObject.addProperty("pengalaman", src?.data?.pengalaman)
        dataObject.addProperty("keterampilan", src?.data?.keterampilan)
        dataObject.addProperty("peminatan", src?.data?.peminatan)

        jsonObject.addProperty("userId", src?.userId)
        jsonObject.add("data", dataObject)

        return jsonObject
    }
}

class BiodataEditSerializer : JsonSerializer<BiodataEdit> {
    override fun serialize(
        src: BiodataEdit?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonObject {
        val jsonObject = JsonObject()
        jsonObject.addProperty("nama", src?.data?.nama)
        jsonObject.add("birthday", context?.serialize(src?.data?.birthday))
        jsonObject.addProperty("alamat", src?.alamat)
        jsonObject.addProperty("deskripsiDiri", src?.deskripsiDiri)
        jsonObject.addProperty("pendidikan", src?.pendidikan)
        jsonObject.addProperty("pengalaman", src?.pengalaman)
        jsonObject.addProperty("keterampilan", src?.keterampilan)
        jsonObject.addProperty("peminatan", src?.peminatan)
        return jsonObject
    }
}

class BirthdaySerializer : JsonSerializer<Birthday> {
    override fun serialize(
        src: Birthday?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        val jsonObject = JsonObject()
        jsonObject.addProperty("day", src?.day)
        jsonObject.addProperty("month", src?.month)
        jsonObject.addProperty("year", src?.year)
        return jsonObject
    }
}



    fun checkUsername(username: String): Boolean {
        return username.length >= 1
    }

    fun checkEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun checkPassword(password: String): Boolean {
        return password.length >= 8
    }

fun createCustomTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

fun formatDate(dateString: String): String {
    val input = ZonedDateTime.parse(dateString)
    val dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
    val output = dateTimeFormatter.format(input)
    return output
}

fun uriToFile(selectedImg: Uri, context: Context): File {
    val contentResolver: ContentResolver = context.contentResolver
    val myFile = createCustomTempFile(context)

    val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
    val outputStream: OutputStream = FileOutputStream(myFile)
    val buf = ByteArray(1024)
    var len: Int
    while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
    outputStream.close()
    inputStream.close()

    return myFile
}

fun reduceFileImage(file: File): File {
    val bitmap = BitmapFactory.decodeFile(file.path)

    var compressQuality = 100
    var streamLength: Int

    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -= 5
    } while (streamLength > 1000000)

    bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))

    return file
}