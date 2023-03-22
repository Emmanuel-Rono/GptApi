package com.example.gptapi
// Import necessary libraries
import android.os.Bundle
import android.telecom.Call
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    // Declare the API endpoint and API key
    private val endpoint = "https://api.openai.com/v1/engine/gpt3.5/completions"
    private val apiKey = "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"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Create an OkHttpClient instance
        val client = OkHttpClient()

        // Create a JSON object with the query parameters
        val jsonObject = JSONObject()
        jsonObject.put("prompt", "What is the meaning of life?")
        jsonObject.put("temperature", 0.5)
        jsonObject.put("max_tokens", 50)

        // Create a RequestBody with the JSON object
        val requestBody = RequestBody.create(
            "application/json".toMediaTypeOrNull(),
            jsonObject.toString()
        )

        // Create a Request with the API endpoint and API key
        val request = Request.Builder()
            .url(endpoint)
            .header("Content-Type", "application/json")
            .header("Authorization", "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")
            .post(requestBody)
            .build()

        // Use the client to send the request asynchronously
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                // Get the response body as a JSON string
                val responseString = response.body?.string()

                // Parse the response string into a JSON object
                val responseObject = JSONObject(responseString)

                // Get the generated text from the response object
                val generatedText = responseObject.getJSONArray("choices")
                    .getJSONObject(0)
                    .getString("text")

                // Display the generated text in the UI thread
                runOnUiThread {
                    findViewById<TextView>(R.id.Display_TextView).text = generatedText

                }
            }
        })
    }}



