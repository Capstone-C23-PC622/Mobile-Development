package com.capstone.siapabisa.ui.custom

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatEditText
import com.capstone.siapabisa.ui.user.DetailLokerActivity
import com.capstone.siapabisa.ui.user.SearchActivity

class RedirectEditText : AppCompatEditText {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                (event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)
            ) {
                val intent = Intent(context, SearchActivity::class.java)
                intent.putExtra(SearchActivity.EXTRA_KEYWORD, text.toString())
                context.startActivity(intent)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }
}