import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ImagePickerViewModel:ViewModel() {

    private val _imageUri = MutableStateFlow<String?>(null)
    val imageUri: StateFlow<String?> get() = _imageUri.asStateFlow()

    fun setImageUri(uri: String?) {
        _imageUri.value = uri
    }

    private val _textInput = MutableStateFlow("")
    val textInput: StateFlow<String> = _textInput

    fun updateTextInput(newText: String) {
        _textInput.value = newText
    }

//private val _imageUris = MutableStateFlow<List<String?>>(List(10) { null }) // Initialize with nulls
//    val imageUris: StateFlow<List<String?>> = _imageUris
//
//    fun setImageUri(uri: String?, index: Int) {
//        _imageUris.value = _imageUris.value.toMutableList().apply {
//            this[index] = uri
//        }
//    }
}