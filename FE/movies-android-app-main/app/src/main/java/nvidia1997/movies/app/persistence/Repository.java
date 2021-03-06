package nvidia1997.movies.app.persistence;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class Repository<T> extends Context {
    protected final String API = "http://192.168.1.152:8081/";
    protected final RequestQueue _queue;
    protected Gson gson;

    public Repository() {
        gson = new Gson();
        this._queue = Volley.newRequestQueue(this);
    }

    protected String getGeneralErrorId() {
        return getEndpoint().toUpperCase() + "_API_ERROR";
    }

    protected abstract String getEndpoint();

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void add(T dto, Consumer onSuccess) {
        update(dto, onSuccess);// on API we 're creating new record if the item has no id
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void removeById(int id, Consumer onSuccess) {
        String url = API + getEndpoint() + "/" + id;

        StringRequest dr = new StringRequest(Request.Method.DELETE, url,
                response -> {
                    onSuccess.accept(null);
                },
                error -> Log.d(getGeneralErrorId(), error.getMessage())
        );
        _queue.add(dr);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void update(T dto, Consumer onSuccess) {
        String url = API + getEndpoint();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                url, new JSONObject(parseObjectForRequestBody(dto)),
                response -> {
                    onSuccess.accept(null);
                },
                error -> {
                    VolleyLog.d(getGeneralErrorId(), "Error: " + error.getMessage());
                }) {

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        _queue.add(jsonObjReq);
    }

    protected Map<String, String> parseObjectForRequestBody(T dto) {
        return new ObjectMapper().convertValue(dto, Map.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void findById(int id, Consumer<T> onSuccess) {
        String url = API + getEndpoint() + "/" + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null,
                        response -> {
                            try {
                                Class<T> persistentClass = (Class<T>)
                                        ((ParameterizedType) getClass().getGenericSuperclass())
                                                .getActualTypeArguments()[0];

                                onSuccess.accept(gson.fromJson(response.toString(), persistentClass));
                            } catch (Exception error) {
                                Log.d(getGeneralErrorId(), error.getMessage());
                            }
                        },

                        error -> {
                            onSuccess.accept(null);
                        }
                );

        _queue.add(jsonObjectRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void findAll(Consumer<List<T>> onSuccess) {
        String url = API + getEndpoint();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null,
                        response -> {
                            try {
                                List<T> dtos = Arrays.asList(parseJsonArray(response.toString()));
                                onSuccess.accept(new ArrayList<>(dtos));
                            } catch (Exception error) {
                                Log.d(getGeneralErrorId(), " " + error.getMessage());
                            }
                        },

                        error -> {
                            Log.d(getGeneralErrorId(), error.getMessage());
                        }
                );

        _queue.add(jsonArrayRequest);
    }

    protected abstract T[] parseJsonArray(String jsonArray);
}
