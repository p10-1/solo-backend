package org.solo.asset.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/asset")
public class AssetController {
    @GetMapping({"","/"})
    public String asset() {
        return "asset";
    }
}
