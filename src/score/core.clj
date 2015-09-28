(ns score.core
  (:require [score.xml-helpers :refer [filter-tag filter-attrs load-resource]] 
            [clojure.string :as string]
            [clojure.data.xml :as xml]
            [clojure.pprint :refer [pprint]]))

(def score (load-resource "test.xml"))

(defn score-part-id->title 
  "Returns a map of score part ID's to score part titles desribed in score."
  [score]
  (->> (filter-tag :score-part score)
       (map (juxt (comp :id :attrs)                      ; pull out id...
                  (comp first :content first :content))) ; and title
       (into {})))

(defn score-part
  "Returns the score-part with `part-id`."
  [part-id score]
  (->> (filter-tag :score-part score)
       (filter-attrs :id part-id)
       first))

(defn xml-score-part->map
  [score-part-xml]
  (->> {:id              #(-> % :attrs :id)
        :name            #(-> % :content first :content first string/trim)
        :instrument-name #(-> % :content second :content first :content first string/trim)}
       (map (fn [[k f]] [k (f score-part-xml)]))
       (into {})))
  
(pprint (xml-score-part->map (score-part "P1" score))) 

(defn part
  "Returns the part with `part-id`."
  [part-id score]
  (->> (filter-tag :part score)
       (filter-attrs :id part-id)
       first))

(def measures 
  "Returns the measures used in a part."
  :content)

(defn measure 
  "Returns measure `n` from list of measures." 
  [measures n]
  (filter-attrs :number (str n) measures)) 

(defn measure-range
  "Returns a range of measures between `start-n` and `finish-n` from measures."
  [start-n finish-n measures]
  (map (partial measure measures) (range start-n (inc finish-n))))

(defn part-measure-range
  "Returns range of measures from `part-id` in a MusicXML file."
  [part-id start-n finish-n score]
  (->> score (part part-id) measures (measure-range 1 5)))

(defn construct-score-fragment 
  [score-part measures] 
  (let [part-id (-> score-part :attrs :id)] 
    (xml/sexp-as-element 
      [:score-partwise {}
       [:part-list {} score-part]
       (concat [:part {:id part-id}] measures)])))

(xml/emit (java.io.File/createTempFile file-name ".xml")
          (construct-score-fragment (score-part "P1" score) 
                                    (part-measure-range "P1" 1 5 score)))
