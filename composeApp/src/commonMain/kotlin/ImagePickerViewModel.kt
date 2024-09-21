import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
data class ImageData(
    val imagePath: String?,  // Stores the file path of the picked image
    val imageDescription: String = ""
)

class ImagePickerViewModel : ViewModel() {
    // StateFlow to observe if an image has been picked
    private val _isImagePicked = MutableStateFlow(false)
    val isImagePicked: StateFlow<Boolean> get() = _isImagePicked

    // StateFlow to hold the image data (platform-agnostic)
    private val _imageData = MutableStateFlow<ImageData?>(null)
    val imageData: StateFlow<ImageData?> get() = _imageData

    // StateFlow to hold the user input text for description
    private val _textInput = MutableStateFlow("")
    val textInput: StateFlow<String> get() = _textInput

    // Function to update the image picked status and image data
    fun setImagePicked(isPicked: Boolean, imagePath: String?) {
        _isImagePicked.value = isPicked
        _imageData.value = ImageData(imagePath = imagePath)  // imagePath is set here
    }

    // Function to update the text input state
    fun updateTextInput(newText: String) {
        _textInput.value = newText
    }

    fun resetImagePicked() {
        _isImagePicked.value = false;
        _imageData.value = null;
    }
}
