package team20.issuetracker.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import team20.issuetracker.login.interceptor.LoginInterceptor;
import team20.issuetracker.login.interceptor.TokenInterceptor;

//@TestConfiguration
//@RequiredArgsConstructor
//public class TestWebConfig implements WebMvcConfigurer {
//
//    private final LoginInterceptor loginInterceptor;
//    private final TokenInterceptor tokenInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//        registry.addInterceptor(tokenInterceptor)
//                .order(1)
//                .addPathPatterns("/**");
//
//        registry.addInterceptor(loginInterceptor)
//                .order(2)
//                .addPathPatterns("/**");
//    }
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:9000/")
//                .allowedMethods("GET", "POST", "PATCH", "DELETE", "OPTIONS")
//                .allowCredentials(true)
//                .maxAge(3600);
//    }
//}
