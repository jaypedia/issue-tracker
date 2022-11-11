package team20.issuetracker.config;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@TestConfiguration
public class WebConfig implements WebMvcConfigurer {

    private final TestLoginInterceptor testLoginInterceptor;

    public WebConfig(TestLoginInterceptor testLoginInterceptor) {
        this.testLoginInterceptor = testLoginInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(testLoginInterceptor)
                .order(1)
                .addPathPatterns("/**");
    }


    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToMilestoneStatus());
        registry.addConverter(new StringToIssueStats());
    }
}
