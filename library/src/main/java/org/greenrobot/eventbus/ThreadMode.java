/*
 * Copyright (C) 2012-2016 Markus Junginger, greenrobot (http://greenrobot.org)
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
package org.greenrobot.eventbus;

/**
 * ThreadModel定义了订阅方法应该在什么线程中执行
 *
 * @author Markus
 */
public enum ThreadMode {
    /**
     * 代表这个方法会在当前发布事件的线程执行
     */
    POSTING,

    /**
     * 代表这个方法会在UI线程执行
     */
    MAIN,

    /**
     * 如果事件是在UI线程中发布出来的，那么就会在子线程中运行，如果事件本来就是子线程中发布出来的，那么直接在该子线程中执行。
     */
    BACKGROUND,

    /**
     * 事件执行在一个独立的异步线程中。强制在后台执行
     */
    ASYNC
}