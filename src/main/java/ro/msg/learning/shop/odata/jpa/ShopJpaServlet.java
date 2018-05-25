package ro.msg.learning.shop.odata.jpa;

import org.apache.olingo.odata2.core.servlet.ODataServlet;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class ShopJpaServlet extends ODataServlet {

    private final transient ApplicationContext applicationContext;

    public ShopJpaServlet(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    protected ShopJpaServiceFactory getServiceFactory(HttpServletRequest request) {

        return applicationContext.getBean(ShopJpaServiceFactory.class);
    }
}
