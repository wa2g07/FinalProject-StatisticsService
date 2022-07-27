package it.polito.wa2.g07.statisticsservice.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class AuthenticationManager: ReactiveAuthenticationManager {

    @Autowired
    lateinit var jwtUtils: JwtUtils

    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        val jwt = authentication.credentials.toString()
        if(jwtUtils.validateJwt(jwt)){
            val userDetails = jwtUtils.getDetailsJwt(jwt)
            val role = jwtUtils.getJwtRoles(jwt)
            val authentication = UsernamePasswordAuthenticationToken(
                    userDetails, null, getAuthorities(role)
            )
            ReactiveSecurityContextHolder.withAuthentication(authentication)
            SecurityContextHolder.getContext().authentication = authentication
            return Mono.just(authentication)
        }
        return Mono.empty()
    }

}