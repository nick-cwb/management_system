package com.isoft.system.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class JwtHelper {

    private static String USERID = "user_id";

    @Value("${token.expire.seconds}")
    private static int EXPIRESSECOND;

    /**
     * 校验token
     * @param jsonWebToken
     * @param base64Security
     * @return
     */
    public static boolean verifyJWT(String jsonWebToken,String userId, String base64Security) throws Exception {
        Algorithm algorithmHS = Algorithm.HMAC256(base64Security);
        JWTVerifier verifier = JWT.require(algorithmHS)
                .withClaim("user_id", userId)
                .build();
        DecodedJWT jwt = verifier.verify(jsonWebToken);
        /*JWTVerifier verifier = JWT.require(algorithmHS).build();
        verifier.verify(jsonWebToken);*/
        return true;
    }

    /**
     * 从token中获取userId
     * @param jsonWebToken
     * @return
     */
    public static String getUserId(String jsonWebToken){
        try{
            DecodedJWT jwt = JWT.decode(jsonWebToken);
            String userId = jwt.getClaim(USERID).asString();
            return userId;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 生成token
     * @param userId
     * @param base64Security
     * @return
     */
    public static String sign(String userId, String base64Security) {
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRESSECOND);
            Algorithm algorithmHS = Algorithm.HMAC256(base64Security);
            return JWT.create().withClaim(USERID, userId).withExpiresAt(date).sign(algorithmHS);
        } catch (Exception e) {
            return null;
        }
    }

}
