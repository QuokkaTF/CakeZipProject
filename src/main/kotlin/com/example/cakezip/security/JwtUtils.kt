package com.example.cakezip.security

import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.apache.tomcat.util.net.openssl.ciphers.Authentication
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtils(private val userDetailsService: UserDetailsServiceImpl) {

    // 토큰 유효 시간 30분
    val EXP_TIME: Long = 1000L * 60 * 30
    val JWT_SECRET: String = "secret"
    val SIGNATURE_ALG: SignatureAlgorithm = SignatureAlgorithm.HS256

    // 토큰 생성
    fun createToken(userEmail: String): String {
        val claims: Claims = Jwts.claims();
        claims["userEmail"] = userEmail

        return Jwts.builder()
            .setClaims(claims)
            .setExpiration(Date(System.currentTimeMillis()+ EXP_TIME))
            .signWith(SIGNATURE_ALG, JWT_SECRET)
            .compact()
    }

    // 토큰 검증
    fun validation(token: String) : Boolean {
        Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token)


        val claims: Claims = getAllClaims(token)
        val exp: Date = claims.expiration
        return exp.after(Date())
    }

    // 토큰에서 userEmail 파싱
    fun parseUserEmail(token: String): String {
        val claims: Claims = getAllClaims(token)
        return claims["userEmail"] as String
    }

    // userEmail로 Authentication 객체 생성
    fun getAuthentication(userEmail: String): UsernamePasswordAuthenticationToken {
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(userEmail)

        return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
    }

    private fun getAllClaims(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(JWT_SECRET)
            .parseClaimsJws(token)
            .body
    }

}