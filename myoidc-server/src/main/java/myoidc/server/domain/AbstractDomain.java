package myoidc.server.domain;


import myoidc.server.domain.shared.UUIDGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Shengzhao Li
 */
@MappedSuperclass
public abstract class AbstractDomain implements Serializable {

    private static final long serialVersionUID = 913921286328215144L;
    /**
     * Database id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected int id;

    @Column(name = "archived", columnDefinition = "tinyint(1)")
    protected boolean archived;

    /**
     * 乐观锁
     */
    @Version
    @Column(name = "version")
    protected int version;

    /**
     * Domain business uuid.
     */
    @Column(name = "uuid", unique = true)
    protected String uuid = UUIDGenerator.generate();

    /**
     * The domain create time.
     */
    @Column(name = "create_time")
//    @Type(type = "org.hibernate.type.LocalDateTimeType")
    protected LocalDateTime createTime = LocalDateTime.now();


    public AbstractDomain() {
    }

    public int id() {
        return id;
    }

    public void id(int id) {
        this.id = id;
    }

    public boolean archived() {
        return archived;
    }

    public AbstractDomain archived(boolean archived) {
        this.archived = archived;
        return this;
    }

    public String uuid() {
        return uuid;
    }

    public void uuid(String uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime createTime() {
        return createTime;
    }

    public boolean isNewly() {
        return id == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractDomain)) {
            return false;
        }
        AbstractDomain that = (AbstractDomain) o;
        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("{id=").append(id);
        sb.append(", archived=").append(archived);
        sb.append(", version=").append(version);
        sb.append(", uuid='").append(uuid).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}