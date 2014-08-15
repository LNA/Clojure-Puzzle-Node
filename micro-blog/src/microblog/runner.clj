(ns microblog.runner
    (:require [microblog.tweet-parser :refer [sender-content-message  output-tweets 
                                              sender-receiver-message]])
    (:gen-class :main true))

(defn -main []
    (sender-content-message)
    (output-tweets "data/complete_input.txt" :content)
    (sender-receiver-message)
    (output-tweets "data/complete_input.txt" :receivers)
    )
