import io.github.cdimascio.swagger.Validate
import pj.saari.Error


val validate = Validate.configure("/swagger.json") { status, messages ->
    Error(status.value(), messages)
}