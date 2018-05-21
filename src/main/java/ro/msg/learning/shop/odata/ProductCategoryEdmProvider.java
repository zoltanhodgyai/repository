package ro.msg.learning.shop.odata;

import org.apache.olingo.odata2.api.edm.EdmMultiplicity;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.*;
import org.apache.olingo.odata2.api.exception.ODataException;

import java.util.ArrayList;
import java.util.List;

public class ProductCategoryEdmProvider extends EdmProvider {

    static final String ENTITY_SET_NAME_PRODUCT_CATEGORY = "ProductCategories";
    static final String ENTITY_NAME_PRODUCT_CATEGORY = "ProductCategory";

    private static final String NAMESPACE = "org.apache.olingo.odata2.ODataProductCategories";

    private static final FullQualifiedName ENTITY_TYPE_1_1 = new FullQualifiedName(NAMESPACE, ENTITY_NAME_PRODUCT_CATEGORY);

    private static final String ENTITY_CONTAINER = "ODataProductCategoryEntityContainer";

    @Override
    public List<Schema> getSchemas() throws ODataException {
        List<Schema> schemas = new ArrayList<>();

        Schema schema = new Schema();
        schema.setNamespace(NAMESPACE);

        List<EntityType> entityTypes = new ArrayList<>();
        entityTypes.add(getEntityType(ENTITY_TYPE_1_1));
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
            if (ENTITY_TYPE_1_1.getName().equals(edmFQName.getName())) {
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

                return new EntityType().setName(ENTITY_TYPE_1_1.getName()).setProperties(properties).setKey(key);
            }
            // other entity types (Product, etc.)
            //else if () {
            //  }
        }

        return null;
    }

    @Override
    public FunctionImport getFunctionImport(final String entityContainer, final String name) throws ODataException {
        if (ENTITY_CONTAINER.equals(entityContainer)) {
            return new FunctionImport().setName(name)
                    .setReturnType(new ReturnType().setTypeName(ENTITY_TYPE_1_1).setMultiplicity(EdmMultiplicity.MANY))
                    .setHttpMethod("GET");
        }
        return null;
    }

    @Override
    public EntitySet getEntitySet(final String entityContainer, final String name) throws ODataException {
        if (ENTITY_CONTAINER.equals(entityContainer)) {
            if (ENTITY_SET_NAME_PRODUCT_CATEGORY.equals(name)) {
                return new EntitySet().setName(name).setEntityType(ENTITY_TYPE_1_1);
            }
        }
        return null;
    }

    @Override
    public EntityContainerInfo getEntityContainerInfo(final String name) throws ODataException {
        if (name == null || ENTITY_CONTAINER.equals(name)) {
            return new EntityContainerInfo().setName(ENTITY_CONTAINER).setDefaultEntityContainer(true);
        }

        return null;
    }
}
