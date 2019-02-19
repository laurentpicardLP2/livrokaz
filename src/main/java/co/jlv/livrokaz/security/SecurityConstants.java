package co.jlv.livrokaz.security;

public class SecurityConstants {

    public static final String SIGN_UP_URLS = "/livrokaz/userctrl/**";
    public static final String SECRET_KEY = "CleSecretePourGenererJWTs";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long TOKEN_EXPIRATION_TIME = 1800_000; //1800 secondes
}