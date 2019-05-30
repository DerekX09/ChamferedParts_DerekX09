
class Parabola extends Primitive {
double Radius, w, a,  b;

    /** The properties. */
    private final PropertyStorage properties = new PropertyStorage();
public PropertyStorage getProperties(){
	return properties;
}

    // from https://www.mathsisfun.com/geometry/parabola.html
    public Parabola(double Radius, double a, double b, double w) {
       this.Radius = Radius;
       if(Math.abs(a)==0){
       	throw new RuntimeException("A value in parabola must be non zero")
       }
       this.a=a;
       this.b=b;
       this.w=w
    }

	private double computeY(double x){
		return (a*x*x)+(b*x);
	}

    /* (non-Javadoc)
     * @see eu.mihosoft.vrl.v3d.Primitive#toPolygons()
     */
    @Override
    public List<Polygon> toPolygons() {
    	ArrayList<Vector3d> points = []
    	points.add(new Vector3d(0, computeY(Radius) ));
    	for(double i=0;i<=1;i+=0.05){
    		double x = Radius*i
    		double y= computeY(x)
    		points.add(new Vector3d(x, y )) 
    	}
    	points.add(new Vector3d(Radius, computeY(Radius) ));
    	
	CSG polygon = Extrude.points(new Vector3d(0, 0, w),// This is the  extrusion depth
	      points// upper right corner
	)
	return polygon.getPolygons();
    }
}
class ParabolicDish extends Primitive {
double Radius,  a;

    /** The properties. */
    private final PropertyStorage properties = new PropertyStorage();
public PropertyStorage getProperties(){
	return properties;
}

    // from https://www.mathsisfun.com/geometry/parabola.html
    public ParabolicDish(double Radius, double FocalLength) {
       this.Radius = Radius;
       if(Math.abs(FocalLength)==0){
       	throw new RuntimeException("A value in parabola must be non zero")
       }
       this.a=FocalLength;
    }

	private double computeY(double x){
		return (x*x)/(a*4.0);
	}

    /* (non-Javadoc)
     * @see eu.mihosoft.vrl.v3d.Primitive#toPolygons()
     */
    @Override
    public List<Polygon> toPolygons() {
    	ArrayList<Vector3d> points = []
    	points.add(new Vector3d(0, computeY(Radius) ));
    	for(double i=0;i<=1;i+=0.05){
    		double x = Radius*i
    		double y= computeY(x)
    		points.add(new Vector3d(x, y )) 
    	}
    	points.add(new Vector3d(Radius, computeY(Radius) ));
	ArrayList<Vector3d> pointsOut = []
    	for(double i=0;i<=360;i+=10){
    		Transform transform = new Transform()
    							.roty(i)
    		pointsOut.addAll(
    			points.collect{
    				it.transformed( transform)
    			}
    			)
    	}
	CSG polygon = eu.mihosoft.vrl.v3d.ext.quickhull3d.HullUtil.hull(pointsOut)
				//.rotx(90)
				//.toZMin()
	polygon=new Cylinder(Radius,polygon.getTotalY()-0.1).toCSG() 
			.rotx(90)
			.difference(	polygon)

	return polygon.getPolygons();
    }
}
class ParabolicCone extends Primitive {
double Radius,  a,  b;

    /** The properties. */
    private final PropertyStorage properties = new PropertyStorage();
public PropertyStorage getProperties(){
	return properties;
}

    // from https://www.mathsisfun.com/geometry/parabola.html
    public ParabolicCone(double Radius, double a, double b) {
       this.Radius = Radius;
       if(Math.abs(a)==0){
       	throw new RuntimeException("A value in parabola must be non zero")
       }
       this.a=a;
       this.b=b;
    }

	private double computeY(double x){
		return (a*x*x)+(b*x);
	}

    /* (non-Javadoc)
     * @see eu.mihosoft.vrl.v3d.Primitive#toPolygons()
     */
    @Override
    public List<Polygon> toPolygons() {
    	ArrayList<Vector3d> points = []
    	points.add(new Vector3d(0, computeY(Radius) ));
    	for(double i=0;i<=1;i+=0.05){
    		double x = Radius*i
    		double y= computeY(x)
    		points.add(new Vector3d(x, y )) 
    	}
    	points.add(new Vector3d(Radius, computeY(Radius) ));
	ArrayList<Vector3d> pointsOut = []
    	for(double i=0;i<=360;i+=10){
    		Transform transform = new Transform()
    							.roty(i)
    		pointsOut.addAll(
    			points.collect{
    				it.transformed( transform)
    			}
    			)
    	}
	CSG polygon = eu.mihosoft.vrl.v3d.ext.quickhull3d.HullUtil.hull(pointsOut)
				//.rotx(90)
				//.toZMin()
	return polygon.getPolygons();
    }
}
return [new ChamferedCube(5,5,5,1.5).toCSG(),
new ChamferedCylinder(5,5,1).toCSG().movex(10),
new Wedge(10,5,20).toCSG().movey(10),
new Isosceles(10,5,20).toCSG().movey(20),
new Fillet(5,10).toCSG().movey(30),
new Parabola(5,-0.27,0,1).toCSG()
	.move(20,20,0),
new ParabolicCone(5,1.27,0).toCSG()
	.move(30,30,0),
	new ParabolicDish(10, 3).toCSG()
]