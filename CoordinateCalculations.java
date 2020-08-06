public class CoordinateCalculations {

    //R_mi = between 3950 - 3963 mi
    //R_km = between 6357 - 6378 km
    private final double R_mi=3963.0; //miles = mi
    private final double R_km=6378.0; //km

    private double lat1AsRad, lat2AsRad, lon1AsRad, lon2AsRad;
    private double bearing;

    public CoordinateCalculations(){}

    /**
     *
     * @param lat1 as Degree
     * @param lon1 as Degree
     * @param lat2 as Degree
     * @param lon2 as Degree
     * @return as Degree
     */
    public double getBearingAsDegree(double lat1, double lon1, double lat2, double lon2) throws Exception {

        if(lat1 < -90.0 || lat1 > 90.0 || lat2 < -90.0 || lat2 > 90.0 || lon1 < -180.0 || lon1 > 180.0 || lon2 < -180.0 || lon2 > 180.0){
            throw new Exception("Latitude or Longitude is out of boundary !");
        }

        lat1AsRad = getDeg2Rad(lat1);
        lat2AsRad = getDeg2Rad(lat2);
        lon1AsRad = getDeg2Rad(lon1);
        lon2AsRad = getDeg2Rad(lon2);

        bearing = Math.atan2(Math.sin(lon2AsRad-lon1AsRad)*Math.cos(lat2AsRad),
                Math.cos(lat1AsRad)*Math.sin(lat2AsRad)-Math.sin(lat1AsRad)*
                        Math.cos(lat2AsRad)*Math.cos(lon2AsRad-lon1AsRad));
        bearing = (getRad2Deg(bearing)+360.0)%360.0;

        return bearing;
    }

    /**
     *
     * @param lat1 as Radian
     * @param lon1 as Radian
     * @param lat2 as Radian
     * @param lon2 as Radian
     * @return as Radian
     */
    public double getBearingAsRadian(double lat1, double lon1, double lat2, double lon2) throws Exception {
        return getDeg2Rad(getBearingAsDegree(lat1, lon1, lat2, lon2));
    }

    /**
     *
     * @param lat1 as Degree
     * @param lon1 as Degree
     * @param lat2 as Degree
     * @param lon2 as Degree
     * @return as Miles
     */
    public double getDistanceAsMiles(double lat1, double lon1, double lat2, double lon2) throws Exception {
        if(lat1 < -90.0 || lat1 > 90.0 || lat2 < -90.0 || lat2 > 90.0 || lon1 < -180.0 || lon1 > 180.0 || lon2 < -180.0 || lon2 > 180.0){
            throw new Exception("Latitude or Longitude is out of boundary !");
        }

        lat1AsRad = getDeg2Rad(lat1);
        lat2AsRad = getDeg2Rad(lat2);
        double difLat = getDeg2Rad(lat2-lat1);
        double difLon = getDeg2Rad(lon2-lon1);

        double dist = R_mi * (2*Math.asin(Math.sqrt(
                Math.pow(Math.sin(difLat/2),2) + Math.pow(Math.sin(difLon/2),2) * Math.cos(lat1AsRad) * Math.cos(lat2AsRad)
        )));

        return dist;
    }

    /**
     *
     * @param lat1 as Degree
     * @param lon1 as Degree
     * @param lat2 as Degree
     * @param lon2 as Degree
     * @return as Nautical Miles
     */
    public double getDistanceAsNauticalMiles(double lat1, double lon1, double lat2, double lon2) throws Exception {
        return getDistanceAsMiles(lat1, lon1, lat2, lon2)*0.8684;
    }

    /**
     *
     * @param lat1 as Degree
     * @param lon1 as Degree
     * @param lat2 as Degree
     * @param lon2 as Degree
     * @return as Kilometers
     */
    public double getDistanceAsKilometers(double lat1, double lon1, double lat2, double lon2) throws Exception {
        return getDistanceAsMiles(lat1, lon1, lat2, lon2)*1.609344;
    }

    /**
     *
     * @param lat as Degree
     * @param lon as Degree
     * @param route as Degree
     * @param distAsKm as Kilometers
     * @return position which contain latitude and longitude
     */
    public Position getDestPositionKm(double lat, double lon, double route, double distAsKm) throws Exception {
        if(lat < -90.0 || lat > 90.0 || lon < -180.0 || lon > 180.0 || route < 0.0 || route > 360.0 || distAsKm < 0.0){
            throw new Exception("Latitude, Longitude, route or distance is out of boundary or negative value!");
        }
        Position position = new Position();
        route = getDeg2Rad(route);
        position.setLat(getRad2Deg(Math.asin(Math.sin(getDeg2Rad(lat))*Math.cos(distAsKm/R_km)+
                Math.cos(getDeg2Rad(lat))*Math.sin(distAsKm/R_km)*Math.cos(route))));
        position.setLon(getRad2Deg(getDeg2Rad(lon)+Math.atan2(Math.sin(route)*Math.sin(distAsKm/R_km)*Math.cos(getDeg2Rad(lat)),
                Math.cos(distAsKm/R_km)-Math.sin(getDeg2Rad(lat))*Math.sin(getDeg2Rad(position.getLat())))));
        return position;
    }

    /**
     *
     * @param lat as Degree
     * @param lon as Degree
     * @param route as Degree
     * @param distAsMile as Mile
     * @return position which contain latitude and longitude
     */
    public Position getDestPositionMile(double lat, double lon, double route, double distAsMile) throws Exception {
        double distAsKm = distAsMile*1.609344;
        return getDestPositionKm(lat, lon, route, distAsKm);
    }

    /**
     *
     * @param lat as Degree
     * @param lon as Degree
     * @param route as Degree
     * @param distAsNM as NM
     * @return position which contain latitude and longitude
     */
    public Position getDestPositionNM(double lat, double lon, double route, double distAsNM) throws Exception {
        double distAsKm = (distAsNM/0.8684)*1.609344;
        return getDestPositionKm(lat, lon, route, distAsKm);
    }

    private double getRad2Deg(double value){
        return Math.toDegrees(value);
    }

    private double getDeg2Rad(double value){
        return Math.toRadians(value);
    }
}


