package ro.msg.learning.shop.odata;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductCategoryServletConfiguration {

    private final ProductCategoryServiceFactory productCategoryServiceFactory;

    public ProductCategoryServletConfiguration(ProductCategoryServiceFactory productCategoryServiceFactory) {
        this.productCategoryServiceFactory = productCategoryServiceFactory;
    }

    @Bean
    @SuppressWarnings("unchecked")
    public ServletRegistrationBean productServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new ProductCategoryServlet(productCategoryServiceFactory), "/productServlet/*");
        bean.setLoadOnStartup(1);
        return bean;
    }

}
