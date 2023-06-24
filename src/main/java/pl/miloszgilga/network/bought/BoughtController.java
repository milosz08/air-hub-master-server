/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: BoughtController.java
 * Last modified: 6/24/23, 2:10 AM
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

package pl.miloszgilga.network.bought;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.jmpsl.security.user.AuthUser;
import org.jmpsl.security.user.CurrentUser;

import java.util.List;

import pl.miloszgilga.network.bought.resdto.InGamePlaneResDto;
import pl.miloszgilga.network.bought.resdto.InGameWorkerResDto;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/bought")
public class BoughtController {

    private final IBoughtService boughtService;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/planes")
    ResponseEntity<List<InGamePlaneResDto>> boughtPlanes(@CurrentUser AuthUser user) {
        return new ResponseEntity<>(boughtService.boughtPlanes(user), HttpStatus.OK);
    }

    @GetMapping("/workers")
    ResponseEntity<List<InGameWorkerResDto>> boughtWorkers(@CurrentUser AuthUser user) {
        return new ResponseEntity<>(boughtService.boughtWorkers(user), HttpStatus.OK);
    }
}
