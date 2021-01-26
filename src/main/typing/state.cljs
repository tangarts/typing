(ns typing.state 
  (:require 
    [reagent.core :as r]
    [typing.utils :refer [strip-text random-text]]
    ))


(def history (r/atom []))

(def text (r/atom (strip-text (random-text))))

(def input (r/atom ""))

(defn default-state []
  "Generates a map with start/end time keys"
  (let [now (.now js/Date)]
    {:stime now
     :etime nil
     :on? false}))

(def timer (r/atom (default-state)))
