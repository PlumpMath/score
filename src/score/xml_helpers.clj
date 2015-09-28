(ns score.xml-helpers
  "A collection of functions which make working with XML documents easier."
  (:require [clojure.java.io :as io]
            [clojure.xml :refer [parse]]))

(def load-resource
  "Load an XML file from resources folder and parses it to a sequence of XML
  elements."
  (comp xml-seq parse io/file io/resource))

(defn filter-tag 
  "Filters all elements of `tag` from sequence of XML elements."
  [tag xml]
  (filter (comp #{tag} :tag) xml))

(defn filter-attrs
  "Filters all elements with attribute `k` set to `value` from sequence of XML
  elements."
  [k value xml]
  (filter (comp #{value} k :attrs) xml))
