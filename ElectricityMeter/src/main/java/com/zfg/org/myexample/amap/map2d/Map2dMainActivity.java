//package com.zfg.org.myexample.amap.map2d;
//
//import android.app.ListActivity;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ListAdapter;
//import android.widget.ListView;
//
//import com.amap.api.maps2d.MapsInitializer;
//import com.zfg.org.myexample.amap.map2d.basic.AbroadMapSwitchActivity;
//import com.zfg.org.myexample.amap.map2d.basic.BaseMapFragmentActivity;
//import com.zfg.org.myexample.amap.map2d.basic.BasicMapActivity;
//import com.zfg.org.myexample.amap.map2d.basic.CameraActivity;
//import com.zfg.org.myexample.amap.map2d.basic.EventsActivity;
//import com.zfg.org.myexample.amap.map2d.basic.LayersActivity;
//import com.zfg.org.myexample.amap.map2d.basic.MapOptionActivity;
//import com.zfg.org.myexample.amap.map2d.basic.OsmMapActivity;
//import com.zfg.org.myexample.amap.map2d.basic.ScreenShotActivity;
//import com.zfg.org.myexample.amap.map2d.basic.UiSettingsActivity;
//import com.zfg.org.myexample.amap.map2d.busline.BuslineActivity;
//import com.zfg.org.myexample.amap.map2d.cloud.CloudActivity;
//import com.zfg.org.myexample.amap.map2d.district.DistrictActivity;
//import com.zfg.org.myexample.amap.map2d.district.DistrictWithBoundaryActivity;
//import com.zfg.org.myexample.amap.map2d.geocoder.GeocoderActivity;
//import com.zfg.org.myexample.amap.map2d.location.LocationSourceActivity;
//import com.zfg.org.myexample.amap.map2d.overlay.CircleActivity;
//import com.zfg.org.myexample.amap.map2d.overlay.GroundOverlayActivity;
//import com.zfg.org.myexample.amap.map2d.overlay.MarkerActivity;
//import com.zfg.org.myexample.amap.map2d.overlay.PolygonActivity;
//import com.zfg.org.myexample.amap.map2d.overlay.PolylineActivity;
//import com.zfg.org.myexample.amap.map2d.poisearch.PoiKeywordSearchActivity;
//import com.zfg.org.myexample.amap.map2d.poisearch.PoiAroundSearchActivity;
//import com.zfg.org.myexample.amap.map2d.poisearch.SubPoiSearchActivity;
//import com.zfg.org.myexample.amap.map2d.route.RouteActivity;
//import com.zfg.org.myexample.amap.map2d.share.ShareActivity;
//import com.zfg.org.myexample.amap.map2d.view.FeatureView;
//import com.zfg.org.myexample.amap.map2d.weather.WeatherSearchActivity;
//import com.zfg.org.myexample.R;
//
///**
// * AMapV1地图demo总汇
// */
//public final class Map2dMainActivity extends ListActivity {
//	private static class DemoDetails {
//		private final int titleId;
//		private final int descriptionId;
//		private final Class<? extends android.app.Activity> activityClass;
//
//		public DemoDetails(int titleId, int descriptionId,
//				Class<? extends android.app.Activity> activityClass) {
//			super();
//			this.titleId = titleId;
//			this.descriptionId = descriptionId;
//			this.activityClass = activityClass;
//		}
//	}
//
//	private static class CustomArrayAdapter extends ArrayAdapter<DemoDetails> {
//		public CustomArrayAdapter(Context context, DemoDetails[] demos) {
//			super(context, R.layout.feature, R.id.title, demos);
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			FeatureView featureView;
//			if (convertView instanceof FeatureView) {
//				featureView = (FeatureView) convertView;
//			} else {
//				featureView = new FeatureView(getContext());
//			}
//			DemoDetails demo = getItem(position);
//			featureView.setTitleId(demo.titleId);
//			featureView.setDescriptionId(demo.descriptionId);
//			return featureView;
//		}
//	}
//
//	private static final DemoDetails[] demos = {
//			new DemoDetails(R.string.basic_map, R.string.basic_description,
//					BasicMapActivity.class),
//			new DemoDetails(R.string.osm_map, R.string.osm_description,
//					OsmMapActivity.class),
//			new DemoDetails(R.string.base_fragment_map,
//					R.string.base_fragment_description,
//					BaseMapFragmentActivity.class),
//			new DemoDetails(R.string.camera_demo, R.string.camera_description,
//					CameraActivity.class),
//			new DemoDetails(R.string.events_demo, R.string.events_description,
//					EventsActivity.class),
//			new DemoDetails(R.string.layers_demo, R.string.layers_description,
//					LayersActivity.class),
//			new DemoDetails(R.string.mapOption_demo,
//					R.string.mapOption_description, MapOptionActivity.class),
//			new DemoDetails(R.string.abroad_demo, R.string.abroad_description,
//					AbroadMapSwitchActivity.class),
//			new DemoDetails(R.string.screenshot_demo,
//					R.string.screenshot_description, ScreenShotActivity.class),
//			new DemoDetails(R.string.uisettings_demo,
//					R.string.uisettings_description, UiSettingsActivity.class),
//			new DemoDetails(R.string.polyline_demo,
//					R.string.polyline_description, PolylineActivity.class),
//			new DemoDetails(R.string.polygon_demo,
//					R.string.polygon_description, PolygonActivity.class),
//			new DemoDetails(R.string.circle_demo, R.string.circle_description,
//					CircleActivity.class),
//			new DemoDetails(R.string.marker_demo, R.string.marker_description,
//					MarkerActivity.class),
//			new DemoDetails(R.string.groundoverlay_demo,
//					R.string.groundoverlay_description,
//					GroundOverlayActivity.class),
//			new DemoDetails(R.string.geocoder_demo,
//					R.string.geocoder_description, GeocoderActivity.class),
//			new DemoDetails(R.string.locationsource_demo,
//					R.string.locationsource_description,
//					LocationSourceActivity.class),
//			new DemoDetails(R.string.poikeywordsearch_demo,
//					R.string.poikeywordsearch_description,
//					PoiKeywordSearchActivity.class),
//			new DemoDetails(R.string.poiaroundsearch_demo,
//					R.string.poiaroundsearch_description,
//					PoiAroundSearchActivity.class),
//			new DemoDetails(R.string.cloud_demo, R.string.cloud_description,
//					CloudActivity.class),
//			new DemoDetails(R.string.busline_demo,
//					R.string.busline_description, BuslineActivity.class),
//			new DemoDetails(R.string.route_demo, R.string.route_description,
//					RouteActivity.class),
//			new DemoDetails(R.string.district_demo,
//					R.string.district_description, DistrictActivity.class),
//			new DemoDetails(R.string.district_boundary_demo,
//					R.string.district_boundary_description,
//					DistrictWithBoundaryActivity.class),
//			new DemoDetails(R.string.share_demo, R.string.share_description,
//					ShareActivity.class),
//			new DemoDetails(R.string.weather_demo,
//				    R.string.weather_description, WeatherSearchActivity.class),
//			new DemoDetails(R.string.subpoi_demo,
//			                R.string.subpoi_description, SubPoiSearchActivity.class)};
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_map2dmain);
//		setTitle("2D地图Demo" + MapsInitializer.getVersion());
//		ListAdapter adapter = new CustomArrayAdapter(
//				this.getApplicationContext(), demos);
//		setListAdapter(adapter);
//
//	}
//
//	@Override
//	public void onBackPressed() {
//		super.onBackPressed();
//		System.exit(0);
//	}
//
//	@Override
//	protected void onListItemClick(ListView l, View v, int position, long id) {
//		DemoDetails demo = (DemoDetails) getListAdapter().getItem(position);
//		startActivity(new Intent(this.getApplicationContext(),
//				demo.activityClass));
//	}
//}
