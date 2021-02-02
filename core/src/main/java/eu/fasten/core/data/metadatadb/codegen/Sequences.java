/*
 * This file is generated by jOOQ.
 */
package eu.fasten.core.data.metadatadb.codegen;


import javax.annotation.processing.Generated;

import org.jooq.Sequence;
import org.jooq.impl.SequenceImpl;


/**
 * Convenience access to all sequences in public
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Sequences {

    /**
     * The sequence <code>public.artifact_repositories_id_seq</code>
     */
    public static final Sequence<Long> ARTIFACT_REPOSITORIES_ID_SEQ = new SequenceImpl<Long>("artifact_repositories_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.artifact_states_id_seq</code>
     */
    public static final Sequence<Long> ARTIFACT_STATES_ID_SEQ = new SequenceImpl<Long>("artifact_states_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.binary_modules_id_seq</code>
     */
    public static final Sequence<Long> BINARY_MODULES_ID_SEQ = new SequenceImpl<Long>("binary_modules_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.callables_id_seq</code>
     */
    public static final Sequence<Long> CALLABLES_ID_SEQ = new SequenceImpl<Long>("callables_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.files_id_seq</code>
     */
    public static final Sequence<Long> FILES_ID_SEQ = new SequenceImpl<Long>("files_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.modules_id_seq</code>
     */
    public static final Sequence<Long> MODULES_ID_SEQ = new SequenceImpl<Long>("modules_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.namespaces_id_seq</code>
     */
    public static final Sequence<Long> NAMESPACES_ID_SEQ = new SequenceImpl<Long>("namespaces_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.package_versions_id_seq</code>
     */
    public static final Sequence<Long> PACKAGE_VERSIONS_ID_SEQ = new SequenceImpl<Long>("package_versions_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.packages_id_seq</code>
     */
    public static final Sequence<Long> PACKAGES_ID_SEQ = new SequenceImpl<Long>("packages_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));
}
