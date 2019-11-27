package com.example.flutter.utils.awsgateway

import android.util.Log
import com.amazonaws.regions.Regions
import com.example.flutter.utils.BlueBird.Companion.context
import com.example.flutter.utils.UserAuthenticationInterface
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler
import com.amazonaws.mobileconnectors.cognitoidentityprovider.*
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler
import com.amazonaws.services.cognitoidentityprovider.model.UsernameExistsException


object AwsAuthentication: UserAuthenticationInterface{
    private val TAG = this.javaClass.name
    private val USER_POOL_ID = "us-west-2_xRWgYy5QY"
    private val CLIENT_ID = "128v6i6snq36daivqbvfdgdgt"
    private val CLIENT_SECRET = "1ldg92rklo7ktmjuhumbesptnk6l2lik4dpqmp90lo56q0m7sp6a"
    private val REGION = Regions.US_WEST_2

    override fun signUp(name: String, alias: String, password: String, profilePicEncoding: String,
                        onSuccess: () -> Unit, onFailure: () -> Unit) {

        val userPool = CognitoUserPool(context, USER_POOL_ID, CLIENT_ID, CLIENT_SECRET, REGION)
        val userAttributes = CognitoUserAttributes()
        userAttributes.addAttribute("name", name)

        val signupCallback = object : SignUpHandler {
            override fun onSuccess(
                cognitoUser: CognitoUser,
                userConfirmed: Boolean,
                cognitoUserCodeDeliveryDetails: CognitoUserCodeDeliveryDetails
            ) {
                if (!userConfirmed) {
                    Log.wtf(TAG, "User wasn't automatically confirmed")
                } else {
                    Log.d(TAG, "User account confirmed. User created.")
                    onSuccess()
                }
            }

            override fun onFailure(exception: Exception) {
                Log.d(TAG, exception.message)
                if  (exception is UsernameExistsException){
                    "crud."
                }
                onFailure()
            }
        }

        userPool.signUpInBackground(alias, password, userAttributes, null, signupCallback)
    }

    override fun signIn(username: String, password: String,
                        onSuccess: (accessToken: String?, userAlias: String?) -> Unit, onFailure: () -> Unit) {

        val userPool = CognitoUserPool(context, USER_POOL_ID, CLIENT_ID, CLIENT_SECRET, REGION)
        val cognitoUser =  userPool.getUser(username)
        signIn(cognitoUser, onSuccess, onFailure, password)
    }

    private fun signIn(cognitoUser: CognitoUser, onSuccess: (accessToken: String?, userAlias: String?) -> Unit,
                       onFailure: () -> Unit, password: String? = null) {

        val authenticationHandler = object : AuthenticationHandler {
            override fun onSuccess(userSession: CognitoUserSession?, newDevice: CognitoDevice?) {
                val accessToken = userSession?.accessToken?.jwtToken
                val userAlias = cognitoUser.userId
                onSuccess(accessToken, userAlias)
            }

            override fun getAuthenticationDetails(
                authenticationContinuation: AuthenticationContinuation,
                userId: String?
            ) {
                if(userId == null || password == null){
                    onFailure()
                    return
                }
                // The API needs user sign-in credentials to continue
                val authenticationDetails = AuthenticationDetails(userId, password, null)

                // Pass the user sign-in credentials to the continuation
                authenticationContinuation.setAuthenticationDetails(authenticationDetails)

                // Allow the sign-in to continue
                authenticationContinuation.continueTask()
            }

            override fun authenticationChallenge(continuation: ChallengeContinuation?) {
                Log.wtf(TAG, "Not sure what authenticationChallenge is...")
            }

            override fun getMFACode(multiFactorAuthenticationContinuation: MultiFactorAuthenticationContinuation) {
                Log.wtf(TAG, "Multi-factor was required, but is not supposed to be.")
//                 Multi-factor authentication is required; get the verification code from user
//                multiFactorAuthenticationContinuation.setMfaCode(mfaVerificationCode)
//                // Allow the sign-in process to continue
//                multiFactorAuthenticationContinuation.continueTask()
            }

            override fun onFailure(exception: Exception) {
                Log.d(TAG, exception.message)
                onFailure()
            }
        }
        cognitoUser.getSessionInBackground(authenticationHandler)
    }


    override fun ninjaSignIn(onSuccess: (accessToken: String?, userAlias: String?) -> Unit, onFailure: () -> Unit) {
        val userPool = CognitoUserPool(context, USER_POOL_ID, CLIENT_ID, CLIENT_SECRET, REGION)
        val cognitoUser =  userPool.currentUser
        signIn(cognitoUser, onSuccess, onFailure)
    }

    override fun logout(onSuccess: () -> Unit, onFailure: () -> Unit) {
        val userPool = CognitoUserPool(context, USER_POOL_ID, CLIENT_ID, CLIENT_SECRET, REGION)
        val user = userPool.getCurrentUser()
        if (user != null) {
            val handler = object: GenericHandler {
                override fun onSuccess() {
                    onSuccess()
                }

                override fun onFailure(exception: java.lang.Exception?) {
                    Log.e(TAG, exception?.message)
                    onFailure()
                }
            }
            user.globalSignOutInBackground(handler)
        }
    }
}