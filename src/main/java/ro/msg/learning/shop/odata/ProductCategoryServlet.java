package ro.msg.learning.shop.odata;

import org.apache.olingo.odata2.core.servlet.ODataServlet;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ProductCategoryServlet extends ODataServlet {

    private final ProductCategoryServiceFactory productCategoryServiceFactory;

    public ProductCategoryServlet(ProductCategoryServiceFactory productCategoryServiceFactory) {
        this.productCategoryServiceFactory = productCategoryServiceFactory;
    }

    @Override
    protected ProductCategoryServiceFactory getServiceFactory(HttpServletRequest request) {

        return productCategoryServiceFactory;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws IOException {

        req.setAttribute(ProductCategoryServiceFactory.FACTORY_INSTANCE_LABEL, productCategoryServiceFactory);
        super.service(req, res);

    }


}
