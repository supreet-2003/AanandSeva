import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class ImagePickerViewModel():ViewModel() {

    private val _imageUri = MutableStateFlow<String?>(null)
    val imageUri: StateFlow<String?> get() = _imageUri.asStateFlow()
    private val _shouldDisplayImage = MutableStateFlow(false)
    val shouldDisplayImage: StateFlow<Boolean> = _shouldDisplayImage
    private val _isImagePicked = MutableStateFlow(false)

    private val _base64Image = MutableStateFlow<String?>(null)
        val base64Image: StateFlow<String?> = _base64Image.asStateFlow()

    val isImagePicked: StateFlow<Boolean> = _isImagePicked

//    @OptIn(ExperimentalEncodingApi::class)
fun setImageBase64(base64: String) {
    _base64Image.value = base64
}
    fun onImagePicked() {
                _isImagePicked.value = true

    }
    fun resetImagePicked() {
        _isImagePicked.value = false
    }


    fun setImageUri(uri: String?) {
        _imageUri.value = uri
    }


    private val _textInput = MutableStateFlow("")
    val textInput: StateFlow<String> = _textInput

    fun updateTextInput(newText: String) {
        _textInput.value = newText
    }



    fun setShouldDisplayImage(display: Boolean) {
        _shouldDisplayImage.value = display
    }


    private val _selectedStatus = MutableStateFlow("Status")
    val selectedStatus: StateFlow<String> = _selectedStatus
    fun updateStatus(newStatus: String) {
        _selectedStatus.value = newStatus
    }

}