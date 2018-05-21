package ro.msg.learning.shop.odata;

import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.api.processor.ODataSingleProcessor;
import org.springframework.stereotype.Component;

@Component
public class ProductCategoryServiceFactory extends ODataServiceFactory {

    private final ProductCategoryDataStore productCategoryDataStore;

    public ProductCategoryServiceFactory(ProductCategoryDataStore productCategoryDataStore) {
        this.productCategoryDataStore = productCategoryDataStore;
    }

    @Override
    public ODataService createService(ODataContext ctx) {

        EdmProvider edmProvider = new ProductCategoryEdmProvider();

        ODataSingleProcessor singleProcessor = new ProductCategoryODataSingleProcessor(productCategoryDataStore);

        return createODataSingleProcessorService(edmProvider, singleProcessor);
    }
}
