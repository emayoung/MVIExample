package com.threedee.mobile_ui.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.threedee.data.repository.user.UserCache
import com.threedee.domain.interactor.user.RegisterUser
import com.threedee.mobile_ui.R
import com.threedee.mobile_ui.databinding.LayoutFragmentRegisterBinding
import com.threedee.mobile_ui.hideKeyboard
import com.threedee.mobile_ui.isValidConfirmPassword
import com.threedee.mobile_ui.isValidEmail
import com.threedee.mobile_ui.isValidFullName
import com.threedee.mobile_ui.isValidMobileNumber
import com.threedee.mobile_ui.isValidPassword
import com.threedee.mobile_ui.showSnackbar
import com.threedee.mobile_ui.validate
import com.threedee.presentation.base.BaseView
import com.threedee.presentation.user.UserIntent
import com.threedee.presentation.user.UserViewModel
import com.threedee.presentation.user.UserViewState
import dagger.android.support.DaggerFragment
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import javax.inject.Inject

class RegisterFragment : DaggerFragment(),
    BaseView<UserIntent, UserViewState> {

    private val registerPublisher =
        BehaviorSubject.create<UserIntent.RegisterIntent>()
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var userCache: UserCache
    private lateinit var userViewModel: UserViewModel
    val compositeDisposable = CompositeDisposable()

    lateinit var binding: LayoutFragmentRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.layout_fragment_register, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
    }

    private fun initViews() {
        binding.buttonSignUp.setOnClickListener {
            if (validateInputFields()) {
                activity?.hideKeyboard()
                registerPublisher.onNext(UserIntent.RegisterIntent(RegisterUser.Param(binding.inputEmail.text.toString(),
                    binding.inputPassword.text.toString(), binding.inputFullName.text.toString(), binding.inputMobileNumber.text.toString())))
            }
        }
    }

    private fun initViewModel() {
        userViewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory).get(UserViewModel::class.java)
        } ?: throw UnsupportedOperationException("Activity is not a valid activity.")

        compositeDisposable.add(userViewModel.states().subscribe({ render(it) }))
        userViewModel.processIntents(intents())
    }

    override fun intents(): Observable<UserIntent> {
        return Observable.merge(initialIntent(), registerPublisher)
    }

    private fun initialIntent(): Observable<UserIntent.IdleIntent> {
        return Observable.just(UserIntent.IdleIntent)
    }

    override fun render(state: UserViewState) {
        when {
            state is UserViewState.RegisterInProgress -> {
                disableSignUpButton()
            }
            state is UserViewState.RegisterIdle -> {
                enableSignUpButton()
            }
            state is UserViewState.RegisterFailed -> {
                enableSignUpButton()
                activity?.showSnackbar("Register failed: ${state.message}")
            }
            state is UserViewState.RegisterUserSuccess -> {
                enableSignUpButton()
                activity?.showSnackbar("Register user success!")
            }
        }
    }

    private fun enableSignUpButton() {
        binding.buttonSignUp.isEnabled = true
    }

    private fun disableSignUpButton() {
        binding.buttonSignUp.isEnabled = false
    }

    private fun validateInputFields(): Boolean {
        binding.inputMobileNumber.validate(
            "Mobile number not valid.",
            { s -> s.isValidMobileNumber() }, binding.layoutMobileNumber
        )
        binding.inputEmail.validate(
            "E-mail not valid.",
            { s -> s.isValidEmail() }, binding.layoutEmail
        )
        binding.inputFullName.validate(
            "Enter your full name",
            { s -> s.isValidFullName() }, binding.layoutFullName
        )
        binding.inputPassword.validate(
            "Password must be greater than 6.",
            { s -> s.isValidPassword() }, binding.layoutPassword
        )
        binding.inputConfirmPassword.validate(
            "Passwords do not match",
            { s -> s.isValidConfirmPassword(binding.inputPassword.text.toString()) },
            binding.layoutConfirmPassword
        )

        if (binding.inputMobileNumber.text.toString().isValidMobileNumber()
            && binding.inputPassword.text.toString().isValidPassword()
            && binding.inputFullName.text.toString().isValidFullName()
            && binding.inputEmail.text.toString().isValidEmail()
            && binding.inputConfirmPassword.text.toString().isValidConfirmPassword(binding.inputPassword.text.toString())
        ) {
            return true
        }
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.dispose()
    }
}