package org.ligboy.translate;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author ligboy ligboy@gmail.com
 */
public class TokenGeneratorTest {

    @Test
    public void token() throws Exception {
        TokenGenerator tokenGenerator1 = new TokenGenerator("406249.3075489964");
        String token11 = tokenGenerator1.token("噫吁嚱，危乎高哉！蜀道之难，难于上青天！");
        Assert.assertEquals("410656.30409", token11);
        String token12 = tokenGenerator1.token("青泥何盘盘，百步九折萦岩峦。 扪参历井仰胁息，以手抚膺坐长叹。 问君西游何时还？畏途巉岩不可攀。 但见悲鸟号古木，雄飞雌从绕林间。 又闻子规啼夜月，愁空山。 蜀道之难,难于上青天，使人听此凋朱颜！ 连峰去天不盈尺，枯松倒挂倚绝壁。 飞湍瀑流争喧豗，砯崖转石万壑雷。 其险也如此，嗟尔远道之人胡为乎来哉！");
        Assert.assertEquals("69451.474530", token12);

        TokenGenerator tokenGenerator2 = new TokenGenerator("406250.1115659798");
        String token21 = tokenGenerator2.token("噫吁嚱，危乎高哉！蜀道之难，难于上青天！");
        Assert.assertEquals("292720.148890", token21);
        String token22 = tokenGenerator2.token("青泥何盘盘，百步九折萦岩峦。 扪参历井仰胁息，以手抚膺坐长叹。 问君西游何时还？畏途巉岩不可攀。 但见悲鸟号古木，雄飞雌从绕林间。 又闻子规啼夜月，愁空山。 蜀道之难,难于上青天，使人听此凋朱颜！ 连峰去天不盈尺，枯松倒挂倚绝壁。 飞湍瀑流争喧豗，砯崖转石万壑雷。 其险也如此，嗟尔远道之人胡为乎来哉！");
        Assert.assertEquals("611115.1008065", token22);

    }

}