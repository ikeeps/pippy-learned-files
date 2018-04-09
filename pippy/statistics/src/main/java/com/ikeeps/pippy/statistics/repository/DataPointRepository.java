package com.ikeeps.pippy.statistics.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ikeeps.pippy.statistics.domain.timeseries.DataPoint;
import com.ikeeps.pippy.statistics.domain.timeseries.DataPointId;

import java.util.List;

@Repository
public interface DataPointRepository extends CrudRepository<DataPoint, DataPointId> {

	List<DataPoint> findByIdAccount(String account);

}
