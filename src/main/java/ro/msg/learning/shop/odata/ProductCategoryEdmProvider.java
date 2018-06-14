package ro.msg.learning.shop.odata;

import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.*;
import org.apache.olingo.odata2.api.exception.ODataException;

import java.util.ArrayList;
import java.util.List;

public class ProductCategoryEdmProvider extends EdmProvider {

    static final String ENTITY_SET_NAME_PRODUCT_CATEGORY = "ProductCategories";
    private static final String ENTITY_NAME_PRODUCT_CATEGORY = "ProductCategory";

    private static final String NAMESPACE = "org.apache.olingo.odata2.ODataProductCategories";

    private static final FullQualifiedName PRODUCT_CATEGORY_ENTITY_TYPE = new FullQualifiedName(NAMESPACE, ENTITY_NAME_PRODUCT_CATEGORY);

    private static final String ENTITY_CONTAINER = "ODataProductCategoryEntityContainer";

    @Override
    public List<Schema> getSchemas() throws ODataException {
        List<Schema> schemas = new ArrayList<>();

        Schema schema = new Schema();
        schema.setNamespace(NAMESPACE);

        List<EntityType> entityTypes = new ArrayList<>();
        entityTypes.add(getEntityType(PRODUCT_CATEGORY_ENTITY_TYPE));
        schema.setEntityTypes(entityTypes);

        List<ComplexType> complexTypes = new ArrayList<>();
        schema.setComplexTypes(complexTypes);

        List<Association> associations = new ArrayList<>();
        schema.setAssociations(associations);

        List<EntityContainer> entityContainers = new ArrayList<>();
        EntityContainer entityContainer = new EntityContainer();
        entityContainer.setName(ENTITY_CONTAINER).setDefaultEntityContainer(true);

        List<EntitySet> entitySets = new ArrayList<>();
        entitySets.add(getEntitySet(ENTITY_CONTAINER, ENTITY_SET_NAME_PRODUCT_CATEGORY));
        entityContainer.setEntitySets(entitySets);

        List<AssociationSet> associationSets = new ArrayList<>();
        entityContainer.setAssociationSets(associationSets);

        List<FunctionImport> functionImports = new ArrayList<>();
        entityContainer.setFunctionImports(functionImports);

        entityContainers.add(entityContainer);
        schema.setEntityContainers(entityContainers);

        schemas.add(schema);

        return schemas;
    }

    @Override
    public EntityType getEntityType(final FullQualifiedName edmFQName) throws ODataException {
        if (NAMESPACE.equals(edmFQName.getNamespace())) {
            if (PRODUCT_CATEGORY_ENTITY_TYPE.getName().equals(edmFQName.getName())) {
                // Properties
                List<Property> properties = new ArrayList<>();
                properties.add(new SimpleProperty().setName("Id").setType(EdmSimpleTypeKind.Int32).setFacets(
                        new Facets().setNullable(false)));
                properties.add(new SimpleProperty().setName("Name").setType(EdmSimpleTypeKind.String));
                properties.add(new SimpleProperty().setName("Description").setType(EdmSimpleTypeKind.String));

                // Navigation Properties - we don't have this yet

                // Key
                List<PropertyRef> keyProperties = new ArrayList<>();
                keyProperties.add(new PropertyRef().setName("Id"));
                Key key = new Key().setKeys(keyProperties);

                return new EntityType().setName(PRODUCT_CATEGORY_ENTITY_TYPE.getName()).setProperties(properties).setKey(key);
            }
            // other entity types (Product, etc.)
            //else if () {
            //  }
        }

        return null;
    }
}
