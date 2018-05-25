package ro.msg.learning.shop.odata.jpa;

import lombok.extern.slf4j.Slf4j;
import org.apache.olingo.odata2.api.ODataCallback;
import org.apache.olingo.odata2.api.ODataDebugCallback;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.exception.ODataApplicationException;
import org.apache.olingo.odata2.api.processor.ODataErrorCallback;
import org.apache.olingo.odata2.api.processor.ODataErrorContext;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAServiceFactory;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
@Slf4j
public class ShopJpaServiceFactory extends ODataJPAServiceFactory {

    private static final String PERSISTENCE_UNIT_NAME = "shop";

    private final LocalContainerEntityManagerFactoryBean factoryBean;

    @Autowired
    public ShopJpaServiceFactory(LocalContainerEntityManagerFactoryBean factoryBean) {
        this.factoryBean = factoryBean;
    }

    @Override
    public ODataJPAContext initializeODataJPAContext() throws ODataJPARuntimeException {
        ODataJPAContext oDataJPAContext = this.getODataJPAContext();
        try {
            oDataJPAContext.setEntityManagerFactory(factoryBean.getObject());
            oDataJPAContext.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
            oDataJPAContext.setPageSize(Integer.MAX_VALUE);
            oDataJPAContext.setJPAEdmExtension(new ShopJpaEdmExtension());
            return oDataJPAContext;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ODataCallback> T getCallback(Class<T> callbackInterface) {

        return (T) (callbackInterface.isAssignableFrom(ScenarioErrorCallback.class) ? new ScenarioErrorCallback()
                : callbackInterface.isAssignableFrom(ODataDebugCallback.class) ? new ScenarioDebugCallback()
                : super.getCallback(callbackInterface));

    }

    private final class ScenarioDebugCallback implements ODataDebugCallback {

        @Override
        public boolean isDebugEnabled() {
            return true;
        }
    }

    private class ScenarioErrorCallback implements ODataErrorCallback {

        @Override
        public ODataResponse handleError(final ODataErrorContext context) throws ODataApplicationException {
            if (context.getHttpStatus() == HttpStatusCodes.INTERNAL_SERVER_ERROR) {
                log.error("Internal Server Error: " + context.getException().toString());
            }

            return EntityProvider.writeErrorDocument(context);
        }
    }
}
