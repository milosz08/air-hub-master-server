/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: Regex.java
 * Last modified: 17/05/2023, 23:32
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

package pl.miloszgilga.validator;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class Regex {
    public final static String LOGIN_EMAIL = "^[a-z0-9.@+-_]{3,100}$";
    public final static String OTA_TOKEN = "^[a-zA-Z0-9]{10}$";
    public final static String PASSWORD_REQUIREMENTS = "^(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%&+=!])(?!.*\\s).{8,30}$";
    public final static String LOGIN = "^[a-z0-9]{3,30}$";
}
