/*
 * Copyright 2018 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.lequ_core.utils

object Platform {
    val DEPENDENCY_SUPPORT_DESIGN: Boolean
    val DEPENDENCY_GLIDE: Boolean

    init {
        DEPENDENCY_SUPPORT_DESIGN = findClassByClassName("android.support.design.widget.Snackbar")
        DEPENDENCY_GLIDE = findClassByClassName("com.bumptech.glide.Glide")
    }

    private fun findClassByClassName(className: String): Boolean {
        var hasDependency: Boolean = try {
            Class.forName(className)
            true
        } catch (e: ClassNotFoundException) {
            false
        }

        return hasDependency
    }
}
