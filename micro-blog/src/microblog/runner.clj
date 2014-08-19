(ns microblog.runner
    (:require [microblog.tweet-parser :refer [sender-content-message  output-tweets 
                                              sender-receiver-message first-level-message
                                              output-first-level-connections]])
    (:gen-class :main true))

(defn -main []
    (sender-content-message)
    (output-tweets "data/complete_input.txt" :content)
    (sender-receiver-message)
    (output-tweets "data/complete_input.txt" :receivers)
    (println "---------------------------------------------------------------")
    (first-level-message)
    (output-first-level-connections "data/complete_input.txt")
    )
