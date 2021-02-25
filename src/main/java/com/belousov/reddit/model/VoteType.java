package com.belousov.reddit.model;

import java.util.ArrayList;
import java.util.Arrays;

public enum VoteType {
  UPVOTE(1),
  DOWNVOTE(-1);

  private final int direction;

  VoteType(int direction) {
    this.direction = direction;
  }

  /*public static VoteType lookup(Integer direction){
    return Arrays.stream(VoteType.values())
        .filter(value -> value.getDirection().equals(direction))
        .findAny()
        .orElseThrow(() -> new SpringRedditException("Vote not found."));
  }*/

  public Integer getDirection(){
    return direction;
  }
}
