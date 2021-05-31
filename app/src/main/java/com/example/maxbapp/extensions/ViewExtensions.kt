import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast

fun Context.showToast(msg: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, length).show()
}

fun Context.showLog(tag: String, msg: String) {
    Log.e(tag, msg)
}

fun Context.openDialer(telephone: String? = "") {
    val phone: Uri = Uri.parse("tel:$telephone")
    val intent = Intent(Intent.ACTION_DIAL, phone)
    try {
        this.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Log.e("tag","dialer can't open tel: $phone")
    }
}