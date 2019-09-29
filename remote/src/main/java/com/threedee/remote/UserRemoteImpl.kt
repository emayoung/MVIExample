package com.threedee.remote

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.threedee.data.repository.user.UserRemote
import com.threedee.domain.interactor.user.GetUserByParam
import com.threedee.domain.interactor.user.LoginUser
import com.threedee.domain.interactor.user.LogoutUser
import com.threedee.domain.interactor.user.RegisterUser
import com.threedee.domain.interactor.user.UpdateUser
import com.threedee.domain.model.User
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class UserRemoteImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val store: FirebaseFirestore,
    private val context: Context
) : UserRemote {
    override fun loginUser(param: LoginUser.Param): Completable {
        return login(param)
    }

    override fun registerUser(param: RegisterUser.Param): Completable {
        return checkUserDoesNotExist(param)
            .andThen(register(param))
            .andThen(saveUser(param))
    }

    override fun logoutUser(param: LogoutUser.Param): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateUser(param: UpdateUser.Param): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLoggedInUser(): Single<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserByParam(param: GetUserByParam.Param): Single<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun register(param: RegisterUser.Param): Completable {
        return Completable.create { completable ->
            auth.createUserWithEmailAndPassword(param.email, param.password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        println("createUserWithEmail:success $user")
                        completable.onComplete()
                    } else {
                        // If sign in fails, display a message to the user.
                        println("createUserWithEmail:failure. ${task.exception}")
                        completable.onError(Throwable("Authentication failed. ${task.exception}"))
                    }
                }
        }
    }

    private fun saveUser(param: RegisterUser.Param): Completable {
        return Completable.create { completable ->
            val user = User(param.email, param.password, param.fullName, param.phone)
            store.collection(DBConstants.usersRef).document(user.email)
                .set(user)
                .addOnSuccessListener { completable.onComplete() }
                .addOnFailureListener { completable.onError(Throwable("Error writing document: $it")) }
        }
    }

    private fun checkUserDoesNotExist(param: RegisterUser.Param): Completable {
        return Completable.create { completable ->
            completable.onComplete()
        }
    }

    private fun login(param: LoginUser.Param): Completable {
        return Completable.create { completable ->
            auth.signInWithEmailAndPassword(param.email, param.password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        completable.onComplete()
                    } else {
                        completable.onError(Throwable("Authentication failed. ${task.exception}"))
                    }
                }
        }
    }
}