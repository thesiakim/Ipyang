package com.project.ipyang.domain.point.repository;

import com.project.ipyang.domain.point.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point,Long> {
}
