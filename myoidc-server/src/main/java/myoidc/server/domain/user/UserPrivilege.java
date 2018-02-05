package myoidc.server.domain.user;



import myoidc.server.domain.AbstractDomain;

import javax.persistence.*;

/**
 * @author Shengzhao Li
 */
@Entity
@Table(name = "user_privilege")
public class UserPrivilege extends AbstractDomain {

    private static final long serialVersionUID = -7207158121413995079L;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "privilege")
    @Enumerated(value = EnumType.STRING)
    private Privilege privilege;

    public UserPrivilege() {
    }

    public UserPrivilege(User user, Privilege privilege) {
        this.user = user;
        this.privilege = privilege;
    }

    public Privilege privilege() {
        return privilege;
    }

    public User user() {
        return user;
    }
}