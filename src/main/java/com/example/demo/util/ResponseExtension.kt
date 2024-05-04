import com.fasterxml.jackson.databind.ObjectMapper
import io.reactivex.rxjava3.core.Flowable
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity


fun Map<String, String>.toJSONStr() = ObjectMapper().writeValueAsString(this)

fun Flowable<Map<String, String>>.toOkResponse(): ResponseEntity<Flowable<Map<String, String>>> {
    return ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(this)
}