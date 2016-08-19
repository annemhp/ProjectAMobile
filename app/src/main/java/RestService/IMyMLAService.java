package RestService;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import Models.Status;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by Development on 8/13/2016.
 */

public interface IMyMLAService {

    @GET("/issues.json?")
    Call<LinkedHashMap<String,Status>> getAllStatus();

    @GET("/issues.json?")
    Call<LinkedHashMap<String,Status>> getStatusUpdates();


}
