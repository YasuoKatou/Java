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

import yksolution.demo.entity.UserMasterEntity;

@Component
@Order(Integer.MIN_VALUE + 1)
public class AuthorityFilter implements Filter {

  private String myName = this.getClass().getSimpleName();

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    System.out.println("[" + myName +"] init!!");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
    System.out.println("[" + myName +"] Before!!");
    UserMasterEntity userMaster = (UserMasterEntity) request.getAttribute("UserMasterEntity");
    System.out.println("[" + myName +"] UserMasterDao -> " + userMaster);
    chain.doFilter(request, response);
    System.out.println("[" + myName +"] after!!");
  }

  @Override
  public void destroy() {
    System.out.println("[" + myName +"] destroy!!");
  }
}
