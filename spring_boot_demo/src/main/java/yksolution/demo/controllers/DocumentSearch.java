package yksolution.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import yksolution.demo.service.DocumentSearchService;

@Controller
@RequestMapping(value="/docs", method=RequestMethod.GET)
public class DocumentSearch {
  @Autowired private DocumentSearchService documentSearchService;

  @RequestMapping(value="/java/package/{className}", method=RequestMethod.GET)
  @ResponseBody
  public String searchJavaPackage(@PathVariable("className") String className) {
    return documentSearchService.searchPackage(className);
  }
}