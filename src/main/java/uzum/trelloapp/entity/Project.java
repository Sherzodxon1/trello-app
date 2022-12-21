package uzum.trelloapp.entity;

import lombok.*;
import uzum.trelloapp.base.BaseEntity;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "project")
public class Project extends BaseEntity {

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Group group;
    @Column(name = "group_id")
    private Integer groupId;
}
