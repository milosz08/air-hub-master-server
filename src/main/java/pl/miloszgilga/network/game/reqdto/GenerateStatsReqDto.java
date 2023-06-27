/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: SendPlaneReqDto.java
 * Last modified: 6/26/23, 7:10 AM
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

package pl.miloszgilga.network.game.reqdto;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

import java.util.List;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Data
@NoArgsConstructor
public class GenerateStatsReqDto {

    @NotNull(message = "jpa.validator.planeId.notNull")
    private Long planeId;

    @NotNull(message = "jpa.validator.crew.notNull")
    @Size(min = 3, message = "jpa.validator.crew.size")
    private List<Long> crew;

    @NotNull(message = "jpa.validator.routeId.notNull")
    private Long routeId;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public String toString() {
        return "{" +
            "planeId=" + planeId +
            ", crew=" + crew +
            ", routeId=" + routeId +
            '}';
    }
}
