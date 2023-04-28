package com.example.accesskeybackend.web.controller;

import com.example.accesskeybackend.web.dto.CheckedIpv6SupportDto;
import com.example.accesskeybackend.web.service.AccessKeyWebService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/web")
@AllArgsConstructor
public class AccessKeyWebController {

    private final AccessKeyWebService webService;


    @GetMapping("/checkIpv6Support")
    public CheckedIpv6SupportDto checkIpv6Support(@RequestParam String siteUrl){

        return webService.checkIpv6SupportByUrl(siteUrl);

    }
}
