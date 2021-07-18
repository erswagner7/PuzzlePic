package com.example.puzzlepic.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.puzzlepic.R
import com.example.puzzlepic.dto.Event
import kotlinx.android.synthetic.main.event_fragment.*

class EventFragment : Fragment() {

    companion object {
        fun newInstance() = EventFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.event_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity.let{
            viewModel = ViewModelProviders.of(it!!).get(MainViewModel::class.java)

        }
      btnSaveEvent.setOnClickListener {
          saveEvent()
      }
    }

    private fun saveEvent() {
        var event = Event()
        with(event){
            type = actEventType.text.toString()
            description = edtDescription.text.toString()
            var quantityString= edtQuantity.text.toString()
                    if(quantityString.length > 0){
                        quantity= quantityString.toDouble()
                    }
            date = edtEventDate.text.toString()

        }
    }


}