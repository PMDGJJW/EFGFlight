package com.pmdgjjw.efgflight.entity;

/**
 * @auth jian j w
 * @date 2020/7/15 12:57
 * @Description
 */
public class PartitionInfo {

    private Integer id;

    private String name;

    private Double comSize;

    private Integer spitSize;

    private Long incrScore;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getComSize() {
        return comSize;
    }

    public void setComSize(Double comSize) {
        this.comSize = comSize;
    }

    public Integer getSpitSize() {
        return spitSize;
    }

    public void setSpitSize(Integer spitSize) {
        this.spitSize = spitSize;
    }

    public Long getIncrScore() {
        return incrScore;
    }

    public void setIncrScore(Long incrScore) {
        this.incrScore = incrScore;
    }

    @Override
    public String toString() {
        return "PartitionInfo{" +
                "comSize=" + comSize +
                ", spitSize=" + spitSize +
                ", incrScore=" + incrScore +
                '}';
    }
}
