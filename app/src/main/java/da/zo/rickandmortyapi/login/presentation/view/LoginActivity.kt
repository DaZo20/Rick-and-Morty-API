package da.zo.rickandmortyapi.login.presentation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import da.zo.rickandmortyapi.MainActivity
import da.zo.rickandmortyapi.common.utils.Email
import da.zo.rickandmortyapi.common.utils.Password
import da.zo.rickandmortyapi.databinding.ActivityLoginBinding
import da.zo.rickandmortyapi.login.domain.model.LoginUser
import da.zo.rickandmortyapi.login.presentation.viewmodel.LoginViewModel
import da.zo.rickandmortyapi.register.presentation.view.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var loginBinding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)
        supportActionBar?.hide()
        initViews()
    }

    private fun initViews() {
        loginBinding.registerBtn.setOnClickListener { navigateToRegisterActivity() }
        loginBinding.loginBtn.setOnClickListener {
            val email: Email = Email(value = loginBinding.emailEt.text.toString())
            val pass: Password = Password(value = loginBinding.passEt.text.toString())
            loginViewModel.onLoginOptionSelected(email = email, password = pass)
            subscribeToDataFlows()
        }
    }

    private fun subscribeToDataFlows() =
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.loginUser.collect{ user -> handleResult(result = user)}
            }
        }

    private fun handleResult(result: LoginUser?) =
        if (result != null){
            navigateToMainActivity()
        }else {
            println("Error: No user detected, login failed")
        }

    private fun navigateToMainActivity() =
        startActivity(Intent(this,MainActivity::class.java))

    private fun navigateToRegisterActivity() =
        startActivity(Intent(this,RegisterActivity::class.java))
}