package io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<Binding : ViewBinding> protected constructor(
    private val bindingFactory: (inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean) -> Binding
) : Fragment() {
    private var _binding: Binding? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = bindingFactory(inflater, container, false).also { _binding = it }.root
}