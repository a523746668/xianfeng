package com.qingyii.hxtz;

import com.qingyii.hxtz.util.TextUtil;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


  @Test
  public void  test_chuli() throws  Exception{
      TextUtil util=new TextUtil();
        ArrayList<String> list= new ArrayList<>();
      list.add("1");
      list.add("asda");
      list.add("mm");
      assertEquals("1,asda,mm",util.chuli(list));
  }

}