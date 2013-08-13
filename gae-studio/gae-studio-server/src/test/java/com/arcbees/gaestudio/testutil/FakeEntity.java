package com.arcbees.gaestudio.testutil;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class FakeEntity {
    @Id
    private Long id;
    @Index
    private String name;
    @Index
    private String nickname;

    FakeEntity() {
    }

    public FakeEntity(String name, String nickname) {
        this.name = name;
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }
}
