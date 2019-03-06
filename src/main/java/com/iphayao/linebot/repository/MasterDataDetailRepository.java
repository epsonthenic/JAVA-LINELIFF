package com.iphayao.linebot.repository;

import com.iphayao.linebot.entity.MasterDataDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MasterDataDetailRepository extends JpaSpecificationExecutor<MasterDataDetail>,
        JpaRepository<MasterDataDetail, Long>,
        PagingAndSortingRepository<MasterDataDetail, Long> {

    /*
SELECT MASTER_DATA .ID,MASTER_DATA.CODE,MASTER_DATA_DETAIL.CODE , MASTER_DATA_DETAIL.VARIABLE1
FROM MASTER_DATA
LEFT JOIN MASTER_DATA_DETAIL
ON MASTER_DATA .ID  = MASTER_DATA_DETAIL .MASTERDATA
where MASTER_DATA .ID = '100' AND MASTER_DATA_DETAIL.CODE LIKE 'progran.list'
  */

    @Query("SELECT DISTINCT a FROM MasterData b LEFT JOIN MasterDataDetail a ON b.id  = a.masterdata where b.id = :id and a.code like concat(:code)")
    List<MasterDataDetail> findMasterDataDetailsByIdEquals(@Param("id") Long id, @Param("code") String code);

    @Query("SELECT DISTINCT a.variable1 FROM MasterData b LEFT JOIN MasterDataDetail a ON b.id  = a.masterdata where b.id = :id and a.code like concat(:code)")
    List<MasterDataDetail> findByVariable1(@Param("id") Long id, @Param("code") String code);

}
