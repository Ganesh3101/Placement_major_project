package com.test.placement_major_project.main_codes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.SignInAccount
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.getInstance
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.test.placement_major_project.R
import com.test.placement_major_project.dataclasses.GoogleData
import java.io.ByteArrayOutputStream

@Suppress("DEPRECATION")
class SignIn_page : AppCompatActivity() {

    val RC_SIGN_IN : Int = 123
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_page)

        firebaseAuth = getInstance()

        FirebaseApp.initializeApp(this)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val signInButton : SignInButton  = findViewById(R.id.google_sign_in_button)
        signInButton.setOnClickListener{
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            Google_auth(account)

        } catch (e: ApiException) {
            Toast.makeText(this, e.toString() , Toast.LENGTH_SHORT).show()
        }
    }

    private fun Google_auth(account: GoogleSignInAccount) {

        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {

                val googleFirstName = account?.givenName ?: ""

                val googleLastName = account?.familyName ?: ""

                val googleEmail = account?.email ?: ""

                var googleProfilePicURL = account?.photoUrl.toString()

                val ref = FirebaseDatabase.getInstance().getReference("Users").child("Google")

                val currentUser: String? = Firebase.auth.currentUser?.uid

                val Google_Upload =
                    GoogleData(googleFirstName, googleLastName, googleEmail, googleProfilePicURL)

                try {
                    ref.child(currentUser.toString()).setValue(Google_Upload)
                        .addOnCompleteListener {

                            Toast.makeText(this, "data saved successfully", Toast.LENGTH_LONG)
                                .show()

                        }
                } catch (e: Exception) {
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()

                }

                val baos = ByteArrayOutputStream()

                val image = baos.toByteArray()

                val storageRef =
                    FirebaseStorage.getInstance().reference.child("pics/${FirebaseAuth.getInstance().currentUser?.uid}")


                val upload = storageRef.putBytes(image)

                upload.addOnCompleteListener { uploadTask ->
                    if (uploadTask.isSuccessful) {
                        storageRef.downloadUrl.addOnCompleteListener { urlTask ->
                            urlTask.result?.let {
                                googleProfilePicURL = it.toString()

                            }

                        }
                    } else {
                        Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show()
                    }
                }

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }

        }
    }
}
