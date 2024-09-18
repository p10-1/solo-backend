package org.solo.asset.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AssetController {
    @GetMapping("/assetPage")
    public String assetPage() {
        return "assetPage";
    }






}
