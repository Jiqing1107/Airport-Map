package module6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.geo.Location;
import parsing.ParseFeed;
import processing.core.PApplet;

/** An applet that shows airports (and routes)
 * on a world map.  
 * @author WEN Jiqing
 * Implemented based on the framework provided by Adam Setters and 
 * the UC San Diego Intermediate Software Development MOOC team
 * Information about the datasets can be checked from: https://openflights.org/data.html
 */
public class AirportMap extends PApplet {
	
	UnfoldingMap map;
	private List<Marker> airportList;
	List<Marker> routeList;
	
	private CommonMarker lastSelected;
	private CommonMarker lastClicked;
	
	public void setup() {
		// setting up PAppler
		size(1000,600, OPENGL);
		
		// setting up map and default events
		map = new UnfoldingMap(this, 230, 25, 750, 550);
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// get features from airport data
		List<PointFeature> features = ParseFeed.parseAirports(this, "airports.dat");
		
		// list for markers, hashmap for quicker access when matching with routes
		airportList = new ArrayList<Marker>();
		HashMap<Integer, Location> airports = new HashMap<Integer, Location>();
		
		// create markers from features
		for(PointFeature feature : features) {
			AirportMarker m = new AirportMarker(feature);
	
			m.setId(feature.getId());
			m.setRadius(5);
			airportList.add(m);
			
			// put airport in hashmap with OpenFlights unique id for key
			airports.put(Integer.parseInt(feature.getId()), feature.getLocation());
		
		}
		
		
		// parse route data
		List<ShapeFeature> routes = ParseFeed.parseRoutes(this, "routes.dat");
		routeList = new ArrayList<Marker>();
		for(ShapeFeature route : routes) {
			
			// get source and destination airportIds
			int source = Integer.parseInt((String)route.getProperty("source"));
			int dest = Integer.parseInt((String)route.getProperty("destination"));
			
			// get locations for airports on route
			if(airports.containsKey(source) && airports.containsKey(dest)) {
				route.addLocation(airports.get(source));
				route.addLocation(airports.get(dest));
			}
			
			SimpleLinesMarker sl = new SimpleLinesMarker(route.getLocations(), route.getProperties());
		
			//System.out.println(sl.getProperties());
			
			//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
			routeList.add(sl);
		}
		
		
		
		//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
		map.addMarkers(routeList);
		
		map.addMarkers(airportList);
		
	}
	
	public void mouseMoved()
	{
		// clear the last selection
		if (lastSelected != null) {
			lastSelected.setSelected(false);
			lastSelected = null;
		
		}
		selectMarkerIfHover(airportList);
		//loop();
	}
	
	private void selectMarkerIfHover(List<Marker> markers)
	{
		// Abort if there's already a marker selected
		if (lastSelected != null) {
			return;
		}
		
		for (Marker m : markers) 
		{
			CommonMarker marker = (CommonMarker)m;
			if (marker.isInside(map,  mouseX, mouseY)) {
				lastSelected = marker;
				marker.setSelected(true);
				return;
			}
		}
	}
	

	public void mouseClicked()
	{
		if (lastClicked != null) {
			unhideMarkers();
			lastClicked = null;
		}
		else if (lastClicked == null) 
		{
			checkAirportsForClick();
		}
	}
	
	public void checkAirportsForClick() {
		if (lastClicked != null) return;
		for (Marker marker : airportList) {
			if (!marker.isHidden() && marker.isInside(map, mouseX, mouseY)) {
				lastClicked = (CommonMarker) marker;
				((CommonMarker) marker).setClicked(true);
				// Hide all the other earthquakes and hide
				for (Marker mhide : airportList) {
					if (mhide != lastClicked) {
						mhide.setHidden(true);
					}
				}
				for (Marker r : routeList) {
					if (r.getProperty("destination").equals(marker.getId())) {
						r.setHidden(false);
						for (Marker mhide : airportList) {
							if (mhide.getId().equals(r.getProperty("source"))) {
								mhide.setHidden(false);
							}
						}
					}
					else if (r.getProperty("source").equals(marker.getId())) {
						r.setHidden(false);
						for (Marker mhide : airportList) {
							if (mhide.getId().equals(r.getProperty("destination"))) {
								mhide.setHidden(false);
							}
						}
					}
					else r.setHidden(true);
				}
			}
		}
	}
	
	private void unhideMarkers() {
		for(Marker marker : airportList) {
			marker.setHidden(false);
		}
		
		for(Marker r : routeList) {
			r.setHidden(false);
		}
		
	}
	
	private void addKey() {	
		// Remember you can use Processing's graphics methods here
		fill(255, 250, 240);
		
		int xbase = 15;
		int ybase = 25;
		
		rect(xbase, ybase, 200, 180);
		
		fill(0);
		textAlign(LEFT, CENTER);
		textSize(12);
		text("Airports Key", xbase+60, ybase+25);
		
		text("Altitude range: 0 ~ 100", xbase+30, ybase+60);
		text("Altitude range: 100 ~ 1000", xbase+30, ybase+80);
		text("Altitude range: 1000 +", xbase+30, ybase+100);
		
		fill(255, 204, 204);
		ellipse(xbase+20, ybase+60, 5, 5);
		fill(255, 102, 102);
		ellipse(xbase+20, ybase+80, 5, 5);
		fill(204, 0, 0);
		ellipse(xbase+20, ybase+100, 5, 5);
		
		fill(102, 102, 102);
		line(xbase+50, ybase+124, xbase+80, ybase+124);
		fill(0);
		text("routes", xbase+90, ybase+120);
		
		fill(51, 51, 255);
		ellipse(xbase+40, ybase+140, 10, 10);
		fill(0);
		text("Clicked airport", xbase+50, ybase+140);
		
	}
	
	public void draw() {
		background(0);
		map.draw();
		addKey();
	}
	

}
