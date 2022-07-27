package it.polito.wa2.g07.statisticsservice.security

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import javax.crypto.SecretKey


@Component
class JwtUtils {

  private val logger: Logger = LoggerFactory.getLogger("JwtUtils")

  @Value("\${secrets.jwtkey}")
  lateinit var b64Key: String

  lateinit var key: SecretKey

  @PostConstruct
  fun initKey(){
    key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(b64Key))
  }

  fun validateJwt(authToken: String): Boolean {
    try {
      Jwts.parserBuilder().setSigningKey(key)
        .build()
        .parseClaimsJws(authToken)
      return true
    } catch (e: SignatureException) {
      logger.error("Invalid JWT signature: {}", e.message)
    } catch (e: MalformedJwtException) {
      logger.error("Invalid JWT token: {}", e.message)
    } catch (e: ExpiredJwtException) {
      logger.error("JWT token is expired: {}", e.message)
    } catch (e: UnsupportedJwtException) {
      logger.error("JWT token is unsupported: {}", e.message)
    } catch (e: IllegalArgumentException) {
      logger.error("JWT claims string is empty: {}", e.message)
    }
    return false
  }

  fun getJwtRoles(authToken: String): String{
    return Jwts.parserBuilder().setSigningKey(key)
            .build()
            .parseClaimsJws(authToken)
            .body["roles"] as String
  }
  fun getDetailsJwt(authToken: String/*,key: Key*/): String {
    val sub = Jwts.parserBuilder().setSigningKey(key)
      .build()
      .parseClaimsJws(authToken)
      .body["sub"] as String
    val roles = Jwts.parserBuilder().setSigningKey(key)
      .build()
      .parseClaimsJws(authToken)
      .body["roles"] as String

    return sub
  }
}

fun getAuthorities(roles: String): MutableList<SimpleGrantedAuthority>?{

  return roles.split("|").stream().map { SimpleGrantedAuthority("ROLE_"+it.uppercase()) }
          .toList()
}