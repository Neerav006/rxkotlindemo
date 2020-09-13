
import com.example.rxkotlinsample.model.NewsResponse
import com.example.rxkotlinsample.model.postmodel.Post
import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface API {

    @GET("everything?q=sports&apiKey=aa67d8d98c8e4ad1b4f16dbd5f3be348")
    fun getAllNews(): Observable<NewsResponse.Response>

    @GET("posts")
    fun getAllPost(): Observable<List<Post>>

}
