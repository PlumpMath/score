(defproject score "0.0.1-SNAPSHOT"
  :description "Renders static chunks of MusicXML files using LilyPond."
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/data.xml "0.0.8"]]
  :profiles {:dev {:dependencies [[midje "1.7.0"]]}})
