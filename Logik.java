import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.*;

import javax.imageio.ImageIO;

import com.sun.opengl.util.Screenshot;

import gov.nasa.worldwind.BasicModel;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.AnnotationLayer;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.GlobeAnnotation;
import gov.nasa.worldwind.render.PointPlacemark;
import gov.nasa.worldwind.render.PointPlacemarkAttributes;

public class Logik
{
    
    public static void exportEXIF(String[] imgs, String outfile) {
    	FileWriter fw = null;
    	try
    	{
    	  fw = new FileWriter( outfile );
    	  fw.write("");
    	}
    	catch ( IOException e ) {
    	  System.err.println( "Konnte Datei nicht erstellen" );
    	  return;
    	}
    	for (String img : imgs) {
    		try {
				fw.append(extractEXIF(img).toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	if(fw != null) {
    		try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
    
	private static Position extractEXIF(String img) {
		javaxt.io.Image tmp = new javaxt.io.Image(img);
		double[] gps = tmp.getGPSCoordinate();
		return Position.fromDegrees(gps[1], gps[0]);
	}

	private static double computeZoomFactor(int height, int width) {
		double factor = 100.0/height;
		if(width*factor <= 100) {			return factor;
		}
		else {
			return 100.0/width;
		}
	}
    
    public static Component getMap() {
    	String[] imgs = {"images/essen.jpg", "images/juelich.jpg", "images/nogpsdata1.jpg"};
    	
        WorldWindowGLCanvas wwd = new WorldWindowGLCanvas();
        wwd.setPreferredSize(new java.awt.Dimension(1000, 800));
        wwd.setModel(new BasicModel());
    	
    	for (String img : imgs) {
    		try {
    			Position pos = extractEXIF(img);
    			javaxt.io.Image tmp = new javaxt.io.Image(img);
    			PointPlacemark pm = new PointPlacemark(pos);
    			PointPlacemarkAttributes pa = new PointPlacemarkAttributes();
    			pm.setAttributes(pa);          
    			RenderableLayer layer = new RenderableLayer();
    			layer.addRenderable(pm);

    			AnnotationLayer al = new AnnotationLayer();
    			GlobeAnnotation ga = new GlobeAnnotation("", pos, Font.decode("Arial-BOLD-13"));
    			ga.getAttributes().setImageSource(img);
    			double factor = computeZoomFactor(tmp.getHeight(), tmp.getWidth());
    			ga.getAttributes().setImageScale(factor);
    			ga.getAttributes().setSize(new Dimension((int)(tmp.getWidth()*factor), (int)(tmp.getHeight()*factor)));
    			ga.getAttributes().setImageRepeat(AVKey.REPEAT_NONE);
    			ga.setMaxActiveAltitude(1081941);
    			al.addAnnotation(ga);

    			wwd.getModel().getLayers().add(layer);
    			wwd.getModel().getLayers().add(al);
    			
    		}
    		catch (NullPointerException e) {
    			System.out.println(img + " enthaelt keine oder beschaedigte GPS-Daten");
    		}
    	}
    	
    	return wwd;
    }
    
    public static void exportMap(WorldWindowGLCanvas wwd, String outfile) {
    	wwd.getContext().makeCurrent();
    	java.awt.image.BufferedImage image = Screenshot.readToBufferedImage(wwd.getWidth(), wwd.getHeight());
    	wwd.getContext().release();
    	File out = new File(outfile);
    	try {
			ImageIO.write(image, "jpg", out);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
