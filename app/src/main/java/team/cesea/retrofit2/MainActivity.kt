package team.cesea.retrofit2

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast


import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    internal lateinit var apiInterface: APIInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        apiInterface = APIClient.client!!.create(APIInterface::class.java!!)


        /**
         * GET List Resources
         */
        val call = apiInterface.doGetListResources()
        call.enqueue(object : Callback<MultipleResources> {
            override fun onResponse(call: Call<MultipleResources>, response: Response<MultipleResources>) {


                Log.d("TAG", response.code().toString() + "")

                var displayResponse = ""

                val resource = response.body()
                val text = resource.page
                val total = resource.total
                val totalPages = resource.totalPages
                val datumList = resource.data

                displayResponse += "$text Page\n$total Total\n$totalPages Total Pages\n"

                for (datum in datumList!!) {
                    displayResponse += datum.id.toString() + " " + datum.name + " " + datum.pantoneValue + " " + datum.year + "\n"
                }

                responseText.text = displayResponse

            }

            override fun onFailure(call: Call<MultipleResources>, t: Throwable) {
                call.cancel()
            }
        })

        /**
         * Create new user
         */
        val user = User("morpheus", "leader")
        val call1 = apiInterface.createUser(user)
        call1.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val user1 = response.body()

                Toast.makeText(applicationContext, user1.name + " " + user1.job + " " + user1.id + " " + user1.createdAt, Toast.LENGTH_SHORT).show()

            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                call.cancel()
            }
        })

        /**
         * GET List Users
         */
        val call2 = apiInterface.doGetUserList("2")
        call2.enqueue(object : Callback<UserList> {
            override fun onResponse(call: Call<UserList>, response: Response<UserList>) {

                val userList = response.body()
                val text = userList.page
                val total = userList.total
                val totalPages = userList.totalPages
                val datumList = userList.data
                Toast.makeText(applicationContext, "$text page\n$total total\n$totalPages totalPages\n", Toast.LENGTH_SHORT).show()

                for (datum in datumList) {
                    Toast.makeText(applicationContext, "id : " + datum.id + " name: " + datum.first_name + " " + datum.last_name + " avatar: " + datum.avatar, Toast.LENGTH_SHORT).show()
                }


            }

            override fun onFailure(call: Call<UserList>, t: Throwable) {
                call.cancel()
            }
        })


        /**
         * POST name and job Url encoded.
         */
        val call3 = apiInterface.doCreateUserWithField("morpheus", "leader")
        call3.enqueue(object : Callback<UserList> {
            override fun onResponse(call: Call<UserList>, response: Response<UserList>) {
                val userList = response.body()
                val text = userList.page
                val total = userList.total
                val totalPages = userList.totalPages
                val datumList = userList.data
                Toast.makeText(applicationContext, "$text page\n$total total\n$totalPages totalPages\n", Toast.LENGTH_SHORT).show()

                for (datum in datumList) {
                    Toast.makeText(applicationContext, "id : " + datum.id + " name: " + datum.first_name + " " + datum.last_name + " avatar: " + datum.avatar, Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<UserList>, t: Throwable) {
                call.cancel()
            }
        })

    }
}