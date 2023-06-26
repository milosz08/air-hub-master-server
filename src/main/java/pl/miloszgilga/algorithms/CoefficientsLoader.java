/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: CoefficientsLoader.java
 * Last modified: 6/24/23, 3:12 AM
 * Project name: air-hub-master-server
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 *
 *     <http://www.apache.org/license/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the license.
 */

package pl.miloszgilga.algorithms;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.StringUtils;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.BiConsumer;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Slf4j
@Component
@RequiredArgsConstructor
class CoefficientsLoader {

    private final ResourceLoader resourceLoader;

    private static final String BOOST_MAP_FILE_NAME = "classpath:algorithms/boost-map.txt";
    private static final String REQ_EXP_MAP_FILE_NAME = "classpath:algorithms/req-exp-map.txt";
    private static final String EXP_BOOST_MAP_FILE_NAME = "classpath:algorithms/exp-boost-map.txt";

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Map<Integer, List<Integer>> loadBoostMap() {
        final Map<Integer, List<Integer>> staffBoostMap = new HashMap<>();
        getFileAndExtractData(BOOST_MAP_FILE_NAME, (key, restOfData) -> {
            final List<Integer> valueList = new ArrayList<>();
            for (int i = 1; i < restOfData.length; i++) {
                valueList.add(Integer.parseInt(restOfData[i]));
            }
            staffBoostMap.put(key, valueList);
        });
        log.info("Successfully loaded '{}' file from classpath resources", BOOST_MAP_FILE_NAME);
        return staffBoostMap;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Map<Integer, Long> loadReqExpMap() {
        final Map<Integer, Long> reqExpMap = new HashMap<>();
        getFileAndExtractData(REQ_EXP_MAP_FILE_NAME, (key, restOfData) ->
            reqExpMap.put(key, Long.parseLong(restOfData[1])));
        log.info("Successfully loaded '{}' file from classpath resources", REQ_EXP_MAP_FILE_NAME);
        return reqExpMap;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Map<Integer, Double> loadExpBoostMap() {
        final Map<Integer, Double> expBoostMap = new HashMap<>();
        getFileAndExtractData(EXP_BOOST_MAP_FILE_NAME, (key, restOfData) ->
            expBoostMap.put(key, Double.parseDouble(restOfData[1])));
        log.info("Successfully loaded '{}' file from classpath resources", EXP_BOOST_MAP_FILE_NAME);
        return expBoostMap;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void getFileAndExtractData(String filename, BiConsumer<Integer, String[]> extractData) {
        final Resource resource = resourceLoader.getResource(filename);
        try {
            final InputStream inputStream = resource.getInputStream();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                final String[] values = StringUtils.split(line, ",");
                final int key = Integer.parseInt(Objects.requireNonNull(values[0]));
                extractData.accept(key, values);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
