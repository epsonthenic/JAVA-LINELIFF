package com.iphayao.linebot.repository;

import com.iphayao.linebot.entity.MasterData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MasterDataRepository extends JpaSpecificationExecutor<MasterData>,
        JpaRepository<MasterData, Long>,
        PagingAndSortingRepository<MasterData, Long> {

}
