/**
* Copyright (c) 2014 Google, Inc. All rights reserved.
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
*
*/
package com.google.fpl.liquidfunpaint.util;

import com.google.fpl.liquidfun.Vec2;
import com.google.fpl.liquidfunpaint.LiquidWorld;

public final class MathHelper {
    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    public static Vec2 normalizePosition(Vec2 pos){
        Vec2 normal = new Vec2();
        normal.setX(2*(pos.getX() - LiquidWorld.getInstance().sRenderWorldWidth/2)/LiquidWorld.getInstance().sRenderWorldWidth);
        normal.setY(2*(pos.getY() - LiquidWorld.getInstance().sRenderWorldHeight/2)/LiquidWorld.getInstance().sRenderWorldHeight);
        return normal;
    }
}
