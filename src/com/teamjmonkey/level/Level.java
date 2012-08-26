package com.teamjmonkey.level;

import com.teamjmonkey.entity.BaseEntity;
import java.util.LinkedList;

public interface Level  {

    public void load();
    public void cleanup();
    public LinkedList<BaseEntity> getAllEntities();
}
