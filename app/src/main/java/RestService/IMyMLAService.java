package RestService;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import Models.ReportProblem;
import Models.Status;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Development on 8/13/2016.
 */

public interface IMyMLAService {

    @GET("issues.json?orderBy=\"userId\"")
    Call<LinkedHashMap<String,Status>> getAllStatus(@Query("equalTo") String id);


   @POST("issues.json?")
   Call<ReportProblem> createReport(@Body ReportProblem newProductDetails);


}
