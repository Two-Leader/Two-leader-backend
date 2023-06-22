package com.twoleader.backend.domain.roomUser.dto.request;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class GetRoomUserRequest {
  @NotNull private UUID userUuid;
}
