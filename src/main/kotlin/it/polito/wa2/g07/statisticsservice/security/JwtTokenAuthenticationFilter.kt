package it.polito.wa2.g07.statisticsservice.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class JwtTokenAuthenticationFilter : WebFilter {

    @Value("\${header.authorization.prefix}")
    lateinit var nameAuthHeaderPrefix : String

    @Autowired
    lateinit var jwtUtils: JwtUtils

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        try {
            val jwt = parseJwt(exchange.request)
            if( jwtUtils.validateJwt(jwt!!)){
                val userDetails = jwtUtils.getDetailsJwt(jwt)
                val role = jwtUtils.getJwtRoles(jwt)
                val authentication = UsernamePasswordAuthenticationToken(
                        userDetails, null, getAuthorities(role)
                )
                return chain.filter(exchange).
                contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication))
            }
        } catch (e: Exception){
            return chain.filter(exchange)
        }
        return chain.filter(exchange)
    }


    private fun parseJwt(request: ServerHttpRequest): String? {

        val headerAuth = request.headers.getFirst(HttpHeaders.AUTHORIZATION)

        return if (headerAuth!!.isNotEmpty() && headerAuth.startsWith(nameAuthHeaderPrefix)) {
            headerAuth.substring(7, headerAuth.length)
        } else null
    }
}