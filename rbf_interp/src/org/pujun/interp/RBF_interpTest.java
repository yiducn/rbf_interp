package org.pujun.interp;

/**
 * Created by milletpu on 16/3/16.
 *
 *
 * x[][] is the original data point coordinate while y[] is the corresponding value.
 * pt[] is a point to be interpolated.
 *

 */
public class RBF_interpTest {
    public static void main(String[] args) {

        double[][] x = {{1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9}, {10}};
        double[] y = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        double[] pt = {6.5};

        RBF_linear rbf_linear = new RBF_linear();
        RBF_interp rbf_interp_linear = new RBF_interp(x,y,rbf_linear);

        RBF_cubic rbf_cubic = new RBF_cubic();
        RBF_interp rbf_interp_cubic = new RBF_interp(x,y,rbf_cubic);

        RBF_thinplate rbf_thinplate = new RBF_thinplate();
        RBF_interp rbf_interp_thinplate = new RBF_interp(x,y,rbf_thinplate);


        RBF_gauss rbf_gauss = new RBF_gauss(2);
        RBF_interp rbf_interp_guass = new RBF_interp(x, y, rbf_gauss);

        RBF_multiquadric rbf_multiquadric = new RBF_multiquadric(2);
        RBF_interp rbf_interp_multiquadric = new RBF_interp(x,y,rbf_multiquadric);

        RBF_inversemultiquadric rbf_inversemultiquadric = new RBF_inversemultiquadric(2);
        RBF_interp rbf_interp_inversemultiquadric = new RBF_interp(x,y,rbf_inversemultiquadric);


        System.out.println(rbf_interp_linear.interp(pt));
        System.out.println(rbf_interp_cubic.interp(pt));
        System.out.println(rbf_interp_thinplate.interp(pt));
        System.out.println(rbf_interp_guass.interp(pt));
        System.out.println(rbf_interp_multiquadric.interp(pt));
        System.out.println(rbf_interp_inversemultiquadric.interp(pt));

    }
}
