package pl.miloszgilga.ahms.algorithms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.BiConsumer;

@Slf4j
@Component
@RequiredArgsConstructor
class CoefficientsLoader {
    private static final String BOOST_MAP_FILE_NAME = "classpath:algorithms/boost-map.txt";
    private static final String REQ_EXP_MAP_FILE_NAME = "classpath:algorithms/req-exp-map.txt";
    private static final String EXP_BOOST_MAP_FILE_NAME = "classpath:algorithms/exp-boost-map.txt";

    private final ResourceLoader resourceLoader;

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

    Map<Integer, Long> loadReqExpMap() {
        final Map<Integer, Long> reqExpMap = new HashMap<>();
        getFileAndExtractData(REQ_EXP_MAP_FILE_NAME, (key, restOfData) ->
            reqExpMap.put(key, Long.parseLong(restOfData[1])));
        log.info("Successfully loaded '{}' file from classpath resources", REQ_EXP_MAP_FILE_NAME);
        return reqExpMap;
    }

    Map<Integer, Double> loadExpBoostMap() {
        final Map<Integer, Double> expBoostMap = new HashMap<>();
        getFileAndExtractData(EXP_BOOST_MAP_FILE_NAME, (key, restOfData) ->
            expBoostMap.put(key, Double.parseDouble(restOfData[1])));
        log.info("Successfully loaded '{}' file from classpath resources", EXP_BOOST_MAP_FILE_NAME);
        return expBoostMap;
    }

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
            throw new RuntimeException(ex);
        }
    }
}
