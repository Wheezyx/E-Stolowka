package pl.prodzajto.estolowkabackend.security;

class Constants {
    static final String SECRET = "SecretKeyToGenJWTs";
    static final long EXPIRATION_TIME = 86_400_000; // 1 day
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
}
