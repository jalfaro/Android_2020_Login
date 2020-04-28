package com.julioalfaro.loginapplication.utilities;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.julioalfaro.loginapplication.App;
import com.julioalfaro.loginapplication.MainActivity;
import com.julioalfaro.loginapplication.RegisterActivity;
import com.julioalfaro.loginapplication.data.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class NetworkUtility {
    private static final String DOMINIO = "http://login-app-ejemplo.herokuapp.com/";

    public static void login(final String user, final String password, final MainActivity activity) {
        StringRequest request = new StringRequest(Request.Method.POST, DOMINIO + "login", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    if (json.getInt("status") == 1) {
                        activity.login(true, json.getString("message"));
                    } else {
                        activity.login(false, json.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                activity.login(false, "Error");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                String json = "{\"user\": \"" + user + "\", \"password\": \"" + password+ "\"}";
                return json.getBytes();
            }
        };
        App.getInstance().getQueue().add(request);

    }

    public static void register(final Usuario user, final RegisterActivity activity) {
        StringRequest request = new StringRequest(Request.Method.POST, DOMINIO + "register", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonRespuesta = null;
                try {
                     jsonRespuesta = new JSONObject(response);
                    activity.registroOperado(jsonRespuesta.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                activity.registroOperado("Error en la respuesta");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                String json ="";

                JSONObject object = new JSONObject();
                try {
                    object.put("nombre", user.getNombre());
                    object.put("email", user.getEmail());
                    object.put("password", user.getPassword());
                    json = object.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return json.getBytes();
            }
        };
        App.getInstance().getQueue().add(request);
    }

    public static String md5(String s)
    {
        MessageDigest digest;
        try
        {
            digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes(Charset.forName("US-ASCII")),0,s.length());
            byte[] magnitude = digest.digest();
            BigInteger bi = new BigInteger(1, magnitude);
            String hash = String.format("%0" + (magnitude.length << 1) + "x", bi);
            return hash;
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return "";
    }

}
