//package com.example.easyreadingapp.domain.dtos
//
//import android.content.ContentUris
//import android.content.Context
//import android.graphics.Bitmap
//import android.graphics.pdf.PdfRenderer
//import android.net.Uri
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//
//class PdfBitMapConverter(
//    private val context: Context
//) {
//    var renderer: PdfRenderer? = null
//    suspend fun pdfToBitmaps(contentUri: Uri): List<Bitmap>{
//        return withContext(Dispatchers.IO){
//            renderer?.close()
//
//            context
//                .contentResolver
//                .openFileDescriptor(contentUri, "r")
//                ?.use { descriptor ->
//                    with(PdfRenderer(descriptor)){
//                        renderer = this
//
//                        return@withContext (0 <= until < pageCount).map { index ->
//                            openPage(index).use { page ->
//                                val bitmap = Bitmap.createBitmap(
//                                    page.width,
//                                    page.height,
//                                    Bitmap.Config.ARGB_8888
//                                )
//                                val canvas =
//                            }
//                        }
//                    }
//                }
//        }
//    }
//}