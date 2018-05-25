package ro.msg.learning.shop.odata.jpa;

import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmExtension;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmSchemaView;

import java.io.InputStream;

public class ShopJpaEdmExtension implements JPAEdmExtension {
    @Override
    public void extendWithOperation(JPAEdmSchemaView view) {

    }

    @Override
    public void extendJPAEdmSchema(JPAEdmSchemaView view) {

    }

    @Override
    public InputStream getJPAEdmMappingModelStream() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        return classloader.getResourceAsStream("JpaEdmMappingModel.xml");
    }
}
