(ns microblog.ui
 (:require
   [clojure.string :refer [replace]]
   [clojure.java.io :as io]
   [clojure.set])
 (:refer-clojure :exclude [replace]))


(defn content-message []
  (println "Here is a list of senders, receivers, and their content: "))
