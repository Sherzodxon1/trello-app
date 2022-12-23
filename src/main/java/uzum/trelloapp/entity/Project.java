package uzum.trelloapp.entity;

import lombok.*;
import uzum.trelloapp.base.BaseEntity;
import uzum.trelloapp.enums.GrPrType;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project")
public class Project extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Group group;
    @Column(name = "group_id")
    private Integer groupId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User owner;
    @Column(name = "owner_id")
    private Integer ownerId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "gr_pr_type")
    private GrPrType grPrType;
}
