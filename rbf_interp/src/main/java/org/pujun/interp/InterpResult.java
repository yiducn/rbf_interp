package org.pujun.interp;

import com.mongodb.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;

/**
 * Created by milletpu on 16/3/25.
 */
public class InterpResult {
    double[][] points;
    double[] spds;
    double[] dirs;

    MongoClient client = new MongoClient("127.0.0.1", 27017);
    DB db = client.getDB("alldata");
    DBCollection meteoCollection = db.getCollection("meteo_data");
    DBCollection meteoStationCollection = db.getCollection("meteo_stations");

    public InterpResult(String timePoint) throws ParseException {
        //确定一个时timePoint
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setCalendar(new GregorianCalendar(new SimpleTimeZone(0, "GMT")));
        Date date = df.parse(timePoint);

        //查询所有站点在此时间下的meteo数据
        BasicDBObject queryData = new BasicDBObject();
        queryData.put("time", date);
        DBCursor dataCursor = meteoCollection.find(queryData);

        //
        BasicDBObject queryLoc = new BasicDBObject();
        DBCursor stationCursor = null;

        DBObject thisStation, thisData;
        ArrayList<double[]> point = new ArrayList<double[]>();
        ArrayList<double[]> spd = new ArrayList<double[]>();
        ArrayList<double[]> dir = new ArrayList<double[]>();

        while (dataCursor.hasNext()) {//循环同一时间点下的所有的站点（0-403个），存入dataPoint[][]
            //得到站点编号，通过编号查询其经纬度
            thisData = dataCursor.next();
            double thisSpd = Double.parseDouble(thisData.get("spd").toString());
            double thisDir = Double.parseDouble(thisData.get("dir").toString());

            //获得point={lat,lon}, value = spd, value = dir数据
            if (thisSpd != -1 && thisDir != -1) {       //去掉缺失的点（缺失的点为－1）
                queryLoc.put("usaf", thisData.get("usaf"));
                stationCursor = meteoStationCollection.find(queryLoc);
                while (stationCursor.hasNext()) {
                    thisStation = stationCursor.next();
                    double thisLat = Double.parseDouble(thisStation.get("lat").toString());
                    double thisLon = Double.parseDouble(thisStation.get("lon").toString());
                    double[] thisPoint = {thisLat, thisLon};
                    point.add(thisPoint);       //存入一个站点的lat和lon，有可能有站点重复，使用while可覆盖
                }

                double[] thisSpdd = {thisSpd};
                spd.add(thisSpdd);      //存入一个spd

                double[] thisDirr = {thisDir};
                dir.add(thisDirr);      //存入一个dir

            }//此时间下的所有站点位置存入了point as INPUT x, 风向spd和风速dir as INPUT y

            //ArrayList形式转为double
            points = new double[point.size()][2];
            for (int i = 0; i < point.size(); i++) {
                points[i][0] = point.get(i)[0];
                points[i][1] = point.get(i)[1];
            }

            spds = new double[spd.size()];
            for (int i = 0; i < spd.size(); i++) {
                spds[i] = spd.get(i)[0];
            }

            dirs = new double[dir.size()];
            for (int i = 0; i < dir.size(); i++) {
                dirs[i] = dir.get(i)[0];
            }
        }
    }


    public void interpSpd(double interpLat, double interpLon){
        RBF_multiquadric rbf_multiquadric = new RBF_multiquadric(2);
        RBF_interp rbf_interp_multiquadric = new RBF_interp(points,spds,rbf_multiquadric);

        double[] pt = {interpLat,interpLon};
        System.out.println(rbf_interp_multiquadric.interp(pt));
    }

    public void interpDir(double interpLat, double interpLon){
        RBF_multiquadric rbf_multiquadric = new RBF_multiquadric(2);
        RBF_interp rbf_interp_multiquadric = new RBF_interp(points,dirs,rbf_multiquadric);

        double[] pt = {interpLat, interpLon};
        System.out.println(rbf_interp_multiquadric.interp(pt));
    }
}
