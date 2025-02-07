package com.ld.infrastructure.apigouv;

import java.util.List;

public class ApiGouvResponse {
    private List<Feature> features;

    public List<Feature> getFeatures() {
        return features;
    }

    public static class Feature {
        private Properties properties;

        public Properties getProperties() {
            return properties;
        }

        public static class Properties {
            private Double score;
            private String name;
            private String postcode;
            private String city;

            // Getters et Setters
            public Double getScore() {
                return score;
            }

            public void setScore(Double score) {
                this.score = score;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPostcode() {
                return postcode;
            }

            public void setPostcode(String postcode) {
                this.postcode = postcode;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }
        }
    }
}