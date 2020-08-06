public class test {
    public static void main(String[] args)  {
        CoordinateCalculations cc = new CoordinateCalculations();
        Position p = new Position();

        double result = 0;
        try {
            result = cc.getBearingAsDegree(12.3, 45.03, -10.003, -5.445);
            System.out.println(result);

            result = cc.getBearingAsRadian(12.3, 45.03, -10.003, -5.445);
            System.out.println(result);

            result = cc.getDistanceAsKilometers(12.3, 45.03, -10.003, -5.445);
            System.out.println(result);

            result = cc.getDistanceAsMiles(12.3, 45.03, -10.003, -5.445);
            System.out.println(result);

            result = cc.getDistanceAsNauticalMiles(12.3, 45.03, -10.003, -5.445);
            System.out.println(result);

            p = cc.getDestPositionKm(12.3, 45.03, 180.0, 100.0);
            System.out.println(p.getLat() + " " + p.getLon());

            p = cc.getDestPositionMile(-12.3, 45.03, 270.0, 100.0);
            System.out.println(p.getLat() + " " + p.getLon());

            p = cc.getDestPositionNM(-12.3, -45.03, 45.0, 100.0);
            System.out.println(p.getLat() + " " + p.getLon());
        } catch (Exception e) {
            System.out.println(e.getMessage());;
        }

    }

}
