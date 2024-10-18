package org.solo.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Api(value = "Root Controller", tags = "ROOT API")

public class RootController {
//
//    @GetMapping("/")
//    @ApiOperation(value = "루트 컴포넌트", notes = "루트 컴포넌트")
//    public String root() {
//        return "index";
//    }
}
