package io.spacecowboyapps.comicly.ui.comic

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import io.spacecowboyapps.comicly.R
import io.spacecowboyapps.comicly.commons.Status
import io.spacecowboyapps.comicly.data.db.Comic
import io.spacecowboyapps.comicly.di.component.ActivityComponent
import io.spacecowboyapps.comicly.ui.commons.BaseActivity
import io.spacecowboyapps.comicly.ui.commons.setVisible
import kotlinx.android.synthetic.main.activity_comics.*
import javax.inject.Inject

class ComicsActivity : BaseActivity() {

    @Inject
    lateinit var adapter: ComicsAdapter

    @Inject
    lateinit var viewModelFactory: ComicsViewModelFactory

    override fun injectActivity(component: ActivityComponent) {
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comics)

        setupViewModel()

        recyclerView_comics.adapter = adapter
    }

    private fun setupViewModel() {
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(ComicsViewModel::class.java)

        viewModel.getComics().observe(this, Observer {
            it?.let {
                when (it.status) {
                    Status.SUCCESS -> showComics(it.data!!)
                    Status.ERROR -> showError(it.message)
                    Status.LOADING -> showLoading()
                }
            }
        })
    }

    private fun showComics(comics: List<Comic>) {
        adapter.updateComics(comics)
        recyclerView_comics.setVisible(true)
        progressBar_comics.setVisible(false)
        textView_comics_error.setVisible(false)
    }

    private fun showError(message: String?) {
        recyclerView_comics.setVisible(false)
        progressBar_comics.setVisible(false)
        textView_comics_error.setVisible(true)
        if (message == null)
            textView_comics_error.text = getString(R.string.unknown_error_occurred)
        else
            textView_comics_error.text = "$message"
    }

    private fun showLoading() {
        recyclerView_comics.setVisible(false)
        progressBar_comics.setVisible(true)
        textView_comics_error.setVisible(false)
    }
}