package yks.ticket.lite.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Integer.MIN_VALUE)
public class LoginFilter implements Filter {
	/** ログ出力. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void destroy() {
		logger.info("destroy LoginFilter");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpServletRequest = (HttpServletRequest)request;
			String requestUri = httpServletRequest.getRequestURI();
			logger.debug("Request URI : " + requestUri);
			if (requestUri.startsWith("/yksticket")) {
				// 次のchain
				chain.doFilter(request, response);
			} else {
				logger.error("can not query string : " + requestUri);
			}
		} else {
			logger.error("can not query string");
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("init LoginFilter");
	}
}