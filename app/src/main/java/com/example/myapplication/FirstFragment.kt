package com.example.myapplication

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentFirstBinding
import okhttp3.internal.notify

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private val informationsListViewModel by viewModels<InformationsListViewModel> {
        InformationsListViewModelFactory(this)
    }

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!


    private fun adapterOnClick(information: Information) {
//        val intent = Intent(this, InformationDetailActivity()::class.java)
//        intent.putExtra(INFORMATION_ID, information.id)
//        startActivity(intent)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val connectivityManager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo?.isConnected() == true) {
            val informationsAdapter = InformationsAdapter { information -> adapterOnClick(information) }
            val recyclerView: RecyclerView? = _binding?.recyclerView
            recyclerView?.adapter = informationsAdapter

            informationsListViewModel.informationsLiveData.observe(viewLifecycleOwner) {
                it.let {
                    informationsAdapter.submitList(it as MutableList<Information>)
                }
            }

            informationsListViewModel.triggerNetworkRequests()

        } else {
            Log.d("test", "network is not connected")
            val alertDialogBuilder = AlertDialog.Builder(context, R.style.Theme_MyApplication)
            alertDialogBuilder.setTitle("Network unavailable !")
            alertDialogBuilder.setMessage("Network is not available. Enable it and restart this app, please.")
            alertDialogBuilder.setCancelable(false)
            alertDialogBuilder.setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, _ ->
                dialog.cancel()
            } )
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


