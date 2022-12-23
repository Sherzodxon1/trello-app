package uzum.trelloapp.entity;

import lombok.*;
import uzum.trelloapp.base.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
public class Task extends BaseEntity {

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Project project;
    @Column(name = "project_id")
    private Integer projectId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_column_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProjectColumn projectColumn;
    @Column(name = "project_column_id")
    private Integer projectColumnId;

    @Column(name = "position")
    private Integer position;

}
