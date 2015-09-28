(ns score.lilypond
  "The Lilypond command line tool is used to render fragments of MusicXML
  documents.

  Should performance be a problem we'll look at integrating more tightly with
  LilyPond backend but I doubt that's neccesary."
  (:require [clojure.java.shell :refer [sh]]
            [score.temporary-file-path :refer [temp-file-path]]))

(prn (temp-file-path "score"))

(def lilypond-bin-path "C:\\Program Files (x86)\\LilyPond\\usr\\bin\\")

(defn lilypond-cmd 
  "Constructs shell command for compiling a .ly file to a .svg file."
  [input-ly-path output-svg-path]
  (str lilypond-bin-path 
       "lilypond -fsvg " input-ly-path
       " -o " output-svg-path))

(defn musicxml2ly-cmd
  [input-xml-file output-ly-path])


;(let [cmd (str "C:\\Python27\\python.exe '" lilypond-bin-path "musicxml2ly.py' resources/test.xml")] (sh cmd))

