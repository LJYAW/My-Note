package com.sino.fas.res.host.entity;

import javax.persistence.*;

/**
 * @ProjectName: SinoITOM_V2
 * @Package: com.sino.fas.res.host.entity
 * @ClassName: ResCompHosts
 * @auther: Mr.Lp
 * @date: 2020/5/12 18:05
 * @desc: DOTO
 * Created with: IntelliJ IDEA
 */

@Entity
@Table(name = "Res_CompHosts", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class ResCompHosts {

    private Long id;
    private Long compHostId;
    private Long hostId;

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompHostId() {
        return compHostId;
    }

    public void setCompHostId(Long compHostId) {
        this.compHostId = compHostId;
    }

    public Long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }
}
