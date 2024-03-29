package org.example.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String SECRET_KEY = "MIAVKMSDVI5TfdsDGP5dDSKNGFG3FDS2sFG4KDMSFA234KMFSDAKLNIOBG8sKJFD82njk2nUIDASF2334";
    @Value("${jwt.expiration}")
    private Long EXPIRATION;

    private static final String CREDENTIAL = "credential";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

//    public String generateToken(User userDetails){
//        return generateToken(new HashMap<>(), userDetails);
//    }
    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }
//    public boolean isTokenValid(String token, User userDetails){
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
//    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
//    public String generateToken(
//            Map<String, Object> extraClaims,
//            User userDetails
//    ){
//        List<String> permissions = new ArrayList<>();
//        Map<String, Object> rolesClaim = new HashMap<>();
//        userDetails.getRole().getPermissionSet().forEach(p -> permissions.add(p.getName()));
//        userDetails.getExtraPermission().forEach(p -> permissions.add(p.getName()));
//        rolesClaim.put("credential", permissions);
//        return Jwts.builder()
//                .setClaims(extraClaims)
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() * EXPIRATION))
//                .signWith(getSignKey())
//                .addClaims(rolesClaim)
//
//                .compact();
//
//    }
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public List<String> extractPermission(String token){
        final Claims claims = extractAllClaims(token);
        return claims.get(CREDENTIAL, List.class);
    }

    private Key getSignKey(){
        byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }
}
