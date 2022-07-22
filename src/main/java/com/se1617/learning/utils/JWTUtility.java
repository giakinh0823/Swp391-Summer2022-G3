/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.se1617.learning.dal.user.UserDBContext;
import com.se1617.learning.model.user.User;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author giaki
 */
public class JWTUtility {

    private static final String secretKey = "SWP-SE1610-GROUP03";

    public static String createJWT(User user, int minus) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String token = JWT.create()
                .withIssuer("auth0") // phát hành token
                .withSubject("user") // chủ đề của token
                .withClaim("userId", user.getId())
                .withClaim("username", user.getUsername())
                .withClaim("fullname", user.getFirst_name() + " " + user.getLast_name())
                .withClaim("email", user.getEmail())
                .withJWTId(UUID.randomUUID().toString())
                .withIssuedAt(Date.from(Instant.now())) // thời điểm token được phát hành,
                .withExpiresAt(Date.from(Instant.now().plus(minus, ChronoUnit.MINUTES))) // thoi diem het han
                .sign(algorithm);
        return token;
    }

    public static User verifyToken(String token) throws Exception {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey); //use more secure key
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            Date expDate = jwt.getExpiresAt();
            if (expDate.getTime() < System.currentTimeMillis()) {
                throw new Exception("Token expires!");
            }
            String userId = jwt.getClaims().get("userId").toString();
            if (userId != null) {
                int id = Integer.parseInt(userId);
                UserDBContext userDB = new UserDBContext();
                User user = userDB.get(id);
                if (user != null) {
                    return user;
                }
            } else {
                throw new Exception("Wrong token!");
            }
        } catch (JWTVerificationException ex) {
            throw new Exception(ex.getMessage());
        }
        return null;
    }

    private static JWTUtility instance = new JWTUtility();

    private JWTUtility() {
    }

    public static JWTUtility getInstance() {
        return instance;
    }
}
