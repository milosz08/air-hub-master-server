/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: Utilities.java
 * Last modified: 19/05/2023, 14:27
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

package pl.miloszgilga.utils;

import pl.miloszgilga.domain.user.UserEntity;
import pl.miloszgilga.domain.worker.WorkerEntity;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class Utilities {

    private Utilities() {
    }

    public static String parseFullName(UserEntity user) {
        return user.getFirstName() + " " + user.getLastName();
    }

    public static String parseWorkerFullName(WorkerEntity worker) {
        return worker.getFirstName() + " " + worker.getLastName();
    }
}
