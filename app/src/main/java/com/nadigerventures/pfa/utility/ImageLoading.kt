package com.nadigerventures.pfa.utility


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.FileProvider
import com.nadigerventures.pfa.R
import com.nadigerventures.pfa.base.getActivity
import java.io.File
import java.io.OutputStream


private val CAMERA_PERMISSION_CODE = 1
private val TAG = "ImageLoading"

private fun copyImage(sourceUri: Uri?, destinationUri: Uri?,context: Context) {
    if(sourceUri == null ||
        destinationUri == null) return
    val contentResolver = context.contentResolver
    val inputStream = contentResolver.openInputStream(sourceUri)
    val outputStream: OutputStream? = contentResolver.openOutputStream(destinationUri)
    Log.i(TAG,"copyImage = sourceuri =$sourceUri and destinationUri = $destinationUri" )
    val buffer = ByteArray(1024)
    var read = inputStream?.read(buffer)
    while (read != -1) {
        outputStream?.write(buffer, 0, read!!)
        read = inputStream?.read(buffer)
    }
    inputStream?.close()
    outputStream?.close()
}

class ComposeFileProvider : FileProvider(
    R.xml.filepaths
) {
    companion object {
        fun getImageUriForProfilePicture(context: Context): Uri {
            val directory = File(context.cacheDir, "images")
            directory.deleteRecursively()
            directory.mkdirs()
            var file = File.createTempFile(
                "Profile_image_",
                ".jpg",
                directory,
            )
           // Log.e(TAG,"File name = ${file}")
            val authority = context.packageName + ".fileprovider"
            return getUriForFile(
                context,
                authority,
                file,
            )
        }
    }
}

@Composable
fun ImagePicker(
    onImageUpdate: (Boolean,Uri?) -> Unit = { b: Boolean, uri: Uri? -> },
    modifier: Modifier = Modifier) {
    val context = LocalContext.current

    var hasImage by rememberSaveable {
        mutableStateOf(false)
    }

    var imageUri by rememberSaveable {
        mutableStateOf<Uri?>(null)
    }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            hasImage = uri != null
            imageUri = uri
            val outputUri = ComposeFileProvider.getImageUriForProfilePicture(context)
            Log.i(TAG,"imagePicker => imageUri  $imageUri and outputUri  $outputUri")
            if(imageUri!= null) {
                copyImage(imageUri!!,outputUri,context)
                onImageUpdate(hasImage,outputUri)
            }
        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            hasImage = success
            Log.i(TAG,"cameraLauncher -> success = $success")
            if(success) {
                onImageUpdate(hasImage,imageUri)
            }
        }
    )


    Box(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            // .padding(50.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    imagePicker.launch("image/*")
                },
            ) {
                Text(
                    text = "Select Image"
                )
            }
            Button(
               // modifier = Modifier.padding(top = 16.dp),
                onClick = {
                    if (checkSelfPermission(context,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(
                            context.getActivity()!!,
                            arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
                    } else {
                        val uri = ComposeFileProvider.getImageUriForProfilePicture(context)
                        imageUri = uri
                        cameraLauncher.launch(uri)
                        Log.i(TAG,"Image url from Camera= $imageUri")

                    }
                },
            ) {
                Text(
                    text = "Take photo"
                )
            }
        }
    }
}