package org.zhang.purchasandsale.auth;

import java.util.Date;
import java.util.HashMap;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

/**
 * 
 * @author G.fj
 * @since 2019年12月23日 下午7:38:58
 *
 */
public class TokenUtils {
	/** 前端请求的标识 */
	public static final String TOKENHEADER = "Authorization";
	public static final String TOKENPREFIX = "Bearer ";
	/** 加密用的字符串 */
	private static final String SECRET = "yhznkj-tm4";
	private static final String ISS = "YHZNKJ";

	/**
	 * Desc:创建Token
	 * 
	 * @param user
	 * @param isRemember
	 * @return
	 */
	public static String createToken(JwtUser user) {
		HashMap<String, Object> claims = new HashMap<String, Object>(5);
		claims.put("eid", user.getEid());
		claims.put("name", user.getUserName());
		return Jwts.builder()
				/** 这里要早set一点，放到后面会覆盖别的字段 */
				.signWith(SignatureAlgorithm.HS512, SECRET)
				/** 内置数据 */
				.setClaims(claims)
				/** 发行者 */
				.setIssuer(ISS)
				/** 唯一id */
				.setId(user.getUid())
				/** token发行时间 */
				.setIssuedAt(new Date())
				/** 超期时间 */
				.setExpiration(new Date(System.currentTimeMillis() + user.getExpire())).compact();
	}

	/**
	 * Desc:Token是否有效
	 * 
	 * @param token
	 * @return
	 */
	public static boolean checkToken(String token) {
		JwtUser user = getTokenUser(token);
		if (user != null) {
			return user.isSuccess();
		} else {
			return false;
		}
	}

	/**
	 * Desc:通过token得到登录的相关信息，一般情况下，需要使用此功能
	 * 
	 * @param token
	 * @return
	 */
	public static JwtUser getTokenUser(String token) {
		JwtUser juser = new JwtUser();
		try {
			Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
			juser.setEid(claims.get("eid", Integer.class));
			juser.setUid(claims.getId());
			juser.setExpire(claims.getExpiration().getTime());
			juser.setIat(claims.getIssuedAt());
			juser.setUserName(claims.get("name").toString());
			juser.setSuccess(true);
		} catch (ExpiredJwtException expired) {
			juser.setError("身份认证令牌已过期，请重新登录");
		} catch (SignatureException e) {
			juser.setError("身份认证令牌无效，请重新登录");
		} catch (MalformedJwtException malformedJwt) {
			juser.setError("身份认证令牌无效，请重新登录");
		} catch (Exception e) {
			juser.setError("身份认证时出现错误，请联系管理员");
		}
		return juser;
	}

	public static JwtUser createFeignUser(int eid) {
		JwtUser juser = new JwtUser();
		if (eid == 0) {
			eid = 1;
		}
		juser.setEid(eid);
		juser.setUid("000000000000000000000000");
		juser.setUserName("system");
		juser.setExpire(24 * 60 * 60 * 1000);
		juser.setIat(new Date());
		return juser;
	}

	public static void main(String[] args) {
		String token = TokenUtils.createToken(TokenUtils.createFeignUser(1));
		System.out.println(token);
	}
}