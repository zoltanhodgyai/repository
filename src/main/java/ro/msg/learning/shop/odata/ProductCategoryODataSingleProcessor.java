package ro.msg.learning.shop.odata;

import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmLiteralKind;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmSimpleType;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.ep.EntityProviderReadProperties;
import org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties;
import org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties.ODataEntityProviderPropertiesBuilder;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.exception.ODataNotFoundException;
import org.apache.olingo.odata2.api.exception.ODataNotImplementedException;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.processor.ODataSingleProcessor;
import org.apache.olingo.odata2.api.uri.KeyPredicate;
import org.apache.olingo.odata2.api.uri.info.*;
import ro.msg.learning.shop.model.ProductCategory;

import java.io.InputStream;
import java.net.URI;
import java.util.Map;

import static ro.msg.learning.shop.odata.ProductCategoryEdmProvider.ENTITY_SET_NAME_PRODUCT_CATEGORY;

public class ProductCategoryODataSingleProcessor extends ODataSingleProcessor {

    private final ProductCategoryDataStore productCategoryDataStore;

    ProductCategoryODataSingleProcessor(ProductCategoryDataStore productCategoryDataStore) {
        this.productCategoryDataStore = productCategoryDataStore;
    }

    @Override
    public ODataResponse readEntitySet(final GetEntitySetUriInfo uriInfo, final String contentType)
            throws ODataException {

        EdmEntitySet entitySet;

        if (uriInfo.getNavigationSegments().isEmpty()) {
            entitySet = uriInfo.getStartEntitySet();

            if (ENTITY_SET_NAME_PRODUCT_CATEGORY.equals(entitySet.getName())) {
                return EntityProvider.writeFeed(contentType, entitySet, productCategoryDataStore.getProductCategories(),
                        EntityProviderWriteProperties.serviceRoot(getContext().getPathInfo().getServiceRoot()).build());
            }
            throw new ODataNotFoundException(ODataNotFoundException.ENTITY);
        }

        throw new ODataNotImplementedException();
    }

    @Override
    public ODataResponse readEntity(final GetEntityUriInfo uriInfo, final String contentType)
            throws ODataException {

        if (uriInfo.getNavigationSegments().isEmpty()) {
            EdmEntitySet entitySet = uriInfo.getStartEntitySet();

            if (ENTITY_SET_NAME_PRODUCT_CATEGORY.equals(entitySet.getName())) {
                Integer id = getKeyValue(uriInfo.getKeyPredicates().get(0));
                Map<String, Object> data = productCategoryDataStore.getProductCategory(id);

                if (data != null) {
                    URI serviceRoot = getContext().getPathInfo().getServiceRoot();
                    ODataEntityProviderPropertiesBuilder propertiesBuilder =
                            EntityProviderWriteProperties.serviceRoot(serviceRoot);

                    return EntityProvider.writeEntry(contentType, entitySet, data, propertiesBuilder.build());
                }
            }
        }

        throw new ODataNotImplementedException();
    }

    @Override
    public ODataResponse createEntity(final PostUriInfo uriInfo, final InputStream content,
                                      final String requestContentType, final String contentType) throws ODataException {
        //No support for creating and linking a new entry
        if (!uriInfo.getNavigationSegments().isEmpty()) {
            throw new ODataNotImplementedException();
        }

        //No support for media resources
        if (uriInfo.getStartEntitySet().getEntityType().hasStream()) {
            throw new ODataNotImplementedException();
        }

        EntityProviderReadProperties properties = EntityProviderReadProperties.init().mergeSemantic(false).build();

        ODataEntry entry = EntityProvider.readEntry(requestContentType, uriInfo.getStartEntitySet(), content, properties);

        Map<String, Object> data = entry.getProperties();

        ProductCategory productCategory = new ProductCategory();
        productCategory.setName((String) data.get("Name"));
        productCategory.setDescription((String) data.get("Description"));

        data = productCategoryDataStore.saveProductCategory(productCategory);

        return EntityProvider.writeEntry(contentType, uriInfo.getStartEntitySet(), data, EntityProviderWriteProperties.serviceRoot(getContext().getPathInfo().getServiceRoot()).build());
    }

    @Override
    public ODataResponse deleteEntity(final DeleteUriInfo uriInfo, final String contentType) throws ODataException {

        if (uriInfo.getNavigationSegments().isEmpty()) {
            EdmEntitySet entitySet = uriInfo.getStartEntitySet();

            if (ENTITY_SET_NAME_PRODUCT_CATEGORY.equals(entitySet.getName())) {
                Integer id = getKeyValue(uriInfo.getKeyPredicates().get(0));
                productCategoryDataStore.deleteProductCategory(id);

                return EntityProvider.writeText("Deleted");
            }
        }

        throw new ODataNotImplementedException();
    }

    @Override
    public ODataResponse updateEntity(final PutMergePatchUriInfo uriInfo, final InputStream content,
                                      final String requestContentType, final boolean merge, final String contentType) throws ODataException {

        if (!uriInfo.getNavigationSegments().isEmpty()) {
            throw new ODataNotImplementedException();
        }

        //No support for media resources
        if (uriInfo.getStartEntitySet().getEntityType().hasStream()) {
            throw new ODataNotImplementedException();
        }

        EntityProviderReadProperties properties = EntityProviderReadProperties.init().mergeSemantic(false).build();

        ODataEntry entry = EntityProvider.readEntry(requestContentType, uriInfo.getStartEntitySet(), content, properties);

        Map<String, Object> data = entry.getProperties();

        ProductCategory productCategory = new ProductCategory();

        productCategory.setId((Integer) data.get("Id"));
        productCategory.setName((String) data.get("Name"));
        productCategory.setDescription((String) data.get("Description"));

        data = productCategoryDataStore.saveProductCategory(productCategory);

        return EntityProvider.writeEntry(contentType, uriInfo.getStartEntitySet(), data, EntityProviderWriteProperties.serviceRoot(getContext().getPathInfo().getServiceRoot()).build());
    }

    private int getKeyValue(final KeyPredicate key) throws ODataException {
        EdmProperty property = key.getProperty();
        EdmSimpleType type = (EdmSimpleType) property.getType();
        return type.valueOfString(key.getLiteral(), EdmLiteralKind.DEFAULT, property.getFacets(), Integer.class);
    }
}
