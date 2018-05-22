package ro.msg.learning.shop.odata.jpa;

import org.apache.olingo.odata2.core.servlet.ODataServlet;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ProductCategoryJpaServlet extends ODataServlet {

    private final ProductCategoryJpaServiceFactory productCategoryJpaServiceFactory;

    public ProductCategoryJpaServlet(ProductCategoryJpaServiceFactory productCategoryServiceFactory) {
        this.productCategoryJpaServiceFactory = productCategoryServiceFactory;
    }

    @Override
    protected ProductCategoryJpaServiceFactory getServiceFactory(HttpServletRequest request) {

        return productCategoryJpaServiceFactory;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws IOException {

        req.setAttribute(ProductCategoryJpaServiceFactory.FACTORY_INSTANCE_LABEL, productCategoryJpaServiceFactory);
        super.service(req, res);
    }


}
