
return [new ChamferedCube(5,5,5,1.5).toCSG(),
new ChamferedCylinder(5,5,1).toCSG().movex(10),
new Wedge(10,5,20).toCSG().movey(10),
new Isosceles(10,5,20).toCSG().movey(20),
new Fillet(5,10).toCSG().movey(30),
 Parabola.extrudeByEquation(5,-0.27,0,1)
	.move(20,20,0),
Parabola.coneByEquation(5,1.27,0.7)
	.move(30,30,0),
	Parabola.coneByFocalLength(10, 10),
	Parabola.coneByHeight(10, 20
	).rotx(90).toZMin(),
]