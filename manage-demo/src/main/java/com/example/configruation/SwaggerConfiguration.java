package com.example.configruation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Value("${spring.application.name:}")
    private String applicationName;

    @Value("${spring.application.author:}")
    private String author;

    @Value("${spring.application.description:}")
    private String description;

    @Value("${swagger.enable:true}")
    private boolean enableSwagger;

    @Value("${swagger.pathMapping:}")
    private String pathMapping;

    @Value("${swagger.header.memberId:false}")
    private boolean swaggerHeaderMemberId;

    @Value("${swagger.header.appSource:false}")
    private boolean swaggerHeaderAppSource;

    @Bean
    @Primary
    public Docket createRestApi() {
        //添加head参数start
        ParameterBuilder country = new ParameterBuilder();
        List<Parameter> params = new ArrayList<>();
        String openApi = "open-api";
        if(StringUtils.isNotEmpty(applicationName) && applicationName.contains(openApi)){
            ParameterBuilder authorization = new ParameterBuilder();
            ParameterBuilder countryCode = new ParameterBuilder();
            authorization.name("Authorization").description("Bearer AppId").modelRef(new ModelRef("string")).parameterType("header").required(false);
            if(openApi.equals(applicationName)){
                ParameterBuilder signature = new ParameterBuilder();
                signature.name("Signature").description("签名").modelRef(new ModelRef("string")).parameterType("header").required(false);
                params.add(signature.build());
            }
            countryCode.name("CountryCode").description("国家码").modelRef(new ModelRef("string")).parameterType("header").required(false);
            params.add(authorization.build());
            params.add(countryCode.build());
        }else{
            country.name("countryCode").description("国家码:NG,TZ,GH").modelRef(new ModelRef("string")).parameterType("header").required(false);
            params.add(country.build());
            if (swaggerHeaderMemberId){
                ParameterBuilder memberId = new ParameterBuilder();
                memberId.name("memberId").description("登录会员ID").modelRef(new ModelRef("string")).parameterType("header").required(false);
                params.add(memberId.build());
            }
            if (swaggerHeaderAppSource){
                ParameterBuilder appSource = new ParameterBuilder();
                appSource.name("appSource").description("请求来源").modelRef(new ModelRef("string")).parameterType("header").required(false);
                params.add(appSource.build());
            }
        }

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(enableSwagger)
                .pathMapping(pathMapping)
                .directModelSubstitute(Timestamp.class,Long.class)
                .directModelSubstitute(Date.class,Long.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example"))
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                //添加head参数end
                .globalOperationParameters(params);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(applicationName + " 服务API")
                .description(description)
                .termsOfServiceUrl("")
                .contact(new Contact(author, "", ""))
                .version("1.0")
                .build();
    }

}
