package uzum.trelloapp.entity;

import lombok.*;
import uzum.trelloapp.base.BaseEntity;
import uzum.trelloapp.enums.GroupType;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "groups")
public class Group extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User owner;
    @Column(name = "owner_id")
    private Long ownerId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type")
    private GroupType type;

}
