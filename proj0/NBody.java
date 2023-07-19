public class NBody {
    public static double readRadius(String path){
        In in = new In(path);
        in.readInt();
        return in.readDouble();
    }

    public static Planet[] readPlanets(String path){
        Planet[] ps = new Planet[5];
        In in = new In(path);
        int i = in.readInt();
        in.readDouble();
        for (int j = 0; j < i; j++) {
            Planet planet = new Planet(in.readDouble(), in.readDouble(), in.readDouble(),
                    in.readDouble(), in.readDouble(), in.readString());
            ps[j] = planet;
        }
        return ps;
    }

    public static void main(String[] args) {
        double T, dt;
        T = Double.valueOf(args[0].toString());
        dt = Double.valueOf(args[1].toString());
        String fileName = args[2];
        double radiu = readRadius(fileName);
        Planet[] ps = readPlanets(fileName);
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-radiu, radiu);
        StdDraw.clear();
        StdDraw.picture(0, 0, "images/starfield.jpg");
        for (Planet planet : ps) {
            planet.draw();
        }
        StdDraw.show();

        for(double d = 0; d < T; d++){
            double xForces[] = new double[5], yForces[] = new double[5];
            int i = 0;
            StdDraw.clear();
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for(Planet planet : ps){
                xForces[i] = planet.calcNetForceExertedByX(ps);
                yForces[i] = planet.calcNetForceExertedByY(ps);
                i++;
            }
            i = 0;
            for(Planet planet : ps){
                planet.update(d, xForces[i], yForces[i]);
                planet.draw();
                i++;
            }
            StdDraw.show();
            StdDraw.pause(10);
        }
        StdOut.printf("%d\n", ps.length);
        StdOut.printf("%.2e\n", radiu);
        for (int i = 0; i < ps.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    ps[i].xxPos, ps[i].yyPos, ps[i].xxVel,
                    ps[i].yyVel, ps[i].mass, ps[i].imgFileName);
        }
    }
}
