package model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QEntity is a Querydsl query type for Entity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QEntity extends EntityPathBase<Entity> {

    private static final long serialVersionUID = -1847768472L;

    public static final QEntity entity = new QEntity("entity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QEntity(String variable) {
        super(Entity.class, forVariable(variable));
    }

    public QEntity(Path<? extends Entity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEntity(PathMetadata metadata) {
        super(Entity.class, metadata);
    }

}

