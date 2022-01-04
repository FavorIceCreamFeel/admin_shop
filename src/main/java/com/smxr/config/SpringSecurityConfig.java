package com.smxr.config;

import com.smxr.service.UserService;
import com.smxr.utils.jwtUtils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * @author smxr
 * @date 2020/10/26
 * @time 18:11
 */
@EnableSwagger2
@EnableAspectJAutoProxy
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    //获取swagger环境属性
    @Value("${swagger.enable}")
    private boolean swagger;
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;
    @Autowired
    private MyAuthenticationException myAuthenticationException;
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private MyAuthenticationFailHandler myAuthenticationFailHandler;
    @Autowired
    private MyJwtTokenFilter myJwtTokenFilter;
    @Autowired
    private UserService userServer;

    //注入Swagger2的Docket的配置实例
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("smxr")//分组  一个Docket的实例为一个组，需要多个的话只需注入多个Docket实例并经行单独的配置即可
                //enable()  是否启动Swagger，如果启动为true，否false则不能在游览器中访问；
                .enable(swagger)
                //RequestHandlerSelectors  配置要扫描接口的方式
                    //basePackage   指定要扫描的包
                    //any()     扫描全部
                    //none()    不扫描
                    //withClassAnnotation()     扫描类上的注解，参数是一个注解类的类对象
                    //withMethodAnnotation()    扫描方法上的注解，参数是一个注解类的类对象
                .select()
                    .apis(RequestHandlerSelectors.basePackage("com.smxr.controller"))
                    //.paths(PathSelectors.ant("/smxr/**")) //paths()   过滤什么路径，不扫描那个
                    .build()
                ;
    }
    //Swagger2配置信息初始化
    private ApiInfo apiInfo(){
        //作者信息
        Contact smxr = new Contact("smxr", "https://smxr.com", "772519606@qq.com");
        // 标题  描述  版本  组织  作者  许可  许可路径
        return  new ApiInfo(
                "smxr的Swagger学习API文档",
                "个人学习练习作品",
                "1.1",
                "https://smxr.com",
                smxr,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }


    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher(){return new HttpSessionEventPublisher();}
    @Bean
    public SessionRegistry sessionRegistry(){return new SessionRegistryImpl(); }
    @Bean
    public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2B,12,new SecureRandom());}
    /**
     * 认证权限加密
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
        auth.userDetailsService(userServer).passwordEncoder(passwordEncoder());

    }

    /**
     * 制定权限规则
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http.authorizeRequests()
                .antMatchers("/favicon.ico","/rabbitMQ/**","/vue/**","/zero/**","/addOrder/shows","/common/**","/css/**","/images/**","/js/**","/json/**","/jsplug/**","/layui/**","/static/**","/showlmag/**").permitAll()
                .antMatchers("/user/**","/").hasAuthority("SSR")
                .antMatchers("/role/**").hasAuthority("Role")
                .antMatchers("/accueil/**").hasAuthority("Role1")
                .antMatchers("/Goods/**").hasAuthority("Role2")
                .antMatchers("/order/**").hasAuthority("Role3")
                .antMatchers("/fileUpload").hasAuthority("Role4")
                .antMatchers("/shoppingTrolley/**").hasAuthority("Role5")
                .and()
//                登陆成功和登陆失败接口实现
                .formLogin().loginPage("/vue/signIn").successHandler(myAuthenticationSuccessHandler).failureHandler(myAuthenticationFailHandler)
                .and()
                .logout().logoutUrl("/logout")
                .and()
//                处理异常和权限不足的接口实现
                .exceptionHandling().accessDeniedHandler(myAccessDeniedHandler).authenticationEntryPoint(myAuthenticationException)
                .and()
//                开启跨域请求
            // 禁用 csrf, 由于使用的是JWT，我们这里不需要csrf
                .csrf().disable().cors();
//              .cors().and().csrf().disable()
        http
//                session管理，由于前后端分离，不启用
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(myJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }


}
