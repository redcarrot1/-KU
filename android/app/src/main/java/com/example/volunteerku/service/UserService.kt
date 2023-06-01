package com.example.volunteerku.service

import android.util.Log
import com.example.volunteerku.data.ChangePasswordRequest
import com.example.volunteerku.data.DuplicateResponse
import com.example.volunteerku.data.EmailCertifyCodeResponse
import com.example.volunteerku.data.EmailResponse
import com.example.volunteerku.data.ExistEmailResponse
import com.example.volunteerku.data.JWT
import com.example.volunteerku.data.User
import com.example.volunteerku.data.SaveImageResponse
import com.example.volunteerku.data.SignupRequest
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserService {

    open class OnResponseListener {
        open fun <T> getResponseBody(body: T, isSuccess: Boolean, err: String) {
            // 오버라이드 후 사용
            return
        }
    }

    private var onResponseListener = OnResponseListener()

    fun setOnResponseListener(listener: OnResponseListener): UserService {
        this.onResponseListener = listener
        return this
    }

    fun isDuplicate(nickname: String) {
        val userService = getRetrofit().create(UserRetrofitInterface::class.java)

        userService.isDuplicate(nickname).enqueue(object : Callback<DuplicateResponse> {
            override fun onResponse(
                call: Call<DuplicateResponse>,
                response: Response<DuplicateResponse>
            ) {
                val resp: DuplicateResponse = response.body()!!
                onResponseListener.getResponseBody(resp, true, "")
            }

            override fun onFailure(call: Call<DuplicateResponse>, t: Throwable) {
                Log.e("retrofit", "onResponse: fail isDuplicate $call")
                t.printStackTrace()
                onResponseListener.getResponseBody(null, false, "서버 연결에 실패하였습니다. 네트워크를 확인해주세요.")
            }
        })
    }

    fun sendEmail(email: String) {
        val userService = getRetrofit().create(UserRetrofitInterface::class.java)

        userService.sendEmail(email).enqueue(object : Callback<EmailResponse> {
            override fun onResponse(call: Call<EmailResponse>, response: Response<EmailResponse>) {
                when (response.code()) {
                    200 -> { // success
                        val resp: EmailResponse = response.body()!!
                        onResponseListener.getResponseBody(resp, true, "")
                    }

                    400, 429 -> { // bad request, too many request
                        val err = JSONObject(
                            response.errorBody()?.string()!!
                        ).get("message").toString()
                        onResponseListener.getResponseBody(null, false, err)
                    }
                }
            }

            override fun onFailure(call: Call<EmailResponse>, t: Throwable) {
                Log.e("retrofit", "onResponse: fail sendEmail $call")
                t.printStackTrace()
                onResponseListener.getResponseBody(null, false, "서버 연결에 실패하였습니다. 네트워크를 확인해주세요.")
            }
        })
    }

    fun certifyCode(email: String, code: String) {
        val userService = getRetrofit().create(UserRetrofitInterface::class.java)

        userService.certifyCode(email, code).enqueue(object : Callback<EmailCertifyCodeResponse> {
            override fun onResponse(
                call: Call<EmailCertifyCodeResponse>,
                response: Response<EmailCertifyCodeResponse>
            ) {
                Log.d("certifyCode", "onResponse: ${response.body()}")
                if (response.code() != 200) {
                    onResponseListener.getResponseBody(null, false, "서버의 응답이 올바르지 않습니다.")
                } else {
                    val resp: EmailCertifyCodeResponse = response.body()!!
                    onResponseListener.getResponseBody(resp, true, "")
                }
            }

            override fun onFailure(call: Call<EmailCertifyCodeResponse>, t: Throwable) {
                Log.e("retrofit", "onResponse: fail certifyCode $call")
                t.printStackTrace()
                onResponseListener.getResponseBody(null, false, "서버 연결에 실패하였습니다. 네트워크를 확인해주세요.")
            }
        })
    }


    fun signIn(email: String, password: String) {
        val userService = getRetrofit().create(UserRetrofitInterface::class.java)
        val request = ChangePasswordRequest(email, password)
        userService.signIn(request).enqueue(object : Callback<JWT> {
            override fun onResponse(
                call: Call<JWT>,
                response: Response<JWT>
            ) {
                Log.d("retrofit", "onResponse: ${response.code()} is received ")
                when (response.code()) {
                    200 -> { // success
                        val resp: JWT = response.body()!!
                        onResponseListener.getResponseBody(resp, true, "")
                    }

                    400 -> { // failed
                        val err = JSONObject(
                            response.errorBody()?.string()!!
                        ).getJSONObject("errorResponse").get("message").toString()
                        onResponseListener.getResponseBody(null, false, err)
                    }
                }
            }

            override fun onFailure(call: Call<SaveImageResponse>, t: Throwable) {
                Log.e("retrofit", "onResponse: fail certifyCode $call")
                t.printStackTrace()
                onResponseListener.getResponseBody(null, false, "서버 연결에 실패하였습니다. 네트워크를 확인해주세요.")
            }
        })
    }
    
    fun saveImage(filePart: MultipartBody.Part) {

        val userService = getRetrofit().create(UserRetrofitInterface::class.java)
        userService.saveImage(filePart).enqueue(object : Callback<SaveImageResponse> {
            override fun onResponse(
                call: Call<SaveImageResponse>,
                response: Response<SaveImageResponse>
            ) {
                Log.d("saveImage", "onResponse: ${response.body()}")
                if (response.code() != 201) {
                    onResponseListener.getResponseBody(null, false, "서버의 응답이 올바르지 않습니다.")
                } else {
                    val resp: SaveImageResponse = response.body()!!
                    onResponseListener.getResponseBody(resp, true, "")
                }
            }

            override fun onFailure(call: Call<SaveImageResponse>, t: Throwable) {
                Log.e("retrofit", "onResponse: fail certifyCode $call")
                t.printStackTrace()
                onResponseListener.getResponseBody(null, false, "서버 연결에 실패하였습니다. 네트워크를 확인해주세요.")
            }
        })
    }


    fun changePassword(email: String, password: String) {
        val userService = getRetrofit().create(UserRetrofitInterface::class.java)
        val request = ChangePasswordRequest(email, password)
        userService.changePassword(request).enqueue(object : Callback<Void> {

           Log.d("retrofit", "onResponse: ${response.code()} is received ")
                when (response.code()) {
                    200 -> { // success
                        onResponseListener.getResponseBody(null, true, "")
                    }

                    400 -> { // failed
                        val err = JSONObject(
                            response.errorBody()?.string()!!
                        ).get("message").toString()
                        onResponseListener.getResponseBody(null, false, err)
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("retrofit", "onResponse: fail change password $call")
                t.printStackTrace()
                onResponseListener.getResponseBody(null, false, "서버 연결에 실패하였습니다. 네트워크를 확인해주세요.")
            }
        })
    }
    
    fun signup(requestBody: SignupRequest) {
        val userService = getRetrofit().create(UserRetrofitInterface::class.java)
        userService.signup(requestBody).enqueue(object : Callback<Void> {

            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                Log.d("retrofit", "onResponse: ${response.code()} is received ")
                when (response.code()) {
                    200 -> { // success
                        onResponseListener.getResponseBody(null, true, "")
                    }

                    400 -> { // failed
                        val err = JSONObject(
                            response.errorBody()?.string()!!
                        ).get("message").toString()
                        onResponseListener.getResponseBody(null, false, err)
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("retrofit", "onResponse: fail change password $call")
                t.printStackTrace()
                onResponseListener.getResponseBody(null, false, "서버 연결에 실패하였습니다. 네트워크를 확인해주세요.")
            }
        })
    }

    fun isExistEmail(email: String) {
        val userService = getRetrofit().create(UserRetrofitInterface::class.java)

        userService.isMember(email).enqueue(object : Callback<ExistEmailResponse> {
            override fun onResponse(
                call: Call<ExistEmailResponse>,
                response: Response<ExistEmailResponse>
            ) {
                Log.d("certifyCode", "onResponse: ${response.body()}")
                if (response.code() != 200) {
                    onResponseListener.getResponseBody(null, false, "서버의 응답이 올바르지 않습니다.")
                } else {
                    val resp: ExistEmailResponse = response.body()!!
                    onResponseListener.getResponseBody(resp, true, "")
                }
            }

            override fun onFailure(call: Call<ExistEmailResponse>, t: Throwable) {
                Log.d("signup", "onResponse: ${response.body()}")
                if (response.code() != 201) {
                    onResponseListener.getResponseBody(null, false, "서버의 응답이 올바르지 않습니다.")
                } else {
                    onResponseListener.getResponseBody(null, true, "")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("retrofit", "onResponse: fail certifyCode $call")
                t.printStackTrace()
                onResponseListener.getResponseBody(null, false, "서버 연결에 실패하였습니다. 네트워크를 확인해주세요.")
            }
        })
    }
}
