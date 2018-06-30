package yksolution.demo.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import yksolution.demo.dao.UserMasterDao;
import yksolution.demo.entity.UserMasterEntity;

@Component
@Order(Integer.MIN_VALUE)
public class LoginFilter implements Filter {

  @Autowired
  private UserMasterDao userMasterDao;

  private String myName = this.getClass().getSimpleName();

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    System.out.println("[" + myName +"] init!!");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
    System.out.println("[" + myName +"] Before!!");
    UserMasterEntity userMaster = userMasterDao.findUser("demo_user1");
    System.out.println("[" + myName +"] UserMasterDao -> " + userMaster);
    request.setAttribute("UserMasterEntity", userMaster);
    chain.doFilter(request, response);
    System.out.println("[" + myName +"] after!!");
  }

  @Override
  public void destroy() {
    System.out.println("[" + myName +"] destroy!!");
  }
}