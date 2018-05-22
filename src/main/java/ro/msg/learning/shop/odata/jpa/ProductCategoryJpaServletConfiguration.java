package ro.msg.learning.shop.odata.jpa;

import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductCategoryJpaServletConfiguration {

    private final ProductCategoryJpaServiceFactory productCategoryJpaServiceFactory;

    public ProductCategoryJpaServletConfiguration(ProductCategoryJpaServiceFactory productCategoryJpaServiceFactory) {
        this.productCategoryJpaServiceFactory = productCategoryJpaServiceFactory;
    }

    @Bean
    @SuppressWarnings("unchecked")
    public ServletRegistrationBean productServletJpa() throws ODataJPARuntimeException {
        productCategoryJpaServiceFactory.initializeODataJPAContext();
        ServletRegistrationBean bean = new ServletRegistrationBean(new ProductCategoryJpaServlet(productCategoryJpaServiceFactory), "/productServletJpa/*");
        bean.setLoadOnStartup(1);
        return bean;
    }

}
