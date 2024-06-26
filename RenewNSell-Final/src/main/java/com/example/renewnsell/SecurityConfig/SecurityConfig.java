package com.example.renewnsell.SecurityConfig;

import com.example.renewnsell.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final MyUserDetailsService myUserDetailsService;



    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()

//=====================================================UserController===========================
                .requestMatchers("api/v1/user/get-all", "api/v1/user/delete/{username}",
                        "api/v1/user/get-user-by-id/{id}", "api/v1/user/get-user-by-username/{username}").hasAuthority("ADMIN")
                .requestMatchers("api/v1/user/delete-my-user", "api/v1/user/login", "api/v1/user/logout", "api/v1/user/get-my-info").authenticated()
//=====================================================CompanyController===========================
                .requestMatchers("api/v1/company/add").permitAll()
                .requestMatchers("api/v1/company/update-my-user").authenticated()
                .requestMatchers("api/v1/company/get-all", "api/v1/company/update/{id}", "api/v1/company/update/{id}").hasAuthority("ADMIN")
//=====================================================CustomerController===========================
                .requestMatchers("api/v1/customer/add").permitAll()
                .requestMatchers("api/v1/customer/update-my-user").authenticated()
                .requestMatchers("api/v1/customer/get-all", "api/v1/customer/update/{id}").hasAuthority("ADMIN")
                .requestMatchers("api/v1/customer/update/{id}").hasAuthority("ADMIN")
                .requestMatchers("api/v1/customer/get-customer-by-id/{id}").hasAuthority("ADMIN")
//=====================================================FixProductController===========================
                .requestMatchers("api/v1/fix-product/get-all-request-fix-product").hasAnyAuthority("ADMIN","EMPLOYEE")
                .requestMatchers("api/v1/fix-product/delete-fix/{fixProductId}").hasAuthority("ADMIN")
                .requestMatchers("api/v1/fix-product/update/{fixId}").hasAuthority("CUSTOMER")
                .requestMatchers("api/v1/fix-product/{fixProductId}").hasAuthority("CUSTOMER")
                .requestMatchers("api/v1/fix-product/request-fix-product").hasAuthority("CUSTOMER")
                .requestMatchers("api/v1/fix-product/accept-price-fix-product/{fixProductId}").hasAuthority("CUSTOMER")
                .requestMatchers("api/v1/fix-product/reject-price-fix-product/{fixProductId}").hasAuthority("CUSTOMER")
                .requestMatchers("api/v1/fix-product/get-fix-product/{fixProductId}").hasAnyAuthority("CUSTOMER","ADMIN","EMPLOYEE")
//=====================================================OrderCompanyController===========================
                .requestMatchers("api/v1/order-company/get-all").hasAuthority("ADMIN")
                .requestMatchers("api/v1/order-company/get-all-order-by-company-id").hasAuthority("COMPANY")
                .requestMatchers("api/v1/order-company/update-order-company/{orderId}").hasAuthority("ADMIN")
                .requestMatchers("api/v1/order-company/delete-order-company/{orderId}").hasAuthority("ADMIN")
                .requestMatchers("api/v1/order-company/change-status-of-order-company/{orderId}").hasAuthority("COMPANY")
                .requestMatchers("api/v1/order-company/get-all-total-price-orders-company").hasAnyAuthority("COMPANY","ADMIN")
                .requestMatchers("api/v1/order-company/get-all-orders-company-of-percent-of-defective/{precent}").hasAuthority("COMPANY")
                .requestMatchers("api/v1/order-company/get-total-orders-company").hasAuthority("COMPANY")
                .requestMatchers("api/v1/order-company/get-total-profit-of-one-product/{productId}").hasAuthority("COMPANY")
                .requestMatchers("api/v1/order-company/get-total-profit-of-one-product-with-out-fix/{productId}").hasAuthority("COMPANY")
                .requestMatchers("api/v1/order-company/get-total-profit-of-one-product-with-fix/{productId}").hasAuthority("COMPANY")
                .requestMatchers("api/v1/order-company/get-total-number-of-order-for-one-product/{productId}").hasAuthority("COMPANY")
                .requestMatchers("api/v1/order-company/get-average-profit").hasAuthority("COMPANY")
                .requestMatchers("api/v1/order-company/get/average-profit").hasAuthority("COMPANY")
                .requestMatchers("api/v1/order-company/company-total-revenue","api/v1/order-company/company-today-revenue",
                        "api/v1/order-company/company-current-month-revenue", "api/v1/order-company/company-all-sold-product",
                        "api/v1/order-company/company-today-sold-product", "api/v1/order-company/company-current-month-sold-product",
                        "api/v1/order-company/company-last-month-sold-product", "api/v1/order-company/company-last-month-revenue").hasAuthority("COMPANY")
//=====================================================OrderController===========================
                .requestMatchers("api/v1/order/get-total-orders").hasAuthority("ADMIN")
                .requestMatchers("api/v1/order/get-all-orders").hasAuthority("ADMIN")
                .requestMatchers("api/v1/order/buy-order").hasAuthority("CUSTOMER")
                .requestMatchers("api/v1/order/update-order/{orderId}").hasAuthority("ADMIN")
                .requestMatchers("api/v1/order/delete-order/{orderId}").hasAuthority("ADMIN")
                .requestMatchers("api/v1/order/cancel-order/{orderId}").hasAuthority("CUSTOMER")
                .requestMatchers("api/v1/order/change-status-of-order/{orderId}").hasAuthority("EMPLOYEE")
                .requestMatchers("api/v1/order/get-all-by-status/{status}").hasAnyAuthority("ADMIN","EMPLOYEE")
                .requestMatchers("api/v1/order/track-order-for-employee/{orderId}").hasAnyAuthority("ADMIN","EMPLOYEE")
                .requestMatchers("api/v1/order/track-order/{orderId}").hasAuthority("CUSTOMER")
                .requestMatchers("api/v1/order/get-all-customer-order").hasAnyAuthority("CUSTOMER","EMPLOYEE")
                .requestMatchers("api/v1/order/get-all-order-by-product-id/{productId}").hasAnyAuthority("ADMIN","EMPLOYEE")
                .requestMatchers("api/v1/order/get-total-orders").hasAuthority("ADMIN")
                .requestMatchers("api/v1/order/get-total-revenue","api/v1/order/get-today-revenue",
                        "api/v1/order/get-current-month-revenue", "api/v1/order/get-last-month-revenue",
                        "api/v1/order/get-last-month-revenue", "api/v1/order/count-all-sold-product",
                        "api/v1/order/count-today-sold-product", "api/v1/order/count-current-month-sold-product",
                        "api/v1/order/count-last-month-sold-product").hasAnyAuthority("EMPLOYEE", "ADMIN")
//=====================================================ProductController===========================
                .requestMatchers("api/v1/product/get-product-name/{name}/","api/v1/product/get-product-defective/{percentOfDefective}","api/v1/product/get-product-category/{category}","api/v1/product/get").permitAll()
                .requestMatchers("api/v1/product/get-product-cname/{name}").permitAll()
                .requestMatchers("api/v1/product/add").hasAuthority("COMPANY")
                .requestMatchers("api/v1/product/delete/{productId}").hasAuthority("COMPANY")
                .requestMatchers("api/v1/product/update/{productId}","api/v1/review/get-all-c").hasAuthority("COMPANY")
                .requestMatchers("api/v1/product/get-all").hasAuthority("COMPANY")
                .requestMatchers("api/v1/product/get-product/{productId}").hasAuthority("COMPANY")
//=====================================================ResponseFixProductController===========================
                .requestMatchers("api/v1/response-fix-product/get-all-response-request-fix-product").hasAuthority("ADMIN")
                .requestMatchers("api/v1/response-fix-product/response-request-fix-product/{fixProductId}").hasAuthority("EMPLOYEE")
                .requestMatchers("api/v1/response-fix-product/update-response-request-fix-product/{fixproductId}").hasAuthority("EMPLOYEE")
                .requestMatchers("api/v1/response-fix-product/delete-request-fix-product/{fixProductId}").hasAuthority("ADMIN")
//=====================================================ReviewController===========================

                .requestMatchers("api/v1/review/add/{companyId}").hasAuthority("CUSTOMER")
                .requestMatchers("api/v1/review/get/{reviewId}","api/v1/review/get-all-cu","api/v1/review/update/{reviewId}").hasAuthority("CUSTOMER")
                .requestMatchers("api/v1/review/delete/{reviewId}").hasAnyAuthority("ADMIN","CUSTOMER")
                .requestMatchers("api/v1/review/get-all-c/{name}","api/v1/review/average-rating/{name}","api/v1/review/number-of-reviews/{name}","api/v1/review/best").permitAll()
                .requestMatchers("api/v1/review/get-all-c").hasAuthority("COMPANY")

//=====================================================UserController===========================
//=====================================================WarrantyController===========================
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/user/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();

    }
}
