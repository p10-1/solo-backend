package org.solo.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RootConfig {
    // AppConfig -> RootConfig 컨트롤러 제외 모든 것
    // Webintial -> WebConfig  초기세팅
    // Webconfig -> Servletconfig 컨트롤러
}
