package com.twoleader.backend.domain.roomUser.repository;

import com.twoleader.backend.domain.roomUser.entity.RoomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomUserRepository extends JpaRepository<RoomUser, Long> {}
