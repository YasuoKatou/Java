package yksolution.demo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TestUtils implements BeanFactoryAware {

  private static final String myName = TestUtils.class.getSimpleName();

  private BeanFactory beanFactory = null;

  public <T> T getBean(Class<T> requiredType) throws BeansException {
    if (beanFactory == null) return null;
    return this.beanFactory.getBean(requiredType);
  }

  public Object getBean(String beanName) throws BeansException {
    if (beanFactory == null) return null;
    return this.beanFactory.getBean(beanName);
  }

  public Object getFilterBean(String beanName) throws BeansException {
    if (beanFactory == null) return null;
    FilterRegistrationBean filterRegistrationBean = (FilterRegistrationBean) this.beanFactory.getBean(beanName);
    Assert.isInstanceOf(FilterRegistrationBean.class, filterRegistrationBean);
    return filterRegistrationBean.getFilter();
  }


  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    this.beanFactory = beanFactory;
    //System.out.println("★★★★★★★★★[" + myName + "] beanFactory -> " + beanFactory.toString());
  }
}