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
import java.util.Random;

import pl.miloszgilga.domain.user.UserEntity;
import pl.miloszgilga.network.game.dto.PlaneWithWorkersAndRouteDto;
import pl.miloszgilga.domain.in_game_plane_params.InGamePlaneParamEntity;
import pl.miloszgilga.domain.in_game_worker_params.InGameWorkerParamEntity;

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

    public LevelBoostRangeDto getLevelBoostRange(int userLevel) {
        if (userLevel == requiredExpMap.size()) {
            return new LevelBoostRangeDto(requiredExpMap.get(userLevel - 1), userLevel);
        }
        return new LevelBoostRangeDto(requiredExpMap.get(userLevel), requiredExpMap.get(userLevel + 1));
    }

    public ComputedWorkerValues computeWorkerValues(int userLevel) {
        return new ComputedWorkerValues(
            drawOneStat(userLevel),
            drawOneStat(userLevel),
            drawOneStat(userLevel),
            drawOneStat(userLevel)
        );
    }

    public int generateExp(PlaneWithWorkersAndRouteDto details, int userLevel) {
        final double expBoost = expBoostMap.get(userLevel);
        final double baseMultiplier = details.plane().getPlane().getBaseMultiplier();
        final double addtlMultiplier = RandomUtils.nextDouble(0.75, 1.25);
        return (int) (details.route().getRouteHours() * expBoost * addtlMultiplier * baseMultiplier);
    }

    private int drawOneStat(int userLevel) {
        int[] range = {1, 20, 40, 60, 80, 100};
        final List<Integer> scales = staffBoostMap.get(userLevel);

        int sumOfScales = 0;
        for (int scale : scales) {
            sumOfScales += scale;
        }
        final Random rand = new Random();
        int randomScale = rand.nextInt(sumOfScales);

        int i = 0;
        while (i < range.length - 1 && randomScale >= scales.get(i)) {
            randomScale-= scales.get(i);
            i++;
        }
        int lowerLimit = range[i];
        int upperLimit = range[i < 5 ? i + 1 : i];

        double randomNumber = rand.nextDouble();
        int drawNumber = (int) (lowerLimit + randomNumber * (upperLimit - lowerLimit));

        if (drawNumber == 100) {
            if (rand.nextInt(15) != 1) {
                return drawOneStat(userLevel);
            }
        }
        return drawNumber;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public int generatePrize(PlaneWithWorkersAndRouteDto details, int userLevel) {
        final double baseMultiplier = details.plane().getPlane().getBaseMultiplier();
        final double expBoost = expBoostMap.get(userLevel);
        final double additionalPrizeMultiplier = RandomUtils.nextDouble(0.6, 1.4);
        return (int) (details.route().getRouteHours() * baseMultiplier * additionalPrizeMultiplier * expBoost);
    }

    public int generateFlightCosts(PlaneWithWorkersAndRouteDto details, int prize) {
        int totalCost = 0;
        for (final InGameWorkerParamEntity worker : details.workers()) {
            final int points = worker.getCooperation() + worker.getSkills() + worker.getExperience();
            totalCost += RandomUtils.nextInt((points / 10), points);
        }
        return prize - totalCost;
    }

    public BoostLevelDto checkIfBoostLevel(int exp, UserEntity user) {
        int i = 0;
        long expAfter = user.getExp() + exp;
        while (checkForLevelUp(i, expAfter, user.getLevel())) {
            i++;
        }
        boolean levelUp = i != 0;

        final int upgrLevel = user.getLevel() + i;
        final long expFrom = requiredExpMap.get(upgrLevel);
        final long expTo = requiredExpMap.get(upgrLevel == requiredExpMap.size() ? upgrLevel : upgrLevel + 1);

        return new BoostLevelDto(expFrom, expTo, (byte) upgrLevel, levelUp);
    }

    public boolean checkForLevelUp(int i, long expAfter, int currentLevel) {
        if (currentLevel + i == requiredExpMap.size()){
            return false;
        }
        return requiredExpMap.get(currentLevel + 1 + i) < expAfter;
    }

    public int getMaxLevel() {
        return requiredExpMap.size();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void generateDamage(InGamePlaneParamEntity plane) {
        plane.setEngine(generateGeneralDamage(0, 5, plane.getEngine()));
        plane.setLandingGeer(generateGeneralDamage(0, 5, plane.getEngine()));
        plane.setWings(generateGeneralDamage(0, 5, plane.getEngine()));
    }

    public int generateGeneralDamage(int from, int to, int initialValue) {
        final boolean isGenerating = RandomUtils.nextBoolean();
        int outputDamage = initialValue;
        if (isGenerating) {
            final int damage = RandomUtils.nextInt(from, to);
            outputDamage -= damage;
            return Math.max(outputDamage, 0);
        }
        return outputDamage;
    }
}
