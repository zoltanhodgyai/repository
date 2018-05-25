package ro.msg.learning.shop.odata.jpa;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShopJpaServletConfiguration {

    @Bean
    @SuppressWarnings("unchecked")
    public ServletRegistrationBean shopServletJpa(ShopJpaServlet shopJpaServlet) {
        ServletRegistrationBean bean = new ServletRegistrationBean(shopJpaServlet, "/ShopServletJpa.svc/*");
        bean.setLoadOnStartup(1);
        return bean;
    }

}
