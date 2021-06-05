package gladun.vlad.weather.data.network

abstract class KnownException(message: String, cause: Throwable?) :
    RuntimeException(message, cause) {
}

class InvalidDataException(message: String, cause: Throwable? = null): KnownException(message, cause)