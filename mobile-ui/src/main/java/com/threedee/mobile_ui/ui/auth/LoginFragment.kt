package com.threedee.mobile_ui.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.threedee.data.repository.user.UserCache
import com.threedee.domain.interactor.user.LoginUser
import com.threedee.mobile_ui.R
import com.threedee.mobile_ui.databinding.LayoutFragmentLoginBinding
import com.threedee.mobile_ui.hideKeyboard
import com.threedee.mobile_ui.isValidEmail
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

class LoginFragment : DaggerFragment(),
    BaseView<UserIntent, UserViewState> {

    private val loginPublisher =
        BehaviorSubject.create<UserIntent.LoginIntent>()
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var userCache: UserCache
    private lateinit var userViewModel: UserViewModel
    val compositeDisposable = CompositeDisposable()

    lateinit var binding: LayoutFragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_fragment_login, container, false)
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
        binding.buttonLogin.setOnClickListener {
            if (validateInputFields()){
                activity?.hideKeyboard()
                loginPublisher.onNext(UserIntent.LoginIntent(LoginUser.Param(binding.inputEmail.text.toString(), binding.inputPassword.text.toString())))
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
        return Observable.merge(initialIntent(), loginPublisher)
    }

    private fun initialIntent(): Observable<UserIntent.IdleIntent> {
        return Observable.just(UserIntent.IdleIntent)
    }

    override fun render(state: UserViewState) {
        when {
            state is UserViewState.LoginInProgress -> {
                disableLoginButton()
            }
            state is UserViewState.LoginIdle -> {
                enableLoginButton()
            }
            state is UserViewState.LoginFailed -> {
                enableLoginButton()
                activity?.showSnackbar("Login failed: ${state.message}")
            }
            state is UserViewState.LoginUserSuccess -> {
                enableLoginButton()
                activity?.showSnackbar("Login user success!")
            }
        }
    }

    private fun enableLoginButton() {
        binding.buttonLogin.isEnabled = true
    }

    private fun disableLoginButton() {
        binding.buttonLogin.isEnabled = false
    }

    private fun validateInputFields(): Boolean {
        binding.inputEmail.validate(
            "E-mail address not valid.",
            { s -> s.isValidEmail() }, binding.layoutEmail)
        binding.inputPassword.validate(
            "Password must be greater than 6.",
            { s -> s.isValidPassword() }, binding.layoutPassword)

        if (binding.inputEmail.text.toString().isValidEmail()
            && binding.inputPassword.text.toString().isValidPassword()) {
            return true
        }
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.dispose()
    }
}