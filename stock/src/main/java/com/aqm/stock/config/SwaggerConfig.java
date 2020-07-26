package com.aqm.stock.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.aqm.stock.model.ResponseEntity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
        .useDefaultResponseMessages(false).
        globalResponseMessage(RequestMethod.POST, Arrays.asList(
            new ResponseMessageBuilder()
              .code(401)
              .message("Unauthorized").responseModel(new ModelRef("ResponseEntity"))
              .build(),
            new ResponseMessageBuilder()
              .code(402)
              .message("Exception").responseModel(new ModelRef("ResponseEntity"))
              .build(),
            new ResponseMessageBuilder()
              .code(403)
              .message("ArgumentInvalidException").responseModel(new ModelRef("ResponseEntity"))
              .build(),
            new ResponseMessageBuilder()
              .code(405)
              .message("InvalidDateInJsonBody").responseModel(new ModelRef("ResponseEntity"))
              .build()
              ))
              .select()
              .apis(RequestHandlerSelectors.basePackage("com.aqm.stock.controller"))
              .paths(PathSelectors.ant("/**")).build();
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfo("Spring Boot Integration with Swagger Doc", "Welcome !", "API V1.0", "Terms of service",
                new Contact("TonyTan", "", "tanxiucai1@gmail.com"), "Apache", "http://www.apache.org/",
                Collections.emptyList());
    }
}