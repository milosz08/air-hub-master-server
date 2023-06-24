/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: GameAlgorithms.java
 * Last modified: 6/24/23, 2:31 AM
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

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.List;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Component
public class GameAlgorithms {

    private final Map<Integer, List<Integer>> staffBoostMap;
    private final Map<Integer, Long> requiredExpMap;
    private final Map<Integer, Double> expBoostMap;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    GameAlgorithms(CoefficientsLoader coefficientsLoader) {
        staffBoostMap = coefficientsLoader.loadBoostMap();
        requiredExpMap = coefficientsLoader.loadReqExpMap();
        expBoostMap = coefficientsLoader.loadExpBoostMap();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ComputedWorkerValues computeWorkerValues(int userLevel) {
        return new ComputedWorkerValues(
            drawOneStat(userLevel),
            drawOneStat(userLevel),
            drawOneStat(userLevel),
            drawOneStat(userLevel)
        );
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private int drawOneStat(int userLevel) {
        final int[] range = { 1, 20, 40, 60, 80, 100 };
        final List<Integer> scales = staffBoostMap.get(userLevel);

        int sumOfScales = 0;
        for (final int scale : scales) {
            sumOfScales += scale;
        }
        int randomScale = RandomUtils.nextInt(1, sumOfScales);

        int i = 0;
        while (i < range.length - 1 && randomScale >= scales.get(i)) {
            randomScale -= scales.get(i);
            i++;
        }
        int lowerLimit = range[i];
        int upperLimit;
        if (i < 5) {
            upperLimit = range[i + 1];
        } else {
            upperLimit = range[i];
        }

        final double randomNumber = RandomUtils.nextDouble();
        final int drawNumber = (int) (lowerLimit + randomNumber * (upperLimit - lowerLimit));
        if(drawNumber != 100) return drawNumber;

        final int randNumHun = RandomUtils.nextInt(1, 15);
        if(randNumHun != 1) return drawOneStat(userLevel);

        return drawNumber;
    }
}
