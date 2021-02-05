/*
 * This file is generated by jOOQ.
 */
package eu.fasten.core.data.metadatadb.codegen.tables.records;


import eu.fasten.core.data.metadatadb.codegen.tables.Dependencies;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.JSONB;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.TableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DependenciesRecord extends TableRecordImpl<DependenciesRecord> implements Record7<Long, Long, String[], String[], String[], Long, JSONB> {

    private static final long serialVersionUID = 1083077090;

    /**
     * Setter for <code>public.dependencies.package_version_id</code>.
     */
    public void setPackageVersionId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.dependencies.package_version_id</code>.
     */
    public Long getPackageVersionId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.dependencies.dependency_id</code>.
     */
    public void setDependencyId(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.dependencies.dependency_id</code>.
     */
    public Long getDependencyId() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>public.dependencies.version_range</code>.
     */
    public void setVersionRange(String... value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.dependencies.version_range</code>.
     */
    public String[] getVersionRange() {
        return (String[]) get(2);
    }

    /**
     * Setter for <code>public.dependencies.architecture</code>.
     */
    public void setArchitecture(String... value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.dependencies.architecture</code>.
     */
    public String[] getArchitecture() {
        return (String[]) get(3);
    }

    /**
     * Setter for <code>public.dependencies.dependency_type</code>.
     */
    public void setDependencyType(String... value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.dependencies.dependency_type</code>.
     */
    public String[] getDependencyType() {
        return (String[]) get(4);
    }

    /**
     * Setter for <code>public.dependencies.alternative_group</code>.
     */
    public void setAlternativeGroup(Long value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.dependencies.alternative_group</code>.
     */
    public Long getAlternativeGroup() {
        return (Long) get(5);
    }

    /**
     * Setter for <code>public.dependencies.metadata</code>.
     */
    public void setMetadata(JSONB value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.dependencies.metadata</code>.
     */
    public JSONB getMetadata() {
        return (JSONB) get(6);
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row7<Long, Long, String[], String[], String[], Long, JSONB> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    @Override
    public Row7<Long, Long, String[], String[], String[], Long, JSONB> valuesRow() {
        return (Row7) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return Dependencies.DEPENDENCIES.PACKAGE_VERSION_ID;
    }

    @Override
    public Field<Long> field2() {
        return Dependencies.DEPENDENCIES.DEPENDENCY_ID;
    }

    @Override
    public Field<String[]> field3() {
        return Dependencies.DEPENDENCIES.VERSION_RANGE;
    }

    @Override
    public Field<String[]> field4() {
        return Dependencies.DEPENDENCIES.ARCHITECTURE;
    }

    @Override
    public Field<String[]> field5() {
        return Dependencies.DEPENDENCIES.DEPENDENCY_TYPE;
    }

    @Override
    public Field<Long> field6() {
        return Dependencies.DEPENDENCIES.ALTERNATIVE_GROUP;
    }

    @Override
    public Field<JSONB> field7() {
        return Dependencies.DEPENDENCIES.METADATA;
    }

    @Override
    public Long component1() {
        return getPackageVersionId();
    }

    @Override
    public Long component2() {
        return getDependencyId();
    }

    @Override
    public String[] component3() {
        return getVersionRange();
    }

    @Override
    public String[] component4() {
        return getArchitecture();
    }

    @Override
    public String[] component5() {
        return getDependencyType();
    }

    @Override
    public Long component6() {
        return getAlternativeGroup();
    }

    @Override
    public JSONB component7() {
        return getMetadata();
    }

    @Override
    public Long value1() {
        return getPackageVersionId();
    }

    @Override
    public Long value2() {
        return getDependencyId();
    }

    @Override
    public String[] value3() {
        return getVersionRange();
    }

    @Override
    public String[] value4() {
        return getArchitecture();
    }

    @Override
    public String[] value5() {
        return getDependencyType();
    }

    @Override
    public Long value6() {
        return getAlternativeGroup();
    }

    @Override
    public JSONB value7() {
        return getMetadata();
    }

    @Override
    public DependenciesRecord value1(Long value) {
        setPackageVersionId(value);
        return this;
    }

    @Override
    public DependenciesRecord value2(Long value) {
        setDependencyId(value);
        return this;
    }

    @Override
    public DependenciesRecord value3(String... value) {
        setVersionRange(value);
        return this;
    }

    @Override
    public DependenciesRecord value4(String... value) {
        setArchitecture(value);
        return this;
    }

    @Override
    public DependenciesRecord value5(String... value) {
        setDependencyType(value);
        return this;
    }

    @Override
    public DependenciesRecord value6(Long value) {
        setAlternativeGroup(value);
        return this;
    }

    @Override
    public DependenciesRecord value7(JSONB value) {
        setMetadata(value);
        return this;
    }

    @Override
    public DependenciesRecord values(Long value1, Long value2, String[] value3, String[] value4, String[] value5, Long value6, JSONB value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached DependenciesRecord
     */
    public DependenciesRecord() {
        super(Dependencies.DEPENDENCIES);
    }

    /**
     * Create a detached, initialised DependenciesRecord
     */
    public DependenciesRecord(Long packageVersionId, Long dependencyId, String[] versionRange, String[] architecture, String[] dependencyType, Long alternativeGroup, JSONB metadata) {
        super(Dependencies.DEPENDENCIES);

        set(0, packageVersionId);
        set(1, dependencyId);
        set(2, versionRange);
        set(3, architecture);
        set(4, dependencyType);
        set(5, alternativeGroup);
        set(6, metadata);
    }
}
