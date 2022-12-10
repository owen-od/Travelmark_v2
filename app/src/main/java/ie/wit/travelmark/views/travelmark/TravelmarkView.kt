package ie.wit.travelmark.views.travelmark

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.wit.travelmark.R
import ie.wit.travelmark.databinding.ActivityTravelmarkBinding
import ie.wit.travelmark.models.TravelmarkModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber.i

class TravelmarkView : AppCompatActivity() {

    private lateinit var binding: ActivityTravelmarkBinding
    lateinit var presenter: TravelmarkPresenter
    var travelmark = TravelmarkModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTravelmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        presenter = TravelmarkPresenter(this)

        i("Travelmark Activity started..")

        binding.btnAdd.setOnClickListener() {
            if (binding.travelmarkLocation.text.toString().isEmpty()) {
                binding.travelmarkLocation.setError("Mandatory field")
            }
            if (binding.travelmarkTitle.text.toString().isEmpty()) {
                binding.travelmarkTitle.setError("Mandatory field")
            }
            if (binding.travelmarkTitle.text.toString().isEmpty() || binding.travelmarkLocation.text.toString().isEmpty()) {
                Snackbar
                    .make(it, R.string.warning_enterTitle, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                GlobalScope.launch(Dispatchers.IO) {
                    presenter.doAddorUpdate(
                        binding.travelmarkLocation.text.toString(),
                        binding.travelmarkTitle.text.toString(),
                        binding.travelmarkDescription.text.toString(),
                        binding.travelmarkRating.rating,
                        binding.travelmarkCategory.checkedRadioButtonId
                    )
                }
            }
        }

        binding.chooseImage.setOnClickListener {
            presenter.cacheTravelmark(
                binding.travelmarkLocation.text.toString(),
                binding.travelmarkTitle.text.toString(),
                binding.travelmarkDescription.text.toString(),
                binding.travelmarkRating.rating,
                binding.travelmarkCategory.checkedRadioButtonId
            )
            presenter.doSelectImage()
        }

        binding.setTravelmarkLocation.setOnClickListener {
            presenter.cacheTravelmark(
                binding.travelmarkLocation.text.toString(),
                binding.travelmarkTitle.text.toString(),
                binding.travelmarkDescription.text.toString(),
                binding.travelmarkRating.rating,
                binding.travelmarkCategory.checkedRadioButtonId
            )
            presenter.doSetLocation()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_travelmark, menu)
        val deleteMenu: MenuItem = menu.findItem(R.id.item_delete)
        if (presenter.edit){
            deleteMenu.setVisible(true)
        }
        else{
            deleteMenu.setVisible(false)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                presenter.doCancel()
            }
            R.id.item_delete -> {
                GlobalScope.launch(Dispatchers.IO) {
                    presenter.doDelete()
                }
            }
            android.R.id.home -> {
                presenter.doHome()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showTravelmark(travelmark: TravelmarkModel) {
        binding.travelmarkTitle.setText(travelmark.title)
        binding.travelmarkDescription.setText(travelmark.description)
        binding.travelmarkLocation.setText(travelmark.location)
        binding.travelmarkRating.rating = travelmark.rating
        var categoryId = when(travelmark.category) {
            "Thing to do" -> R.id.option_do
            "Food to eat" -> R.id.option_eat
            else -> R.id.option_see
        }
        binding.travelmarkCategory.check(categoryId)
        if (travelmark.image != "") {
        Picasso.get()
            .load(travelmark.image)
            .into(binding.travelmarkImage)
            binding.chooseImage.setText(R.string.button_changeImage)
        }
    }

    fun showEditView() {
        binding.btnAdd.setText(R.string.menu_saveTravelmark)
        binding.addPlacemarkHeader.setText("Update Travelmark")
    }

    fun updateImage(image: String){
        i("Image updated")
        Picasso.get()
            .load(image)
            .into(binding.travelmarkImage)
        binding.chooseImage.setText(R.string.button_changeImage)
        }

    }