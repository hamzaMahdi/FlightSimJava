//name:Hamza Mahdi
//date:14.12.2013
//description:flight simulator ISP
// The "V2" class.
import java.awt.*;
import hsa.Console;

public class V2
{
    static Console c;//console 

    static final int N = 5; //angles of the polygon



    static final double R = 250; //radius of polygon
    static final double R1 = 350;
    static final double R2 = 300;

    static final double SCREEN_X = 640; //amount of x pixels in the console
    static final double SCREEN_Y = 500; //amount of y pixels in the console
    //scale and shift
    static final double X_SCALE = +1.0;
    static final double X_SHIFT = SCREEN_X / 2.0;
    static final double Y_SCALE = +1.0;
    static final double Y_SHIFT = SCREEN_Y / 2.0;

    static final double ZV = 300; //human eye

    static final double CUBE_OFFSET = -3000; //z coordinate of the cube

    static final double MOUNTAIN_OFFSET = -1000; //z coordinate of the mountain

    static final double Y0 = 100; //the smallest y coordinate of (cube,runway,median lines)

    static final int TIME_DELAY = 50; //the time that delays in ms

    final static double DELTA_TIME = 0.10; //increases the running time (for acceleration)

    static final double L = 1000; //cube's angle length

    static final int M = 2; //array length for the road

    static final double MEDIAN_LENGTH = 1100; //median line length
    static final double MEDIAN_WEIDTH = 30; //median line weidth
    static final double RUNWAY_WEIDTH = 200; //runway weidth
    //related to the perspective
    static final double EPSILON = 1e-3;
    static final double INFINITY = 1e+3;
    //movement keys
    static final char UPWARD = 'w';
    static final char DOWNWARD = 's';
    static final char LEFT = 'a';
    static final char RIGHT = 'd';

    static final double ALPHA = 0.10; //the angle which decides movement
    //initial mountain coordinates so i don't hard code the numbers inside the method
    static final double xmountain1 = 150;
    static final double xmountain2 = 100;
    static final double xmountain3 = 50;
    static final double ymountain = -100;


    public static void main (String[] args)
    {
	c = new Console ();

	V2 s = new V2 ();


	int counter = 0; //to switch polygon colors

	//polygon arrays
	double x[] = new double [N];
	double y[] = new double [N];
	double x1[] = new double [N];
	double y1[] = new double [N];
	double x2[] = new double [N];
	double y2[] = new double [N];
	//cube arrays
	double x_cube0[] = new double [8];
	double y_cube0[] = new double [8];
	double z_cube0[] = new double [8];
	double x_cube[] = new double [8];
	double y_cube[] = new double [8];
	double z_cube[] = new double [8];
	//mountain arrays
	double x_mountain0[] = new double [5];
	double y_mountain0[] = new double [5];
	double z_mountain0[] = new double [5];
	double x_mountain[] = new double [5];
	double y_mountain[] = new double [5];
	double z_mountain[] = new double [5];
	//runway arrays(each dimension for each side)
	double x_runway0[] [] = new double [2] [M + 1];
	double y_runway0[] [] = new double [2] [M + 1];
	double z_runway0[] [] = new double [2] [M + 1];
	double x_runway[] [] = new double [2] [M + 1];
	double y_runway[] [] = new double [2] [M + 1];
	double z_runway[] [] = new double [2] [M + 1];
	//median line array
	double x_medianline0[] [] = new double [2] [M];
	double y_medianline0[] [] = new double [2] [M];
	double z_medianline0[] [] = new double [2] [M];
	double x_medianline[] [] = new double [2] [M];
	double y_medianline[] [] = new double [2] [M];
	double z_medianline[] [] = new double [2] [M];

	// related to acceleration
	double t = 0; //running time
	double VZ = 0; //speed

	char key; //variable for the program to recognize the keys
	//set initial objects coordinates outside the loop(see the algorithm)
	s.setCubeCoordinates (x_cube0, y_cube0, z_cube0);
	s.setMountainCoordinates (x_mountain0, y_mountain0, z_mountain0);
	s.setRunWayCoordinates (x_runway0, y_runway0, z_runway0);
	s.setMedianlineCoordinates (x_medianline0, y_medianline0, z_medianline0);
	s.setPolygonCoordinates (x, x1, x2, y, y1, y2);


	while (true)
	{


	    if (c.isCharAvail ()) //detect keys
	    {
		key = c.getChar ();
		if (key == RIGHT) //use beta for left and right not alpha (actually it is the same thing since it is the same value)
		{
		    //use positive angle
		    s.rotateCubeLeftAndRight (x_cube, x_cube0, y_cube, y_cube0, z_cube, z_cube0, +ALPHA, 8);
		    s.rotateMountainLeftAndRight (x_mountain, x_mountain0, y_mountain, y_mountain0, z_mountain, z_mountain0, +ALPHA, 5);
		    s.rotateRunwayLeftAndRight (x_runway, x_runway0, y_runway, y_runway0, z_runway, z_runway0, +ALPHA);
		    s.rotateMedianlineLeftAndRight (x_medianline, x_medianline0, y_medianline, y_medianline0, z_medianline, z_medianline0, +ALPHA);
		    t = 0; //reset running time
		}
		else if (key == LEFT)
		{
		    //use negative angle
		    s.rotateCubeLeftAndRight (x_cube, x_cube0, y_cube, y_cube0, z_cube, z_cube0, -ALPHA, 8);
		    s.rotateMountainLeftAndRight (x_mountain, x_mountain0, y_mountain, y_mountain0, z_mountain, z_mountain0, -ALPHA, 5);
		    s.rotateRunwayLeftAndRight (x_runway, x_runway0, y_runway, y_runway0, z_runway, z_runway0, -ALPHA);
		    s.rotateMedianlineLeftAndRight (x_medianline, x_medianline0, y_medianline, y_medianline0, z_medianline, z_medianline0, -ALPHA);
		    t = 0; //reset running time
		}
		else if (key == UPWARD)
		{
		    //use negative angle
		    rotateCubeUpAndDown (x_cube, x_cube0, y_cube, y_cube0, z_cube, z_cube0, -ALPHA, 8);
		    s.rotateMountainUpAndDown (x_mountain, x_mountain0, y_mountain, y_mountain0, z_mountain, z_mountain0, -ALPHA, 5);
		    s.rotateRunwayUpAndDown (x_runway, x_runway0, y_runway, y_runway0, z_runway, z_runway0, -ALPHA);
		    s.rotateMedianlineUpAndDown (x_medianline, x_medianline0, y_medianline, y_medianline0, z_medianline, z_medianline0, -ALPHA);
		    t = 0; //reset running time
		}
		else if (key == DOWNWARD)
		{
		    //use positive angle
		    rotateCubeUpAndDown (x_cube, x_cube0, y_cube, y_cube0, z_cube, z_cube0, +ALPHA, 8);
		    s.rotateMountainUpAndDown (x_mountain, x_mountain0, y_mountain, y_mountain0, z_mountain, z_mountain0, +ALPHA, 5);
		    s.rotateRunwayUpAndDown (x_runway, x_runway0, y_runway, y_runway0, z_runway, z_runway0, +ALPHA);
		    s.rotateMedianlineUpAndDown (x_medianline, x_medianline0, y_medianline, y_medianline0, z_medianline, z_medianline0, +ALPHA);
		    t = 0; //reset running time
		}

	    }
	    //translate z coordinates of the objects(no need to translate polygon because it doesn't have a z coordinate
	    s.translateCube (x_cube, x_cube0, y_cube, y_cube0, z_cube, z_cube0, VZ, t, 8);
	    s.translateMountain (x_mountain, x_mountain0, y_mountain, y_mountain0, z_mountain, z_mountain0, VZ, t);
	    s.translateRunWay (x_runway, x_runway0, y_runway, y_runway0, z_runway, z_runway0, VZ, t);
	    s.translateMedianline (x_medianline, x_medianline0, y_medianline, y_medianline0, z_medianline, z_medianline0, VZ, t);
	    //draw objects
	    s.drawCube (x_cube, y_cube, z_cube, Color.black);
	    s.drawMountain (x_mountain, y_mountain, z_mountain, Color.black);
	    s.drawRunWay (x_runway, y_runway, z_runway, Color.black);
	    s.drawMedianline (x_medianline, y_medianline, z_medianline, Color.black);
	    s.drawPolygonShape (x, x1, x2, y, y1, y2, N, Color.red, Color.white, counter);

	    //time delay
	    try
	    {
		Thread.sleep (TIME_DELAY);
	    }
	    catch (InterruptedException e)

	    {
	    }

	    //clear screen
	    s.drawCube (x_cube, y_cube, z_cube, Color.white);
	    s.drawMountain (x_mountain, y_mountain, z_mountain, Color.white);
	    s.drawRunWay (x_runway, y_runway, z_runway, Color.white);
	    s.drawMedianline (x_medianline, y_medianline, z_medianline, Color.white);

	    counter += 1; //change polygon color
	    //increase time and speed
	    t += DELTA_TIME;
	    VZ += 1;


	}

    } // main method


    public static void setPolygonCoordinates (double x[], double x1[], double x2[], double y[], double y1[], double y2[])  //method to set the polygon coordinates
    {
	for (int i = 0 ; i <= N - 1 ; ++i)
	{
	    x [i] = R * Math.sin (2 * Math.PI * i / N);
	    y [i] = -R * Math.cos (2 * Math.PI * i / N);
	    x1 [i] = R1 * Math.sin (2 * Math.PI * i / N);
	    y1 [i] = -R1 * Math.cos (2 * Math.PI * i / N);
	    x2 [i] = R2 * Math.sin (2 * Math.PI * i / N);
	    y2 [i] = -R2 * Math.cos (2 * Math.PI * i / N);

	}

    }


    public static void drawPolygonShape (double x[], double x1[], double x2[], double y[], double y1[], double y2[], int n, Color B, Color W, int counter)  //draw polygon
    {
	/*if the remainder of counter == 0 draw in red color
	since it increases by one each loop it switches between odd and even number so it draws with different color every loop*/
	if (counter % 2 == 0)
	{

	    for (int i = 0 ; i <= n - 1 ; ++i)
	    {
		c.setColor (B); //red
		c.drawLine ((int) Math.round (x [i % n] + SCREEN_X / 2),
			(int) Math.round (y [i % n] + SCREEN_Y / 2),
			(int) Math.round (x [(i + 1) % n] + SCREEN_X / 2),
			(int) Math.round (y [(i + 1) % n] + SCREEN_Y / 2));
		c.setColor (W); //white
		c.drawLine ((int) Math.round (x2 [i % n] + SCREEN_X / 2),
			(int) Math.round (y2 [i % n] + SCREEN_Y / 2),
			(int) Math.round (x2 [(i + 1) % n] + SCREEN_X / 2),
			(int) Math.round (y2 [(i + 1) % n] + SCREEN_Y / 2));
		c.setColor (B); //red
		c.drawLine ((int) Math.round (x1 [i % n] + SCREEN_X / 2),
			(int) Math.round (y1 [i % n] + SCREEN_Y / 2),
			(int) Math.round (x1 [(i + 1) % n] + SCREEN_X / 2),
			(int) Math.round (y1 [(i + 1) % n] + SCREEN_Y / 2));
	    }

	}
	else
	{

	    for (int i = 0 ; i <= n - 1 ; ++i)
	    {
		c.setColor (W); //white
		c.drawLine ((int) Math.round (x [i % n] + SCREEN_X / 2),
			(int) Math.round (y [i % n] + SCREEN_Y / 2),
			(int) Math.round (x [(i + 1) % n] + SCREEN_X / 2),
			(int) Math.round (y [(i + 1) % n] + SCREEN_Y / 2));
		c.setColor (B); //red
		c.drawLine ((int) Math.round (x2 [i % n] + SCREEN_X / 2),
			(int) Math.round (y2 [i % n] + SCREEN_Y / 2),
			(int) Math.round (x2 [(i + 1) % n] + SCREEN_X / 2),
			(int) Math.round (y2 [(i + 1) % n] + SCREEN_Y / 2));
		c.setColor (W); //white
		c.drawLine ((int) Math.round (x1 [i % n] + SCREEN_X / 2),
			(int) Math.round (y1 [i % n] + SCREEN_Y / 2),
			(int) Math.round (x1 [(i + 1) % n] + SCREEN_X / 2),
			(int) Math.round (y1 [(i + 1) % n] + SCREEN_Y / 2));
	    }
	}
    }


    public static void setCubeCoordinates (double x_cube0[], double y_cube0[], double z_cube0[])
    {
	//set cube coordintes
	x_cube0 [0] = +L;
	y_cube0 [0] = -2 * L + Y0;
	z_cube0 [0] = +L + CUBE_OFFSET;

	x_cube0 [1] = -L;
	y_cube0 [1] = Y0;
	z_cube0 [1] = -L + CUBE_OFFSET;

	x_cube0 [2] = -L;
	y_cube0 [2] = -2 * L + Y0;
	z_cube0 [2] = +L + CUBE_OFFSET;

	x_cube0 [3] = +L;
	y_cube0 [3] = Y0;
	z_cube0 [3] = -L + CUBE_OFFSET;

	x_cube0 [4] = -L;
	y_cube0 [4] = Y0;
	z_cube0 [4] = +L + CUBE_OFFSET;

	x_cube0 [5] = +L;
	y_cube0 [5] = -2 * L + Y0;
	z_cube0 [5] = -L + CUBE_OFFSET;

	x_cube0 [6] = +L;
	y_cube0 [6] = Y0;
	z_cube0 [6] = +L + CUBE_OFFSET;

	x_cube0 [7] = -L;
	y_cube0 [7] = -2 * L + Y0;
	z_cube0 [7] = -L + CUBE_OFFSET;
    }


    public static void setMountainCoordinates (double x_mountain0[], double y_mountain0[], double z_mountain0[])
    {
	/* x_mountain0 [0] = -xmountain1;
	 y_mountain0 [0] = 0.9 * ymountain;
	 z_mountain0 [0] = MOUNTAIN_OFFSET;*/
	//set the mountain coordinates
	x_mountain0 [0] = -xmountain2;
	y_mountain0 [0] = 0.5 * ymountain;
	z_mountain0 [0] = MOUNTAIN_OFFSET;

	x_mountain0 [1] = -xmountain3;
	y_mountain0 [1] = 0.7 * ymountain;
	z_mountain0 [1] = MOUNTAIN_OFFSET;

	x_mountain0 [2] = 0;
	y_mountain0 [2] = 0.5 * ymountain;
	z_mountain0 [2] = MOUNTAIN_OFFSET;

	x_mountain0 [3] = xmountain3;
	y_mountain0 [3] = 0.9 * ymountain;
	z_mountain0 [3] = MOUNTAIN_OFFSET;

	x_mountain0 [4] = xmountain2;
	y_mountain0 [4] = 0.3 * ymountain;
	z_mountain0 [4] = MOUNTAIN_OFFSET;

	/* x_mountain0 [6] = xmountain1;
	 y_mountain0 [6] = 0.8 * ymountain;
	 z_mountain0 [6] = MOUNTAIN_OFFSET;*/
    }


    public static void setRunWayCoordinates (double x_runway0[] [], double y_runway0[] [], double z_runway0[] [])
    {
	for (int i = 0 ; i <= M ; ++i)
	{
	    x_runway0 [0] [i] = -RUNWAY_WEIDTH;
	    y_runway0 [0] [i] = Y0;
	    z_runway0 [0] [i] = -i * MEDIAN_LENGTH; //median lenght because it has the same length as the median length
	    x_runway0 [1] [i] = +RUNWAY_WEIDTH;
	    y_runway0 [1] [i] = Y0;
	    z_runway0 [1] [i] = -i * MEDIAN_LENGTH;
	}

    }


    public static void setMedianlineCoordinates (double x_medianline0[] [], double y_medianline0[] [], double z_medianline0[] [])
    {
	for (int i = 0 ; i <= M - 1 ; ++i)
	{
	    x_medianline0 [0] [i] = -MEDIAN_WEIDTH;
	    y_medianline0 [0] [i] = Y0;
	    z_medianline0 [0] [i] = -i * MEDIAN_LENGTH;
	    x_medianline0 [1] [i] = +MEDIAN_WEIDTH;
	    y_medianline0 [1] [i] = Y0;
	    z_medianline0 [1] [i] = -i * MEDIAN_LENGTH;
	}
    }





    public static void rotateCubeLeftAndRight (double x_cube[], double x_cube0[], double y_cube[], double y_cube0[], double z_cube[], double z_cube0[], double ALPHA, int n)
    {
	for (int i = 0 ; i <= n - 1 ; ++i)
	{ //don't change the y coordinate when you rotate left and right
	    x_cube0 [i] = (x_cube [i]) * Math.cos (ALPHA) + Math.sin (ALPHA) * (z_cube [i]);
	    y_cube0 [i] = (y_cube [i]);
	    z_cube0 [i] = -Math.sin (ALPHA) * (x_cube [i]) + Math.cos (ALPHA) * (z_cube [i]);
	}

    }


    public static void rotateCubeUpAndDown (double x_cube[], double x_cube0[], double y_cube[], double y_cube0[], double z_cube[], double z_cube0[], double ALPHA, int n)
    {
	for (int i = 0 ; i <= n - 1 ; ++i)
	{ //don't change the x coordinate when you rotate up and down
	    x_cube0 [i] = x_cube [i];
	    y_cube0 [i] = (y_cube [i]) * Math.cos (ALPHA) + Math.sin (ALPHA) * (z_cube [i]);
	    z_cube0 [i] = -(y_cube [i]) * Math.sin (ALPHA) + Math.cos (ALPHA) * (z_cube [i]);
	}
    }


    public static void rotateMountainLeftAndRight (double x_mountain[], double x_mountain0[], double y_mountain[], double y_mountain0[], double z_mountain[], double z_mountain0[], double ALPHA, int n)
    { //don't change the y coordinate when you rotate left and right
	for (int i = 0 ; i <= n - 1 ; ++i)
	{
	    x_mountain0 [i] = (x_mountain [i]) * Math.cos (ALPHA) + Math.sin (ALPHA) * (z_mountain [i]);
	    y_mountain0 [i] = (y_mountain [i]);
	    z_mountain0 [i] = -Math.sin (ALPHA) * (x_mountain [i]) + Math.cos (ALPHA) * (z_mountain [i]);
	}
    }


    public static void rotateMountainUpAndDown (double x_mountain[], double x_mountain0[], double y_mountain[], double y_mountain0[], double z_mountain[], double z_mountain0[], double ALPHA, int n)
    {
	for (int i = 0 ; i <= n - 1 ; ++i)
	{ //don't change the x coordinate when you rotate up and down
	    x_mountain0 [i] = x_mountain [i];
	    y_mountain0 [i] = (y_mountain [i]) * Math.cos (ALPHA) + Math.sin (ALPHA) * (z_mountain [i]);
	    z_mountain0 [i] = -(y_mountain [i]) * Math.sin (ALPHA) + Math.cos (ALPHA) * (z_mountain [i]);
	}
    }


    public static void rotateRunwayLeftAndRight (double x_runway[] [], double x_runway0[] [], double y_runway[] [], double y_runway0[] [], double z_runway[] [], double z_runway0[] [], double ALPHA)
    {
	for (int i = 0 ; i <= M ; ++i)
	{ //don't change the y coordinate when you rotate left and right
	    x_runway0 [0] [i] = x_runway [0] [i] * Math.cos (ALPHA) + Math.sin (ALPHA) * z_runway [0] [i];
	    y_runway0 [0] [i] = y_runway [0] [i];
	    z_runway0 [0] [i] = -Math.sin (ALPHA) * x_runway [0] [i] + Math.cos (ALPHA) * z_runway [0] [i];
	    x_runway0 [1] [i] = x_runway [1] [i] * Math.cos (ALPHA) + Math.sin (ALPHA) * z_runway [1] [i];
	    y_runway0 [1] [i] = y_runway [1] [i];
	    z_runway0 [1] [i] = -Math.sin (ALPHA) * x_runway [1] [i] + Math.cos (ALPHA) * z_runway [1] [i];
	}
    }


    public static void rotateRunwayUpAndDown (double x_runway[] [], double x_runway0[] [], double y_runway[] [], double y_runway0[] [], double z_runway[] [], double z_runway0[] [], double ALPHA)
    {
	for (int i = 0 ; i <= M ; ++i)
	{ //don't change the x coordinate when you rotate up and down
	    x_runway0 [0] [i] = x_runway [0] [i];
	    y_runway0 [0] [i] = y_runway [0] [i] * Math.cos (ALPHA) + Math.sin (ALPHA) * z_runway [0] [i];
	    z_runway0 [0] [i] = -y_runway [0] [i] * Math.sin (ALPHA) + Math.cos (ALPHA) * z_runway [0] [i];
	    x_runway0 [1] [i] = x_runway [1] [i];
	    y_runway0 [1] [i] = y_runway [1] [i] * Math.cos (ALPHA) + Math.sin (ALPHA) * z_runway [1] [i];
	    z_runway0 [1] [i] = -y_runway [1] [i] * Math.sin (ALPHA) + Math.cos (ALPHA) * z_runway [1] [i];
	}
    }


    public static void rotateMedianlineLeftAndRight (double x_medianline[] [], double x_medianline0[] [], double y_medianline[] [], double y_medianline0[] [], double z_medianline[] [], double z_medianline0[] [], double ALPHA)
    {
	for (int i = 0 ; i <= M - 1 ; ++i)
	{ //don't change the y coordinate when you rotate left and right
	    x_medianline0 [0] [i] = x_medianline [0] [i] * Math.cos (ALPHA) + Math.sin (ALPHA) * z_medianline [0] [i];
	    y_medianline0 [0] [i] = y_medianline [0] [i];
	    z_medianline0 [0] [i] = -Math.sin (ALPHA) * x_medianline [0] [i] + Math.cos (ALPHA) * z_medianline [0] [i];
	    x_medianline0 [1] [i] = x_medianline [1] [i] * Math.cos (ALPHA) + Math.sin (ALPHA) * z_medianline [1] [i];
	    y_medianline0 [1] [i] = y_medianline [1] [i];
	    z_medianline0 [1] [i] = -Math.sin (ALPHA) * x_medianline [1] [i] + Math.cos (ALPHA) * z_medianline [1] [i];
	}
    }


    public static void rotateMedianlineUpAndDown (double x_medianline[] [], double x_medianline0[] [], double y_medianline[] [], double y_medianline0[] [], double z_medianline[] [], double z_medianline0[] [], double ALPHA)
    {
	for (int i = 0 ; i <= M - 1 ; ++i)
	{ //don't change the x coordinate when you rotate up and down
	    x_medianline0 [0] [i] = x_medianline [0] [i];
	    y_medianline0 [0] [i] = y_medianline [0] [i] * Math.cos (ALPHA) + Math.sin (ALPHA) * z_medianline [0] [i];
	    z_medianline0 [0] [i] = -y_medianline [0] [i] * Math.sin (ALPHA) + Math.cos (ALPHA) * z_medianline [0] [i];
	    x_medianline0 [1] [i] = x_medianline [1] [i];
	    y_medianline0 [1] [i] = y_medianline [1] [i] * Math.cos (ALPHA) + Math.sin (ALPHA) * z_medianline [1] [i];
	    z_medianline0 [1] [i] = -y_medianline [1] [i] * Math.sin (ALPHA) + Math.cos (ALPHA) * z_medianline [1] [i];
	}
    }




    public static void translateCube (double x_cube[], double x_cube0[], double y_cube[], double y_cube0[], double z_cube[], double z_cube0[], double VZ, double t, int n)
    { //just change the z coordinate
	for (int i = 0 ; i <= n - 1 ; ++i)
	{
	    x_cube [i] = x_cube0 [i];
	    y_cube [i] = y_cube0 [i];
	    z_cube [i] = z_cube0 [i] + VZ * t;
	}

    }


    public static void translateMountain (double x_mountain[], double x_mountain0[], double y_mountain[], double y_mountain0[], double z_mountain[], double z_mountain0[], double VZ, double t)
    {
	for (int i = 0 ; i <= 4 ; ++i)
	{ //just change the z coordinate
	    x_mountain [i] = x_mountain0 [i];
	    y_mountain [i] = y_mountain0 [i];
	    z_mountain [i] = z_mountain0 [i] + VZ * t;
	}
    }


    public static void translateRunWay (double x_runway[] [], double x_runway0[] [], double y_runway[] [], double y_runway0[] [], double z_runway[] [], double z_runway0[] [], double VZ, double t)
    {
	for (int i = 0 ; i <= M ; ++i)
	{ //just change the z coordinate
	    x_runway [0] [i] = x_runway0 [0] [i];
	    y_runway [0] [i] = y_runway0 [0] [i];
	    z_runway [0] [i] = z_runway0 [0] [i] + VZ * t;
	    x_runway [1] [i] = x_runway0 [1] [i];
	    y_runway [1] [i] = y_runway0 [1] [i];
	    z_runway [1] [i] = z_runway0 [1] [i] + VZ * t;
	}
    }


    public static void translateMedianline (double x_medianline[] [], double x_medianline0[] [], double y_medianline[] [], double y_medianline0[] [], double z_medianline[] [], double z_medianline0[] [], double VZ, double t)
    {
	for (int i = 0 ; i <= M - 1 ; ++i)
	{ //just change the z coordinate
	    x_medianline [0] [i] = x_medianline0 [0] [i];
	    y_medianline [0] [i] = y_medianline0 [0] [i];
	    z_medianline [0] [i] = z_medianline0 [0] [i] + VZ * t;
	    x_medianline [1] [i] = x_medianline0 [1] [i];
	    y_medianline [1] [i] = y_medianline0 [1] [i];
	    z_medianline [1] [i] = z_medianline0 [1] [i] + VZ * t;
	}
    }



    public static void drawCube (double x_cube[], double y_cube[], double z_cube[], Color C)
    {


	c.setColor (C); //changes between draw and clear


	for (int i = 0 ; i <= 7 ; ++i)
	    for (int j = i + 1 ; j <= 7 ; ++j)
		if (Math.abs (Math.pow (x_cube [i] - x_cube [j], 2) + Math.pow (y_cube [i] - y_cube [j], 2) + Math.pow (z_cube [i] - z_cube [j], 2) - Math.pow (2 * L, 2)) < EPSILON)
		    c.drawLine ((int) Math.round (convertToScreenXCoordinate (perspectiveX (x_cube [i], z_cube [i]))),
			    (int) Math.round (convertToScreenYCoordinate (perspectiveY (y_cube [i], z_cube [i]))),
			    (int) Math.round (convertToScreenXCoordinate (perspectiveX (x_cube [j], z_cube [j]))),
			    (int) Math.round (convertToScreenYCoordinate (perspectiveY (y_cube [j], z_cube [j]))));
    }


    public static void drawMountain (double x_mountain[], double y_mountain[], double z_mountain[], Color C)
    {
	c.setColor (C);
	for (int i = 0 ; i <= 3 ; ++i) //5 because it is the point and the next point
	    c.drawLine ((int) Math.round (convertToScreenXCoordinate (perspectiveX (x_mountain [i], z_mountain [i]))),
		    (int) Math.round (convertToScreenYCoordinate (perspectiveY (y_mountain [i], z_mountain [i]))),
		    (int) Math.round (convertToScreenXCoordinate (perspectiveX (x_mountain [i + 1], z_mountain [i + 1]))),
		    (int) Math.round (convertToScreenYCoordinate (perspectiveY (y_mountain [i + 1], z_mountain [i + 1]))));
    }


    public static void drawRunWay (double x_runway[] [], double y_runway[] [], double z_runway[] [], Color C)
    {
	c.setColor (C);
	//the left side of the road
	c.drawLine ((int) Math.round (convertToScreenXCoordinate (perspectiveX (x_runway [0] [0], z_runway [0] [0]))),
		(int) Math.round (convertToScreenYCoordinate (perspectiveY (y_runway [0] [0], z_runway [0] [0]))),
		(int) Math.round (convertToScreenXCoordinate (perspectiveX (x_runway [0] [1], z_runway [0] [1]))),
		(int) Math.round (convertToScreenYCoordinate (perspectiveY (y_runway [0] [1], z_runway [0] [1]))));

	//the right side of the road
	c.drawLine ((int) Math.round (convertToScreenXCoordinate (perspectiveX (x_runway [1] [0], z_runway [1] [0]))),
		(int) Math.round (convertToScreenYCoordinate (perspectiveY (y_runway [1] [0], z_runway [1] [0]))),
		(int) Math.round (convertToScreenXCoordinate (perspectiveX (x_runway [1] [1], z_runway [1] [1]))),
		(int) Math.round (convertToScreenYCoordinate (perspectiveY (y_runway [1] [1], z_runway [1] [1]))));
	//the initial horizontal line of the road
	c.drawLine ((int) Math.round (convertToScreenXCoordinate (perspectiveX (x_runway [0] [0], z_runway [0] [0]))),
		(int) Math.round (convertToScreenYCoordinate (perspectiveY (y_runway [0] [0], z_runway [0] [0]))),
		(int) Math.round (convertToScreenXCoordinate (perspectiveX (x_runway [1] [0], z_runway [1] [0]))),
		(int) Math.round (convertToScreenYCoordinate (perspectiveY (y_runway [1] [0], z_runway [1] [0]))));
	//the final horizontal line of the road
	c.drawLine ((int) Math.round (convertToScreenXCoordinate (perspectiveX (x_runway [0] [1], z_runway [0] [1]))),
		(int) Math.round (convertToScreenYCoordinate (perspectiveY (y_runway [0] [1], z_runway [0] [1]))),
		(int) Math.round (convertToScreenXCoordinate (perspectiveX (x_runway [1] [1], z_runway [1] [1]))),
		(int) Math.round (convertToScreenYCoordinate (perspectiveY (y_runway [1] [1], z_runway [1] [1]))));


    }


    public static void drawMedianline (double x_medianline[] [], double y_medianline[] [], double z_medianline[] [], Color C)
    {
	c.setColor (C);
	//the left side of the medianline
	for (int i = 0 ; i <= 2 ; ++i)
	    for (int j = i + 1 ; j <= 1 ; ++j)
		c.drawLine ((int) Math.round (convertToScreenXCoordinate (perspectiveX (x_medianline [0] [i], z_medianline [0] [i]))),
			(int) Math.round (convertToScreenYCoordinate (perspectiveY (y_medianline [0] [i], z_medianline [0] [i]))),
			(int) Math.round (convertToScreenXCoordinate (perspectiveX (x_medianline [0] [j], z_medianline [0] [j]))),
			(int) Math.round (convertToScreenYCoordinate (perspectiveY (y_medianline [0] [j], z_medianline [0] [j]))));
	//the right side of the medianline
	for (int i = 0 ; i <= 2 ; ++i)
	    for (int j = i + 1 ; j <= 1 ; ++j)
		c.drawLine ((int) Math.round (convertToScreenXCoordinate (perspectiveX (x_medianline [1] [i], z_medianline [1] [i]))),
			(int) Math.round (convertToScreenYCoordinate (perspectiveY (y_medianline [1] [i], z_medianline [1] [i]))),
			(int) Math.round (convertToScreenXCoordinate (perspectiveX (x_medianline [1] [j], z_medianline [1] [j]))),
			(int) Math.round (convertToScreenYCoordinate (perspectiveY (y_medianline [1] [j], z_medianline [1] [j]))));
	//the initial horizontal line of the road
	c.drawLine ((int) Math.round (convertToScreenXCoordinate (perspectiveX (x_medianline [0] [0], z_medianline [0] [0]))),
		(int) Math.round (convertToScreenYCoordinate (perspectiveY (y_medianline [0] [0], z_medianline [0] [0]))),
		(int) Math.round (convertToScreenXCoordinate (perspectiveX (x_medianline [1] [0], z_medianline [1] [0]))),
		(int) Math.round (convertToScreenYCoordinate (perspectiveY (y_medianline [1] [0], z_medianline [1] [0]))));
	//the final horizontal line of the road
	c.drawLine ((int) Math.round (convertToScreenXCoordinate (perspectiveX (x_medianline [0] [M - 1], z_medianline [0] [M - 1]))),
		(int) Math.round (convertToScreenYCoordinate (perspectiveY (y_medianline [0] [M - 1], z_medianline [0] [M - 1]))),
		(int) Math.round (convertToScreenXCoordinate (perspectiveX (x_medianline [1] [M - 1], z_medianline [1] [M - 1]))),
		(int) Math.round (convertToScreenYCoordinate (perspectiveY (y_medianline [1] [M - 1], z_medianline [1] [M - 1]))));

    }


    //perspective,scale and shift
    public static double perspectiveX (double x, double z)
    {
	if (z < ZV)
	    return x / (1 - z / ZV);
	else
	    return x * INFINITY;
    }


    public static double perspectiveY (double y, double z)
    {
	if (z < ZV)
	    return y / (1 - z / ZV);
	else
	    return y * INFINITY;
    }


    public static double convertToScreenXCoordinate (double x_local)
    {
	return X_SCALE * x_local + X_SHIFT;
    }


    public static double convertToScreenYCoordinate (double y_local)
    {
	return Y_SCALE * y_local + Y_SHIFT;
    }
} // StellatedPolygon class
