package org.pujun.interp;

import com.mongodb.*;

import javax.swing.plaf.basic.BasicCheckBoxMenuItemUI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by milletpu on 16/3/16.
 *
 *
 * x[][] is the original data point coordinate while y[] is the corresponding value.
 * pt[] is a point to be interpolated.
 *

 */
public class RBF_interpTest {
    public static void main(String[] args) throws ParseException {

        InterpResult interpResult = new InterpResult("2013-12-18 06:00:00");    //set a time point

        //(48.05,125.883)在“2013-12-18 06:00:00”时刻的spd为2，dir为40。测试插值结果是否正确
        interpResult.interpSpd(48.05,125.883);     //set the lat and lon to get its interpolated wind-speed value
        interpResult.interpDir(48.05,125.883);     //set the lat and lon to get its interpolated wind-direction value

        //(48.767,121.917)在“2013-12-18 06:00:00”时刻的spd为7，dir为340，测试插值结果是否正确
        interpResult.interpSpd(48.767,121.917);     //set the lat and lon to get its interpolated wind-speed value
        interpResult.interpDir(48.767,121.917);     //set the lat and lon to get its interpolated wind-direction value

        //计算未知点(40,116)在此时刻下的spd和dir
        interpResult.interpSpd(40,116);     //set the lat and lon to get its interpolated wind-speed value
        interpResult.interpDir(40,116);     //set the lat and lon to get its interpolated wind-direction value





        /*
        double[][] x = {{1,1}, {2,2}, {3,3}, {4,4}, {5,5}, {6,6}, {7,7}, {8,8}, {9,9}, {10,10}};
        double[] y = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        double[] pt = {6.5,6.5};

        RBF_multiquadric rbf_multiquadric = new RBF_multiquadric(2);
        RBF_interp rbf_interp_multiquadric = new RBF_interp(x,y,rbf_multiquadric);

        System.out.println(rbf_interp_multiquadric.interp(pt));
        */
    }
}
