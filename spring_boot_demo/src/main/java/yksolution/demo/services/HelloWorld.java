package yksolution.demo.services;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import yksolution.demo.entity.UserMasterEntity;

@Controller
public class HelloWorld {

  private String myName = this.getClass().getSimpleName();

  @RequestMapping("/helloworld")
  @ResponseBody
  public String helloworld(@RequestAttribute("UserMasterEntity") UserMasterEntity userMaster) {
    System.out.println("[" + myName +"] UserMasterDao -> " + userMaster);
    return "Hello World !!";
  }
}