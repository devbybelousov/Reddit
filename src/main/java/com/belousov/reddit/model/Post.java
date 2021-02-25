package com.belousov.reddit.model;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long postId;

  @NotBlank(message = "Post Name cannot be empty or Null")
  private String postName;

  @Nullable
  private String url;

  @Nullable
  @Lob
  private String description;
  private Integer voteCount = 0;

  @ManyToOne
  @JoinColumn(name = "userId", referencedColumnName = "userId")
  private User user;
  private Instant createdDate;

  @ManyToOne
  @JoinColumn(name = "id", referencedColumnName = "id")
  private Subreddit subreddit;
}
