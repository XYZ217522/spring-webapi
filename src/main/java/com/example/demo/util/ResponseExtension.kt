import com.fasterxml.jackson.databind.ObjectMapper


fun Map<String, String>.toJSONStr() = ObjectMapper().writeValueAsString(this)