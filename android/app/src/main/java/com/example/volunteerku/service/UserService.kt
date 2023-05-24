package com.example.volunteerku.service

import android.util.Log
import com.example.volunteerku.VolunteerKUApplication.Companion.user
import com.example.volunteerku.data.CommonResponse
import com.example.volunteerku.data.DuplicateResponse
import com.example.volunteerku.data.EmailCertifyCodeResponse
import com.example.volunteerku.data.EmailResponse
import com.example.volunteerku.data.LogoutResponse
import com.example.volunteerku.data.RefreshTokenResponse
import com.example.volunteerku.data.User
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

    fun register(user: User) {
        val userService = getRetrofit().create(UserRetrofitInterface::class.java)

        userService.register(user).enqueue(object : Callback<EmailCertifyCodeResponse> {
            override fun onResponse(
                call: Call<EmailCertifyCodeResponse>,
                response: Response<EmailCertifyCodeResponse>
            ) {
                Log.d("retrofit", "onResponse: ${response.code()} is received ")
                when (response.code()) {
                    200 -> { // success
                        val resp: EmailCertifyCodeResponse = response.body()!!
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

            override fun onFailure(call: Call<EmailCertifyCodeResponse>, t: Throwable) {
                Log.e("retrofit", "onResponse: fail register $call")
                t.printStackTrace()
                onResponseListener.getResponseBody(null, false, "서버 연결에 실패하였습니다. 네트워크를 확인해주세요.")
            }
        })
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

    fun login(token: String) {
        val userService = getRetrofit().create(UserRetrofitInterface::class.java)

        userService.login(token).enqueue(object : Callback<EmailCertifyCodeResponse> {
            override fun onResponse(
                call: Call<EmailCertifyCodeResponse>,
                response: Response<EmailCertifyCodeResponse>
            ) {
                Log.d("retrofit", "onResponse: ${response.code()} is received ")
                when (response.code()) {
                    200 -> { // success
                        val resp: EmailCertifyCodeResponse = response.body()!!
                        onResponseListener.getResponseBody(resp, true, "")
                    }

                    401 -> { // failed
                        val err = JSONObject(
                            response.errorBody()?.string()!!
                        ).getJSONObject("errorResponse").get("code").toString()
                        //Log.d("login_test", "onResponse: " + response.errorBody()!!.string())
                        onResponseListener.getResponseBody(null, false, err)
                    }
                }
            }

            override fun onFailure(call: Call<EmailCertifyCodeResponse>, t: Throwable) {
                Log.e("retrofit", "onResponse: fail login $call")
                t.printStackTrace()
                onResponseListener.getResponseBody(null, false, "서버 연결에 실패하였습니다. 네트워크를 확인해주세요.")
            }
        })
    }

    fun logout(token: String) {
        val userService = getRetrofit().create(UserRetrofitInterface::class.java)

        userService.logout(token).enqueue(object : Callback<LogoutResponse> {
            override fun onResponse(
                call: Call<LogoutResponse>,
                response: Response<LogoutResponse>
            ) {
                when (response.code()) {
                    200 -> { // success
                        onResponseListener.getResponseBody(response.body()!!, true, "")
                    }

                    401 -> { // failed
                        val err = JSONObject(
                            response.errorBody()?.string()!!
                        ).getJSONObject("errorResponse").get("message").toString()
                        onResponseListener.getResponseBody(null, false, err)
                    }
                }
            }

            override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                Log.e("retrofit", "onResponse: fail logout $call")
                t.printStackTrace()
                onResponseListener.getResponseBody(null, false, "서버 연결에 실패하였습니다. 네트워크를 확인해주세요.")
            }
        })
    }

    fun reissuanceToken(token: String) {
        val userService = getRetrofit().create(UserRetrofitInterface::class.java)

        userService.reissuanceToken(token).enqueue(object : Callback<RefreshTokenResponse> {
            override fun onResponse(
                call: Call<RefreshTokenResponse>,
                response: Response<RefreshTokenResponse>
            ) {
                Log.d("retrofit", "onResponse: ${response.code()} is received ")
                when (response.code()) {
                    200 -> { // success
                        val resp: RefreshTokenResponse = response.body()!!
                        onResponseListener.getResponseBody(resp, true, "")
                    }

                    401 -> { // failed
                        val err = JSONObject(
                            response.errorBody()?.string()!!
                        ).getJSONObject("errorResponse").get("code").toString()
                        onResponseListener.getResponseBody(null, false, err)
                    }
                }
            }

            override fun onFailure(call: Call<RefreshTokenResponse>, t: Throwable) {
                Log.e("retrofit", "onResponse: fail reissuanceToken $call")
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

    fun getUserInfo(token: String) {
        val userService = getRetrofit().create(UserRetrofitInterface::class.java)

        userService.getUserInfo(token).enqueue(object : Callback<CommonResponse> {
            override fun onResponse(
                call: Call<CommonResponse>,
                response: Response<CommonResponse>
            ) {
                try {
                    when (response.code()) {
                        200 -> {
                            val res = JSONObject(response.body()!!.response.toString())
                            user.email = res.getString("email")
                            user.nickname = res.getString("nickname")
                            user.major = res.getString("major")
                            onResponseListener.getResponseBody(null, true, "")
                        }

                        else -> {
                            Log.d("deleteUser", "onResponse: ${response.code()}")
                            onResponseListener.getResponseBody(
                                null,
                                false,
                                "서버 오류로 유저 정보를 불러오지 못했습니다."
                            )
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    onResponseListener.getResponseBody(null, false, "서버 연결중 오류가 발생했습니다.")
                }
            }

            override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                Log.e("retrofit", "onResponse: fail deleteUser $call")
                t.printStackTrace()
                onResponseListener.getResponseBody(null, false, "서버 연결에 실패하였습니다. 네트워크를 확인해주세요.")
            }
        })
    }

}