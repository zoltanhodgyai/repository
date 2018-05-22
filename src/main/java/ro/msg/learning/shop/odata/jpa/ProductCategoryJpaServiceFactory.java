package ro.msg.learning.shop.odata.jpa;

import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAServiceFactory;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Component
public class ProductCategoryJpaServiceFactory extends ODataJPAServiceFactory {

    private static final String PERSISTENCE_UNIT_NAME = "productCategory";

    private final EntityManagerFactory entityManagerFactory;

    public ProductCategoryJpaServiceFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public ODataJPAContext initializeODataJPAContext() throws ODataJPARuntimeException {
        ODataJPAContext oDataJPAContext = this.getODataJPAContext();
        try {
            oDataJPAContext.setEntityManagerFactory(entityManagerFactory);
            oDataJPAContext.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
            return oDataJPAContext;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
