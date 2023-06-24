/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: ShopPlanesResDto.java
 * Last modified: 6/23/23, 12:03 AM
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

package pl.miloszgilga.network.shop.resdto;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public record ShopPlanesResDto(
    Long id,
    String planeName,
    String categoryName,
    int landingGeer,
    int wings,
    int engine,
    byte upgrade,
    int price
) {
    public ShopPlanesResDto(Long id, String planeName, String categoryName, int price) {
        this(id, planeName, categoryName, 100, 100, 100, (byte) 0, price);
    }
}
