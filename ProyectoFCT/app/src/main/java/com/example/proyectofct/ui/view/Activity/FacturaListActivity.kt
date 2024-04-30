package com.example.proyectofct.ui.view.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.lifecycleScope
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.proyectofct.R
import com.example.proyectofct.ViewPagerAdapter
import com.example.proyectofct.core.RetrofitHelper
import com.example.proyectofct.data.model.FacturaAdapter
import com.example.proyectofct.data.network.FacturaService
import com.example.proyectofct.databinding.ActivityFacturaListBinding
import com.example.proyectofct.domain.GetFacturasUseCase
import com.example.proyectofct.domain.model.toFacturaModel
import com.example.proyectofct.ui.view.Fragment.FacturaListFragment
import com.example.proyectofct.ui.view.Fragment.FiltrarFacturasFragment
import com.example.proyectofct.ui.viewModel.FacturaListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject

@AndroidEntryPoint
class FacturaListActivity : AppCompatActivity() {
    lateinit var binding: ActivityFacturaListBinding
    private lateinit var adapter: FacturaAdapter

    @Inject
    lateinit var getFacturasUseCase: GetFacturasUseCase

    val viewModel: FacturaListViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFacturaListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val usarMock = intent.getBooleanExtra("mock", false)

        initUI()
        val fragment = FacturaListFragment()
        val fragmentManager = supportFragmentManager
        val bundle = Bundle()
        bundle.putBoolean("mock", usarMock)
        fragment.arguments = bundle

        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.container_view, fragment)
        transaction.commit()

    }

    private fun initUI() {

        binding.containerView.visibility = View.VISIBLE


    }

    private fun mostrarPopupInformacion() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Información")
        builder.setMessage("Esta funcionalidad aún no está disponible")
        builder.setPositiveButton("Cerrar") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("facturas", "FacturasListActivity_destroy")
    }
}