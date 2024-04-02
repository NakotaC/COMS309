package onetoone;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.builders.ApiInfoBuilder;


@Configuration
@EnableSwagger2
@Import(SpringDataRestConfiguration.class)
public class SpringFoxConfig {

    @Bean
    public Docket myDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("onetoone"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Sorry Plus API")
                .description("COMS 309 Sorry Plus Backend API")
                .termsOfServiceUrl("https://localhost:8080/terms")
                .version("0.0.2")
                .build();
    }

}
