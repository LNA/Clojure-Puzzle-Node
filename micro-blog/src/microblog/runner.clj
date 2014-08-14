(ns microblog.runner
    (:require [microblog.tweet-parser :refer [sender-content-message parse-tweets sender-receiver-message parse-sender-and-receiver]])
    (:gen-class :main true))

(defn -main []
    (sender-content-message)
    (parse-tweets "data/complete_input.txt")
    (sender-receiver-message)
    (parse-sender-and-receiver "data/complete_input.txt")
    )
