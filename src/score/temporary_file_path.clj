(ns score.temporary-file-path
  "This namespace provides functions for creating temporary file paths.

  The algorithm utilised has extracted from Java's implementation of 
  File.createTempFile().

  Java's File.createTempFile complects temporary file path creation and
  temporary file creation and can not be used to generate an unused file
  path without creating and manually deleting a file at that path first.")

;;;; Random number generation
;;; We use a cryptographicly secure random generator to produce one off random
;;; numbers to embed in our temporary file names.

(def secure-random-generator 
  "A 'cryptographicly secure' random number generator."
  (java.security.SecureRandom.))

(defn secure-rand 
  "Returns a cryptographically secure random number."
  [] (.nextLong secure-random-generator))

;;;; Temporary file path generation

(def temp-directory 
  "Path to the temporary folder on ths system."
  (-> (System/getProperties) (.get "java.io.tmpdir")))

(defn temp-file-path 
  "Returns a valid temporary file path with `prefix` and `suffix` attached if
  specified."
  [& prefix suffix]
  (str temp-directory prefix "_" 
       (Math/abs (secure-rand))
       (when suffix (str "." suffix))))
