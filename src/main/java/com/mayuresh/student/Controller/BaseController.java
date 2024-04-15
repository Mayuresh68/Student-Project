package com.mayuresh.student.Controller;


import com.mayuresh.student.Models.BaseEntity;
import com.mayuresh.student.Service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

public class BaseController{}
//@RestController
//@RequestMapping("/api")
//public abstract class BaseController<T extends BaseEntity>{
//
//    @Autowired
//    private BaseService<T> baseService;
//
//    @RequestMapping(value = "/save", method = RequestMethod.POST)
//    public String createData(@RequestBody T entity) {
//        return "mayuresh";
//    }
//
//
//}
