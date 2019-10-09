package module6;

import java.util.List;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import processing.core.PConstants;
import processing.core.PGraphics;

/** 
 * A class to represent AirportMarkers on a world map.
 *   
 * @author WEN Jiqing
 * Implemented based on the framework provided by Adam Setters 
 * and the UC San Diego Intermediate Software Development MOOC team
 *
 */
public class AirportMarker extends CommonMarker {
	public static List<SimpleLinesMarker> routes;
	
	public AirportMarker(Feature city) {
		super(((PointFeature)city).getLocation(), city.getProperties());
	
	}
	
	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		if(getClicked()) {
			colorClicked(pg);
			pg.ellipse(x, y, 10, 10);
		}
		else {
			colorUnClicked(pg);
			pg.ellipse(x, y, 5, 5);
		}		
	}
	
	private void colorUnClicked(PGraphics pg) {
		int alt = getAltitude();
		if (alt >= 1000) {
			pg.fill(204, 0, 0);
		}
		else if ((alt >= 100) && (alt < 1000)) {
			pg.fill(255, 102, 102);
		}
		else {
			pg.fill(255, 204, 204);
		}
	}
	
	private void colorClicked(PGraphics pg) {
		pg.fill(51, 51, 255);
	}

	@Override
	public void showTitle(PGraphics pg, float x, float y) {
		 // show rectangle with title
		String name = getName();
		pg.pushStyle();
		
		pg.rectMode(PConstants.CORNER);
		
		pg.stroke(110);
		pg.fill(255,255,255);
		pg.rect(x, y + 15, pg.textWidth(name) +6, 18, 5);
		
		pg.textAlign(PConstants.LEFT, PConstants.TOP);
		pg.fill(0);
		pg.text(name, x + 3 , y +18);
		
		
		pg.popStyle();
		// show routes
		
		
	}
	
	public String getName() {
		return (String) getProperty("name");
	}
	
	public String getCity() {
		return (String) getProperty("city");
	}
	
	public String getCountry() {
		return (String) getProperty("country");
	}
 
	public int getAltitude() {
		return Integer.parseInt(getProperty("altitude").toString());
	}
	
	public String getCode() {
		return (String) getProperty("code");
	}
	
}
