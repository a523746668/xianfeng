package com.qingyii.hxt.base.mvp.model.entity;

import java.util.List;

/**
 * Created by xubo on 2017/7/4.
 */

public class MyLocations {
    /**
     * location : [{"lat":28.192253080405344,"lng":113.03056240081787,"text":"万家丽广场"}]
     * map : {"lat":28.19171439485025,"lng":113.03929864556885,"zoom":13}
     */

    private MapBean map;
    private List<LocationBean> location;

    public MapBean getMap() {
        return map;
    }

    public void setMap(MapBean map) {
        this.map = map;
    }

    public List<LocationBean> getLocation() {
        return location;
    }

    public void setLocation(List<LocationBean> location) {
        this.location = location;
    }

    public static class MapBean {
        /**
         * lat : 28.19171439485025
         * lng : 113.03929864556885
         * zoom : 13
         */

        private double lat;
        private double lng;
        private int zoom;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public int getZoom() {
            return zoom;
        }

        public void setZoom(int zoom) {
            this.zoom = zoom;
        }
    }

    public static class LocationBean {
        /**
         * lat : 28.192253080405344
         * lng : 113.03056240081787
         * text : 万家丽广场
         */

        private double lat;
        private double lng;
        private String text;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String toString(){
            return lat+","+lng;
        }
    }
}
