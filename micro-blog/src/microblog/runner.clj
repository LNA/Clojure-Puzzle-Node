(ns microblog.runner
    (:require [microblog.tweet-parser :refer [name-of-sender content]])
    (:gen-class :main true))

(defn -main []
    (name-of-sender "data/complete_input.txt")
    (content "data/complete_input.txt")
    )
