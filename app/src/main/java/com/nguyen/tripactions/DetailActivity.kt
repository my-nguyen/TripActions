package com.nguyen.tripactions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.nguyen.tripactions.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ARTICLE_OBJECT = "ARTICLE_OBJECT"

        fun newIntent(context: Context, article: Article) : Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(EXTRA_ARTICLE_OBJECT, article)
            return intent
        }
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var article: Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        article = intent.getSerializableExtra(EXTRA_ARTICLE_OBJECT) as Article
        // set up to open WebView and not a browser
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        binding.webView.loadUrl(article.webUrl)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_share, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            /*R.id.action_share_to_facebook -> {
//                val content = ShareLinkContent.Builder()
//                    .setContentUrl(Uri.parse("https://developers.facebook.com"))
//                    .build()

//                FacebookSdk.sdkInitialize(applicationContext)
//                val shareDialog = ShareDialog(this)
//                val linkContent = ShareLinkContent.Builder()
//                    .setContentTitle("Title")
//                    .setContentDescription("Body Of Test Post")
//                    .setContentUrl(Uri.parse("http://someurl.com/here"))
//                    .build()
//                shareDialog.show(linkContent)

//                val callbackManager = CallbackManager.Factory.create();
//                val shareDialog = ShareDialog(this)
//                if (ShareDialog.canShow(ShareLinkContent::class.java)) {
//                    val linkContent = ShareLinkContent.Builder()
//                        .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
//                        .build()
//                    shareDialog.show(linkContent)
//                }

                true
            }*/
            R.id.action_send_email -> {
                composeEmail(arrayOf("nguyen_my@yahoo.com"), article.title, article.webUrl)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun composeEmail(addresses: Array<String>, subject: String, url: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, url)
        }

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}
