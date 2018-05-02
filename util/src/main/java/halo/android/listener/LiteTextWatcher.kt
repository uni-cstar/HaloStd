package halo.android.listener

import android.text.Editable
import android.text.TextWatcher

/**
 * Created by Lucio on 17/11/23.
 */

abstract class LiteTextWatcher : TextWatcher {

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }
}