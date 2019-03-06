package com.iphayao.linebot.service;

import com.iphayao.linebot.entity.MasterDataDetail;

import java.util.List;

public interface AppMailDataService {

    List<MasterDataDetail> masterDatakey(Long id, String code);
}
