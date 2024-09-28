import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.http.FileContent
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.google.api.services.drive.model.File
import com.google.api.services.drive.model.Permission
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.FileOutputStream
import java.io.File as files


fun getGoogleDriveService(): Drive {
    val resourceAsStream = Thread.currentThread().contextClassLoader.getResourceAsStream("aanandseva-ccc66d6e586e.json")

    // Ensure the stream is not null
    requireNotNull(resourceAsStream) { "Resource file not found: aanandseva-ccc66d6e586e.json" }

    val credential: Credential = GoogleCredential.fromStream(resourceAsStream)
        .createScoped(listOf(DriveScopes.DRIVE_FILE))

    return Drive.Builder(
        com.google.api.client.http.javanet.NetHttpTransport(),
        GsonFactory.getDefaultInstance(),
        credential
    ).setApplicationName("Drive API Kotlin").build()
}
suspend fun makeFilePublic(service: Drive, fileId: String) {
    withContext(Dispatchers.IO) {
        try {
            val permission = Permission().apply {
                type = "anyone"  // Anyone with the link can access
                role = "reader"  // Can only view the file
            }
            service.permissions().create(fileId, permission)
                .setFields("id")
                .execute()

            println("File permissions updated successfully!")
        } catch (e: Exception) {
            println("Error updating file permissions: ${e.message}")
        }
    }
}

suspend fun uploadFileToGoogleDrive(service: Drive, filePath: String?, folderId: String? = null): File? {
    loading.value = true
    return withContext(Dispatchers.IO) {
        try {
            // Check if file exists
            val file = java.io.File(filePath)
            println("File exists: ${file.exists()}, Path: $filePath")

            if (!file.exists()) {
                println("File not found at path: $filePath")
                return@withContext null
            }

            // Create file metadata
            val fileMetadata = File().apply {
                name = file.name
                parents = folderId?.let { listOf(it) }
            }

            println("Uploading file with name: ${fileMetadata.name}")

            // Create file content (MIME type can be adjusted based on file type)
            val mediaContent = FileContent("image/jpeg", file)
            println("Media content created with file: ${file.absolutePath}")

            // Extra logging for the file
            println("File content type: ${mediaContent.type}")
            println("File size: ${file.length()} bytes")

            // Upload the file to Google Drive
            val uploadedFile = service.files().create(fileMetadata, mediaContent)
                .setFields("id, webViewLink, webContentLink")
                .execute()

            println("File uploaded successfully: ID = ${uploadedFile.id}")
            println("Web View Link: ${uploadedFile.webViewLink}")
            uploadedFile
        } catch (e: com.google.api.client.googleapis.json.GoogleJsonResponseException) {
            println("Google API error: ${e.statusCode} - ${e.details}")
            null
        } catch (e: Exception) {
            println("General error during file upload: ${e.message}")
            e.printStackTrace()
            null
        }
    }
}

suspend fun downloadImageToCache(driveService: Drive, fileId: String, cacheDir: String): java.io.File? {
    val file = java.io.File(cacheDir, "$fileId.jpg")  // Create file in cache directory

    if (file.exists()) {
        // File already in cache
        return file
    } else {
        // Download from Google Drive
        withContext(Dispatchers.IO) {
            val outputStream = FileOutputStream(file)
            driveService.files().get(fileId)
                .executeMediaAndDownloadTo(outputStream)
            outputStream.close()
        }
        return if (file.exists()) file else null
    }
}

fun isImageInCache(fileName: String, cacheDir: String): Boolean {
    val file = java.io.File(cacheDir, fileName)
    return file.exists()
}

suspend fun processOrders(orders: List<Order>?, driveService: Drive) {
    loading.value = true
    val cacheDir = getCacheDirectory()

    if (orders != null) {
        for (order in orders) {
            val fileName = order.file.fileName
            val fileId = extractFileIdFromUrl(order.file.url)
            val check = isImageInCache("$fileId.jpg", cacheDir)
            // Check if image is already in cache
            if (!check) {
                // If not, download the image from Google Drive
                val downloadedFile = downloadImageToCache(driveService, fileId, cacheDir)
                if (downloadedFile != null) {
                    println("Image downloaded and saved to cache: ${downloadedFile.absolutePath}")
                    order.imageStorageLink = downloadedFile.absolutePath
                } else {
                    println("Failed to download image for order: ${order._id}")
                }
            } else {
                println("Image already exists in cache: $fileName")
                order.imageStorageLink = "$cacheDir/$fileId.jpg"
            }
        }
    }
}

suspend fun fetchDoctorImages(doctors: List<Doctor>?, driveService: Drive) {
    loading.value = true
    val cacheDir = getCacheDirectory()

    if (doctors != null) {
        for (doctor in doctors) {
            val fileName = doctor.name
            if(doctor.photo != null){
                val fileId = extractFileIdFromUrl(doctor.photo)
                val check = isImageInCache("$fileId.jpg", cacheDir)
                // Check if image is already in cache
                if (!check) {
                    // If not, download the image from Google Drive
                    val downloadedFile = downloadImageToCache(driveService, fileId, cacheDir)
                    if (downloadedFile != null) {
                        println("Doctor Image downloaded and saved to cache: ${downloadedFile.absolutePath}")
                        doctor.photoStorageLink = downloadedFile.absolutePath
                    } else {
                        println("Failed to download image for doctor: ${doctor.name}")
                    }
                } else {
                    println("Doctor Image already exists in cache: $fileName")
                    doctor.photoStorageLink = "$cacheDir/$fileId.jpg"
                }
            }
        }
    }
}

// Utility function to get cache directory
fun getCacheDirectory(): String {
    return System.getProperty("java.io.tmpdir") ?: "/tmp"
}

// Utility function to extract file ID from Google Drive URL
fun extractFileIdFromUrl(url: String): String {
    val regex = "https://drive.google.com/file/d/([a-zA-Z0-9-_]+)".toRegex()
    val matchResult = regex.find(url)
    return matchResult?.groupValues?.get(1) ?: error("Invalid Google Drive URL")
}

