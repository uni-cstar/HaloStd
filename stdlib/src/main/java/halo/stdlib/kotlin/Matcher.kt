/*
 * Copyright (C) 2018 Lucio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package halo.stdlib.kotlin

/**
 * Created by Lucio on 18/5/2.
 */

/**
 * 是否是版本数字；eg. like 1 or 1.0  or 3.1.25
 */
fun String.isVersionNumber(content: String): Boolean {
    return content.matches(Regex("[\\d.]+"))
}