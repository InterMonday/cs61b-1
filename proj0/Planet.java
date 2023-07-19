public class Planet {
    double xxPos, yyPos, xxVel, yyVel, mass;
    String imgFileName;
    static final double G = 6.67e-11;

    public Planet(double xxPos, double yyPos, double xxVel, double yyVel, double mass, String imgFileName) {
        this.xxPos = xxPos;
        this.yyPos = yyPos;
        this.xxVel = xxVel;
        this.yyVel = yyVel;
        this.mass = mass;
        this.imgFileName = imgFileName;
    }

    public Planet(Planet planet) {
        this.xxPos = planet.xxPos;
        this.yyPos = planet.yyPos;
        this.xxVel = planet.xxVel;
        this.yyVel = planet.yyVel;
        this.mass = planet.mass;
        this.imgFileName = planet.imgFileName;
    }

    public double calcDistance(Planet planet) {
        return Math.sqrt((this.xxPos - planet.xxPos) * (this.xxPos - planet.xxPos) +
                (this.yyPos - planet.yyPos) * (this.yyPos - planet.yyPos));
    }

    public double calcForceExertedBy(Planet planet) {
        double r = this.calcDistance(planet);
        return (G * this.mass * planet.mass) / (r * r);
    }

    public double calcForceExertedByX(Planet planet) {
        return (planet.xxPos - this.xxPos) * this.calcForceExertedBy(planet) / this.calcDistance(planet);
    }

    public double calcForceExertedByY(Planet planet) {
        return (planet.yyPos - this.yyPos) * this.calcForceExertedBy(planet) / this.calcDistance(planet);
    }

    public double calcNetForceExertedByX(Planet[] allPlanet) {
        double sum = 0.0;
        for (Planet planet : allPlanet) {
            if (this.equals(planet)) continue;
            sum += this.calcForceExertedByX(planet);
        }
        return sum;
    }

    public double calcNetForceExertedByY(Planet[] allPlanet) {
        double sum = 0.0;
        for (Planet planet : allPlanet) {
            if (this.equals(planet)) continue;
            sum += this.calcForceExertedByY(planet);
        }
        return sum;
    }

    public void update(double time, double fx, double fy) {
        xxVel += fx / mass * time;
        yyVel += fy / mass * time;
        xxPos += xxVel * time;
        yyPos += yyVel * time;
    }

    public void draw(){
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}
