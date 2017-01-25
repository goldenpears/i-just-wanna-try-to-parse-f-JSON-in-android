/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.locovna.a_bit_of_json;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Displays information about Something.
 */
public class MainActivity extends AppCompatActivity {

  /**
   * Tag for the log messages
   */
  public static final String LOG_TAG = MainActivity.class.getSimpleName();

  /**
   * URL to query for Some information
   */
  private static final String REQUEST_URL = "";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Kick off an {@link AsyncTask} to perform the network request
//    UserAsyncTask task = new UserAsyncTask();
//    task.execute();
  }

  /**
   * Update the screen to display information from the given {@link Something}.
   */
  private void updateUi(Something something) {
    // Display the Something name in the UI
    TextView titleTextView = (TextView) findViewById(R.id.something_text_view);
    titleTextView.setText(something.name);
  }

  /**
   * {@link AsyncTask} to perform the network request on a background thread, and then
   * update the UI with the first earthquake in the response.
   */
  private class UserAsyncTask extends AsyncTask<URL, Void, Something> {

    @Override
    protected Something doInBackground(URL... urls) {
      // Create URL object
      URL url = null;
      try {
        url = createUrl(REQUEST_URL);
      } catch (MalformedURLException e) {
        e.printStackTrace();
      }

      // Perform HTTP request to the URL and receive a JSON response back
      String jsonResponse = "";
      try {
        jsonResponse = makeHttpRequest(url);
      } catch (IOException e) {
        Log.e(LOG_TAG, "Problem making HTTP request", e);
      }

      // Extract relevant fields from the JSON response and create an {@link Something} object
      Something something = extractFeatureFromJson(jsonResponse);

      // Return the {@link Something} object as the result fo the {@link SomethingAsyncTask}
      return something;
    }

    /**
     * Update the screen with the given something (which was the result of the
     * {@link UserAsyncTask}).
     */
    @Override
    protected void onPostExecute(Something something) {
      if (something == null) {
        return;
      }

      updateUi(something);
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private URL createUrl(String stringUrl) throws MalformedURLException {
      URL url = null;
      try {
        url = new URL(stringUrl);
      } catch (MalformedURLException exception) {
        Log.e(LOG_TAG, "Error with creating URL", exception);
        return null;
      }
      return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private String makeHttpRequest(URL url) throws IOException {
      String jsonResponse = "";
      if (url == null) {
        return jsonResponse;
      }

      HttpURLConnection urlConnection = null;
      InputStream inputStream = null;

      try {
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */);
        urlConnection.setConnectTimeout(15000 /* milliseconds */);
        urlConnection.connect();

        if (urlConnection.getResponseCode() == 200) {
          inputStream = urlConnection.getInputStream();
          jsonResponse = readFromStream(inputStream);
        } else {
          Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
        }
      } catch (IOException e) {
        Log.e(LOG_TAG, "Problem retrieving the something JSON results: ", e);
      } finally {
        if (urlConnection != null) {
          urlConnection.disconnect();
        }
        if (inputStream != null) {
          // function must handle java.io.IOException here
          inputStream.close();
        }
      }
      return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private String readFromStream(InputStream inputStream) throws IOException {
      StringBuilder output = new StringBuilder();
      if (inputStream != null) {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String line = reader.readLine();
        while (line != null) {
          output.append(line);
          line = reader.readLine();
        }
      }
      return output.toString();
    }

    /**
     * Return an {@link Something} object by parsing out information
     */
    private Something extractFeatureFromJson(String somethingJSON) {
      //if the JSON string empty or null return early
      if (TextUtils.isEmpty(somethingJSON)) {
        return null;
      }

      try {
        JSONArray somethingArray = new JSONArray(somethingJSON);
        // If there are results in the features array
        if (somethingArray.length() > 0) {
          JSONObject firstUser = somethingArray.getJSONObject(0);
          String name = firstUser.getString("something");
          // Create a new {@link Something} object
          return new Something(name);
        }
      } catch (JSONException e) {
        Log.e(LOG_TAG, "Problem parsing the something JSON results", e);
      }
      return null;
    }
  }
}
