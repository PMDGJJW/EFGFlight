package com.pmdgjjw.efgflight.entity;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @auth jian j w
 * @date 2020/6/28 15:58
 * @Description
 */

@Table(name = "partiton_detail")
public class PartitionDetail implements Serializable {

    @Id
    private Long id;

    @Column(name = "user_id" )
    private Long userId;

    @Column(name = "partition_id")
    private Long partitionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPartitionId() {
        return partitionId;
    }

    public void setPartitionId(Long partitionId) {
        this.partitionId = partitionId;
    }

    @Override
    public String toString() {
        return "PartitionDetail{" +
                "id=" + id +
                ", userId=" + userId +
                ", partitionId=" + partitionId +
                '}';
    }
}
