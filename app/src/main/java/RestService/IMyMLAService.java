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

/**
 * Created by Development on 8/13/2016.
 */

public interface IMyMLAService {

    @GET("issues.json?orderBy=\"userId\"&equalTo=\"seshnitw\"")
    Call<LinkedHashMap<String,Status>> getAllStatus();

   @POST("issues.json?")
   Call<ReportProblem> createReport(@Body ReportProblem newProductDetails);


}
