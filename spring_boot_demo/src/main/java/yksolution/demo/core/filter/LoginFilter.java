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

import yksolution.demo.core.FilterBase;
import yksolution.demo.dao.UserMasterDao;
import yksolution.demo.entity.UserMasterEntity;

@Component
@Order(Integer.MIN_VALUE)
public class LoginFilter extends FilterBase implements Filter {

  @Autowired
  private UserMasterDao userMasterDao;

  private String myName = this.getClass().getSimpleName();

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
	  logger.info("[" + myName +"] init!!");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
    logger.trace("[" + myName +"] Before!!");
    UserMasterEntity userMaster = userMasterDao.findUser("demo_user1");
    logger.trace("[" + myName +"] UserMasterDao -> " + userMaster);
    request.setAttribute("UserMasterEntity", userMaster);
    chain.doFilter(request, response);
    logger.trace("[" + myName +"] after!!");
  }

  @Override
  public void destroy() {
	  logger.info("[" + myName +"] destroy!!");
  }
}