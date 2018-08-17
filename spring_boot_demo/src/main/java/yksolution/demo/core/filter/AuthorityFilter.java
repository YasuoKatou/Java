package yksolution.demo.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import yksolution.demo.core.FilterBase;
import yksolution.demo.core.dto.RequestDto;

@Component
@Order(Integer.MIN_VALUE + 1)
public class AuthorityFilter extends FilterBase implements Filter {

  private String myName = this.getClass().getSimpleName();

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    logger.info("[" + myName +"] init!!");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
  throws IOException, ServletException {
    logger.trace("[" + myName +"] Before!!");
    RequestDto requestDto = (RequestDto) request.getAttribute("RequestDto");
    logger.trace("[" + myName +"] UserMasterDao -> " + requestDto.getUserMaster());
    chain.doFilter(request, response);
    logger.trace("[" + myName +"] after!!");
  }

  @Override
  public void destroy() {
    logger.info("[" + myName +"] destroy!!");
  }
}