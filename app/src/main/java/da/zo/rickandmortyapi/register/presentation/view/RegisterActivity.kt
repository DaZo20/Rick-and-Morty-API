package da.zo.rickandmortyapi.register.presentation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import da.zo.rickandmortyapi.MainActivity
import da.zo.rickandmortyapi.common.utils.Email
import da.zo.rickandmortyapi.common.utils.Password
import da.zo.rickandmortyapi.databinding.ActivityRegisterBinding
import da.zo.rickandmortyapi.register.domain.model.RegisterUser
import da.zo.rickandmortyapi.register.presentation.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private val registerViewModel: RegisterViewModel by viewModels()
    private lateinit var registerBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)
        supportActionBar?.hide()
        initViews()
    }

    private fun initViews() =
        registerBinding.signupBtn.setOnClickListener {
            val email: Email = Email(value = registerBinding.emailEt.text.toString())
            val pass: Password = Password(value = registerBinding.registerPasswordEt.text.toString())
            registerViewModel.onRegisterOptionSelected(email = email, password = pass)
            subscribeToDataFlows()
        }

    private fun subscribeToDataFlows() =
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                registerViewModel.registerUser.collect{ user -> handleResult(result = user)}
            }
        }

    private fun handleResult(result: RegisterUser?) =
        if (result != null) {
            navigateToMainActivity()
        }else {
            //Toast.makeText(this,"Error al registrarse", Toast.LENGTH_SHORT).show()
        }

    private fun navigateToMainActivity() =
        startActivity(Intent(this, MainActivity::class.java))
}