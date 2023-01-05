package com.topview.shop.auth.support.core;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.topview.shop.common.core.constant.SecurityConstants;
import com.topview.shop.common.core.util.WebUtils;

import com.topview.shop.common.security.service.UserDetailsService;
import lombok.SneakyThrows;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author Akai
 */
public class DaoAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	/**
	 * 用于在未找到用户时执行PasswordEncoder#matches(CharSequence,String) 以避免SEC-2056的明文密码
	 */
	private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";

	private final static BasicAuthenticationConverter BASIC_CONVERT = new BasicAuthenticationConverter();

	private PasswordEncoder passwordEncoder;

	/**
	 * 用于在未找到用户时执行PasswordEncoder以避免SEC-2056的密码。 这是必要的，因为如果密码格式无效，某些PasswordEncode实现将短路
	 */
	private volatile String userNotFoundEncodedPassword;

	private org.springframework.security.core.userdetails.UserDetailsService userDetailsService;

	private UserDetailsPasswordService userDetailsPasswordService;

	public DaoAuthenticationProvider() {
		setMessageSource(SpringUtil.getBean("securityMessageSource"));
		setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
	}

	@Override
	@SuppressWarnings("deprecation")
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		// app 模式不用校验密码
		String grantType = WebUtils.getRequest().get().getParameter(OAuth2ParameterNames.GRANT_TYPE);
		if (StrUtil.equals(SecurityConstants.APP, grantType)) {
			return;
		}
		if (authentication.getCredentials() == null) {
			this.logger.debug("Failed to authenticate since no credentials provided");
			throw new BadCredentialsException(this.messages
					.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}
		String presentedPassword = authentication.getCredentials().toString();
		if (!this.passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
			this.logger.debug("Failed to authenticate since password does not match stored value");
			throw new BadCredentialsException(this.messages
					.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}
	}

	@SneakyThrows
	@Override
	protected final UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) {
		prepareTimingAttackProtection();
		HttpServletRequest request = WebUtils.getRequest().orElseThrow(
				(Supplier<Throwable>) () -> new InternalAuthenticationServiceException("web request is empty"));
		Map<String, String> paramMap = ServletUtil.getParamMap(request);
		String grantType = paramMap.get(OAuth2ParameterNames.GRANT_TYPE);
		String clientId = paramMap.get(OAuth2ParameterNames.CLIENT_ID);
		if (StrUtil.isBlank(clientId)) {
			clientId = BASIC_CONVERT.convert(request).getName();
		}
		Map<String, UserDetailsService> userDetailsServiceMap = SpringUtil.getBeansOfType(UserDetailsService.class);
		String finalClientId = clientId;
		Optional<UserDetailsService> optional = userDetailsServiceMap.values().stream()
				.filter(service -> service.support(finalClientId, grantType))
				.max(Comparator.comparingInt(Ordered::getOrder));
		if (!optional.isPresent()) {
			throw new InternalAuthenticationServiceException("UserDetailsService error , not register");
		}
		try {
			UserDetails loadedUser = optional.get().loadUserByUsername(username);
			if (loadedUser == null) {
				throw new InternalAuthenticationServiceException(
						"UserDetailsService returned null, which is an interface contract violation");
			}
			return loadedUser;
		}
		catch (UsernameNotFoundException ex) {
			mitigateAgainstTimingAttack(authentication);
			throw ex;
		}
		catch (InternalAuthenticationServiceException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,
			UserDetails user) {
		boolean upgradeEncoding = this.userDetailsPasswordService != null
				&& this.passwordEncoder.upgradeEncoding(user.getPassword());
		if (upgradeEncoding) {
			String presentedPassword = authentication.getCredentials().toString();
			String newPassword = this.passwordEncoder.encode(presentedPassword);
			user = this.userDetailsPasswordService.updatePassword(user, newPassword);
		}
		return super.createSuccessAuthentication(principal, authentication, user);
	}

	private void prepareTimingAttackProtection() {
		if (this.userNotFoundEncodedPassword == null) {
			this.userNotFoundEncodedPassword = this.passwordEncoder.encode(USER_NOT_FOUND_PASSWORD);
		}
	}

	private void mitigateAgainstTimingAttack(UsernamePasswordAuthenticationToken authentication) {
		if (authentication.getCredentials() != null) {
			String presentedPassword = authentication.getCredentials().toString();
			this.passwordEncoder.matches(presentedPassword, this.userNotFoundEncodedPassword);
		}
	}

	/**
	 * Sets the PasswordEncoder instance to be used to encode and validate passwords. If
	 * not set, the password will be compared using
	 * {@link PasswordEncoderFactories#createDelegatingPasswordEncoder()}
	 * @param passwordEncoder must be an instance of one of the {@code PasswordEncoder}
	 * types.
	 */
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");
		this.passwordEncoder = passwordEncoder;
		this.userNotFoundEncodedPassword = null;
	}

	protected PasswordEncoder getPasswordEncoder() {
		return this.passwordEncoder;
	}

	public void setUserDetailsService(
			org.springframework.security.core.userdetails.UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	protected org.springframework.security.core.userdetails.UserDetailsService getUserDetailsService() {
		return this.userDetailsService;
	}

	public void setUserDetailsPasswordService(UserDetailsPasswordService userDetailsPasswordService) {
		this.userDetailsPasswordService = userDetailsPasswordService;
	}

}
