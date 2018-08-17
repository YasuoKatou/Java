package yksolution.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import yksolution.demo.core.ControllerBase;
import yksolution.demo.core.dto.RequestDto;

@Controller
public class HelloWorld extends ControllerBase {

  private String myName = this.getClass().getSimpleName();

  @RequestMapping(value="/helloworld", method=RequestMethod.GET)
  @ResponseBody
  public String helloworld(@RequestAttribute("RequestDto") RequestDto requestDto) {
    logger.trace("[" + myName +"] UserMasterDao -> " + requestDto.getUserMaster());
    return "Hello World !!";
  }

  @RequestMapping(value="/hellodemo", method=RequestMethod.GET)
  @ResponseBody
  public String helloHoge() {
    return "Hello Demo !!";
  }
}