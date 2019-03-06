package com.iphayao.linebot.service;


import com.iphayao.linebot.entity.MasterDataDetail;
import com.iphayao.linebot.repository.MasterDataDetailRepository;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AppMailServiceImp implements AppMailDataService {

    @Autowired
    private MasterDataDetailRepository masterDataDetailRepository;

    public static List<String> LIST_KEYWORD = new ArrayList<>();
    public static List<String> LIST_PROGRAM = new ArrayList<>();

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<MasterDataDetail> masterDatakey(Long id, String code) {
        List<MasterDataDetail> listKeyword = masterDataDetailRepository.findMasterDataDetailsByIdEquals(id, code);
        LOGGER.info("listKeyword : {}", listKeyword.size());
        String resultKeyword = "";
        List<String> keywordSplitList = null;
        resultKeyword = listKeyword.get(0).getVariable1();
        LOGGER.info("num : {}", resultKeyword);
        switch (code) {
            case "program.list": {
                LIST_PROGRAM = null;
                LIST_PROGRAM = new ArrayList<>();
                keywordSplitList = Arrays.asList(resultKeyword.split("\\s*,\\s*"));
                LOGGER.info("program.list : {}", keywordSplitList);
                for (String progran : keywordSplitList) {
                    LIST_PROGRAM.add(progran);
                }
                LOGGER.info("program : {}", LIST_PROGRAM);
            }

            break;
            case "keyword.list": {
                LIST_KEYWORD = null;
                LIST_KEYWORD = new ArrayList<>();
                keywordSplitList = Arrays.asList(resultKeyword.split("\\s*,\\s*"));
                LOGGER.info("Keywor.list : {}", keywordSplitList);
                for (String keyword : keywordSplitList) {
                    LIST_KEYWORD.add(keyword);
                }
                LOGGER.info("Keywor : {}", LIST_KEYWORD);
            }
            break;
            default:
                break;
        }
        LOGGER.info("Keywor : {}", LIST_KEYWORD);
        LOGGER.info("program : {}", LIST_PROGRAM);
        return null;
    }

    public static boolean checkTextMatches(String str) {
        int countKeyword = 0;
        int countProgram = 0;

        for (String keyword : LIST_KEYWORD) {
            if (str.indexOf(keyword) >= 0) {
                countKeyword++;
            }
        }
        for (String keyword : LIST_PROGRAM) {
            if (str.indexOf(keyword) >= 0) {
                countProgram++;
                break;
            }
        }
        if (countKeyword != 0 && countProgram != 0) {
            return true;

        } else {
            return false;
        }
    }

}
