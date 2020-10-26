package com.smxr.config;

        import org.springframework.context.annotation.Configuration;
        import org.springframework.web.servlet.config.annotation.CorsRegistry;
        import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
        import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author smxr
 * @date 2020/10/19
 * @time 14:31
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 处理跨域问题解决方法
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
//                .allowCredentials(true)
//                .exposedHeaders("*")
                .maxAge(3600);
    }
    /**
     * 处理ndlers(ResourceHandlerRegistry registry)
     * //WINDOWS用
     * 	registry.addResourceHandler("/img/**").addResourceLocations("file:E:/upload/");
     * //LINUX用
     *  registry.addResourceHandler("/img/**").addResourceLocations("file:/usr/local/upload/");
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //图片存放路径和映射地址
//        windows dir下
        registry.addResourceHandler("/upload").addResourceLocations("file:E:/upload/");
//        linux  file下
//        registry.addResourceHandler("/upload").addResourceLocations("file:/usr/local/upload/");
        //静态文件存放映射地址
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/resources/");
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/public/");

    }
}
